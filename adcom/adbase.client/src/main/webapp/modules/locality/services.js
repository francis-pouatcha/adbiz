'use strict';

angular.module('AdBase')
.factory('localityService',
    ['localityResource','$cookieStore','sessionManager','$q',
    function (localityResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.loadAll = function(){
            var deferred = $q.defer();
        	localityResource.listAll(localityResource.getSearchInput.start,localityResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.find = function(searchInput){
            var deferred = $q.defer();
            localityResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            localityResource.create(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create!")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            localityResource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.loadOne = function (identif){

            var deferred = $q.defer();

            localityResource.findById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.delete = function (identif){

            var deferred = $q.defer();

            localityResource.delete(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.findAllActive = function (searchInput){

            var deferred = $q.defer();

            localityResource.findAllActive(searchInput).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.doFind = function (searchInput){

            var deferred = $q.defer();

            localityResource.doFind(searchInput).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };



        return service;
    }]
);
