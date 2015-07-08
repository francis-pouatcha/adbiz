(function () {
    'use strict';
    angular.module('AdBase').controller('loginChangePwdController',loginChangePwdController);

    loginChangePwdController.$inject = ['$scope', 'loginService','$location','$routeParams'];

    function loginChangePwdController($scope,loginService, $location, $routeParams){
        var self = this ;
        self.login = {};
        self.changePwd = changePwd;
        self.newPwd = "";
        self.newConfirmPwd = "";
        self.error = "";

        function changePwd(){

            console.log(self.newConfirmPwd);
            console.log(self.newPwd);

            if(self.newPwd == self.newConfirmPwd){

                self.login.pwdHashed = self.newPwd;
                loginService.update(self.login).then(function(result){

                    $location.path('/login/show/'+result.identif);
                })

            }else{
                self.error = "new password and new confim password are different!";
            }


        };

        init();

        function init(){
            load();
        }

        function load(){

            var identif = $routeParams.identif ;

            loginService.loadLogin(identif).then(function(result){

                self.login = result;

            })

        };
    };



})();