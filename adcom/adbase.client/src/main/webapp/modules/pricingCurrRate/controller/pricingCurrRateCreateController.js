(function () {
    'use strict';
    angular.module('AdBase').controller('pricingCurrRateCreateController',pricingCurrRateCreateController);

    pricingCurrRateCreateController.$inject = ['$scope', 'pricingCurrRateService','$location'];

    function pricingCurrRateCreateController($scope,pricingCurrRateService, $location){
        var self = this ;
        self.pricingCurrRate = {};
        self.create = create;
        self.error = "";

        function create(){
            pricingCurrRateService.create(self.pricingCurrRate).then(function(result){
                $location.path('/pricingCurrRate/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };
    };



})();