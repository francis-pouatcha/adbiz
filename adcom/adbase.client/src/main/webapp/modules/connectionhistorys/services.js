'use strict';

angular.module('AdBase')
.factory('connectionHistorysService',
    ['connectionHistorysResource','$cookieStore','sessionManager','$q',
    function (connectionHistorysResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.searchConnectionHistorys = function(searchInput){
            var deferred = $q.defer();
            connectionHistorysResource.searchConnectionHistorys(searchInput).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when fetching items"); 
            });
            return deferred.promise;
    	};
        
        service.findByLogin = function(loginName){
            var deferred = $q.defer();
            connectionHistorysResource.findByLogin(loginName).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured while looking connection history item"); 
            });
            return deferred.promise;
    	};
        
        return service;
    }]
);
