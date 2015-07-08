'use strict';

angular.module('Admanager')
    .factory('LoginConfigurationUtils', ['sessionManager', '$translate', 'genericResource', '$q', '$http', function (sessionManager, $translate, genericResource, $q, $http) {
        var service = {};

        service.loginConfiguration = '/adbase.server/rest/loginconfigurations';
        service.login = '/adbase.server/rest/logins';
        service.language = sessionManager.language;

        service.create = function(entity){
            var deferred = $q.defer();
            genericResource.create(service.loginConfiguration, entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create, be sure that the loginConfiguration is correct !")
            });
            return deferred.promise;
        };
        
        service.update = function(entity){
            var deferred = $q.defer();
            genericResource.update(service.loginConfiguration, entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update loginConfiguration")
            });
            return deferred.promise;
        };
        
        service.loadLoginConfiguration = function(identif){
            var deferred = $q.defer();
             genericResource.findById(service.loginConfiguration, identif).success(function(data){
                 deferred.resolve(data);
             }).error(function(){
                 deferred.reject("Can not load loginConfiguration")
             });
             return deferred.promise;
         };
         
         service.loadLoginConfigurations = function(searchInput){
             var deferred = $q.defer();
              genericResource.findByLike(service.loginConfiguration, searchInput).success(function(data){
                  deferred.resolve(data);
              }).error(function(){
                  deferred.reject("Can not load loginConfigurations")
              });
              return deferred.promise;
          };
          
          service.loadLogin = function(value){
              return genericResource.findByLikePromissed(service.login, "loginName", value)
                  .then(function (entitySearchResult) {
                      return entitySearchResult.resultList;
                  })

          };

        service.nextLoginConfiguration = function(id){
            var deferred = $q.defer();
            $http.get(service.loginConfiguration+'/nextLogin/'+id)
                .success(function(data){
                    deferred.resolve(data);
                }).error(function(data){
                    deferred.reject("no more loginConfiguration !")
                });
            return deferred.promise;
        };

        service.previousLoginConfiguration = function(id){
            var deferred = $q.defer();
            $http.get(service.loginConfiguration+'/previousLogin/'+id)
                .success(function(data){
                    deferred.resolve(data);
                }).error(function(data){
                    deferred.reject("no more loginConfiguration !")
                });
            return deferred.promise;
        };

        service.translate = function () {
            $translate([
                    'LoginConfiguration_loginName_description.title',
                    'LoginConfiguration_loginName_description.text',
                    'LoginConfiguration_maxRebate_description.title',
                    'LoginConfiguration_maxRebate_description.text',
                    
                    'Entity_show.title',
                    'Entity_previous.title',
                    'Entity_list.title',
                    'Entity_next.title',
                    'Entity_edit.title',
                    'Entity_create.title',
                    'Entity_update.title',
                    'Entity_Result.title',
                    'Entity_search.title',
                    'Entity_cancel.title',
                    'Entity_save.title',
                    'Entity_By.title',
                    'Entity_saveleave.title',
                    'Entity_add.title'
                 ])
                .then(function (translations) {
                    service.translations = translations;
                });
        };
        service.translate();
        return service;
}])
.controller('LoginConfigurationCtrls', ['$scope', 'genericResource', 'LoginConfigurationUtils',
function ($scope, genericResource, LoginConfigurationUtils) {

	$scope.searchInput = {};
	$scope.loginConfigurations = [];
	$scope.totalItems = 0;
    $scope.LoginConfigurationUtils = LoginConfigurationUtils;
	$scope.loginConfigurationsearchResults = {};
	$scope.itemPerPage=25;
	$scope.currentPage = 1;
	$scope.maxSize = 5 ;
	$scope.handleSearchRequestEvent = handleSearchRequestEvent;
	$scope.handlePrintRequestEvent = handlePrintRequestEvent;
	$scope.paginate = paginate;
	
	function init(){
		$scope.searchInput = {
                entity:{},
                fieldNames:[],
                start:0,
                max:$scope.itemPerPage
        }

		findByLike($scope.searchInput);
   };
   
   init();
   
   function findByLike(searchInput){
	   LoginConfigurationUtils.loadLoginConfigurations(searchInput).then(function(entitySearchResult) {
		   $scope.loginConfigurations = entitySearchResult.resultList;
       	   $scope.totalItems = entitySearchResult.count;
       });
   }
   
   function processSearchInput(){
       var fieldNames = [];
       if($scope.searchInput.entity.loginName){
    	   fieldNames.push('loginName') ;
       }
       $scope.searchInput.fieldNames = fieldNames ;
       return $scope.searchInput;
   };
   
   function  handleSearchRequestEvent(){
       processSearchInput();
       findByLike($scope.searchInput);
  };
  
  function paginate(){
	  $scope.searchInput.start = (($scope.currentPage - 1)  * $scope.itemPerPage) ;
	  $scope.searchInput.max = $scope.itemPerPage ;
      findByLike($scope.searchInput);
  };


  function handlePrintRequestEvent(){

  };


}])
.controller('LoginConfigurationCreateCtrls', ['$scope', 'genericResource', 'LoginConfigurationUtils', '$location',
function ($scope, genericResource, LoginConfigurationUtils, $location) {

	$scope.loginConfiguration = {};
	$scope.create = create;
	$scope.error = "";
    $scope.LoginConfigurationUtils = LoginConfigurationUtils;

    function create(){
    	LoginConfigurationUtils.create($scope.loginConfiguration).then(function(result){
             $location.path('/loginConfiguration/show/'+result.identif);
         },function(error){
        	 $scope.error = error;
         })
     };

}])
.controller('LoginConfigurationShowCtrls', ['$scope', 'LoginConfigurationUtils', '$location', '$routeParams',
function ($scope, LoginConfigurationUtils, $location, $routeParams) {

	$scope.loginConfiguration = {};
	$scope.show = show;
	$scope.previous = previous;
	$scope.next = next;
	$scope.error = "";
    $scope.LoginConfigurationUtils = LoginConfigurationUtils;

    init();

    function init(){
        show();
    };

    function show(){
        var identif = $routeParams.identif ;
        LoginConfigurationUtils.loadLoginConfiguration(identif).then(function(result){
        	$scope.loginConfiguration = result;
        })
    };
    
    function previous(){
    	$scope.error = "";
    	LoginConfigurationUtils.previousLoginConfiguration($scope.loginConfiguration.loginName).then(function(result){
    		$scope.loginConfiguration = result;
        },function(error){
        	$scope.error = error;
        })
    };

    function next(){
    	$scope.error = "";
    	LoginConfigurationUtils.nextLoginConfiguration($scope.loginConfiguration.loginName).then(function(result){
    		$scope.loginConfiguration = result;
        },function(error){
        	$scope.error = error;
        })
    };

}])
.controller('LoginConfigurationUpdateCtrls', ['$scope', 'genericResource', 'LoginConfigurationUtils', '$location', '$routeParams',
function ($scope, genericResource, LoginConfigurationUtils, $location, $routeParams) {

	$scope.loginConfiguration = {};
	$scope.edit = edit;
	$scope.error = "";
    $scope.LoginConfigurationUtils = LoginConfigurationUtils;

   function edit(){
	   LoginConfigurationUtils.update($scope.loginConfiguration).then(function(result){
           $location.path('/loginConfiguration/show/'+result.identif);
       },function(error){
    	   $scope.error = error;
       })
   };

   init();

   function init(){
       load();
   }

   function load(){
       var identif = $routeParams.identif ;
       LoginConfigurationUtils.loadLoginConfiguration(identif).then(function(result){
    	   $scope.loginConfiguration = result;
       },function(error){
    	   $scope.error = error;
       })
   };

}]);
  