'use strict';

angular.module("adaptmt")

.controller('aptAptmtRepportsController',['$scope','genericResource', '$translate', 'aptAptmtRepportsService','aptAptmtsService','$location','$rootScope',
                                function($scope,genericResource, $translate, aptAptmtRepportsService,aptAptmtsService,$location,$rootScope){
	
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
	      
	   function init(){
	       
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
	       if(self.searchInput.entity.title){
	           fileName.push('aptaptmtIdentif') ;
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
