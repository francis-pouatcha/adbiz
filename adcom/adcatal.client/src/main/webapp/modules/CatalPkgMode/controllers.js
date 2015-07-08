'use strict';
    
angular.module('AdCatal')

.controller('catalPkgModeCtrl',['$scope','catalPkgModeService',function($scope,catalPkgModeService){
	
    var self = this ;
    $scope.catalPkgModeCtrl = self;

    self.searchInput = {
        entity:{},
        fieldNames:[],
        start:0,
        max:self.itemPerPage
    };
    self.totalItems ;
    self.itemPerPage=25;
    self.currentPage = 1;
    self.maxSize = 5 ;
    self.catalPkgModes = [];
    self.searchEntity = {};
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.handleSearchRequestEvent = handleSearchRequestEvent;
    self.handlePrintRequestEvent = handlePrintRequestEvent;
    self.paginate = paginate;
    self.delete = deleteItem;

    init();
    function init(){
        self.searchInput = {
            entity:{},
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
        searchCatalPkgModes(self.searchInput);
    }

    function searchCatalPkgModes(searchInput){
    	catalPkgModeService.searchCatalPkgModes(searchInput).then(function(entitySearchResult) {
            self.catalPkgModes = entitySearchResult.resultList;
            self.totalItems = entitySearchResult.count ;
        });
    }

    function  handleSearchRequestEvent(){
    	searchCatalPkgModes(self.searchInput);
    };

    function paginate(){
    	self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
    	self.searchInput.max = self.itemPerPage ;
    	searchCatalPkgModes(self.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
    
    function deleteItem(identif) {
        catalPkgModeService.deleteById(identif).then(function (data){
            searchCatalPkgModes(self.searchInput);
        },function(error) {
            console.log(error);
        });
    };
}])
.controller('catalPkgModeCreateCtrl',['$scope','catalPkgModeService','$location',function($scope,catalPkgModeService,$location){
	var self = this ;
    $scope.catalPkgModeCreateCtrl = self;
    self.catalPkgMode = {};
    self.create = create;
    self.error = "";

    function create(){
    	catalPkgModeService.create(self.catalPkgMode).then(function(result){

            $location.path('/CatalPkgModes');
        },function(error){
            self.error = error;
        })

    };
	
}])
.controller('catalPkgModeEditCtrl',['$scope','catalPkgModeService','$routeParams','$location',function($scope,catalPkgModeService,$routeParams,$location){
	var self = this ;
    $scope.catalPkgModeEditCtrl = self;
    self.catalPkgMode = {};
    self.edit = edit;
    self.error = "";

    function edit(){
    	catalPkgModeService.update(self.catalPkgMode).then(function(result){

            $location.path('/CatalPkgModes/show/'+result.identif);
        },function(error){
            self.error = error;
        });

    };
    function init() {
        var identif = $routeParams.identif;
        catalPkgModeService.findByIdentif(identif).then(function(result){
            self.catalPkgMode = result;
        },function (error) {
            self.error = error;
        });
    }
    init();
}])
.controller('catalPkgModeShowCtrl',['$scope','catalPkgModeService','$routeParams',function($scope,catalPkgModeService,$routeParams){
	var self = this ;
    $scope.catalPkgModeShowCtrl = self;
    self.catalPkgMode = {};
    self.error = "";
    function init() {
        var identif = $routeParams.identif;
        catalPkgModeService.findByIdentif(identif).then(function(result){
            self.catalPkgMode = result;
        },function (error) {
            self.error = error;
        });
    }
    init();
}]);
