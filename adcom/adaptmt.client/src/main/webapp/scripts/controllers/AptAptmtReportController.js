(function () {
	'use strict';
	angular.module('adaptmt').controller('aptAptmtReportController',aptAptmtReportController);

	aptAptmtReportController.$inject = ['$scope', 'aptAptmtRepportsService','$location','aptAptmtsService','$routeParams'];

	function aptAptmtReportController($scope,aptAptmtRepportsService, $location,aptAptmtsService,$routeParams){
		var self = this ;
		self.AptAptmtRepport = {};
		self.Report = Report;
		self.error = "";
		self.aptAptmt = {};
		self.aptAptmts = {};

		function show(){

			var identif = $routeParams.id;

			aptAptmtsService.loadAptAptmt(identif).then(function(result){

				self.aptAptmt = result;
				self.aptAptmts = result;

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

			self.AptAptmtRepport.title = "REPORT DU RENDEZ-VOUS";

		};

		init();

		function Report(){
		
				if (self.AptAptmtRepport.aptmtIdentify == null){
					self.AptAptmtRepport.aptmtIdentify = self.aptAptmt.aptmtnNbr;
					self.aptAptmts.parentIdentify = self.aptAptmt.aptmtnNbr;
					
				}
				aptAptmtsService.cancel(self.aptAptmt).then(function(result){

				},function(error){
					self.error = error;
				});
				aptAptmtsService.create(self.aptAptmts).then(function(result){

				},function(error){
					self.error = error;
				});
				aptAptmtRepportsService.create(self.AptAptmtRepport).then(function(result){
					$location.path('/aptaptmtRepport/show/'+result.id);
				},function(error){
					self.error = error;
				});

			

		};


	};
	
})();