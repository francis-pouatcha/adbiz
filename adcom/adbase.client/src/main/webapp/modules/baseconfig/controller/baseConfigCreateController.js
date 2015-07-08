(function () {
    'use strict';
    angular.module('AdBase').controller('baseConfigCreateController',baseConfigCreateController);

    baseConfigCreateController.$inject = ['$scope', 'baseConfigService','$location', 'growl'];

    function baseConfigCreateController($scope,baseConfigService, $location,growl){
        var self = this ;
        
        self.baseConfig = {};
        self.create = create;
        self.error = "";
        
        self.totalItems ;
        self.searchEntity = {};
        
        function create(){
        	if(self.totalItems == 0) {
	        	baseConfigService.create(self.baseConfig).then(function(result){
	                $location.path('/baseconfig/show/'+result.id);
	            },function(error){
	                self.error = error;
	            });
        	} else {
        		growl.addInfoMessage("You cannot have multi base configs, just edit the only one item <br /> config is unique.");
        	}
        };
        
        
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
        	baseConfigService.findbaseConfigs(searchInput).then(function(entitySearchResult) {
                self.totalItems = entitySearchResult.count ;
            });
        }

    };
    
})();