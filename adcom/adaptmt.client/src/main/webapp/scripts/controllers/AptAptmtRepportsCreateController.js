(function () {
	'use strict';
	angular.module('adaptmt').controller('aptAptmtRepportCreateController',aptAptmtRepportCreateController);

	aptAptmtRepportCreateController.$inject = ['$scope', 'aptAptmtRepportsService','$location','aptAptmtsService','$routeParams'];

	function aptAptmtRepportCreateController($scope,aptAptmtRepportsService, $location,aptAptmtsService,$routeParams){
		var self = this ;
		self.AptAptmtRepport = {};
		self.create = create;
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

		};

		init();

		function create(){
			if (self.AptAptmtRepport.aptmtIdentify == null){
			self.AptAptmtRepport.aptmtIdentify = self.aptAptmt.aptmtnNbr;
		}
		aptAptmtRepportsService.create(self.AptAptmtRepport).then(function(result){
			$location.path('/aptaptmtRepport/show/'+result.id);
		},function(error){
			self.error = error;
		});

	};


};



})();