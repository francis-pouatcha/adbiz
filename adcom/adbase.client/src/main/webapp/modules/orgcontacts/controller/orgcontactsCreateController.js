(function () {
    'use strict';
    angular.module('AdBase').controller('orgcontactsCreateController',orgcontactsCreateController);

    orgcontactsCreateController.$inject = ['$scope', 'orgcontactService', 'countryService','orgUnitsService','$location'];

    function orgcontactsCreateController($scope,orgcontactService,countryService,orgUnitsService,$location){
        var self = this ;
        self.orgContact = {};
        self.countrys = [];
        self.create = create;
        self.orgunits = [];
        self.error = "";
        
        init();
        
        function init(){
            loadDependencies();
        };
        
        function create(){
            orgcontactService.create(self.orgContact).then(function(result){
               $location.path('/orgcontacts/show/'+result.identif);
            }, function(error){
                self.error = error;
            });
        };
        
        function loadDependencies() {
            
            countryService.findActifsFromNow().then(function(data){
                self.countrys = data;
            },function(error){
                self.error = error;
            });
            orgUnitsService.findActifsFromNow().then(function(data){
               self.orgunits = data; 
            },function(error){
                self.error = error;
            });
        }
    };



})();