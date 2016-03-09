(function() {
    'use strict';

    angular
        .module('app.catalArtManufSupp')
        .run(appRun);

    appRun.$inject = ['routerHelper', 'BASE_VIEW_ADCATAL'];
    /* @ngInject */
    function appRun(routerHelper, BASE_VIEW_ADCATAL) {
        routerHelper.configureStates(getStates(BASE_VIEW_ADCATAL));
    }

    function getStates(BASE_VIEW_ADCATAL) {
        return [
            {
                state: 'listCatalArtManufSupp',
                config: {
                    url: '/catal-art-manuf-supp',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-manuf-supp/views/list.html',
                    controller: 'CatalArtManufSuppController',
                    controllerAs: 'vm',
                    title: 'List CatalArtManufSupps'
                }
            },
            {
                state: 'createCatalArtManufSupp',
                config: {
                    url: '/catal-art-manuf-supp/create',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-manuf-supp/views/create.html',
                    controller: 'CatalArtManufSuppController',
                    controllerAs: 'vm',
                    title: 'Create CatalArtManufSupp'
                }
            },
            {
                state: 'viewCatalArtManufSupp',
                config: {
                    url: '/catal-art-manuf-supp/:catalArtManufSuppId',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-manuf-supp/views/view.html',
                    controller: 'CatalArtManufSuppController',
                    controllerAs: 'vm',
                    title: 'View CatalArtManufSupp'
                }
            },
            {
                state: 'editCatalArtManufSupp',
                config: {
                    url: '/catal-art-manuf-supp/:catalArtManufSuppId/edit',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-manuf-supp/views/edit.html',
                    controller: 'CatalArtManufSuppController',
                    controllerAs: 'vm',
                    title: 'Edit CatalArtManufSupp'
                }
            }
        ];
    }
})();
