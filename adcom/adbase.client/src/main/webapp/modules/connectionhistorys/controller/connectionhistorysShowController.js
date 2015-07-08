(function () {
    'use strict';
    angular.module('AdBase').controller('connectionHistorysShowController',connectionHistorysShowController);

    connectionHistorysShowController.$inject = ['$scope', 'connectionHistorysService','$location','$routeParams'];

    function connectionHistorysShowController($scope,connectionHistorysService, $location,$routeParams){
        var self = this ;
        self.connectionHistory = {};
        self.show = show;
        self.error;
        init();

        function init(){
            show();
        }

        function show(){

            var loginName = $routeParams.loginName ;
            connectionHistorysService.findByLogin(loginName).then(function(result){
                self.connectionHistory = result
            },function(error){
                self.error = error;
            })
        };
        
    };



})();