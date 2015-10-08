var module = angular.module('product',[]);

var auth = {};
var logout = function(){
    console.log('*** LOGOUT');
    auth.loggedIn = false;
    auth.authz = null;
    window.location = auth.logoutUrl;
};


angular.element(document).ready(function ($http) {
    var keycloakAuth = new Keycloak('rest/keycloak.json');
    auth.loggedIn = false;

    keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
        auth.loggedIn = true;
        auth.authz = keycloakAuth;
        auth.logoutUrl = keycloakAuth.authServerUrl + "/realms/adcom/tokens/logout?redirect_uri=/adcatal.client/index.html";
        module.factory('Auth', function() {
            return auth;
        });
        angular.bootstrap(document, ["product"]);
    }).error(function () {
            window.location.reload();
        });

});

module.controller('GlobalCtrl', function($scope, endpointClient, searchResultHandler){
    $scope.resultHandler = searchResultHandler.newResultHandler();
    $scope.reloadData = function() {
    	endpointClient.listAll('/adcatal.server/rest/catalarticles',0,10).success(function(data) {
    		$scope.resultHandler.searchResult(data);
        });
    };
    $scope.logout = logout;
});

module.factory('authInterceptor', function($q, Auth) {
    return {
        request: function (config) {
            var deferred = $q.defer();
            if (Auth.authz.token) {
                Auth.authz.updateToken(5).success(function() {
                    config.headers = config.headers || {};
                    config.headers.Authorization = 'Bearer ' + Auth.authz.token;

                    deferred.resolve(config);
                }).error(function() {
                        deferred.reject('Failed to refresh token');
                    });
            }
            return deferred.promise;
        }
    };
});




module.config(function($httpProvider) {
    $httpProvider.responseInterceptors.push('errorInterceptor');
    $httpProvider.interceptors.push('authInterceptor');

});

module.factory('errorInterceptor', function($q) {
    return function(promise) {
        return promise.then(function(response) {
            return response;
        }, function(response) {
            if (response.status == 401) {
                console.log('session timeout?');
                logout();
            } else if (response.status == 403) {
                alert("Forbidden");
            } else if (response.status == 404) {
                alert("Not found");
            } else if (response.status) {
                if (response.data && response.data.errorMessage) {
                    alert(response.data.errorMessage);
                } else {
                    alert("An unexpected server error has occurred");
                }
            }
            return $q.reject(response);
        });
    };
});

module.factory('mathUtils',function(){
    var service = {};
    service.greaterThan = function(a,b){
    	if(typeof a === 'undefined') return false;
    	if(typeof b === 'undefined') return a>-1;
    	return a>b;
    };
    return service;
});

module.factory('endpointClient', function($http,$q){
    var service = {};

    // Returns created entity
    service.create = function(urlBase, entity){
        return $http.post(urlBase,entity);
    };

    // Returns 200 + Deleted Entity
    service.deleteById = function(urlBase, id){
        return $http.delete(urlBase + '/' + id);
    };

    // Returns update entity
    service.update = function(urlBase, entity){
        return $http.put(urlBase+'/'+entity.id,entity);
    };

    // Returns NOT_FOUND(404, "Not Found")
    // Returns found entity
    service.findById = function(urlBase, id){
        return $http.get(urlBase + '/' + id);
    };
    
    // Returns NOT_FOUND(404, "Not Found")
    // Returns found entity
    service.findByIdentif = function(urlBase, identif){
        return $http.get(urlBase+'/findByIdentif/'+identif);
    };
    

    // Returns Long
    service.count = function(urlBase){
        return $http.get(urlBase + '/count');
    };

    // Returns CoreAbstIdentifObjectSearchResult<E>
    service.listAll = function(urlBase, start, max){
        return $http.get(urlBase+'?start='+start+'&max='+max);
    };
    
    // Returns Long
    service.countBy = function(urlBase, searchInput){
        return $http.post(urlBase+'/countBy',searchInput);
    };
    
    // Returns CoreAbstIdentifObjectSearchResult<E>
    service.findBy = function(urlBase, searchInput){
        return $http.post(urlBase+'/findBy',searchInput);
    };
    
    // Returns Long
    service.countByLike = function(urlBase, searchInput){
        return $http.post(urlBase+'/countByLike',searchInput);
    };
    
    // ===== FIND BY LIKE =====//
    // Returns CoreAbstIdentifObjectSearchResult<E>
    service.findByLike = function(urlBase, searchInput){
        return $http.post(urlBase+'/findByLike',searchInput);
    };
    // Returns a promise of CoreAbstIdentifObjectSearchResult<E>
    service.findByLikePromissed = function (urlBase, fieldName, fieldValue){
    	if(angular.isUndefined(urlBase) || !urlBase ||
    		angular.isUndefined(fieldName) || !fieldName || 
    		angular.isUndefined(fieldValue) || !fieldValue) return;
          
        var searchInput = {entity:{},fieldNames:[],start:0,max:10};
        searchInput.entity[fieldName]= fieldValue;
        if(searchInput.fieldNames.indexOf(fieldName)==-1)
        	searchInput.fieldNames.push(fieldName);
          var deferred = $q.defer();
          service.findByLike(urlBase, searchInput)
          .success(function(entitySearchResult) {
              deferred.resolve(entitySearchResult);
            })
          .error(function(error){
              deferred.reject('No entity found');
          });
      return deferred.promise;
    }
    // Returns a promise of CoreAbstIdentifObjectSearchResult<E>
    service.findByLikeWithSearchInputPromissed = function (urlBase, searchInput){
    	if(angular.isUndefined(urlBase) || !urlBase ||
    		angular.isUndefined(searchInput) || !searchInput) return;          
          var deferred = $q.defer();
          service.findByLike(urlBase, searchInput)
          .success(function(entitySearchResult) {
              deferred.resolve(entitySearchResult);
            })
          .error(function(error){
              deferred.reject('No entity found');
          });
      return deferred.promise;
    }

    // ===== FIND CUSTOM =====//
    service.findCustom = function(urlBase, searchInput){
        return $http.post(urlBase+'/findCustom',searchInput);
    };
    service.findCustomPromissed = function (urlBase, fieldName, fieldValue){
    	if(angular.isUndefined(urlBase) || !urlBase ||
    		angular.isUndefined(fieldName) || !fieldName || 
    		angular.isUndefined(fieldValue) || !fieldValue) return;
          
        var searchInput = {entity:{},fieldNames:[],start:0,max:10};
        searchInput.entity[fieldName]= fieldValue;
        if(searchInput.fieldNames.indexOf(fieldName)==-1)
        	searchInput.fieldNames.push(fieldName);
          var deferred = $q.defer();
          service.findCustom(urlBase, searchInput)
          .success(function(entitySearchResult) {
              deferred.resolve(entitySearchResult);
            })
          .error(function(error){
              deferred.reject('No entity found');
          });
      return deferred.promise;
    }
    service.findCustomWithSearchInputPromissed = function (urlBase, searchInput){
    	if(angular.isUndefined(urlBase) || !urlBase ||
    		angular.isUndefined(searchInput) || !searchInput) return;          
          var deferred = $q.defer();
          service.findCustom(urlBase, searchInput)
          .success(function(entitySearchResult) {
              deferred.resolve(entitySearchResult);
            })
          .error(function(error){
              deferred.reject('No entity found');
          });
      return deferred.promise;
    }

    return service;
    
});

module.factory('searchResultHandler',function(mathUtils){
    var service = {};
    service.newResultHandler = function(){
    	return new ResultHandler(); 
    };

    var ResultHandler = function(){
        var handler = this;
        var itemPerPageVar = 10;
        var currentPageVar = 1;
        var nakedSearchResult = {
	    	count:0,
	    	total:0,
	    	resultList:[],
	    	className:'',
	    	searchInput:{
	    		entity:{},
	    		start:0,
	    		max:itemPerPageVar,
	    		fieldNames:[],
	    		sortFieldNames:[],
	    		className:'',
	    	},
	    	// not exposed to the server environment.
	    	currentPage:currentPageVar,
	    	itemPerPage:itemPerPageVar,
	    	selectedIndex:-1
        };
        var searchResultVar = angular.copy(nakedSearchResult);
        var keyField = 'identif';
        var displayInfoVar = {};
        var equalsFnct = function(entityA, entityB){
			if(!entityA && !entityB) return true;
			if(!entityB) return false;
			return entityA[keyField]==entityB[keyField];
        };
        var dependents = {};
        function setSelectedIndex(selectedIndexIn){
        	searchResultVar.selectedIndex=selectedIndexIn;
        	dependents = {};
        }
        this.searchResult = function(searchResultIn){
        	if(searchResultVar===searchResultIn) return;
        	if(angular.isUndefined(searchResultIn)) return;
        	
    		searchResultVar.count = searchResultIn.count;
    		searchResultVar.total = searchResultIn.total;
    		
    		angular.copy(searchResultIn.resultList, searchResultVar.resultList);
    		
    		angular.copy(searchResultIn.searchInput, searchResultVar.searchInput);

    		if(angular.isDefined(searchResultIn.currentPage)){
    			searchResultVar.currentPage=searchResultIn.currentPage;
    		} else {
    			searchResultVar.currentPage=currentPageVar;
    		}

    		if(angular.isDefined(searchResultIn.itemPerPage)){
    			searchResultVar.itemPerPage=searchResultIn.itemPerPage;
    		} else {
    			searchResultVar.itemPerPage=itemPerPageVar;
    		}
    		
    		if(angular.isDefined(searchResultIn.selectedIndex)){
    			setSelectedIndex(searchResultIn.selectedIndex);
    		} else {
    			setSelectedIndex(-1);
    		}
        };
        this.hasEntities = function(){
            return searchResultVar.resultList && searchResultVar.resultList.length>0;
        };
        this.entities = function(){
        	return searchResultVar.resultList;
        };
        this.selectedIndex= function(selectedIndexIn){
            if(mathUtils.greaterThan(selectedIndexIn) && angular.isDefined(searchResultVar.resultList[selectedIndexIn])){
            	setSelectedIndex(selectedIndexIn);
            }
            return searchResultVar.selectedIndex;
        };
        this.selectedObject= function(selectedIn){
        	if(angular.isUndefined(selectedIn))return searchResultVar.selectedIndex; 
    		var length = searchResultVar.resultList.length;
    		for	(var index = 0; index < length; index++) {
    			if(!equalsFnct(selectedIn, searchResultVar.resultList[index])) continue;
    			searchResultVar.selectedIndex = index;
    			return searchResultVar.selectedIndex; 
    		}
    		length = searchResultVar.resultList.push(selectedIn);
    		searchResultVar.count +=1;
    		searchResultVar.selectedIndex = length -1;
            return searchResultVar.selectedIndex;
        };
        this.entity = function(){
        	if(!mathUtils.greaterThan(searchResultVar.selectedIndex)) return;
        	if(angular.isUndefined(searchResultVar.resultList[searchResultVar.selectedIndex])) return;
        	return searchResultVar.resultList[searchResultVar.selectedIndex];
        };
        this.totalItems = function(){
            return searchResultVar.count;
        };
        this.currentPage = function(currentPageIn){
            if(mathUtils.greaterThan(currentPageIn,-1)) searchResultVar.currentPage=currentPageIn;
            return searchResultVar.currentPage;
        };
        this.maxResult = function(maxResultIn) {
            if(mathUtils.greaterThan(maxResultIn,-1)) searchResultVar.searchInput.max=maxResultIn;
            return searchResultVar.searchInput.max;
        };
        this.itemPerPage = function(itemPerPageIn){
            if(mathUtils.greaterThan(itemPerPageIn,-1))searchResultVar.itemPerPage=itemPerPageIn;
            return searchResultVar.itemPerPage;
        };
        this.searchInput = function(searchInputIn){
            if(angular.isUndefined(searchInputIn))
                return angular.copy(searchResultVar.searchInput);

    		angular.copy(searchInputIn, searchResultVar.searchInput);
            return searchInputIn;
        };
        this.searchInputChanged = function(searchInputIn){
            return angular.equals(searchResultVar.searchInput, searchInputIn);
        };
        this.newSearchInput = function(){
            return angular.copy(nakedSearchResult.searchInput);
        };
        this.paginate = function(){
        	searchResultVar.searchInput.start = ((searchResultVar.currentPage - 1)  * searchResultVar.itemPerPage);
        	searchResultVar.searchInput.max = searchResultVar.itemPerPage;
            return handler.searchInput();
        };
        this.indexOf = function(entity){
        	if(angular.isUndefined(entity) || !entity) return -1;
    		var length = searchResultVar.resultList.length;
    		for	(var index = 0; index < length; index++) {
    			if(equalsFnct(entity, searchResultVar.resultList[index])) return index;
    		}
    		return -1;
        };
        // Replaces the object at the specified index.
        this.replace = function(entity){
        	if(angular.isUndefined(entity) || !entity) return;
        	var index = handler.indexOf(entity);
        	if(angular.isUndefined(index) || index<0) return;
        	searchResultVar.resultList[index]=entity;
        	return index;
        };
        // Add the specified entity to the end of the list. Remove the copy
        // from the array if existent.
        this.push = function(entity){
        	if(angular.isUndefined(entity) || !entity) return;
        	var index = handler.indexOf(entity);
        	if(angular.isDefined(index) && index>=0){ // slice
        		searchResultVar.resultList.splice(index, 1);
        		searchResultVar.count -=1;
        	}
            var length = searchResultVar.resultList.push(entity);
    		searchResultVar.count +=1;
    		return length -1;
        };
        // Add the specified entity to the start of the list. Remove the copy
        // from the array if existent.
        this.unshift = function(entity){
        	if(angular.isUndefined(entity) || !entity) return;
        	var index = handler.indexOf(entity);
        	if(mathUtils.greaterThan(index) && index>=0){ // slice
        		searchResultVar.resultList.splice(index, 1);
        		searchResultVar.count -=1;
        	}
            var length = searchResultVar.resultList.unshift(entity);
    		searchResultVar.count +=1;
    		return 0;
        }
        this.previous = function (){
        	if(searchResultVar.resultList.length<=0) return;

            if(searchResultVar.selectedIndex<=0){
            	setSelectedIndex(searchResultVar.resultList.length-1);
            } else {
            	setSelectedIndex(searchResultVar.selectedIndex-=1);
            }
            return handler.entity();
        };
        this.next = function(){
        	if(searchResultVar.resultList.length<=0) return;
        	
        	if(searchResultVar.selectedIndex>=searchResultVar.resultList.length-1 || searchResultVar.selectedIndex<0){
        		setSelectedIndex(0);
        	} else {
        		setSelectedIndex(searchResultVar.selectedIndex+=1);
        	}

            return handler.entity();
        };
        this.unsetDependent = function(fieldName){
        	if(angular.isUndefined(fieldName)) return;
        	
        	if(angular.isDefined(dependents[fieldName]))
        		delete dependents[fieldName];
        };
        this.dependent = function(fieldName, dependentIn){
        	if(angular.isUndefined(fieldName)) return;
        	
        	if(angular.isDefined(dependentIn) && dependentIn)
        		dependents[fieldName] = dependentIn;

        	return dependents[fieldName];
        };
        this.displayInfo = function(displayInfoIn){
        	if(angular.isDefined(displayInfoIn) && displayInfoIn)
        		displayInfoVar = displayInfoIn;
        	return displayInfoVar;
        };
    };
    
    return service;
	
});

module.factory('dependentTabManager',function(){
    var service = {};
    
    // Instantiates a new TabManager
    service.newTabManager = function(tabNameList){
    	return new TabManager(tabNameList); 
    };

    // Create a new tab manager with a tab name list.
    var TabManager = function(tabNameListIn){
    	if(!angular.isArray(tabNameListIn) || tabNameListIn.length<1)
    		throw "Tab manager expecting an array of string, with tab name.";
    	var tabNameList = tabNameListIn;
        var activeTabNameVar= tabNameList[0];
        
        this.activeTab=function(activeTabNameIn){
        	if(angular.isDefined(activeTabNameIn) && tabNameList.indexOf(activeTabNameIn)>-1)
        		activeTabNameVar=activeTabNameIn;
        	return activeTabNameVar;
        };
        
        this.isActive = function(tabName){
        	if(angular.isDefined(tabName) && tabNameList.indexOf(tabName)>-1)
        		return angular.equals(activeTabNameVar,tabName);

        	return false;
        };
    };
    
    return service;
});
