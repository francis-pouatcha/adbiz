'use strict';

angular.module('adaptmt')


.factory('aptAptmtContactsService',['sessionManager','$translate','genericResource','$q', '$http',function(sessionManager,$translate,genericResource,$q,$http){
	var service = {};

	service.urlBase='/adaptmt.server/rest/aptaptmtContacts';


	service.create = function(entity){
		var deferred = $q.defer();
		genericResource.create(service.urlBase, entity).success(function(data){
			deferred.resolve(data);
		}).error(function(error){
			deferred.reject("Can not create, be sure that the aptAptmtContact name is !")
		});
		return deferred.promise;
	};

	service.deleteById = function(id){
		var deferred = $q.defer();
		genericResource.deleteById(service.urlBase, id).success(function(data){
			deferred.resolve(data);
		}).error(function(error){
			deferred.reject("Can not create, be sure that the aptAptmtContact name is !")
		});
		return deferred.promise;
	};



	service.updateAptAptmtContact = function(entity){
		var deferred = $q.defer();
		genericResource.update(service.urlBase, entity).success(function(data){
			deferred.resolve(data);
		}).error(function(){
			deferred.reject("Can not update")
		});
		return deferred.promise;
	};

	service.loadAptAptmtContact = function(identif){
		var deferred = $q.defer();
		genericResource.findById(service.urlBase, identif).success(function(data){
			deferred.resolve(data);
		}).error(function(){
			deferred.reject("Can not update")
		});
		return deferred.promise;
	};

	service.loadAptAptmtContacts = function(searchInput){
		var deferred = $q.defer();
		genericResource.findBy(service.urlBase, searchInput).success(function(data){
			deferred.resolve(data);
		}).error(function(){
			deferred.reject("Can not update")
		});
		return deferred.promise;
	};
	
	 service.loadAptAptmtContact = function(id){
	    	
	    	var searchInput = {
					entity : {
						id : id
					},
					fieldNames : [ "id"],
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

	service.findAptAptmtContacts = function(searchInput){
		var deferred = $q.defer();
		genericResource.findByLike(service.urlBase, searchInput)
		.success(function(data, status, headers, config){
			deferred.resolve(data);
		}).error(function(data, status, headers, config){
			deferred.reject("An error occured while fetching appointments");
		});
		return deferred.promise;
	};

	service.loadContacts = function(fullname){
		return genericResource.findByLikePromissed(service.urlBase, 'fullname',fullname)
		.then(function(entitySearchResult){
			if(!angular.isUndefined(entitySearchResult))
				return entitySearchResult.resultList;
			else return "";
		});
	};

	service.nextAptAptmtContact = function(id){
		var deferred = $q.defer();

		$http.get(service.urlBase+'/nextContact/'+id)
		.success(function(data){
			deferred.resolve(data);
		}).error(function(data){
			deferred.reject("no more appointment !")
		});
		return deferred.promise;
	}

	service.previousAptAptmtContact = function(id){
		var deferred = $q.defer();

		$http.get(service.urlBase+'/previousContact/'+id)
		.success(function(data){
			deferred.resolve(data);
		}).error(function(data){
			deferred.reject("no more appointment !")
		});
		return deferred.promise;
	}



	return service;
}]);