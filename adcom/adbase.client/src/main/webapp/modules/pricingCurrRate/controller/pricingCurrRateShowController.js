(function () {
    'use strict';
    angular.module('AdBase').controller('pricingCurrRateShowController',pricingCurrRateShowController);

    pricingCurrRateShowController.$inject = ['$scope', 'pricingCurrRateService','$location','$routeParams'];

    function pricingCurrRateShowController($scope,pricingCurrRateService, $location,$routeParams){
        var self = this ;
        self.pricingCurrRate = {};
        self.remove = remove;

       show();

        function show(){
            var identif = $routeParams.identif ;
            pricingCurrRateService.loadOne(identif).then(function(result){
               self.pricingCurrRate = result;
            })
        };

        function remove(id){
            pricingCurrRateService.delete(id).then(function(result){
                $location.path('/pricingCurrRate');
            })
        }
    };
})();