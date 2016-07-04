'use strict';
(function () {
    'use strict';

    var appModule = angular.module('AdCshdwr', [
        'app.core',
        'app.layout',
        'smart-table',
        'ui.bootstrap',
        'ngRoute',
        'ngCookies',
        'ui.bootstrap',
        //'SessionManager',
        //'AuthInterceptor',
        'ngSanitize',
        //'NavBar',
        'ngResource',
        'datePicker',
        'ADUtils',
        'pascalprecht.translate',
        'httpProgress'
    ]);

    var auth = {};
    var logout = function() {
        console.log('*** LOGOUT');
        auth.loggedIn = false;
        auth.authz = null;
        window.location = auth.logoutUrl;
    };

    /* jshint ignore:start */
    /* @ngInject */
    angular.element(document).ready(function ($http, Auth) {
        var keycloakAuth = new Keycloak('rest/keycloak.json');
        auth.loggedIn = false;
        keycloakAuth.init({onLoad: 'login-required'}).success(function () {
            auth.loggedIn = true;
            auth.authz = keycloakAuth;
            auth.logoutUrl = keycloakAuth.authServerUrl +
                '/realms/adcom/tokens/logout?redirect_uri=/adcshdwr.client';
            appModule.factory('Auth', function() {
                return auth;
            });
            angular.bootstrap(document, ['AdCshdwr']);
        }).error(function () {
            window.location.reload();
        })
    });


    /* jshint ignore:end */

    /* @ngInject */
    appModule.factory('authInterceptor', function($q, Auth) {
        return {
            request: function (config) {
                var deferred = $q.defer();
                if (Auth.authz) {
                    if (Auth.authz.token) {
                        Auth.authz.updateToken(5).success(function () {
                            config.headers = config.headers || {};
                            config.headers.Authorization = 'Bearer ' + Auth.authz.token;

                            deferred.resolve(config);
                        }).error(function () {
                            deferred.reject('Failed to refresh token');
                        });
                    }
                }
                return deferred.promise;
            }
        };
    });

    /* @ngInject */
    appModule.factory('errorInterceptor', function($q) {
        return function(promise) {
            return promise.then(function(response) {
                return response;
            }, function(response) {
                if (response.status === 401) {
                    console.log('session timeout?');
                    logout();
                } else if (response.status === 403) {
                    window.alert('Forbidden');
                } else if (response.status === 404) {
                    window.alert('Not found');
                } else if (response.status) {
                    if (response.data && response.data.errorMessage) {
                        window.alert(response.data.errorMessage);
                    } else {
                        window.alert('An unexpected server error has occurred');
                    }
                }
                return $q.reject(response);
            });
        };
    });

    /* @ngInject */
    appModule.config(function($httpProvider, $locationProvider,$routeProvider,$translateProvider) {
        $httpProvider.interceptors.push('errorInterceptor');
        $httpProvider.interceptors.push('authInterceptor');
        $locationProvider.html5Mode(false);
        $routeProvider
        .when('/CdrPymnts',{templateUrl:'views/CdrPymnt/CdrPymnts.html',controller:'cdrPaymntsCtlr'})
            .when('/CdrPymnts/new',{templateUrl:'views/CdrPymnt/createCdrPymnt.html',controller:'cdrPaymntCreateCtlr'})
            .when('/CdrPymnts/edit/:identif',{templateUrl:'views/CdrPymnt/editCdrPymnt.html',controller:'cdrPaymntEditCtlr'})
            .when('/SlsInvoices',{templateUrl:'views/SlsInvoice/SlsInvoices.html',controller:'cdrSlsInvoicesCtlr'})
            .when('/SlsInvoices/show',{templateUrl:'views/SlsInvoice/SlsInvoiceShow.html',controller:'cdrSlsInvoicesShowCtlr'})
            .when('/CdrCshDrawers',{templateUrl:'views/CdrCshDrawer/CdrCshDrawers.html',controller:'cdrCshDrawersCtlr'})
            .when('/CdrCshDrawers/new',{templateUrl:'views/CdrCshDrawer/createCdrCshDrawer.html',controller:'cdrCshDrawersCreateCtlr'})

            .when('/CdrCshDrawers/show/:id',{templateUrl:'views/CdrCshDrawer/showCdrCshDrawer.html',controller:'cdrCshDrawersEditCtlr'})
            .when('/CdrDrctSales',{templateUrl:'views/CdrDrctSale/CdrDrctSales.html',controller:'cdrDrctSalesCtlr'})
            .when('/CdrDrctSales/new/:origDsNbr',{templateUrl:'views/CdrDrctSale/createDrctSales.html',controller:'cdrDrctSalesCreateCtlr'})
            .when('/CdrDrctSales/new',{templateUrl:'views/CdrDrctSale/createDrctSales.html',controller:'cdrDrctSalesCreateCtlr'})
            .when('/CdrDrctSales',{templateUrl:'views/CdrDrctSale/CdrDrctSales.html',controller:'cdrDrctSalesCtlr'})
            .when('/CdrDrctSales/show/:id',{templateUrl:'views/CdrDrctSale/showDrctSales.html',controller:'cdrDrctSalesShowCtlr'})
            .otherwise({redirectTo: '/CdrDrctSales'});


        $translateProvider.useLoader('$translatePartialLoader', {
            urlTemplate: '{part}/locale-{lang}.json'
        });
    });
    /* jshint ignore:start */
    /* @ngInject */
    appModule.controller('GlobalController', function($rootScope, Auth, BASE_ROUTE, genericResource) {
        var vm = this;
        vm.logout = logout;
        vm.BASE_ROUTE = BASE_ROUTE;
        vm.urlBase = genericResource.urlBase;

        if (Auth.authz) {
            if (Auth.authz.idToken) {
                //jscs:disable
                vm.username = Auth.authz.tokenParsed.name; //to ignore camelCase
                //jscs:enable
            } else {
                Auth.authz.loadUserProfile(function () {
                    if(vm.username)
                        vm.username = Auth.authz.profile.username;
                }, function () {
                    console.log('failed to retrieve user profile');
                });
            }
        }
        $rootScope.username = vm.username;
        $rootScope.logout = vm.logout;
        $rootScope.appTitle = 'Inventaire';

        /* $rootScope.reportLink = [
         {
         link:genericResource.urlBase()+'/adreport.server/frameset?__report=invintry.rptdesign',
         name:'Rapport Fiche Comptage 1'
         },
         {
         link:genericResource.urlBase()+'/adreport.server/frameset?__report=invintry2.rptdesign',
         name:'Rapport Fiche Comptage 2'
         },
         {
         link:genericResource.urlBase()+'/adreport.server/frameset?__report=invintry3.rptdesign',
         name:'Rapport Fiche Inventaire'
         }
         ];*/
    });
    /* jshint ignore:end */

    appModule.constant('APP_CONFIG',{
        'appName':'Cash Drawer',
        'appVersion':'1.0.0-SNAPSHOT'

    });

    appModule.run(function(formlyConfig) {
        formlyConfig.setType({
            name: 'typeahead',
            template: '<input type="text"' +
                'ng-model="model[options.key]" typeahead-min-length="2"' +
                'typeahead="item.identif  for item in to.options($viewValue) | limitTo:8" ' +
                'class="form-control">',
            wrapper: ['bootstrapLabel', 'bootstrapHasError']
        });
    });
})();





