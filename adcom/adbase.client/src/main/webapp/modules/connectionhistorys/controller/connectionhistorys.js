(function () {
    'use strict';
    angular.module('AdBase').controller('connectionHistorysController',connectionHistorysController);

    connectionHistorysController.$inject = ['$scope', 'connectionHistorysService','countryService','ouTypeService'];

    function connectionHistorysController($scope,connectionHistorysService,countryService,ouTypeService){
        var self = this ;

        self.totalItems ;
        self.itemPerPage=25 ;
        self.currentPage = 1;
        self.maxSize = 5 ;
        self.connectionHistorys = [];
        self.searchInput = {};
        self.selectedIndex  ;
        self.doSearch = search;
        self.handlePrintRequestEvent = handlePrintRequestEvent;
        
        self.searchInput = {
            entity:{},
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        };
        init();

        function init(){
            search();
        }

        function search(){
            connectionHistorysService.searchConnectionHistorys(self.searchInput).then(function(searchResult){
               self.connectionHistorys=searchResult.resultList;
            });
        }
        
        function handlePrintRequestEvent(){

        }
    };



})();