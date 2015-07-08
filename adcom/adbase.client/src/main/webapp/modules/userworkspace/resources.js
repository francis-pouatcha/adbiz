'use strict';

angular.module('AdBase').factory('userWorkspaceResource',['$http', function($http){
    var service = {};
    var urlBase = '/adbase.server/rest/userworkspaces',
    searchInput = {
        entity:{},
        start:0,
        max:100,
        fieldNames:[]
    },
    entity = {};
    
    service.create = function(entity){
        return $http.post(urlBase,entity);
    };

    service.update = function(entity){
        return $http.put(urlBase+'/'+entity.id,entity);
    };

    service.findBy = function(entitySearchInput){
        return $http.post(urlBase+'/findBy',entitySearchInput);
    };

    service.findByLike = function(entitySearchInput){
        return $http.post(urlBase+'/findByLike',entitySearchInput);
    };

    service.nextLogin = function(entityId){
        return $http.get(urlBase+'/nextLogin/'+entityId);
    };

    service.previousLogin = function(entityId){
        return $http.get(urlBase+'/previousLogin/'+entityId);
    };

    service.findById = function(entityId){
        return $http.get(urlBase+'/'+entityId);
    };

    service.loadUserWorkspaces = function(orgUnit,loginName){
        return $http.get(urlBase+'/loadUserWorkspacesDto/'+orgUnit+'/'+loginName);
    };

    service.saveUserWorkspace = function(loginWorkspaceDtos){
        return $http.post(urlBase+'/saveUserWorkspace',loginWorkspaceDtos);
    };

    service.deleteById = function(entityId){
        return $http.delete(urlBase+'/'+entityId);
    };

    service.listAll = function(start,max){
        return $http.get(urlBase,{params: {start: start,max:max}});
    };

    service.getSearchInput = function(){
        return searchInput ;
    };

    service.getEntity = function(){
        return entity ;
    };


    function getSearchInput(){
        return searchInput ;
    };
    
    return service;
    
}]);