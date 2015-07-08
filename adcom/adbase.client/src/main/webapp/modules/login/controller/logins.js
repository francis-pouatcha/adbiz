(function () {
    'use strict';
    angular.module('AdBase').controller('loginController',loginController);

    loginController.$inject = ['$scope', 'loginService','orgUnitsService'];

    function loginController($scope,loginService,orgUnitsService){
        var self = this ;

        self.searchInput = {};
        self.totalItems ;
        self.itemPerPage=25;
        self.currentPage = 1;
        self.maxSize = 5 ;
        self.logins = [];
        self.searchEntity = {};
        self.selectedLogin = {} ;
        self.selectedIndex  ;
        self.handleSearchRequestEvent = handleSearchRequestEvent;
        self.handlePrintRequestEvent = handlePrintRequestEvent;
        self.paginate = paginate;
        self.orgUnits = [];

        init();

        function init(){
            self.searchInput = {
                entity:{},
                fieldNames:[],
                start:0,
                max:self.itemPerPage
            }
            findByLike(self.searchInput);
            loadOrg();
        }

        function loadOrg(){
            orgUnitsService.findActifsFromNow().then(function(entitySearchResult){
                self.orgUnits = entitySearchResult;

            })
        }

        function findByLike(searchInput){
            loginService.findLogins(searchInput).then(function(entitySearchResult) {
                self.logins = entitySearchResult.resultList;
                self.totalItems = entitySearchResult.count ;
            });
        }

        function processSearchInput(){
            var fileName = [];
            if(self.searchInput.entity.loginName){
                fileName.push('loginName') ;
            }
            if(self.searchInput.entity.fullName){
                fileName.push('fullName') ;
            }
            if(self.searchInput.entity.ouIdentif){
                fileName.push('ouIdentif') ;
            }
            self.searchInput.fieldNames = fileName ;
            return self.searchInput ;
        };

        function  handleSearchRequestEvent(){
             processSearchInput();
            findByLike(self.searchInput);
        };

        function paginate(){
            self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
            self.searchInput.max = self.itemPerPage ;
            findByLike(self.searchInput);
        };


        function handlePrintRequestEvent(){

        }

    };



})();