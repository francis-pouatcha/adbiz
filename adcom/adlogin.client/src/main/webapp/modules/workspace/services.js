'use strict';

angular.module('AdLogin')
.factory('workspaceService',
    ['$http','sessionManager','workspaceResource',
    function ($http,sessionManager,workspaceResource) {
        var service = {};
        var workspaces=[];
        service.getWorkspaces = function(){
        	return workspaces;
        };
        service.loadWorkspaces = function(successCallback, errorCallback){
    		workspaceResource.findUserWorkspaces()
    			.success(function(data, status, headers, config){
    				setWorkspaces(data);
    				successCallback(data, status, headers, config);
    			}).error(function(data, status, headers, config){
    				errorCallback(data, status, headers, config);
    			});
    	};

    	return service;
    	
    	function setWorkspaces(data){
    		while(workspaces.length > 0) {
    			workspaces.pop();
    		}
    		var i =0;
    		for (i = 0; i < data.length; i++) {
    			workspaces.push(data[i]);
    		}
    	}
    }]
);
