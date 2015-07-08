'use strict';

angular.module('AdBase').factory('orgUnitsResource',['$http', function($http){
    var service = {};
    var urlBase = '/adbase.server/rest/orgunits',
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
    service.createFromDto = function(entity){
        return $http.post(urlBase+'/createFromDto',entity);
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
    
    service.searchOrgUnits = function(searchInput) {
        return $http.post(urlBase+'/searchOrgUnits',searchInput);
    };

    
    service.findDtoByIdentif = function(identif) {
        return $http.get(urlBase+'/dtoByidentif/'+identif);
    };
    
    service.findEntityByIdentif = function(identif) {
        return $http.get(urlBase+'/entityByidentif/'+identif);
    };
    
    
    service.findActifsFromNow = function() {
        return $http.get(urlBase+'/findActifsFromNow');
    };
    return service;
    
}]);