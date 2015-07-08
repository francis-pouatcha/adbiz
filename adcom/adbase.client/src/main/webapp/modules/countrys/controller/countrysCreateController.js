(function () {
    'use strict';
    angular.module('AdBase').controller('countrysCreateController',countrysCreateController);

    countrysCreateController.$inject = ['$scope', 'countryService','$location'];

    function countrysCreateController($scope,countryService,$location){
        var self = this ;
        self.country = {};
        self.create = create;
        self.error = "";
        
        init();
        
        function init(){};
        
        function create(){
            countryService.create(self.country).then(function(result){
               $location.path('/countrys/show/'+result.identif);
            }, function(error){
                self.error = error;
            });
        };
    };



})();