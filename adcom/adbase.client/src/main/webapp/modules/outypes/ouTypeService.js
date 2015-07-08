'use strict';

angular.module('AdBase')
.factory('ouTypeService',
    ['ouTypeResource','$q',
    function (ouTypeResource,$q) {
        var service = {};
          service.findActifsFromNow = function(){
            var deferred = $q.defer();
            ouTypeResource.findActifsFromNow().success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when searching actives items."); 
            });
            return deferred.promise;
    	};
    	return service;
    }]
);
