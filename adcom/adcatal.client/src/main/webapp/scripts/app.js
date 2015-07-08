'use strict';

angular.module('AdCatal', [
      'ngRoute',
      'ngCookies',
      'SessionManager',
      'ui.bootstrap',
      'AuthInterceptor',
      'ngSanitize',
      'pascalprecht.translate',
      'NavBar',
       'ngResource'
])
.constant('APP_CONFIG',{
	'appName':'Product Catalogue',
	'appVersion':'1.0.0-SNAPSHOT'

})

.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/CatalArticle/CatalArticles.html',controller:'catalArticlesCtlr'})
      .when('/CatalArticles/new',{templateUrl:'views/CatalArticle/CatalArticleCreate.html',controller:'catalArticleCreateCtlr'})
      .when('/CatalArticles',{templateUrl:'views/CatalArticle/CatalArticles.html',controller:'catalArticlesCtlr'})
      .when('/CatalArticles/edit/:pic',{templateUrl:'views/CatalArticle/CatalArticleEdit.html',controller:'catalArticleEditCtlr'})
      .when('/CatalArticles/show/:pic',{templateUrl:'views/CatalArticle/CatalArticleShow.html',controller:'catalArticleShowCtlr'})
//      .when('/CatalArtDetailConfigs',{templateUrl:'views/CatalArtDetailConfig/search.html',controller:'SearchCatalArtDetailConfigController'})
//      .when('/CatalArtDetailConfigs/new',{templateUrl:'views/CatalArtDetailConfig/detail.html',controller:'NewCatalArtDetailConfigController'})
//      .when('/CatalArtDetailConfigs/edit/:CatalArtDetailConfigId',{templateUrl:'views/CatalArtDetailConfig/detail.html',controller:'EditCatalArtDetailConfigController'})
//      .when('/CatalArtEquivalences',{templateUrl:'views/CatalArtEquivalence/search.html',controller:'SearchCatalArtEquivalenceController'})
//      .when('/CatalArtEquivalences/new',{templateUrl:'views/CatalArtEquivalence/detail.html',controller:'NewCatalArtEquivalenceController'})
//      .when('/CatalArtEquivalences/edit/:CatalArtEquivalenceId',{templateUrl:'views/CatalArtEquivalence/detail.html',controller:'EditCatalArtEquivalenceController'})
//      .when('/CatalArtFeatMappings',{templateUrl:'views/CatalArtFeatMapping/search.html',controller:'SearchCatalArtFeatMappingController'})
//      .when('/CatalArtFeatMappings/new',{templateUrl:'views/CatalArtFeatMapping/detail.html',controller:'NewCatalArtFeatMappingController'})
//      .when('/CatalArtFeatMappings/edit/:CatalArtFeatMappingId',{templateUrl:'views/CatalArtFeatMapping/detail.html',controller:'EditCatalArtFeatMappingController'})
//      .when('/CatalArtManufSupps',{templateUrl:'views/CatalArtManufSupp/search.html',controller:'SearchCatalArtManufSuppController'})
//      .when('/CatalArtManufSupps/new',{templateUrl:'views/CatalArtManufSupp/detail.html',controller:'NewCatalArtManufSuppController'})
//      .when('/CatalArtManufSupps/edit/:CatalArtManufSuppId',{templateUrl:'views/CatalArtManufSupp/detail.html',controller:'EditCatalArtManufSuppController'})
//      .when('/CatalFamilyFeatMapings',{templateUrl:'views/CatalFamilyFeatMaping/search.html',controller:'SearchCatalFamilyFeatMapingController'})
//      .when('/CatalFamilyFeatMapings/new',{templateUrl:'views/CatalFamilyFeatMaping/detail.html',controller:'NewCatalFamilyFeatMapingController'})
//      .when('/CatalFamilyFeatMapings/edit/:CatalFamilyFeatMapingId',{templateUrl:'views/CatalFamilyFeatMaping/detail.html',controller:'EditCatalFamilyFeatMapingController'})
//      .when('/CatalManufSuppls',{templateUrl:'views/CatalManufSuppl/search.html',controller:'SearchCatalManufSupplController'})
//      .when('/CatalManufSuppls/new',{templateUrl:'views/CatalManufSuppl/detail.html',controller:'NewCatalManufSupplController'})
//      .when('/CatalManufSuppls/edit/:CatalManufSupplId',{templateUrl:'views/CatalManufSuppl/detail.html',controller:'EditCatalManufSupplController'})
//      .when('/CatalPicMappings',{templateUrl:'views/CatalPicMapping/search.html',controller:'SearchCatalPicMappingController'})
//      .when('/CatalPicMappings/new',{templateUrl:'views/CatalPicMapping/detail.html',controller:'NewCatalPicMappingController'})
//      .when('/CatalPicMappings/edit/:CatalPicMappingId',{templateUrl:'views/CatalPicMapping/detail.html',controller:'EditCatalPicMappingController'})
      .when('/CatalPkgModes',{templateUrl:'views/CatalPkgMode/search.html',controller:'catalPkgModeCtrl'})
      .when('/CatalPkgModes/new',{templateUrl:'views/CatalPkgMode/create.html',controller:'catalPkgModeCreateCtrl'})
      .when('/CatalPkgModes/edit/:identif',{templateUrl:'views/CatalPkgMode/edit.html',controller:'catalPkgModeEditCtrl'})
      .when('/CatalPkgModes/show/:identif',{templateUrl:'views/CatalPkgMode/show.html',controller:'catalPkgModeShowCtrl'})
      .when('/CatalProductFamilies',{templateUrl:'views/CatalProductFamily/search.html',controller:'SearchCatalProductFamilyController'})
      .when('/CatalProductFamilies/new',{templateUrl:'views/CatalProductFamily/detail.html',controller:'NewCatalProductFamilyController'})
      .when('/CatalProductFamilies/edit/:CatalProductFamilyId',{templateUrl:'views/CatalProductFamily/detail.html',controller:'EditCatalProductFamilyController'})
      .otherwise({ redirectTo: '/' });
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: '{part}/locale-{lang}.json'
    });

	
    
}])
//
//.controller('NavController', function NavController($scope, $location) {
//    $scope.matchesRoute = function(route) {
//        var path = $location.path();
//        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
//    };
//})
//
.run(['$rootScope', '$location','sessionManager','$translate','APP_CONFIG','$translatePartialLoader',
      function ($rootScope, $location, sessionManager,$translate,APP_CONFIG,$translatePartialLoader) {
    $rootScope.appName = APP_CONFIG.appName ;
    $rootScope.appVersion = APP_CONFIG.appVersion ;
    $translatePartialLoader.addPart('/adcatal.client/i18n/main');
    sessionManager.appMenuUrl("/adcatal.client/menu.html");
    $rootScope.sessionManager = sessionManager;
    $rootScope.$on('$locationChangeStart', function (event, next, current) {
    	var noSess = !sessionManager.hasValues(sessionManager.terminalSession(), sessionManager.userSession());
    	if(noSess){
			var sessParam = $location.search();
			if(sessParam && sessionManager.hasValues(sessParam.trm,sessParam.usr)){
				sessionManager.wsin(sessParam.trm,sessParam.usr,
					function(data, status, headers, config){
						sessionManager.language(headers('X-USER-LANG'),false);
						$location.path('/');
					}
				);
			}
    	}
    });
}]);
