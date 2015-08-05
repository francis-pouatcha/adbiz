(function () {
    'use strict';

    angular.module('app', [
        'app.core',
        'app.widgets',
        'app.dashboard',
        'app.layout'
    ])
        .config(function ($httpProvider) {
            /*$httpProvider.defaults.useXDomain = true;
             delete $httpProvider.defaults.headers.common['X-Requested-With'];
             $httpProvider.defaults.headers.common["Accept"] = "application/json";
             $httpProvider.defaults.headers.common["Content-Type"] = "application/json";*/
        })
        .run(function(formlyConfig) {
            formlyConfig.setType({
                name: 'typeahead',
                template: '<input type="text" ng-model="model[options.key]" typeahead-min-length="2"'+
                    'typeahead="item.identif  for item in to.options($viewValue) | limitTo:8" ' +
                    'class="form-control">',
                wrapper: ['bootstrapLabel', 'bootstrapHasError']
            });
        });

})();
