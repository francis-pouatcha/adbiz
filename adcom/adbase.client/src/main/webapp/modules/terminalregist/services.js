'use strict';

angular.module('AdBase')
.factory('secTerminalRegistService',
    ['secTerminalRegistResource','$cookieStore','sessionManager','$q',
    function (secTerminalRegistResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.loadAll = function(){
            var deferred = $q.defer();
        	secTerminalRegistResource.listAll(secTerminalRegistResource.getSearchInput.start,secTerminalRegistResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.find = function(searchInput){
            var deferred = $q.defer();
            secTerminalRegistResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            secTerminalRegistResource.create(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create!")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            secTerminalRegistResource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.loadOne = function (identif){

            var deferred = $q.defer();

            secTerminalRegistResource.findById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.delete = function (identif){

            var deferred = $q.defer();

            secTerminalRegistResource.deleteById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.findAllActiveTerminals = function (searchInput){

            var deferred = $q.defer();

            secTerminalRegistResource.findAllActiveTerminals(searchInput).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };


        return service;
    }]
);
