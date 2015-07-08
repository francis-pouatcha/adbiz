(function () {
    'use strict';
    angular.module('AdBase').controller('orgcontactsEditController',orgcontactsEditController);

    orgcontactsEditController.$inject = ['$scope','orgcontactService','countryService','orgUnitsService' ,'$location','$routeParams'];

    function orgcontactsEditController($scope,orgcontactService,countryService,orgUnitsService, $location,$routeParams){
        var self = this ;
        self.orgContact = {};
        self.error = "";
        self.update=update;
        self.countrys=[];
        self.orgunits=[];
        
        init();
        
        function init(){
            var identif = $routeParams.identif ;
            orgcontactService.findByIdentifEntity(identif).then(function(result){
                self.orgContact = result;
            },function(error){
                self.error = error;
            });
            loadDependencies();
        }
        function update(){
            orgcontactService.update(self.orgContact).then(function(result){
                $location.path('/orgcontacts/show/'+result.identif);
            },function(error){
                self.error = error;
            })
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