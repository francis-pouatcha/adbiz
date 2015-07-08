(function () {
    'use strict';
    angular.module('AdBase').controller('converterCurrRateEditController',converterCurrRateEditController);

    converterCurrRateEditController.$inject = ['$scope', 'converterCurrRateService','$location','$routeParams'];

    function converterCurrRateEditController($scope,converterCurrRateService, $location,$routeParams){
        var self = this ;
        self.converterCurrRate = {};
        self.edit = edit;
        self.error = "";

        load();
        
        function edit(){
            converterCurrRateService.update(self.converterCurrRate).then(function(result){
                $location.path('/converterCurrRate/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };

        function load(){
            var identif = $routeParams.identif ;
            converterCurrRateService.loadOne(identif).then(function(result){
                self.converterCurrRate = result;
            },function(error){
                self.error = error;
            })
        };
    };



})();