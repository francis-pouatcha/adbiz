(function () {
    'use strict';
    angular.module('AdBase').controller('userWorkspaceController',userWorkspaceController);

    userWorkspaceController.$inject = ['$scope', 'userWorkspaceService','$location','$routeParams'];

    function userWorkspaceController($scope,userWorkspaceService, $location, $routeParams){
        var self = this ;
        self.workspacesDto = [];
        self.selectedWorkspace = {};
        self.saveWorkspace = saveWorkspace;
        self.error = "";
        self.loginName;


        init();

        function init(){
            var loginName = $routeParams.loginName;
            var ouIdentif = $routeParams.ouIdentif;
            self.loginName = loginName;
            loadWorkspace(ouIdentif,loginName);
        }


        function saveWorkspace(){

            userWorkspaceService.saveUserWorkspace( self.workspacesDto).then(function (data) {
                self.workspacesDto = data;
            });
        };

        function loadWorkspace(ouIdentif,loginName){
            userWorkspaceService.loadUserWorkspaces(ouIdentif,loginName).then(function(data){
                self.workspacesDto = data;
            })
        };
    };



})();