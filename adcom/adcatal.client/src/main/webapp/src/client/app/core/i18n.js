/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .config(function ($translateProvider) {

            $translateProvider.translations('en', {
                TITLE: 'Hello',
                'CatalArticle_sppu_description.title': 'Sales Price per Unit'
            });

 //------------french----------------

            $translateProvider.translations('fr', {
                TITLE: 'salut',
                'CatalArticle_sppu_description.title': 'Prix de Vente Unitaire'
            });
            $translateProvider.preferredLanguage('fr');
        });

})();
