(function () {
    'use strict';
    angular.module('AdBase').controller('orgcontactsShowController',orgcontactsShowController);

    orgcontactsShowController.$inject = ['$scope', 'orgcontactService','$location','$routeParams'];

    function orgcontactsShowController($scope,orgcontactService, $location,$routeParams){
        var self = this ;
        self.orgContact = {};
        self.error ;
        self.remove = remove;
        init();

        function init(){
            show();
        }

        function show(){
            var identif = $routeParams.identif ;
            orgcontactService.findByIdentifDto(identif).then(function(data){
                self.orgContact = data;
            },function(error){
               self.error = error; 
            });
        };
        
        function remove(id) {
            orgcontactService.remove(id).then(function(data){
                $location.path("/orgcontacts");
            },function(error){
                self.error = error;
            });
        }
    };



})();