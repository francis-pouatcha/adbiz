(function () {
    'use strict';

    var appModule = angular.module('addashboard', [
        'app.core',
        'app.widgets',
        'app.dashboard',
        'app.layout'
    ]);

    var auth = {};
    var logout = function () {
        console.log('*** LOGOUT');
        auth.loggedIn = false;
        auth.authz = null;
        window.location = auth.logoutUrl;
    };

    appModule.factory('Auth', function () {
        return auth;
    });

    /* jshint ignore:start */
    /* @ngInject */
    angular.element(document).ready(function ($http, Auth) {
        var keycloakAuth = new Keycloak('http://localhost:8080/addashboard.client/' +
            'rest/keycloak.json');
        console.log(keycloakAuth);
        console.log(keycloakAuth.authServerUrl);
        auth.loggedIn = false;
        keycloakAuth.init({onLoad: 'login-required'}).success(function () {
            auth.loggedIn = true;
            auth.authz = keycloakAuth;
            auth.logoutUrl = keycloakAuth.authServerUrl +
                '/realms/adcom/tokens/logout?redirect_uri=/addashboard.client';
            Auth = auth;
            angular.bootstrap(document, ['addashboard']);
        }).error(function () {
            window.location.reload();
        });

    });
    /* jshint ignore:end */

    /* @ngInject */
    appModule.factory('authInterceptor', function ($q, Auth) {
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
    appModule.factory('errorInterceptor', function ($q) {
        return function (promise) {
            return promise.then(function (response) {
                return response;
            }, function (response) {
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
    appModule.config(function ($httpProvider, $locationProvider,
                               flowFactoryProvider, API_BASE_URL) {
        $httpProvider.interceptors.push('errorInterceptor');
        $httpProvider.interceptors.push('authInterceptor');
        //$locationProvider.html5Mode(false);
        flowFactoryProvider.defaults = {
            target: API_BASE_URL + '/importExport/upload',
            permanentErrors: [404, 500, 501],
            maxChunkRetries: 1,
            chunkRetryInterval: 5000,
            simultaneousUploads: 4,
            progressCallbacksInterval: 1,
            testChunks: false,
            withCredentials: true,
            method: 'octet'
        };
        // You can also set default events:
        flowFactoryProvider.on('catchAll', function (event) {
            //console.log('catchAll', arguments);
            //logger.
        });
        // Can be used with different implementations of Flow.js
        //flowFactoryProvider.factory = fustyFlowFactory;
    });
    /* jshint ignore:start */
    /* @ngInject */
    appModule.controller('GlobalController', function ($rootScope, Auth, BASE_ROUTE, BASE_SERVER) {
        var vm = this;
        vm.logout = logout;
        vm.BASE_ROUTE = BASE_ROUTE;
        vm.BASE_SERVER = BASE_SERVER;

        if (Auth.authz) {
            if (Auth.authz.idToken) {
                //jscs:disable
                vm.username = Auth.authz.idToken.preferred_username; //to ignore camelCase
                //jscs:enable
            } else {
                Auth.authz.loadUserProfile(function () {
                    vm.username = Auth.authz.profile.username;
                }, function () {
                    console.log('failed to retrieve user profile');
                });
            }
        }
        $rootScope.username = vm.username;
        $rootScope.logout = vm.logout;
    });
    /* jshint ignore:end */
})();
