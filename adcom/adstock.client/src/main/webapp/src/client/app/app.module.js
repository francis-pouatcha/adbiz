(function () {
    'use strict';

    var appModule = angular.module('adstock', [
        'app.core',
        'app.layout'
    ]);

    var auth = {};
    var logout = function() {
        console.log('*** LOGOUT');
        auth.loggedIn = false;
        auth.authz = null;
        window.location = auth.logoutUrl;
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
                '/realms/adcom/tokens/logout?redirect_uri=/adstock.client';
            //Auth = auth;
            appModule.factory('Auth', function() {
                return auth;
            });
            angular.bootstrap(document, ['adstock']);
        }).error(function () {
            console.log(keycloakAuth);
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
    appModule.config(function($httpProvider, $locationProvider) {
        $httpProvider.interceptors.push('errorInterceptor');
        $httpProvider.interceptors.push('authInterceptor');
        $locationProvider.html5Mode(false);
    });
    /* jshint ignore:start */
    /* @ngInject */
    appModule.controller('GlobalController', function($rootScope, Auth, BASE_ROUTE, BASE_SERVER) {
        var vm = this;
        vm.logout = logout;
        vm.BASE_ROUTE = BASE_ROUTE;
        vm.BASE_SERVER = BASE_SERVER;

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
        $rootScope.appTitle = 'Stock';
    });
    
    /* Runtime configuration */
    appModule.run(function(formlyConfig) {
        
       
        formlyConfig.setType({
            name: 'typeahead',
            template: '<input type="text" ng-model="model[options.key]" typeahead-min-length="2"' +
                'typeahead="item.identif  for item in to.options($viewValue) | limitTo:8" ' +
                'class="form-control">',
            wrapper: ['bootstrapLabel', 'bootstrapHasError']
        });
        
/***************** Formly datePicker configuration *****************/         
        var attributes = [
            'date-disabled',
            'custom-class',
            'show-weeks',
            'starting-day',
            'init-date',
            'min-mode',
            'max-mode',
            'format-day',
            'format-month',
            'format-year',
            'format-day-header',
            'format-day-title',
            'format-month-title',
            'year-range',
            'shortcut-propagation',
            'datepicker-popup',
            'show-button-bar',
            'current-text',
            'clear-text',
            'close-text',
            'close-on-date-selection',
            'datepicker-append-to-body'
          ];

          var bindings = [
            'datepicker-mode',
            'min-date',
            'max-date'
          ];

          var ngModelAttrs = {};

          angular.forEach(attributes, function(attr) {
            ngModelAttrs[camelize(attr)] = {attribute: attr};
          });

          angular.forEach(bindings, function(binding) {
            ngModelAttrs[camelize(binding)] = {bound: binding};
          });

        formlyConfig.setType({
            name: 'datepicker',
            template: '<input class="form-control" ng-model="model[options.key]" is-open="to.isOpen" datepicker-options="to.datepickerOptions" />',
            wrapper: ['bootstrapLabel', 'bootstrapHasError'],
            defaultOptions: {
              ngModelAttrs: ngModelAttrs,
              templateOptions: {
                addonLeft: {
                  class: 'glyphicon glyphicon-calendar',
                  onClick: function(options, scope) {
                    options.templateOptions.isOpen = !options.templateOptions.isOpen;
                  }
                },
                onFocus: function($viewValue, $modelValue, scope) {
                  scope.to.isOpen = !scope.to.isOpen;
                },
                datepickerOptions: {}
              }
            }
         });
        
        function camelize(string) {
            string = string.replace(/[\-_\s]+(.)?/g, function(match, chr) {
              return chr ? chr.toUpperCase() : '';
            });
            // Ensure 1st char is always lowercase
            return string.replace(/^([A-Z])/, function(match, chr) {
              return chr ? chr.toLowerCase() : '';
            });
         }
      /***************** End Formly datePicker configuration *****************/
        
    });

})();
