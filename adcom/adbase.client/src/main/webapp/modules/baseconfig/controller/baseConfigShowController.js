(function () {
    'use strict';
    angular.module('AdBase').controller('baseConfigShowController',baseConfigShowController);

    baseConfigShowController.$inject = ['$scope', 'baseConfigService','$location','$routeParams'];

    function baseConfigShowController($scope,baseConfigService, $location,$routeParams){
        var self = this ;
        self.baseConfig = {};
        self.show = show;

        init();

        function init(){
            show();
        }

        function show(){
            var identif = $routeParams.identif ;
            baseConfigService.loadBaseConfig(identif).then(function(result){
               self.baseConfig = result;
            })
        };

    };

})();