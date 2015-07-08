'use strict';

angular.module('AdCatal').factory('catalProductFamilyResource',['$http', function($http){
    var service = {};
    var urlBase = '/adcatal.server/rest/catalproductfamilys',
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

    service.findCustom = function(entitySearchInput){
        return $http.post(urlBase+'/findCustom',entitySearchInput);
    };

    service.findByIdentif = function(identif){
        return $http.get(urlBase+'/'+identif);
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