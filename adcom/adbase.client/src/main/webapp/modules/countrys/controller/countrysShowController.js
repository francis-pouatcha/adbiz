(function () {
    'use strict';
    angular.module('AdBase').controller('countrysShowController',countrysShowController);

    countrysShowController.$inject = ['$scope', 'countryService','$location','$routeParams'];

    function countrysShowController($scope,countrySerivce, $location,$routeParams){
        var self = this ;
        self.country = {};
        self.error ;
        self.remove = remove;
        init();

        function init(){
            show();
        }

        function show(){
            var identif = $routeParams.identif ;
            countrySerivce.findByIdentif(identif).then(function(data){
                self.country = data;
            },function(error){
               self.error = error; 
            });
        };
        
        function remove(id) {
            countrySerivce.remove(id).then(function(data){
                $location.path("/countrys");
            },function(error){
                self.error = error;
            })
        }
    };



})();