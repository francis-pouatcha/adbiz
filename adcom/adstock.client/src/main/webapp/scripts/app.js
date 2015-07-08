'use strict';

angular.module('AdStock', [
      'ngRoute',
      'ngCookies',
      'ui.bootstrap',
      'SessionManager',
      'AuthInterceptor',
      'ngSanitize',
      'pascalprecht.translate',
      'NavBar',
       'ngResource',
       'datePicker',
       'ADUtils',
        'AdCatal'
])
.constant('APP_CONFIG',{
	'appName':'Stock',
	'appVersion':'1.0.0-SNAPSHOT'
})
.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider.otherwise({redirectTo: '/' });
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: '{part}/locale-{lang}.json'
    });
}])
.run(['$rootScope', '$location','sessionManager','$translate','APP_CONFIG','$translatePartialLoader','commonTranslations',
      function ($rootScope, $location, sessionManager,$translate,APP_CONFIG,$translatePartialLoader, commonTranslations) {
    $rootScope.appName = APP_CONFIG.appName ;
    $rootScope.appVersion = APP_CONFIG.appVersion ;
    sessionManager.appMenuUrl("/adstock.client/menu.html");
    $translatePartialLoader.addPart('/adstock.client/i18n/main');
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
