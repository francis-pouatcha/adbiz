(function () {
    'use strict';
    angular.module('AdBase').controller('loginShowController',loginShowController);

    loginShowController.$inject = ['$scope', 'loginService','$location','$routeParams'];

    function loginShowController($scope,loginService, $location,$routeParams){
        var self = this ;
        self.login = {};
        self.show = show;
        self.previous = previous;
        self.next = next;
        self.lockOrUnlockLogin = lockOrUnlockLogin;
        self.enableOrDisableLogin = enableOrDisableLogin;

        init();

        function init(){
            show();
        }

        function show(){

            var identif = $routeParams.identif ;

            loginService.loadLogin(identif).then(function(result){

               self.login = result;

            })

        };

        function lockOrUnlockLogin(){
            self.login.accountLocked = !self.login.accountLocked ;

            loginService.update(self.login).then(function(result){

                self.login = result;
            })
        };

        function enableOrDisableLogin(){
            self.login.disableLogin = !self.login.disableLogin ;

            loginService.update(self.login).then(function(result){

                self.login = result;
            })
        };

        function previous(){
            self.error = "";
            loginService.previousLogin(self.login.loginName).then(function(result){
                self.login = result;
            },function(error){
                self.error = error;
            })

        }



        function next(){
            self.error = "";
            loginService.nextLogin(self.login.loginName).then(function(result){
                self.login = result;
            },function(error){
                self.error = error;
            })

        }
    };



})();