'use strict';

angular.module('AdProcmt', [
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
	'appName':'Procurement',
	'appVersion':'1.0.0-SNAPSHOT'

})

.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
    .when('/',{templateUrl:'views/PrcmtDelivery/PrcmtDeliverys.html',controller:'prcmtDeliveryCtrl'})
    .when('/PrcmtDeliverys',{templateUrl:'views/PrcmtDelivery/PrcmtDeliverys.html',controller:'prcmtDeliveryCtrl'})
    .when('/PrcmtDeliverys/new',{templateUrl:'views/PrcmtDelivery/PrcmtDeliveryCreate.html',controller:'prcmtDeliveryCreateCtlr'})
    .when('/PrcmtDeliverys/edit/:identif',{templateUrl:'views/PrcmtDelivery/PrcmtDeliveryEdit.html',controller:'prcmtDeliveryEditCtlr'})
    .when('/PrcmtDeliverys/addItem',{templateUrl:'views/PrcmtDelivery/PrcmtDeliveryAddItem.html',controller:'prcmtDeliveryAddItemCtlr'})

        .when('/PrcmtOrders',{templateUrl:'views/PrcmtOrder/PrcmtOrders.html',controller:'prcmtOrderCtrl'})
        .when('/PrcmtOrders/new',{templateUrl:'views/PrcmtOrder/PrcmtOrderCreate.html',controller:'prcmtOrderCreateCtlr'})
        .when('/PrcmtOrders/edit/:identif',{templateUrl:'views/PrcmtOrder/PrcmtOrderEdit.html',controller:'prcmtOrderEditCtlr'})
        .when('/PrcmtOrders/show',{templateUrl:'views/PrcmtOrder/PrcmtOrderShow.html',controller:'prcmtOrderShowCtlr'})

    .when('/PrcmtDlvryItems',{templateUrl:'views/PrcmtDlvryItem/search.html',controller:'SearchPrcmtDlvryItemController'})
    .when('/PrcmtDlvryItems/new',{templateUrl:'views/PrcmtDlvryItem/detail.html',controller:'NewPrcmtDlvryItemController'})
    .when('/PrcmtDlvryItems/edit/:PrcmtDlvryItemId',{templateUrl:'views/PrcmtDlvryItem/detail.html',controller:'EditPrcmtDlvryItemController'})
    .when('/PrcmtPOItems',{templateUrl:'views/PrcmtPOItem/search.html',controller:'SearchPrcmtPOItemController'})
    .when('/PrcmtPOItems/new',{templateUrl:'views/PrcmtPOItem/detail.html',controller:'NewPrcmtPOItemController'})
    .when('/PrcmtPOItems/edit/:PrcmtPOItemId',{templateUrl:'views/PrcmtPOItem/detail.html',controller:'EditPrcmtPOItemController'})
    .when('/PrcmtProcOrders',{templateUrl:'views/PrcmtProcOrder/search.html',controller:'SearchPrcmtProcOrderController'})
    .when('/PrcmtProcOrders/new',{templateUrl:'views/PrcmtProcOrder/detail.html',controller:'NewPrcmtProcOrderController'})
    .when('/PrcmtProcOrders/edit/:PrcmtProcOrderId',{templateUrl:'views/PrcmtProcOrder/detail.html',controller:'EditPrcmtProcOrderController'})
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
    sessionManager.appMenuUrl("/adprocmt.client/menu.html");
    $translatePartialLoader.addPart('/adprocmt.client/i18n/main');
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
