/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .config(function ($translateProvider) {

            $translateProvider.translations('en', {

            });

            $translateProvider.translations('fr', {

            });
            $translateProvider.preferredLanguage('en');
        });

})();
