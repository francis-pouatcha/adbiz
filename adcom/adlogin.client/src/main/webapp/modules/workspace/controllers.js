'use strict';

angular.module('AdLogin')
.controller('workspaceController',
    ['$scope','workspaceService','$location','sessionManager',
     function ($scope,workspaceService,$location,sessionManager) {
    	
    	$scope.workspaces=workspaceService.getWorkspaces();

    	$scope.loadWorkspaces = function(){
    		workspaceService.loadWorkspaces(function(data, status, headers, config){}, function(data, status, headers, config){});
    	};
    	
    	$scope.switchWorkspace = function(identif){
        	sessionManager.wsout(identif);
    	};
    }]
);