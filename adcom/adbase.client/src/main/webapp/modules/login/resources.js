'use strict';

angular.module('AdBase').factory('loginResource',['$http', function($http){
    var service = {};
    var urlBase = '/adbase.server/rest/logins',
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
    service.processPasswordChange = function(data){
        return $http.post(urlBase+'/processPasswordChange',data);
    };
    service.changeUserPassword = function(data){
        return $http.post(urlBase+'/changeUserPassword',data);
    };
    service.changeMyPassword = function(data){
        return $http.post(urlBase+'/changeMyPassword',data);
    };
    service.findManagedLogins = function(searchInput){
        return $http.post(urlBase+'/findManagedLogins',searchInput);
    };

    function getSearchInput(){
        return searchInput ;
    };
    
    return service;
    
}]);