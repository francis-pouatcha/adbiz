/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .config(function ($translateProvider) {

            $translateProvider.translations('en', {
                LANG_FR: 'French',
                LANG_EN: 'English',
                logout: 'logout'
            });

            $translateProvider.translations('fr', {
                LANG_FR: 'Francais',
                LANG_EN: 'Englais',
                logout: 'deconnection'
            });
            $translateProvider.preferredLanguage('en');
        });

})();
