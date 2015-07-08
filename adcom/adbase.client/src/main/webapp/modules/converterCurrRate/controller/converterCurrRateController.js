(function () {
    'use strict';
    angular.module('AdBase').controller('converterCurrRateController',converterCurrRateController);

    converterCurrRateController.$inject = ['$scope', 'converterCurrRateService'];

    function converterCurrRateController($scope,converterCurrRateService){
        var self = this ;

        self.searchInput = {};
        self.totalItems ;
        self.itemPerPage=25 ;
        self.currentPage = 1;
        self.maxSize = 5 ;
        self.converterCurrRates = [];
        self.searchEntity = {};
        self.selectedIndex  ;
        self.handleSearchRequestEvent = handleSearchRequestEvent;
        self.handlePrintRequestEvent = handlePrintRequestEvent;
        self.paginate = paginate;

        init();

        function init(){
            self.searchInput = {
                entity:{},
                fieldNames:[],
                start:0,
                max:self.itemPerPage
            }
            findAllActive(self.searchInput);
        }

        function findAllActive(searchInput){
            converterCurrRateService.doFind(searchInput).then(function(entitySearchResult) {
                self.converterCurrRates = entitySearchResult.resultList;
                self.totalItems = entitySearchResult.count ;
            });
        }
        function searchRequest(searchInput){
            converterCurrRateService.doFind(searchInput).then(function(entitySearchResult) {
                self.converterCurrRates = entitySearchResult.resultList;
                self.totalItems = entitySearchResult.count ;
            });
        }

        function processSearchInput(){
            var fileName = [];
            if(self.searchInput.entity.srcCurrISO3){
                fileName.push('srcCurrISO3') ;
            }
            if(self.searchInput.entity.destCurrISO3){
                fileName.push('destCurrISO3') ;
            }
            self.searchInput.fieldNames = fileName ;
            return self.searchInput ;
        };

        function  handleSearchRequestEvent(){
            var searchInput =   processSearchInput();
            searchRequest(searchInput);
        };

        function paginate(){
            self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
            self.searchInput.max = self.itemPerPage ;
            searchRequest(self.searchInput);
        };


        function handlePrintRequestEvent(){

        }

    };



})();