(function () {
	'use strict';
	angular.module('adaptmt').controller('aptAptmtCancelController',aptAptmtCancelController);

	aptAptmtCancelController.$inject = ['$scope', 'aptAptmtRepportsService','$location','aptAptmtsService','$routeParams'];

	function aptAptmtCancelController($scope,aptAptmtRepportsService, $location,aptAptmtsService,$routeParams){
		var self = this ;
		self.AptAptmtRepport = {};
		self.cancel = cancel;
		self.error = "";
		self.aptAptmt = {};
		self.aptAptmts = {};

		function show(){

			var identif = $routeParams.id;

			aptAptmtsService.loadAptAptmt(identif).then(function(result){

				self.aptAptmt = result;

			})

		};

		function init(){
			show();   
			self.searchInput = {
					entity:{},
					fieldNames:[],
					start:0,
					max:$scope.itemPerPage
			}

			aptAptmtsService.loadAptAptmts(self.searchInput).then(function(entitySearchResult) {
				self.aptAptmts = entitySearchResult.resultList;

			});

			self.AptAptmtRepport.title = "ANNULATION DU RENDEZ-VOUS";

		};

		init();

		function cancel(){
			if (self.aptAptmt.status != "FORTHCOMMING"){

				self.error = error;
			}
			else{
				if (self.AptAptmtRepport.aptmtIdentify == null){
					self.AptAptmtRepport.aptmtIdentify = self.aptAptmt.aptmtnNbr;
				}
				aptAptmtsService.cancel(self.aptAptmt).then(function(result){

				},function(error){
					self.error = error;
				});
				aptAptmtRepportsService.create(self.AptAptmtRepport).then(function(result){
					$location.path('/aptaptmtRepport/show/'+result.id);
				},function(error){
					self.error = error;
				});

			}

		};


	};



})();