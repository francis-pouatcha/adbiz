/**
 * @author guymoyo
 * @name LoaderInterceptor
 */

'use strict';
angular.module('httpProgress',['ngProgress'])
.factory('httpProgressInterceptor',['$injector', function($injector){
        return {

            'request': function(config) {
                var ngProg = $injector.get('ngProgress');
                ngProg.start();
                return config;
            },

            'response': function(response) {
                var ngProg = $injector.get('ngProgress');
                ngProg.complete();
                return response;
            }

        };
}]);
