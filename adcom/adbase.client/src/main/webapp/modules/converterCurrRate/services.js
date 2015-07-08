'use strict';

angular.module('AdBase')
.factory('converterCurrRateService',
    ['converterCurrRateResource','$cookieStore','sessionManager','$q',
    function (converterCurrRateResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.loadAll = function(){
            var deferred = $q.defer();
        	converterCurrRateResource.listAll(converterCurrRateResource.getSearchInput.start,converterCurrRateResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.find = function(searchInput){
            var deferred = $q.defer();
            converterCurrRateResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            converterCurrRateResource.create(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create!")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            converterCurrRateResource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.loadOne = function (identif){

            var deferred = $q.defer();

            converterCurrRateResource.findById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.delete = function (identif){

            var deferred = $q.defer();

            converterCurrRateResource.delete(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.findAllActive = function (searchInput){

            var deferred = $q.defer();

            converterCurrRateResource.findAllActive(searchInput).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.doFind = function (searchInput){

            var deferred = $q.defer();

            converterCurrRateResource.doFind(searchInput).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };


        return service;
    }]
);
