'use strict';

angular.module("adaptmt")

.controller('aptAptmtUpdateController',['$scope','genericResource', '$translate', 'aptAptmtsService','$location','$routeParams',
                                function($scope,genericResource, $translate, aptAptmtsService,$location,$routeParams){

        $scope.aptAptmt = {};
        
        var self = this;
        self.aptAptmts = {};
    	self.searchInput = {};
    	self.error = {};
	
        $scope.update = function(){
            aptAptmtsService.updateAptAptmt($scope.aptAptmt).then(function(result){
            	$location.path('/aptaptmt/show/' + result.id);
            })

        };
                              
        function show(){

        	var identif = $routeParams.id;
            aptAptmtsService.loadAptAptmt(identif).then(function(result){
               $scope.aptAptmt = result;
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
            
        }

        init();
}]);