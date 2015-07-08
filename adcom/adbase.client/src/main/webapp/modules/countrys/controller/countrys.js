(function () {
    'use strict';
    angular.module('AdBase').controller('countrysController',countryController);

    countryController.$inject = ['$scope','countryService'];

    function countryController($scope,countryService){
        var self = this ;

        self.totalItems ;
        self.itemPerPage=25 ;
        self.currentPage = 1;
        self.maxSize = 5 
        self.searchInput = {};
        self.selectedIndex  ;
        self.countrys=[];
        self.doSearch = search;
        self.handlePrintRequestEvent = handlePrintRequestEvent;
        self.paginate=paginate;
        
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
            countryService.search(self.searchInput).then(function(searchResult){
                self.countrys = searchResult.resultList;
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
    };



})();