(function () {
    'use strict';
    angular.module('AdBase').controller('countrysEditController',countrysEditController);

    countrysEditController.$inject = ['$scope','countryService','$location','$routeParams'];

    function countrysEditController($scope,countryService,$location,$routeParams){
        var self = this ;
        self.country = {};
        self.error = "";
        self.update=update;
        
        init();
        
        function init(){
            var identif = $routeParams.identif ;
            countryService.findByIdentif(identif).then(function(result){
                self.country = result;
            },function(error){
                self.error = error;
            })
        }
        function update(){
            countryService.update(self.country).then(function(result){
                $location.path('/countrys/show/'+result.identif);
            },function(error){
                self.error = error;
            })
        };
    };



})();