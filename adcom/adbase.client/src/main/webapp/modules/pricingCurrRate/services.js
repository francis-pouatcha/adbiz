'use strict';

angular.module('AdBase')
.factory('pricingCurrRateService',
    ['pricingCurrRateResource','$cookieStore','sessionManager','$q',
    function (pricingCurrRateResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.loadAll = function(){
            var deferred = $q.defer();
        	pricingCurrRateResource.listAll(pricingCurrRateResource.getSearchInput.start,pricingCurrRateResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.find = function(searchInput){
            var deferred = $q.defer();
            pricingCurrRateResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            pricingCurrRateResource.create(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create!")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            pricingCurrRateResource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.loadOne = function (identif){

            var deferred = $q.defer();

            pricingCurrRateResource.findById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.delete = function (identif){

            var deferred = $q.defer();

            pricingCurrRateResource.deleteById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.findAllActive = function (searchInput){

            var deferred = $q.defer();

            pricingCurrRateResource.findAllActive(searchInput).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.doFind = function (searchInput){

            var deferred = $q.defer();

            pricingCurrRateResource.doFind(searchInput).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };


        return service;
    }]
);
