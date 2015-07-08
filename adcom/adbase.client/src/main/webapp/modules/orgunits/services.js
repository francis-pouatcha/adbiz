'use strict';

angular.module('AdBase')
.factory('orgUnitsService',
    ['orgUnitsResource','$cookieStore','sessionManager','$q',
    function (orgUnitsResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.searchOrgUnits = function(searchInput){
            var deferred = $q.defer();
            orgUnitsResource.searchOrgUnits(searchInput).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when fetching items"); 
            });
            return deferred.promise;
    	};
        
        service.createFromDto = function(orgUnit){
            var deferred = $q.defer();
            orgUnitsResource.createFromDto(orgUnit).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when creating item"); 
            });
            return deferred.promise;
    	};
        
        service.update = function(orgUnit){
            var deferred = $q.defer();
            orgUnitsResource.update(orgUnit).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when updating item"); 
            });
            return deferred.promise;
    	};
        
        service.findDtoByIdentif = function(identif){
            var deferred = $q.defer();
            orgUnitsResource.findDtoByIdentif(identif).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when searching item"); 
            });
            return deferred.promise;
    	};
        
        
        service.findEntityByIdentif = function(identif){
            var deferred = $q.defer();
            orgUnitsResource.findEntityByIdentif(identif).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when searching item"); 
            });
            return deferred.promise;
    	};
        
        service.remove = function (id) {
            var deferred = $q.defer();
            orgUnitsResource.deleteById(id).success(function(data,status,headers,config){
                deferred.resolve(data);
            },function (data,status,headers,config){
                deferred.reject("An error occured during orgunit deletion");
            });
            return deferred.promise;
        }
        
        service.findActifsFromNow = function () {
            var deferred = $q.defer();
            orgUnitsResource.findActifsFromNow().success(function(data,status,headers,config){
                deferred.resolve(data);
            },function (data,status,headers,config){
                deferred.reject("An error occured during searching entities");
            });
            return deferred.promise;
        }
        return service;
    }]
);
