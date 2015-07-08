'use strict';
    
angular.module('adaptmt')


.factory('aptAptmtBsPtnrService',['sessionManager','$translate','genericResource','$q', '$http',function(sessionManager,$translate,genericResource,$q,$http){
    var service = {};

    service.urlBase='/adaptmt.server/rest/aptaptmtbsptnrs';
    service.bnsptnrUrlBase = '/adbnsptnr.server/rest/bpbnsptnrs';
    
    service.create = function(entity){
        var deferred = $q.defer();
        genericResource.create(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(error){
            deferred.reject("Can not create, be sure that the aptaptmtbsptnrs property( id of appointment and of bsnptnr) name is not null!")
        });
        return deferred.promise;
    };
    
    service.deleteById = function(id){
        var deferred = $q.defer();
        genericResource.deleteById(service.urlBase, id).success(function(data){
            deferred.resolve(data);
        }).error(function(error){
            deferred.reject("Can not create, be sure that the aptaptmtbsptnrs property( id of appointment and of bsnptnr) name is not null!")
        });
        return deferred.promise;
    };

    service.updateAptAptmtBsptnrs = function(entity){
        var deferred = $q.defer();
        genericResource.update(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.loadAptAptmtBsptnr = function(aptmtIdentify){
    	
    	var searchInput = {
				entity : {
					aptmtIdentify : aptmtIdentify
				},
				fieldNames : [ "aptmtIdentify" ],
				start : 0,
				max:10
			}
    	
    	var deferred = $q.defer();
    	genericResource.findByLike(service.urlBase, searchInput).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
    	
      /* var deferred = $q.defer();
       $http.get(service.urlBase, searchInput)
       .success(function(data){
           deferred.resolve(data);
       }).error(function(data){
           deferred.reject("no more aptmtbnsptnr !")
       });*/
       return deferred.promise;
    };
    
 service.loadAptAptmtContact = function(aptmtIdentify,contactnNbr){
    	
    	var searchInput = {
				entity : {
					aptmtIdentify : aptmtIdentify,
					contactnNbr : contactnNbr
				},
				fieldNames : [ "aptmtIdentify"],
				start : 0,
				max:10
			}
    	
    	var deferred = $q.defer();
    	genericResource.findByLike(service.urlBase, searchInput).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not load")
        });
    
       return deferred.promise;
    };
    
    service.findAptAptmtBsptnrs = function(searchInput){
        var deferred = $q.defer();
        genericResource.findByLike(service.urlBase, searchInput)
            .success(function(data, status, headers, config){
                deferred.resolve(data);
            }).error(function(data, status, headers, config){
                deferred.reject("An error occured while fetching aptaptmtbsptnrs");
            });
        return deferred.promise;
    };
    
    service.nextAptAptmtBsptnrs = function(id){
        var deferred = $q.defer();
        
        $http.get(service.urlBase+'/nextLogin/'+id)
        .success(function(data){
            deferred.resolve(data);
        }).error(function(data){
            deferred.reject("no more aptaptmtbsptnrs !")
        });
        return deferred.promise;
    }

    service.previousAptAptmtBsptnrs = function(id){
        var deferred = $q.defer();
        
        $http.get(service.urlBase+'/previousLogin/'+id)
        .success(function(data){
            deferred.resolve(data);
        }).error(function(data){
            deferred.reject("no more appointment !")
        });
        return deferred.promise;
    }
    
    service.loadPartnersByName = function(fullName){
        return genericResource.findByLikePromissed(service.bnsptnrUrlBase, 'fullName', fullName)
        .then(function(entitySearchResult){
            if(!angular.isUndefined(entitySearchResult))
            return entitySearchResult.resultList;
            else return "";
        });
    };
    
   


    return service;
}]);