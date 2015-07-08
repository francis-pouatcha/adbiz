(function () {
    'use strict';
    angular.module('AdBase').controller('orgUnitEditController',orgUnitEditController);

    orgUnitEditController.$inject = ['$scope', 'orgUnitsService','countryService','ouTypeService','$location','$routeParams'];

    function orgUnitEditController($scope,orgUnitsService,countryService,ouTypeService,$location,$routeParams){
        var self = this ;
        self.orgUnit = {};
        self.error = "";
        self.countrys = [];
        self.outypes = [];
        self.update=update;
        
        init();
        
        function init(){
            var identif = $routeParams.identif ;
            orgUnitsService.findEntityByIdentif(identif).then(function(result){
                self.orgUnit = result
            })

            populateSearchBar();//load data required for the view.
        }
        function update(){

            orgUnitsService.update(self.orgUnit).then(function(result){

                $location.path('/orgunits/show/'+result.identif);
            },function(error){
                self.error = error;
            })

        };
        
        function populateSearchBar(){
            countryService.findActifsFromNow().then(function(data){
               self.countrys=data;
            });
            ouTypeService.findActifsFromNow().then(function(data){
                self.outypes = data.resultList;
            });
        }
    };



})();