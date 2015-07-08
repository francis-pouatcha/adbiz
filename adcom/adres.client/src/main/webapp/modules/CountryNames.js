'use strict';
    
angular.module('NavBar')

.controller('countryNamesCtlr',['$scope','$modalInstance','genericResource','urlBase','countryNameHolder',
                                function($scope,$modalInstance, genericResource,urlBase,countryNameHolder){
	
    var self = this ;
    $scope.countryNamesCtlr = self;

    self.searchInput = {
        entity:{},
        fieldNames:[],
        start:0,
        max:self.itemPerPage
    };
    self.totalItems ;
    self.itemPerPage=10;
    self.currentPage = 1;
    self.maxSize = 5 ;
    self.entities = [];
    self.searchEntity = {};
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.handleSearchRequestEvent = handleSearchRequestEvent;
    self.paginate = paginate;
    self.error = "";
    self.select=select;
    
    init();

    function init(){
        self.searchInput = {
            entity:{},
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
        findByLike(self.searchInput);
    }

    function findByLike(searchInput){
    	genericResource.findByLike(urlBase + 'basecountrynames', searchInput)
    		.success(function(entitySearchResult) {
	            self.entities = entitySearchResult.resultList;
	            self.totalItems = entitySearchResult.count ;
    		});
    }

    function processSearchInput(){
        var fieldNames = [];
        if(self.searchInput.entity.iso2){
        	fieldNames.push('iso2') ;
        }
        if(self.searchInput.entity.iso3){
        	fieldNames.push('iso3') ;
        }
        if(self.searchInput.entity.langsIso2){
        	fieldNames.push('langsIso2') ;
        }
        if(self.searchInput.entity.name){
        	fieldNames.push('name') ;
        }
        self.searchInput.fieldNames = fieldNames ;
        return self.searchInput ;
    }

    function  handleSearchRequestEvent(){
    	processSearchInput();
    	findByLike(self.searchInput);
    }

    function paginate(){
    	self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
    	self.searchInput.max = self.itemPerPage ;
    	findByLike(self.searchInput);
    }
    
    function select(countryName){
    	countryNameHolder.countryName=countryName;
    	$modalInstance.dismiss('cancel');
    }
}]);
