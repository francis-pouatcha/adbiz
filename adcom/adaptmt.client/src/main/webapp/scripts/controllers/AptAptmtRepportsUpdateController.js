'use strict';

angular.module("adaptmt")

.controller('aptAptmtRepportUpdateController',['$scope','genericResource', '$translate', 'aptAptmtRepportsService','$location','$routeParams',
                                function($scope,genericResource, $translate, aptAptmtRepportsService,$location,$routeParams){

        $scope.aptAptmtRepport = {};
        
        var self = this;
        self.aptAptmtRepports = {};
    	self.searchInput = {};
    	self.error = {};
	
        $scope.update = function(){
            aptAptmtRepportsService.updateAptAptmtRepport($scope.aptAptmtRepport).then(function(result){
            	$location.path('/aptaptmtRepport/show/' + result.id);
            })

        };
                              
        function show(){

        	var identif = $routeParams.id;
            aptAptmtRepportsService.loadAptAptmtRepport(identif).then(function(result){
               $scope.aptAptmtRepport = result;
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
                });
            
        }

        init();
}]);