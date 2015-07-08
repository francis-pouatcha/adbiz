'use strict';

angular.module('AdBase')
.factory('countryService',
    ['countryResource','$cookieStore','sessionManager','$q',
    function (countryResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.create = function (country) {
            var deferred = $q.defer();
            countryResource.create(country).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("An error occured when creating the country.")
            });
            return deferred.promise;
        };
        
        service.update = function (country) {
            var deferred = $q.defer();
            countryResource.update(country).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("An error occured while updating the country.")
            });
            return deferred.promise;
        };
        service.findActifsFromNow = function(){
            var deferred = $q.defer();
            countryResource.findActifsFromNow().success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when searching actives countries"); 
            });
            return deferred.promise;
    	};
        
        service.search = function(searchInput){
            var deferred = $q.defer();
            countryResource.search(searchInput).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when searching countries"); 
            });
            return deferred.promise;
    	};
        
        service.findByIdentif = function(identif){
            var deferred = $q.defer();
            countryResource.findByIdentif(identif).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when searching countries"); 
            });
            return deferred.promise;
    	};
        
        service.remove = function (id) {
            var deferred = $q.defer();
            countryResource.deleteById(id).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured while deleting the country");
            });
            return deferred.promise;
        }
        return service;
    }]
);
