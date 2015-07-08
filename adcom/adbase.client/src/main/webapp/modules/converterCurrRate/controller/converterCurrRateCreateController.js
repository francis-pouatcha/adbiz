(function () {
    'use strict';
    angular.module('AdBase').controller('converterCurrRateCreateController',converterCurrRateCreateController);

    converterCurrRateCreateController.$inject = ['$scope', 'converterCurrRateService','$location'];

    function converterCurrRateCreateController($scope,converterCurrRateService, $location){
        var self = this ;
        self.converterCurrRate = {};
        self.create = create;
        self.error = "";

        function create(){
            converterCurrRateService.create(self.converterCurrRate).then(function(result){
                $location.path('/converterCurrRate/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };
    };



})();