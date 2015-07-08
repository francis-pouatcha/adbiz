'use strict';

angular.module('AdCatal').factory('catalArt2ProductFamilyResource',['$http', function($http){
    var service = {};
    var urlBase = '/adcatal.server/rest/catalart2productfamilys',
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
    
    service.createCustom = function(artPic,entity){
        return $http.post(urlBase+'/createCustom/'+artPic,entity);
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

    service.findByArtPic = function(artPic){
        return $http.get(urlBase+'/findByArtPic/'+artPic);
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
    function getSearchInput(){
        return searchInput ;
    };
    
    return service;
    
}]);