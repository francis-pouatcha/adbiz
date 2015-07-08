'use strict';

angular.module('AdBase')
.factory('ouWorkspaceService',
    ['ouWorkspaceResource','$q',
    function (ouWorkspaceResource,$q) {
        var service = {};

        service.findActivesOuWorkspaces = function(targetOuIdentif){
            var deferred = $q.defer();
        	ouWorkspaceResource.findActivesOuWorkspaces(targetOuIdentif)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};
          
        service.assignWorkspaces = function(dtoHolder){
            var deferred = $q.defer();
        	ouWorkspaceResource.assignWorkspaces(dtoHolder)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};
        
        return service;
    }]
);
