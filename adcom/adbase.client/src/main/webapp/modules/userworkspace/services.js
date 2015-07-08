'use strict';

angular.module('AdBase')
.factory('userWorkspaceService',
    ['userWorkspaceResource','$cookieStore','sessionManager','$q',
    function (userWorkspaceResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.loadLogins = function(){
            var deferred = $q.defer();
        	userWorkspaceResource.listAll(userWorkspaceResource.getSearchInput.start,userWorkspaceResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.findLogins = function(searchInput){
            var deferred = $q.defer();
            userWorkspaceResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            userWorkspaceResource.create(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create, be sure that the login name is unique!")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            userWorkspaceResource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.load = function (identif){

            var deferred = $q.defer();

            userWorkspaceResource.findById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("user do not exist!")
            });
            return deferred.promise;
        };

        service.loadUserWorkspaces = function (orgUnit,loginName){
            var deferred = $q.defer();
            userWorkspaceResource.loadUserWorkspaces(orgUnit,loginName).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };

        service.saveUserWorkspace = function (loginWorkspaceDtos){
            var deferred = $q.defer();
            userWorkspaceResource.saveUserWorkspace(loginWorkspaceDtos).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };


        return service;
    }]
);
