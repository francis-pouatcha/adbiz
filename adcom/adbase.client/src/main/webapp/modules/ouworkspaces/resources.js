'use strict';

angular.module('AdBase').factory('ouWorkspaceResource',['$http', function($http){
    var service = {};
    var urlBase = '/adbase.server/rest/ouworkspaces',
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

    service.findById = function(entityId){
        return $http.get(urlBase+'/'+entityId);
    };

    service.deleteById = function(entityId){
        return $http.delete(urlBase+'/'+entityId);
    };

    service.delete = function(entityId){
        return $http.put(urlBase+'/'+entityId);
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
    
    service.findActivesOuWorkspaces = function(targetOuIdentif){
        return $http.get(urlBase+'/findActivesOuWorkspaces/'+targetOuIdentif);
    };
    
    service.assignWorkspaces = function(dtoHolder){
        return $http.post(urlBase+'/assignWorkspaces',dtoHolder);
    };
    return service;
    
}]);