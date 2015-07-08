'use strict';

angular.module('AdCshdwr', [
      'ngRoute',
      'ngCookies',
      'ui.bootstrap',
      'SessionManager',
      'AuthInterceptor',
      'ngSanitize',
      'NavBar',
       'ngResource',
       'datePicker',
       'ADUtils',
        'pascalprecht.translate',
       'httpProgress'
])
.constant('APP_CONFIG',{
	'appName':'Cash Drawer',
	'appVersion':'1.0.0-SNAPSHOT'

})

.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
    .when('/CdrPymnts',{templateUrl:'views/CdrPymnt/CdrPymnts.html',controller:'cdrPaymntsCtlr'})
    .when('/CdrPymnts/new',{templateUrl:'views/CdrPymnt/createCdrPymnt.html',controller:'cdrPaymntCreateCtlr'})
    .when('/CdrPymnts/edit/:identif',{templateUrl:'views/CdrPymnt/editCdrPymnt.html',controller:'cdrPaymntEditCtlr'})
    .when('/SlsInvoices',{templateUrl:'views/SlsInvoice/SlsInvoices.html',controller:'cdrSlsInvoicesCtlr'})
    .when('/SlsInvoices/show',{templateUrl:'views/SlsInvoice/SlsInvoiceShow.html',controller:'cdrSlsInvoicesShowCtlr'})
    .when('/CdrCshDrawers',{templateUrl:'views/CdrCshDrawer/CdrCshDrawers.html',controller:'cdrCshDrawersCtlr'})
    .when('/CdrCshDrawers/new',{templateUrl:'views/CdrCshDrawer/createCdrCshDrawer.html',controller:'cdrCshDrawersCreateCtlr'})

    .when('/CdrCshDrawers/show/:id',{templateUrl:'views/CdrCshDrawer/showCdrCshDrawer.html',controller:'cdrCshDrawersEditCtlr'})                       .when('/CdrDrctSales',{templateUrl:'views/CdrDrctSale/CdrDrctSales.html',controller:'cdrDrctSalesCtlr'})
    .when('/CdrDrctSales/new/:origDsNbr',{templateUrl:'views/CdrDrctSale/createDrctSales.html',controller:'cdrDrctSalesCreateCtlr'})
        .when('/CdrDrctSales/new',{templateUrl:'views/CdrDrctSale/createDrctSales.html',controller:'cdrDrctSalesCreateCtlr'})
    .when('/CdrDrctSales',{templateUrl:'views/CdrDrctSale/CdrDrctSales.html',controller:'cdrDrctSalesCtlr'})
    .when('/CdrDrctSales/show/:id',{templateUrl:'views/CdrDrctSale/showDrctSales.html',controller:'cdrDrctSalesShowCtlr'})
    .otherwise({redirectTo: '/CdrDrctSales'});
    
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
    $translatePartialLoader.addPart('/adcshdwr.client/i18n/main');
    sessionManager.appMenuUrl("/adcshdwr.client/menu.html");
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
