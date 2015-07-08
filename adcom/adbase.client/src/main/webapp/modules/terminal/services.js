'use strict';

angular.module('AdBase')
.factory('secTerminalService',
    ['secTerminalResource','$cookieStore','sessionManager','$q',
    function (secTerminalResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.loadAll = function(){
            var deferred = $q.defer();
        	secTerminalResource.listAll(secTerminalResource.getSearchInput.start,secTerminalResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.find = function(searchInput){
            var deferred = $q.defer();
            secTerminalResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            secTerminalResource.create(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create!")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            secTerminalResource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.loadOne = function (identif){

            var deferred = $q.defer();

            secTerminalResource.findById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.delete = function (identif){

            var deferred = $q.defer();

            secTerminalResource.delete(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.findAllActiveTerminals = function (searchInput){

            var deferred = $q.defer();

            secTerminalResource.findAllActiveTerminals(searchInput).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };


        return service;
    }]
);
