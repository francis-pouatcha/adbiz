(function () {
    'use strict';
    angular.module('AdBase').controller('orgUnitController',orgUnitController);

    orgUnitController.$inject = ['$scope', 'orgUnitsService','countryService','ouTypeService'];

    function orgUnitController($scope,orgUnitsService,countryService,ouTypeService){
        var self = this ;

        self.totalItems ;
        self.itemPerPage=25 ;
        self.currentPage = 1;
        self.maxSize = 5 ;
        self.orgunits = [];
        self.searchInput = {};
        self.selectedIndex  ;
        self.partner = {};
        self.countrys=[];
        self.outypes=[];
        self.doSearch = search;
        self.handlePrintRequestEvent = handlePrintRequestEvent;
        
        self.searchInput = {
            entity:{
                identif:"hack_value"
            },
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        };//the hack value is to prevent the illegal argument exception during deserialization of the searchinput.
        init();

        function init(){

            search();
            populateSearchBar();
        }

        function search(){
            orgUnitsService.searchOrgUnits(self.searchInput).then(function(searchResult){
               self.orgunits=searchResult.dtos;
            });
        }
        
        function handlePrintRequestEvent(){

        }
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