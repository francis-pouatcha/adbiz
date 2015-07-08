(function () {
    'use strict';
    angular.module('AdBase').controller('baseConfigsController',baseConfigsController);

    baseConfigsController.$inject = ['$scope', 'baseConfigService', 'growl', '$location'];

    function baseConfigsController($scope,baseConfigService,growl,$location){
        var self = this ;

        self.searchInput = {};
        self.totalItems ;
        self.itemPerPage=25;
        self.currentPage = 1;
        self.maxSize = 5 ;
        self.baseConfigs = [];
        self.searchEntity = {};
        self.selectedBaseConfig = {} ;
        self.selectedIndex  ;
        self.create = create;
        

        init();

        function init(){
            self.searchInput = {
                entity:{},
                fieldNames:[],
                start:0,
                max:self.itemPerPage
            }
            findByLike(self.searchInput);
        }

        function findByLike(searchInput){
        	baseConfigService.findbaseConfigs(searchInput).then(function(entitySearchResult) {
                self.baseConfigs = entitySearchResult.resultList;
                self.totalItems = entitySearchResult.count ;
            });
        }

        function handlePrintRequestEvent(){

        }
        
        function create(){
        	if(self.totalItems == 0)
        		$location.path("/baseconfig/create");
        	else
        		growl.addInfoMessage("You cannot have multi base configs, just edit the only one item <br /> config is unique.");
        }

    };



})();