(function () {
    'use strict';
    angular.module('AdBase').controller('loginCreateController',loginCreateController);

    loginCreateController.$inject = ['$scope', 'loginService','$location','orgUnitsService'];

    function loginCreateController($scope,loginService, $location,orgUnitsService){
        var self = this ;
        self.login = {};
        self.create = create;
        self.error = "";
        self.orgUnits = [];

        loadOrg();

        function create(){

            loginService.create(self.login).then(function(result){

                $location.path('/login/show/'+result.identif);
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