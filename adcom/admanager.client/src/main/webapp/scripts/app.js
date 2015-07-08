'use strict';

angular.module('Admanager', [
      'ngRoute',
      'ngCookies',
      'SessionManager',
       'ui.bootstrap',
      'AuthInterceptor',
      'ngSanitize',
      'pascalprecht.translate',
      'NavBar',
      'datePicker',
      'httpProgress'
])
.constant('APP_CONFIG',{
	'appName':'Manager',
	'appVersion':'1.0.0-SNAPSHOT'

})

.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
    .when('/',{templateUrl:'views/CshDrawers.html',controller:'CshDrawerCtrls'})
    .when('/cdrCstmrVchr',{templateUrl:'views/CdrCstmrVchrs.html',controller:'CdrCstmrVchrCtrls'})
    .when('/loginConfiguration',{templateUrl:'views/LoginConfiguration/LoginConfigurations.html',controller:'LoginConfigurationCtrls'})
    .when('/loginConfiguration/create',{templateUrl:'views/LoginConfiguration/create.html',controller:'LoginConfigurationCreateCtrls'})
    .when('/loginConfiguration/show/:identif',{templateUrl:'views/LoginConfiguration/show.html',controller:'LoginConfigurationShowCtrls'})
    .when('/loginConfiguration/update/:identif',{templateUrl:'views/LoginConfiguration/update.html',controller:'LoginConfigurationUpdateCtrls'})
    .otherwise({redirectTo: '/'});
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    $httpProvider.interceptors.push('httpProgressInterceptor');
    
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
    sessionManager.appMenuUrl("/admanager.client/menu.html");
    $translatePartialLoader.addPart('/admanager.client/i18n/main');
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
