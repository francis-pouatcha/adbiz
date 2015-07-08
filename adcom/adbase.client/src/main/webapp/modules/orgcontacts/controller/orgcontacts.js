(function () {
    'use strict';
    angular.module('AdBase').controller('orgcontactsController',orgcontactsController);

    orgcontactsController.$inject = ['$scope','orgcontactService','countryService'];

    function orgcontactsController($scope,orgcontactService,countryService){
        var self = this ;

        self.totalItems ;
        self.itemPerPage=25 ;
        self.currentPage = 1;
        self.maxSize = 5 
        self.searchInput = {};
        self.selectedIndex  ;
        self.orgcontacts=[];
        self.doSearch = search;
        self.handlePrintRequestEvent = handlePrintRequestEvent;
        self.paginate=paginate;
        self.countrys=[];
        
        self.searchInput = {
            entity:{},
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        };
        init();

        function init(){
            loadDependencies();
            search();
        }

        function search(){
            orgcontactService.search(self.searchInput).then(function(searchResult){
                self.orgcontacts = searchResult.dtos;
                self.totalItems = searchResult.count;
            });
        }
        
        function handlePrintRequestEvent(){
            //print data
        }
        
        function paginate(){
            self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
            self.searchInput.max = self.itemPerPage ;
            search(self.searchInput);
        };
                
        function loadDependencies() {
            countryService.findActifsFromNow().then(function(data){
                self.countrys = data;
            },function(error){
                self.error = error;
            })
        }
    };



})();