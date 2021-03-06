'use strict';

angular.module('AdBase').factory('ouTypeResource',['$http', function($http){
    var service = {};
    var urlBase = '/adbase.server/rest/outypes',
    searchInput = {
        entity:{},
        start:0,
        max:20,
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

    service.listAll = function(start,max){
        return $http.get(urlBase,{params: {start: start,max:max}});
    };

    service.getSearchInput = function(){
        return searchInput ;
    };

    service.getEntity = function(){
        return entity ;
    };
    
    service.findActifsFromNow = function() {
        return $http.get(urlBase+'/findActifsFromNow');
    }
    service.search = function(searchInput) {
        return $http.post(urlBase+'/search',searchInput);
    }
    return service;
    
}]);