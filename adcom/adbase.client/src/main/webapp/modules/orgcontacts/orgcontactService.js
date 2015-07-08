'use strict';

angular.module('AdBase')
.factory('orgcontactService',
    ['orgcontactResource','$cookieStore','sessionManager','$q',
    function (orgcontactResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.create = function (orgcontact) {
            var deferred = $q.defer();
            orgcontactResource.create(orgcontact).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("An error occured when creating the orgcontact.")
            });
            return deferred.promise;
        };
        
        service.update = function (orgcontact) {
            var deferred = $q.defer();
            orgcontactResource.update(orgcontact).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("An error occured while updating the orgcontact.")
            });
            return deferred.promise;
        };
        
        service.search = function(searchInput){
            var deferred = $q.defer();
            orgcontactResource.search(searchInput).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when searching countries"); 
            });
            return deferred.promise;
    	};
        
        service.findByIdentifEntity = function(identif){
            var deferred = $q.defer();
            orgcontactResource.findByIdentifEntity(identif).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when searching org. contact entity"); 
            });
            return deferred.promise;
    	};
        
        service.findByIdentifDto = function(identif){
            var deferred = $q.defer();
            orgcontactResource.findByIdentifDto(identif).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when searching org. contact dto."); 
            });
            return deferred.promise;
    	};
        
        service.remove = function (id) {
            var deferred = $q.defer();
            orgcontactResource.deleteById(id).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured while deleting the orgcontact");
            });
            return deferred.promise;
        }
        return service;
    }]
);
