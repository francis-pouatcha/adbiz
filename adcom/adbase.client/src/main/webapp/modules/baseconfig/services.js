'use strict';

angular.module('AdBase')
.factory('baseConfigService',
    ['baseConfigResource','$cookieStore','sessionManager','$q',
    function (baseConfigResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.loadBaseConfigs = function(){
            var deferred = $q.defer();
        	baseConfigResource.listAll(baseConfigResource.getSearchInput.start,baseConfigResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.findbaseConfigs = function(searchInput){
            var deferred = $q.defer();
            baseConfigResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            baseConfigResource.create(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create, be sure that the baseConfig is correct !")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            baseConfigResource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.loadBaseConfig = function (identif){
            var deferred = $q.defer();
            baseConfigResource.findById(identif).success(function(data){
                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("base config do not exist!")
            });
            return deferred.promise;
        };

        return service;
    }]
);
