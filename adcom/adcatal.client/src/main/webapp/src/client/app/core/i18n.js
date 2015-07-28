/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .config(function ($translateProvider) {

            $translateProvider.translations('en', {
                TITLE: 'Hello',
                'CatalArticle_sppu_description.title': 'Sales Price per Unit',
                LANG_FR: 'French',
                LANG_EN: 'English'
            });

 //------------french----------------

            $translateProvider.translations('fr', {
                TITLE: 'salut',
                'CatalArticle_sppu_description.title': 'Prix de Vente Unitaire',
                LANG_FR: 'Francais',
                LANG_EN: 'Englais'
            });
            $translateProvider.preferredLanguage('fr');
        });

})();
