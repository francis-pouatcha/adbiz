'use strict';

angular.module('AdAcc', [
      'ngRoute',
      'ngCookies',
      'SessionManager',
      'AuthInterceptor',
      'ngSanitize',
      'pascalprecht.translate',
      'NavBar'
])
.constant('APP_CONFIG',{
	'appName':'Business Partner',
	'appVersion':'1.0.0-SNAPSHOT'

})

.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
    .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
    .when('/AccAccounts',{templateUrl:'views/AccAccount/search.html',controller:'SearchAccAccountController'})
    .when('/AccAccounts/new',{templateUrl:'views/AccAccount/detail.html',controller:'NewAccAccountController'})
    .when('/AccAccounts/edit/:AccAccountId',{templateUrl:'views/AccAccount/detail.html',controller:'EditAccAccountController'})
    .when('/AccBlncs',{templateUrl:'views/AccBlnc/search.html',controller:'SearchAccBlncController'})
    .when('/AccBlncs/new',{templateUrl:'views/AccBlnc/detail.html',controller:'NewAccBlncController'})
    .when('/AccBlncs/edit/:AccBlncId',{templateUrl:'views/AccBlnc/detail.html',controller:'EditAccBlncController'})
    .when('/AccBrrs',{templateUrl:'views/AccBrr/search.html',controller:'SearchAccBrrController'})
    .when('/AccBrrs/new',{templateUrl:'views/AccBrr/detail.html',controller:'NewAccBrrController'})
    .when('/AccBrrs/edit/:AccBrrId',{templateUrl:'views/AccBrr/detail.html',controller:'EditAccBrrController'})
    .when('/AccCoAs',{templateUrl:'views/AccCoA/search.html',controller:'SearchAccCoAController'})
    .when('/AccCoAs/new',{templateUrl:'views/AccCoA/detail.html',controller:'NewAccCoAController'})
    .when('/AccCoAs/edit/:AccCoAId',{templateUrl:'views/AccCoA/detail.html',controller:'EditAccCoAController'})
    .when('/AccPstgs',{templateUrl:'views/AccPstg/search.html',controller:'SearchAccPstgController'})
    .when('/AccPstgs/new',{templateUrl:'views/AccPstg/detail.html',controller:'NewAccPstgController'})
    .when('/AccPstgs/edit/:AccPstgId',{templateUrl:'views/AccPstg/detail.html',controller:'EditAccPstgController'})
    .otherwise({
      redirectTo: '/'
    });
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: '{part}/locale-{lang}.json'
    });
}])

.controller('LandingPageController', function LandingPageController() {})

.controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
})

.run(['$rootScope', '$location','sessionManager','$translate','APP_CONFIG','$translatePartialLoader',
      function ($rootScope, $location, sessionManager,$translate,APP_CONFIG,$translatePartialLoader) {
    $rootScope.appName = APP_CONFIG.appName ;
    $rootScope.appVersion = APP_CONFIG.appVersion ;
    $translatePartialLoader.addPart('/adacc.client/i18n/main');
    sessionManager.appMenuUrl("/adacc.client/menu.html");
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
