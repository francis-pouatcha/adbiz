(function () {
    'use strict';
    angular.module('AdBase').controller('secTermRegistController',secTermRegistController);

    secTermRegistController.$inject = ['$scope', 'secTerminalRegistService'];

    function secTermRegistController($scope,secTerminalRegistService){
        var self = this ;

        self.searchInput = {};
        self.totalItems ;
        self.itemPerPage=25 ;
        self.currentPage = 1;
        self.maxSize = 5 ;
        self.secTerminals = [];
        self.searchEntity = {};
        self.selectedLogin = {} ;
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
            findAllActiveTerminals(self.searchInput);
        }

        function findAllActiveTerminals(searchInput){
            secTerminalRegistService.findAllActiveTerminals(searchInput).then(function(entitySearchResult) {
                self.secTerminals = entitySearchResult.resultList;
                self.totalItems = entitySearchResult.count ;
            });
        }

        function findRequest(searchInput){
            secTerminalRegistService.find(searchInput).then(function(entitySearchResult) {
                self.secTerminals = entitySearchResult.resultList;
                self.totalItems = entitySearchResult.count ;
            });
        }

        function processSearchInput(){
            var fileName = [];
            if(self.searchInput.entity.partnerIds){
                fileName.push('partnerIds') ;
            }
            self.searchInput.fieldNames = fileName ;
            return self.searchInput ;
        };

        function  handleSearchRequestEvent(){
            var searchInput =   processSearchInput();
            findRequest(self.searchInput);
        };

        function paginate(){
            self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
            self.searchInput.max = self.itemPerPage ;
            find(self.searchInput);
        };


        function handlePrintRequestEvent(){

        }

    };



})();