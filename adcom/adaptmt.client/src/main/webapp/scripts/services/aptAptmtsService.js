'use strict';

angular.module('adaptmt')


.factory('aptAptmtsService',['sessionManager','$translate','genericResource','$q', '$http',function(sessionManager,$translate,genericResource,$q,$http){
	var service = {};

	service.urlBase='/adaptmt.server/rest/aptaptmts';
	service.loginnamessUrlBase = '/adbase.server/rest/loginnamess';
	service.loginsUrlBase = '/adbase.server/rest/logins';

	service.create = function(entity){
		var deferred = $q.defer();
		genericResource.create(service.urlBase, entity).success(function(data){
			deferred.resolve(data);
		}).error(function(error){
			deferred.reject("Can not create, be sure that the aptAptmt name is !")
		});
		return deferred.promise;
	};

	service.updateAptAptmt = function(entity){
		var deferred = $q.defer();
		genericResource.update(service.urlBase, entity).success(function(data){
			deferred.resolve(data);
		}).error(function(){
			deferred.reject("Can not update")
		});
		return deferred.promise;
	};

	service.close = function(entity){
		var deferred = $q.defer();
		genericResource.update(service.urlBase + "/close", entity).success(function(data){
			deferred.resolve(data);
		}).error(function(){
			deferred.reject("Can not close")
		});
		return deferred.promise;
	};

	service.loadAptAptmt = function(identif){
		var deferred = $q.defer();
		genericResource.findById(service.urlBase, identif).success(function(data){
			deferred.resolve(data);
		}).error(function(){
			deferred.reject("Can not update")
		});
		return deferred.promise;
	};

	service.loadAptAptmts = function(searchInput){
		var deferred = $q.defer();
		genericResource.findCustom(service.urlBase, searchInput).success(function(data){
			deferred.resolve(data);
		}).error(function(){
			deferred.reject("Can not update")
		});
		return deferred.promise;
	};

	service.loadAptAptmtBnsPtnrs = function(identif){
		var deferred = $q.defer();

		$http.get(service.urlBase + '/bnsptnrs/' + identif)
		.success(function(data){
			deferred.resolve(data);
			console.log(" data to populate (success) : " + data);
		}).error(function(data){
			console.log(" data to populate (error) : " + data);
			deferred.reject("Can not update BnsPtnrs of current AptAptmt !")
		});
		return deferred.promise;
	}
	
	service.getCurrentOrgUnit = function(){
		var deferred = $q.defer();

		$http.get(service.urlBase +"/getcurrentorgunit")
		.success(function(data){
			deferred.resolve(data);
			console.log(" data to populate (success) : " + data);
		}).error(function(data){
			console.log(" data to populate (error) : " + data);
			deferred.reject("Can not load  the current Login !")
		});
		return deferred.promise;
	}


	service.findAptAptmts = function(searchInput){
		var deferred = $q.defer();
		genericResource.findByLike(service.urlBase, searchInput)
		.success(function(data, status, headers, config){
			deferred.resolve(data);
		}).error(function(data, status, headers, config){
			deferred.reject("An error occured while fetching appointments");
		});
		return deferred.promise;
	};

	service.nextAptAptmt = function(id){
		var deferred = $q.defer();

		$http.get(service.urlBase+'/nextLogin/'+id)
		.success(function(data){
			deferred.resolve(data);
		}).error(function(data){
			deferred.reject("no more appointment !")
		});
		return deferred.promise;
	}

	service.previousAptAptmt = function(id){
		var deferred = $q.defer();

		$http.get(service.urlBase+'/previousLogin/'+id)
		.success(function(data){
			deferred.resolve(data);
		}).error(function(data){
			deferred.reject("no more appointment !")
		});
		return deferred.promise;
	}

	service.loadUsers = function(loginName){
		var deferred = $q.defer();
		genericResource.findByLikePromissed(service.loginnamessUrlBase,'loginName',loginName)
		.then(function(data){
			console.log(data);
			if(!angular.isUndefined(data)){
				deferred.resolve(data.resultList); 			
			}else return "";
		}, function(error){
			deferred.reject("no find logins !")

		});
		return deferred.promise;
		
	}
	
	service.loadLogins = function(searchInput){
		var deferred = $q.defer();
		genericResource.listAll(service.loginsUrlBase,searchInput)
		.then(function(data){
			console.log(data);
			if(!angular.isUndefined(data)){
				deferred.resolve(data.resultList); 			
			}else return "";
		}, function(error){
			deferred.reject("no find logins !")

		});
		return deferred.promise;
		
	}


	return service;
}]);