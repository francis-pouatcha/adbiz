'use strict';

angular.module("adaptmt")

.controller('aptAptmtRepportListController',['$scope','genericResource', '$translate', 'aptAptmtRepportsService','aptAptmtsService','$location','$rootScope','$routeParams',
                                          function($scope,genericResource, $translate, aptAptmtRepportsService,aptAptmtsService,$location,$rootScope,$routeParams){

	var self = this;

	self.error = [];
	self.searchInput = {};
	self.aptAptmtRepports = [];
	self.totalItems = 0;
	self.aptAptmtRepportsearchResults = {};
	self.itemPerPage=25;
	self.currentPage = 1;
	self.maxSize = 5 ;
	self.handleSearchRequestEvent = handleSearchRequestEvent;
	self.handlePrintRequestEvent = handlePrintRequestEvent;
	self.paginate = paginate;
	self.logins = [];
	self.aptAptmts = {};
	self.aptAptmt = {};
	
	
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
		
		
		
		
		aptAptmtRepportsService.loadAptAptmtRepports(self.searchInput).then(function(entitySearchResult) {
			self.aptAptmtRepports = entitySearchResult.resultList;
			self.totalItems = entitySearchResult.count ;
			
		});
		
	

		aptAptmtsService.loadAptAptmts(self.searchInput).then(function(entitySearchResult) {
			self.aptAptmts = entitySearchResult.resultList;
		});
		
		self.handleSearchRequestEvent();
		
	};

	init();
	
	
	

	function findByLike(searchInput){
		aptAptmtRepportsService.findAptAptmtRepports(searchInput).then(function(entitySearchResult) {
			self.aptAptmtRepports = entitySearchResult.resultList;
			self.totalItems = entitySearchResult.count ;
		});
	}

	function processSearchInput(){
		var fileName = [];
		if(self.searchInput.entity.aptmtIdentify){
			fileName.push('aptmtIdentify') ;
		}
		if(self.searchInput.entity.title){
			fileName.push('title') ;
		}
		if(self.searchInput.entity.description){
			fileName.push('description') ;
		}
		if(self.searchInput.entity.createdUserId){
			fileName.push('createUserId') ;
		}
		if(self.searchInput.entity.closedUserId){
			fileName.push('created');
		}
		self.searchInput.fieldNames = fileName ;
		return self.searchInput ;
	};

	function  handleSearchRequestEvent(){
		self.searchInput.entity.aptmtIdentify = self.aptAptmt.aptmtnNbr;
		processSearchInput();
		findByLike(self.searchInput);
	};

	function paginate(){
		self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
		self.searchInput.max = self.itemPerPage ;
		findByLike(self.searchInput);
	};


	function handlePrintRequestEvent(){

	}

}]);
