'use strict';

angular.module("adaptmt")

.controller('aptAptmtShowController',['$scope', '$rootScope','genericResource', '$translate', 'aptAptmtsService','$location','$routeParams', '$log', 'bpBnsPtnrState', 'bpBnsPtnrUtils', '$filter', 'aptAptmtBsPtnrService','aptAptmtContactsService','$q','aptAptmtBsPtnrPresentService',
                                      function($scope, $rootScope,genericResource, $translate, aptAptmtsService,$location,$routeParams, $log, bpBnsPtnrState, bpBnsPtnrUtils, $filter, aptAptmtBsPtnrService,aptAptmtContactsService,$q,aptAptmtBsPtnrPresentService){

	var self = this;
	self.aptAptmt = {};
	self.AptAptmtRepport = {};
	self.itemPerPage = 10 ;
	self.actPartner = false;
	self.actContact = false;
	self.actParticipant = false;
	self.isIntern = true;
	self.actAppointment = true;
	self.isAssociate = false;
	self.newParticipant = newParticipant;
	self.newContact = newContact;
	self.partner = {};
	self.aptAptmtContactConfirm = [];
	self.contacts = {};
	self.partner = {};
	self.aptAptmts = {};
	self.aptAptmtContact = {};
	self.aptAptmtContactConfirm = [];
	self.aptAptmtContactRemove = [];
	self.contacts = {};
	self.searchInput = {};
	self.error = {};
	self.searchInput = {};
	self.error = {};
	self.show = show;
	self.associate = associate;
	self.createOtherAptmt = createOtherAptmt;
	self.previous = previous;
	self.next = next;
	self.bnsPtnrIdentif = "";
	self.aptAptmtBsPtnr = {};
	self.bnsptnrs = [];
	self.controlIfCancelable = controlIfCancelable;
	self.controlIfCloturable = controlIfCloturable;
	self.createPartner = createPartner;
	self.remove = remove;
	self.add = add;
	$scope.aptAptmtsService = aptAptmtsService;
	$scope.aptAptmtBsPtnrService = aptAptmtBsPtnrService;
	$scope.aptAptmtContactsService = aptAptmtContactsService;
	self.logins = [];
	self.loginConnected = [];
	self.createContact = createContact;
	var currentDate;
	self.aptAptmtPartner = {
			partner : "",
			fullname : "",
			bnsptntIdentify : "",
			title : "",
			personalMobile : "",
			professionalMobile : ""
	}
	self.id = $routeParams.id;
	self.contactConfirm = [];
	self.OrgUnit = [];
	self.logins = [];
	self.loginsConfirm = [];
	self.searchEntity = {};
	self.selectedLogin = {} ;
	self.paginate = paginate;
	self.orgUnits = [];
	self.organisation = '';
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
	self.contactConfirm = [];
	self.contactPresentConfirm = [];
	self.pass = pass;
	self.close = close;
	self.present = present;
	self.absent = absent;
	self.isUnavailable = isUnavailable;
	self.previousT = previousT;
	$scope.error = "";
	$scope.bpBnsPtnrUtils = bpBnsPtnrUtils;
	$scope.loadCountryNames = bpBnsPtnrUtils.loadCountryNames;
	$scope.bpBnsPtnr = {};
	$scope.isLogin = false;
	function show(){

		var identif = $routeParams.id;


		aptAptmtsService.loadAptAptmt(identif).then(function(result){

			self.aptAptmt = result;
			self.searchInput.entity.aptaptmtIdentify = self.aptAptmt.aptmtnNbr;
			self.AptAptmtRepport.aptmtIdentify = self.aptAptmt.aptmtnNbr;
			self.aptAptmts.parentIdentify = self.aptAptmt.aptmtnNbr;

		});





		var fileName = [];

		fileName.push(self.aptAptmt.aptmtnNbr);

		self.searchInput.fieldNames = fileName ;

		aptAptmtsService.getCurrentOrgUnit().then(
				function(result) {
					console.log(result);
					self.OrgUnit.push(result);
					for(var i in self.OrgUnit){
						self.organisation = self.OrgUnit[i].fullName;
					}
					console.log(self.OrgUnit);
				});

	};

	function createPartner() {
		console.log($scope.bpBnsPtnr);
		genericResource
		.create(bpBnsPtnrUtils.urlBase,
				$scope.bpBnsPtnr)
				.success(
						function(bpBnsPtnr) {
							// console.log(" old created
							// buiness partner identif :
							// " + self.bnsPtnrIdentif +
							// " for appointment : " +
							// self.aptAptmt.title);
							self.bnsPtnrIdentif = bpBnsPtnr.identif;
							console
							.log(" new business partner itentif is : "
									+ self.bnsPtnrIdentif
									+ " for appointment : "
									+ self.aptAptmt.title);
						}).error(function(error) {
							$scope.error = error;
							console.log($scope.error);
						});
	}



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

//		aptAptmtContactsService.loadAptAptmtContact()
	}

	function showAptmtContactPresent(){
		self.contactPresentConfirm = []; 

		aptAptmtBsPtnrPresentService.loadAptAptmtBsPtnrPresent($routeParams.id).then(function(result){
			console.log(result);
			for(var i in result.resultList){
				aptAptmtContactsService.loadAptAptmtContact(result.resultList[i].contactnNbr).then(function(resultTwo){
					for(var j in resultTwo.resultList){
						self.contactPresentConfirm.push(resultTwo.resultList[j]);
					}
				},function(error){
					console.log("error during aptAptmtContactsService.loadAptAptmtContact")
				});
			}
		},function(error){
			console.log("error during aptAptmtBsPtnrService.loadAptAptmtBsptnr");
		});

//		aptAptmtContactsService.loadAptAptmtContact()
	}




	/*	var translateChangeSuccessHdl = $rootScope.$on(
			'$translateChangeSuccess', function() {
				bpBnsPtnrUtils.translate();
			});

	$scope.$on('$destroy', function() {
		translateChangeSuccessHdl();
	});  */


	function init(){
		//	if (bpBnsPtnrState.resultHandler.hasEntities())
		//		return;
		findByLike($scope.searchInput);
		show();
		//	fetchBnsPtnrs();
		controlIfCancelable();
		controlIfCloturable();

		showAptmtContact();
		showAptmtContactPresent();

		self.AptAptmtRepport.title = "CLOTURE DU RENDEZ-VOUS";

	}

	function findByLike(searchInput) {
		genericResource
		.findByLike(bpBnsPtnrUtils.urlBase,
				searchInput)
				.success(
						function(entitySearchResult) {
							// store search
							bpBnsPtnrState.resultHandler
							.searchResult(entitySearchResult);
						}).error(function(error) {
							$scope.error = error;
						});
	}

	init();

	function add() {

		if (self.aptAptmtBsPtnr.contactnNbr == undefined) {
			console.log("rien n'est définit");
		} else {

			var idIncome = self.aptAptmtBsPtnr.contactnNbr.id;
			var test = false;
			var found = $filter('filter')(self.contactConfirm, {id: idIncome}, true);
			if (found.length) {
				test = true;
			}
			if(test){
				console.log('this contact already exist in this list');
				alert('this contact already exist in this list');

			}else{
				console.log(self.aptAptmtBsPtnr.contactnNbr);
				self.aptAptmtContact.aptmtIdentify = $routeParams.id;
				self.aptAptmtContact.contactnNbr = self.aptAptmtBsPtnr.contactnNbr.id;
				console.log(self.aptAptmtContact);
				//		self.aptAptmtContactConfirm.push(self.aptAptmtContact);
				createAptmtBsPtnr(self.aptAptmtContact);
				//		console.log(self.aptAptmtContactConfirm);
				self.aptAptmtContact = {};
				setTimeout(showAptmtContact,2500);
			}
		}

	}

	function createAptmtBsPtnr(liste){
		console.log(liste);
		aptAptmtBsPtnrService.create(liste).then(function(result){
			console.log(liste);
			console.log("add the participation contact" + liste);
		},function(error){
			console.log("error when add participants");
		});
	}

	function createAptmtBsPtnrPresent(liste){
		console.log(liste);
		aptAptmtBsPtnrPresentService.create(liste).then(function(result){
			console.log(liste);
			console.log("add the present contact" + liste);
		},function(error){
			console.log("error when add present participants");
		});
	}

	function removeAptmtBsPtnr(liste){
		console.log(liste);

		console.log("remove the participation contact" + liste.id);
		console.log(liste.id);
		aptAptmtBsPtnrService.deleteById(liste.id).then(function(result){
			console.log("remove the participation contact" + liste);

			//	self.aptAptmtContactRemove = [];

			console.log('voici ma liste vide');
			console.log(liste);
		},function(error){
			console.log("error when remove participants");
		});
	}

	function removeAptmtBsPtnrPresent(liste){
		console.log(liste);
		console.log("remove the participation contact" + liste.id);
		console.log(liste.id);
		aptAptmtBsPtnrPresentService.deleteById(liste.id).then(function(result){
			console.log("remove the participation contact" + liste);

			//	self.aptAptmtContactRemove = [];

			console.log('voici ma liste vide');
			console.log(liste);
		},function(error){
			console.log("error when remove participants");
		});
	}



	function createContact() {
		console.log(self.partner.fullName);
		self.contacts.bnsptntIdentify = self.partner.fullName;
		if(self.partner == self.organisation ){
			console.log(self.contacts);
			self.contacts.loginIdentify = self.contacts.fullname.loginName;
			self.contacts.fullname = self.contacts.fullname.fullName;
			self.contacts.bnsptntIdentify  = self.partner;
			
		}
		console.log(self.contacts);
		aptAptmtContactsService.create(self.contacts)
		.then(function(result) {
			console.log('contact have had created');
		}, function(error) {
			$scope.error = error;
			console.log("error for create contact");
		});

	}

	function remove(index) {

		console.log(self.contactConfirm[index].id);
		aptAptmtBsPtnrService.loadAptAptmtContact($routeParams.id,self.contactConfirm[index].id).then(function(result){
			console.log(self.contactConfirm[index].id);
			for (var i in result.resultList){
				if(result.resultList[i].contactnNbr == self.contactConfirm[index].id){
					removeAptmtBsPtnr(result.resultList[i]);
				}
			}
			console.log(self.aptAptmtContactRemove);
		},function(error){

			console.log('error for search the entity  to remove')

		});



		setTimeout(showAptmtContact,2500);


	}



	function newParticipant(){
		self.actAppointment = false;
		self.actParticipant = true;
	}

	function newContact(){
		self.actAppointment = false;
		self.actContact = true;
	}



	function controlIfCancelable(){
		if (self.aptAptmt.status != "FORTHCOMMING"){
			return true;
		}
		else{
			return false;
		}
	}

	function controlIfCloturable(){
		if (self.aptAptmt.status != "ONGOING"){
			return true;
		}
		else{
			return false;
		}
	}

	function previous(){
		self.error = "";
		aptAptmtsService.previousAptAptmt(self.aptAptmt.id).then(function(result){
			self.aptAptmt = result;
			init();
		},function(error){
			self.error = error;
		})

	}

	function next(){
		self.error = "";
		aptAptmtsService.nextAptAptmt(self.aptAptmt.id).then(function(result){
			self.aptAptmt = result;
			init();
		},function(error){
			self.error = error;
		})

	}

	function closed(){	



	};

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

				//$location.path('#/aptaptmt/show/'+ self.aptAptmt.id);

			},function(error){
				self.error = error;
			});

		}

	}

	$scope.onPartnerSelected = function(item, model,
			label) {
		if (item.ptnrType == "INDIVIDUAL") {
			self.contacts.fullname = self.partner.fullName;
		}
	};


	function associate(){
	//	$scope.isLogin = true;
		if(self.isAssociate){
			self.partner = "";
			self.isAssociate = false;
		}
		else{
		console.log(self.OrgUnit);
		console.log(self.organisation);
		self.partner = self.organisation;
		self.isAssociate = true;
		console.log(self.isAssociate);
		}
	}
	
	$scope.isLogin = function(){
		return self.partner == self.organisation;
	}

	function createOtherAptmt(){
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

	function previousT(){
		self.disTab2 = true;
		self.disTab1 = false;
		self.actTab2 = false;
		self.actTab1 = true;
	}

	function paginate(){
		self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
		self.searchInput.max = self.itemPerPage ;
		findByLike(self.searchInput);
	};

	function present() {
		console.log(self.contactConfirm);
		if (self.aptAptmtBsPtnrPresent.contactnNbr == undefined) {
			console.log("rien n'est définit");
		} else {

			var idIncome = self.aptAptmtBsPtnrPresent.contactnNbr.id;
			var test, testPresent = false;
			var found = $filter('filter')(self.contactConfirm, {id: idIncome}, true);
			var foundPresent = $filter('filter')(self.contactConfirm, {id: idIncome}, true);

			if (found.length) {
				test = true;
			}
			if (foundPresent.length) {
				testPresent = true;
			}
			if(test){

				if(testPresent){
					console.log(self.aptAptmtBsPtnrPresent.contactnNbr);
					self.aptAptmtContact.aptmtIdentify = $routeParams.id;
					self.aptAptmtContact.contactnNbr = self.aptAptmtBsPtnrPresent.contactnNbr.id;

					console.log(self.aptAptmtContact);

					//		self.aptAptmtContactConfirm.push(self.aptAptmtContact);
					createAptmtBsPtnrPresent(self.aptAptmtContact);

					//		console.log(self.aptAptmtContactConfirm);

					self.aptAptmtContact = {};
					//	self.contactPresentConfirm.push(self.aptAptmtBsPtnrPresent.contactnNbr);
					setTimeout(showAptmtContactPresent,2500);

				}else{
					console.log('this contact is already exist in present contact');
					alert('this contact is already exist in present contact');
				}
			}else{

				console.log('this contact dont call for this appointment');
				alert('this contact dont call for this appointment');

			}
		}

	}

	function absent(index) {

		console.log(self.contactPresentConfirm[index].id);
		aptAptmtBsPtnrPresentService.loadAptAptmtContact($routeParams.id,self.contactPresentConfirm[index].id).then(function(result){
			console.log(self.contactPresentConfirm[index].id);
			for (var i in result.resultList){
				if(result.resultList[i].contactnNbr == self.contactPresentConfirm[index].id){
					removeAptmtBsPtnrPresent(result.resultList[i]);
				}
			}

		},function(error){

			console.log('error for search the entity  to remove')

		});
		setTimeout(showAptmtContactPresent,2500);
	}
	
	function isUnavailable(index){
		if (self.contactConfirm[index].availability == "AVAILABLE"){
			return false;
		}else{
			return true;
		}
	}
}]);