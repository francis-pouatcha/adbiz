(function () {
    'use strict';

    var appModule = angular.module('adcatal', [
        'app.core',
        'app.layout',
        'smart-table'
    ]);

    var auth = {};
    var logout = function() {
        console.log('*** LOGOUT');
        auth.loggedIn = false;
        auth.authz.logout();
    };

    /*appModule.factory('Auth', function() {
        return auth;
    });*/

    /* jshint ignore:start */
    /* @ngInject */
    angular.element(document).ready(function ($http, Auth) {
        var keycloakAuth = new Keycloak('rest/keycloak.json');
        console.log(keycloakAuth);
        auth.loggedIn = false;
        keycloakAuth.init({onLoad: 'login-required'}).success(function () {
            auth.loggedIn = true;
            auth.authz = keycloakAuth;
            auth.logoutUrl = keycloakAuth.authServerUrl +
                '/realms/adcom/tokens/logout?redirect_uri=/adcatal.client';
            //Auth = auth;
            appModule.factory('Auth', function() {
                return auth;
            });
            angular.bootstrap(document, ['adcatal']);
        }).error(function () {
            console.log(keycloakAuth);
//            window.location.reload();
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
    appModule.config(function($httpProvider, $locationProvider) {
        $httpProvider.interceptors.push('errorInterceptor');
        $httpProvider.interceptors.push('authInterceptor');
        $locationProvider.html5Mode(false);
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
        console.log(Auth.authz);
        $rootScope.username = vm.username;
        $rootScope.logout = vm.logout;
        $rootScope.appTitle = 'Catalogue';

        // ARRAY OF REPORTS LINKS

        $rootScope.reportLink = [
            {
                link: '/adreport.server/frameset?__report=catal.rptdesign',
                name: 'Rapport du Catalogue'
            }
        ];
    });
    /* jshint ignore:end */


    appModule.run(function($rootScope, $translate, formlyConfig) {
        formlyConfig.setType({
            name: 'typeahead',
            template: '<input type="text"' +
                'ng-model="model[options.key]" typeahead-min-length="2"' +
                'typeahead="item.identif  for item in to.options($viewValue) | limitTo:8" ' +
                'class="form-control">',
            wrapper: ['bootstrapLabel', 'bootstrapHasError']
        });
        formlyConfig.setType({
            name: 'typeaheadTwo',
            template: '<input type="text"' +
                'ng-model="model[options.key]" typeahead-min-length="2"' +
                'typeahead="item as item.artName for item in to.options($viewValue) | limitTo:8" ' +
                'class="form-control">',
            wrapper: ['bootstrapLabel', 'bootstrapHasError']
        });
        $rootScope.$on('$stateChangeStart', function(event, toState) {
            if (toState.title) {
                $translate(toState.title).then(function(translation) {
                    $rootScope.pageTitle = translation;
                },
                function() {
                    $rootScope.pageTitle = 'adcatal.client';
                });
            }
        });
    });
})();
