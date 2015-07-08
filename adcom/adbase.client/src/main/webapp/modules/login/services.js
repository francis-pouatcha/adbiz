'use strict';

angular.module('AdBase')
.factory('loginService',
    ['loginResource','$cookieStore','sessionManager','$q',
    function (loginResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.loadLogins = function(){
            var deferred = $q.defer();
        	loginResource.listAll(loginResource.getSearchInput.start,loginResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.findLogins = function(searchInput){
            var deferred = $q.defer();
            loginResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            loginResource.create(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create, be sure that the login name is unique!")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            loginResource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.loadLogin = function (identif){

            var deferred = $q.defer();

            loginResource.findById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("user do not exist!")
            });
            return deferred.promise;
        };

        service.nextLogin = function(loginName){
            var deferred = $q.defer();
            loginResource.nextLogin(loginName).success(function(data){
                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("no more user!")
            });
            return deferred.promise;
        }

        service.previousLogin = function(loginName){
            var deferred = $q.defer();
            loginResource.previousLogin(loginName).success(function(data){
                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("no more user!")
            });
            return deferred.promise;
        }


        return service;
    }]
);
