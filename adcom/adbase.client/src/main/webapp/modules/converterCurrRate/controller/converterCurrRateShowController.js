(function () {
    'use strict';
    angular.module('AdBase').controller('converterCurrRateShowController',converterCurrRateShowController);

    converterCurrRateShowController.$inject = ['$scope', 'converterCurrRateService','$location','$routeParams'];

    function converterCurrRateShowController($scope,converterCurrRateService, $location,$routeParams){
        var self = this ;
        self.converterCurrRate = {};
        self.remove = remove;

       show();

        function show(){
            var identif = $routeParams.identif ;
            converterCurrRateService.loadOne(identif).then(function(result){
               self.converterCurrRate = result;
            })
        };

        function remove(id){
            converterCurrRateService.delete(id).then(function(result){
                $location.path('/converterCurrRate');
            })
        }
    };
})();