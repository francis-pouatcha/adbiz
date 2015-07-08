'use strict';

angular.module('AdLogin')

.controller('loginController',
    ['$scope', '$rootScope', '$location','sessionManager','workspaceService',
    function ($scope, $rootScope, $location,sessionManager,workspaceService) {
        $scope.login = function () {
        	sessionManager.login($scope.username, $scope.password, successCallback, errorCallback);
        };
        function successCallback(data, status, headers, config){
        	$location.path('/workspaces');
        	$scope.password = '';
        }
        function errorCallback (data, status, headers, config){
			$scope.password = '';
        };
        function emptyCallback (data, status, headers, config){
        };
    }]
);