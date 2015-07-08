(function () {
    'use strict';
    angular.module('AdBase').controller('baseConfigEditController',baseConfigEditController);

    baseConfigEditController.$inject = ['$scope', 'baseConfigService','$location','$routeParams'];

    function baseConfigEditController($scope,baseConfigService, $location,$routeParams){
        var self = this ;
        self.baseConfig = {};
        self.edit = edit;
        self.error = "";
        self.orgUnits = [];

        function edit(){
        	baseConfigService.update(self.baseConfig).then(function(result){
                $location.path('/baseconfig/show/'+result.identif);
            },function(error){
                self.error = error;
            })
        };

        init();

        function init(){
            load();
        };

        function load(){
            var identif = $routeParams.identif ;
            baseConfigService.loadBaseConfig(identif).then(function(result){
                self.baseConfig = result;
            },function(error){
                self.error = error;
            })
        };
        
    };
    
})();
