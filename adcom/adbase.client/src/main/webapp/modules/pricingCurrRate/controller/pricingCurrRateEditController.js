(function () {
    'use strict';
    angular.module('AdBase').controller('pricingCurrRateEditController',pricingCurrRateEditController);

    pricingCurrRateEditController.$inject = ['$scope', 'pricingCurrRateService','$location','$routeParams'];

    function pricingCurrRateEditController($scope,pricingCurrRateService, $location,$routeParams){
        var self = this ;
        self.pricingCurrRate = {};
        self.edit = edit;
        self.error = "";

        load();
        
        function edit(){
            pricingCurrRateService.update(self.pricingCurrRate).then(function(result){
                $location.path('/pricingCurrRate/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };

        function load(){
            var identif = $routeParams.identif ;
            pricingCurrRateService.loadOne(identif).then(function(result){
                self.pricingCurrRate = result;
            },function(error){
                self.error = error;
            })
        };
    };



})();