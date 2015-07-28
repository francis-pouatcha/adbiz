'use strict';

angular.module('adaptmt')


.factory('aptAptmtNotificationsService',['sessionManager','$translate','genericResource','$q', '$http',function(sessionManager,$translate,genericResource,$q,$http){
	var service = {};

	service.urlBase='/adaptmt.server/rest/aptaptmtNotifications';


	service.create = function(entity){
		var deferred = $q.defer();
		genericResource.create(service.urlBase, entity).success(function(data){
			deferred.resolve(data);
		}).error(function(error){
			deferred.reject("Can not create, be sure that the aptAptmtNotification name is !")
		});
		return deferred.promise;
	};

	service.deleteById = function(id){
		var deferred = $q.defer();
		genericResource.deleteById(service.urlBase, id).success(function(data){
			deferred.resolve(data);
		}).error(function(error){
			deferred.reject("Can not create, be sure that the aptAptmtNotification name is !")
		});
		return deferred.promise;
	};



	service.updateAptAptmtNotification = function(entity){
		var deferred = $q.defer();
		genericResource.update(service.urlBase, entity).success(function(data){
			deferred.resolve(data);
		}).error(function(){
			deferred.reject("Can not update")
		});
		return deferred.promise;
	};

	service.loadAptAptmtNotification = function(identif){
		var deferred = $q.defer();
		genericResource.findById(service.urlBase, identif).success(function(data){
			deferred.resolve(data);
		}).error(function(){
			deferred.reject("Can not update")
		});
		return deferred.promise;
	};

	service.loadAptAptmtNotifications = function(searchInput){
		var deferred = $q.defer();
		genericResource.findBy(service.urlBase, searchInput).success(function(data){
			deferred.resolve(data);
		}).error(function(){
			deferred.reject("Can not update")
		});
		return deferred.promise;
	};
	
	 service.loadAptAptmtNotification = function(id){
	    	
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

	service.findAptAptmtNotifications = function(searchInput){
		var deferred = $q.defer();
		genericResource.findByLike(service.urlBase, searchInput)
		.success(function(data, status, headers, config){
			deferred.resolve(data);
		}).error(function(data, status, headers, config){
			deferred.reject("An error occured while fetching appointments");
		});
		return deferred.promise;
	};

	service.loadNotifications = function(fullname){
		return genericResource.findByLikePromissed(service.urlBase, 'fullname',fullname)
		.then(function(entitySearchResult){
			if(!angular.isUndefined(entitySearchResult))
				return entitySearchResult.resultList;
			else return "";
		});
	};

	service.nextAptAptmtNotification = function(id){
		var deferred = $q.defer();

		$http.get(service.urlBase+'/nextNotification/'+id)
		.success(function(data){
			deferred.resolve(data);
		}).error(function(data){
			deferred.reject("no more appointment !")
		});
		return deferred.promise;
	}

	service.previousAptAptmtNotification = function(id){
		var deferred = $q.defer();

		$http.get(service.urlBase+'/previousNotification/'+id)
		.success(function(data){
			deferred.resolve(data);
		}).error(function(data){
			deferred.reject("no more appointment !")
		});
		return deferred.promise;
	}



	return service;
}]);