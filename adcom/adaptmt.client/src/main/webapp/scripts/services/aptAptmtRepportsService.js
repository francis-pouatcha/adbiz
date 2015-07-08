'use strict';
    
angular.module('adaptmt')


.factory('aptAptmtRepportsService',['sessionManager','$translate','genericResource','$q', '$http',function(sessionManager,$translate,genericResource,$q,$http){
    var service = {};

    service.urlBase='/adaptmt.server/rest/aptaptmtRepports';
    
    service.create = function(entity){
        var deferred = $q.defer();
        genericResource.create(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(error){
            deferred.reject("Can not create, be sure that the aptAptmtRepport name is !")
        });
        return deferred.promise;
    };

    service.updateAptAptmtRepport = function(entity){
        var deferred = $q.defer();
        genericResource.update(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.loadAptAptmtRepport = function(identif){
       var deferred = $q.defer();
        genericResource.findById(service.urlBase, identif).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.loadAptAptmtRepports = function(searchInput){
       var deferred = $q.defer();
        genericResource.findBy(service.urlBase, searchInput).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.findAptAptmtRepports = function(searchInput){
        var deferred = $q.defer();
        genericResource.findByLike(service.urlBase, searchInput)
            .success(function(data, status, headers, config){
                deferred.resolve(data);
            }).error(function(data, status, headers, config){
                deferred.reject("An error occured while fetching appointments");
            });
        return deferred.promise;
    };
    
    service.nextAptAptmtRepport = function(id){
        var deferred = $q.defer();
        
        $http.get(service.urlBase+'/nextLogin/'+id)
        .success(function(data){
            deferred.resolve(data);
        }).error(function(data){
            deferred.reject("no more appointment !")
        });
        return deferred.promise;
    }

    service.previousAptAptmtRepport = function(id){
        var deferred = $q.defer();
        
        $http.get(service.urlBase+'/previousLogin/'+id)
        .success(function(data){
            deferred.resolve(data);
        }).error(function(data){
            deferred.reject("no more appointment !")
        });
        return deferred.promise;
    }


    return service;
}]);