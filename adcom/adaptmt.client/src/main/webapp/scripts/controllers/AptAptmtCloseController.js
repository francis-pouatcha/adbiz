(function () {
	'use strict';
	angular.module('adaptmt').controller('aptAptmtCloseController',aptAptmtCloseController);

	aptAptmtCloseController.$inject = ['$scope', 'aptAptmtRepportsService','$location','aptAptmtsService','aptAptmtBsPtnrService','aptAptmtContactsService','$routeParams','loginService','$filter'];

	function aptAptmtCloseController($scope,aptAptmtRepportsService, $location,aptAptmtsService,aptAptmtBsPtnrService,aptAptmtContactsService,$routeParams,loginService,$filter){

		var self = this ;
		self.AptAptmtRepport = {};
		self.close = close;
		self.aptAptmt = {};
		self.error = "";
		self.aptAptmt = {};
		self.aptAptmts = {};
		self.disTab1 = false;
		self.disTab2 = true;
		self.disTab3 = true;
		self.actTab1 = true;
		self.actTab2 = false;
		self.actTab3 = false;
		self.create = create;
		self.logins = [];
		self.totalItems ;
		self.itemPerPage=25;
		self.currentPage = 1;
		self.maxSize = 5 ;
		self.logins = [];
		self.loginsConfirm = [];
		self.searchEntity = {};
		self.selectedLogin = {} ;
		self.paginate = paginate;
		self.orgUnits = [];
		self.searchInputTwo = {
				entity:{},
				fieldNames:[],
				start:0,
				max:self.itemPerPage
		};
		self.searchInput = {
				entity:{},
				fieldNames:[],
				start:0,
				max:self.itemPerPage
		};
		self.addPresent = addPresent;
		self.contactConfirm = [];
		self.pass = pass;
		self.previous = previous;
		
		function showAptmtContact(){
			self.contactConfirm = []; 
			aptAptmtBsPtnrService.loadAptAptmtBsptnr($routeParams.id).then(function(result){
				console.log(result);
				for(var i in result.resultList){
					aptAptmtContactsService.loadAptAptmtContact(result.resultList[i].contactnNbr).then(function(resultTwo){
						for(var j in resultTwo.resultList){
							self.contactConfirm.push(resultTwo.resultList[j]);
						}
					},function(error){
						console.log("error during aptAptmtContactsService.loadAptAptmtContact")
					});
				}
			},function(error){
				console.log("error during aptAptmtBsPtnrService.loadAptAptmtBsptnr");
			});

			aptAptmtContactsService.loadAptAptmtContact()
		}


		function showAptLog(){
			var r = $.Deferred();
			console.log('-----------------FONCTION SHOWAPTLOG---------------------------');
			aptAptmtLoginsService.findAptAptmtLogins(self.searchInput).then(function(entitySearchResult) {
				var donne = entitySearchResult.resultList;
				for(var i in donne){
					console.log("je suis dans la boucle for ");
					console.log(donne[i]);
					if (donne[i].aptmtIdentify == $routeParams.id){
						
						    console.log(self.logins);
							for(var j in self.logins){
								console.log("je suis dans ");
								console.log(self.logins[j]);

								if (self.logins[j].id == donne[i].loginIdentify){
									console.log("Condition bonne");
									self.loginsConfirm.push(self.logins[j]);
									console.log(self.loginConfirm);
								}else{
									console.log("Condition mauvaise");
								}
							}
							console.log(self.loginsConfirm);
							
					
					}
					else{
						console.log("la condition  if ne marche pas");
					}

				}


				r.resolve();
			});




			console.log("-----------------------fin de showAptLog--------------------------------");
			return r;
		}
		
		function processsearchInputTwo(identif){
			var fileName = [];
			fileName.push(identif) ;

			self.searchInput.fieldNames = fileName ;
			return self.searchInput ;
		};

		function addPresent(){
			console.log("je suis dans la fonction");

			for(var i in self.logins){
				console.log("je suis dans la premiere boucle");
				console.log(" login with id : " +  self.logins[i].id + " checked ? : " + self.logins[i].checkOn);
				var idIncome = self.logins[i].id;
				if(self.logins[i].checkOn == true){

					var found = $filter('filter')(self.loginsConfirm, {id: idIncome}, true);
					if (found.length) {
						console.log("already exist in array to send to the server !");
					} else {
						self.loginsConfirm.push(self.logins[i]);
					}

				}
			};

			for(var j in self.loginsConfirm){
				console.log("je suis dans la seconde boucle");
				var entity = {aptmtIdentify: $routeParams.id, loginIdentify: self.loginsConfirm[j].id};
				sendToServer(entity);
			}

			console.log(self.loginsConfirm);

			self.disTab1 = true;
			self.disTab2 = false;
			self.actTab1 = false;
			self.actTab2 = true;
			self.aptAptmts.parentIdentify = self.aptAptmt.aptmtnNbr;
			console.log("je sort de la fonction");
		};

		function sendToServer(entity){
			aptAptmtRepportLoginsService.create(entity)
			.then(function(result){
				console.log("entity : " + result + " has send successfully");
			},function(error){
				console.log(error);
			});
		}

		function create(){
			aptAptmtsService.create(self.aptAptmts)
			.then(function(result){

			},function(error){
				$scope.error = error;
			});

			pass();

		};

		function pass(){

			self.disTab2 = true;
			self.disTab3 = false;
			self.actTab2 = false;
			self.actTab3 = true;

		}
		
		function previous(){
			self.disTab2 = true;
			self.disTab1 = false;
			self.actTab2 = false;
			self.actTab1 = true;
		}

		function close(){
			if (self.aptAptmt.status != "FORTHCOMMING"){

				self.error = error;
			}
			else{
				if (self.AptAptmtRepport.aptmtIdentify == null){
					self.AptAptmtRepport.aptmtIdentify = $routeParams.id;
				}
				
				aptAptmtsService.close(self.aptAptmt).then(function(result){

				},function(error){
					self.error = error;
				});
				aptAptmtRepportsService.create(self.AptAptmtRepport).then(function(result){

					$location.path('#/aptaptmt/show/'+ self.aptAptmt.id);

				},function(error){
					self.error = error;
				});

			}

		}

		function show(){

			var identif = $routeParams.id;

			aptAptmtsService.loadAptAptmt(identif).then(function(result){

				self.aptAptmt = result;
				self.searchInput.entity.aptaptmtIdentify = self.aptAptmt.aptmtnNbr;
				self.AptAptmtRepport.aptmtIdentify = self.aptAptmt.aptmtnNbr;

			});





			var fileName = [];

			fileName.push(self.aptAptmt.aptmtnNbr);

			self.searchInput.fieldNames = fileName ;

			



		};

		function tab(){


			$scope.alertMe = function() {

			};
		}

		function init(){
			show(); 
			
			tab();
			self.searchInput = {
					entity:{},
					fieldNames:[],
					start:0,
					max:$scope.itemPerPage
			}

			aptAptmtsService.loadAptAptmts(self.searchInput).then(function(entitySearchResult) {
				self.aptAptmts = entitySearchResult.resultList;

			});

			self.AptAptmtRepport.title = "CLOTURE DU RENDEZ-VOUS";
			
			showAptmtContact();


		};

		init();




	};

	function paginate(){
		self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
		self.searchInput.max = self.itemPerPage ;
		findByLike(self.searchInput);
	};



})();