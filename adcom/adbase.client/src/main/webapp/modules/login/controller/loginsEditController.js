(function () {
    'use strict';
    angular.module('AdBase').controller('loginEditController',loginEditController);

    loginEditController.$inject = ['$scope', 'loginService','$location','$routeParams','orgUnitsService'];

    function loginEditController($scope,loginService, $location,$routeParams,orgUnitsService){
        var self = this ;
        self.login = {};
        self.edit = edit;
        self.error = "";
        self.orgUnits = [];

        function edit(){

            loginService.update(self.login).then(function(result){

                $location.path('/login/show/'+result.identif);
            },function(error){
                self.error = error;
            })

        };

        init();

        function init(){
            load();
            loadOrg();
        }

        function load(){

            var identif = $routeParams.identif ;

            loginService.loadLogin(identif).then(function(result){

                self.login = result;

            },function(error){
                self.error = error;
            })

        };

        function loadOrg(){
            orgUnitsService.findActifsFromNow().then(function(entitySearchResult){
                self.orgUnits = entitySearchResult;

            })
        }
    };



})();