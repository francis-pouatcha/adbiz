(function() {
    'use strict';

    angular
        .module('app.catalArtManufSupp')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listCatalArtManufSupp',
                config: {
                    url: '/catal-art-manuf-supp',
                    templateUrl: 'app/catal-art-manuf-supp/views/list.html',
                    controller: 'CatalArtManufSuppController',
                    controllerAs: 'vm',
                    title: 'List CatalArtManufSupps'
                }
            },
            {
                state: 'createCatalArtManufSupp',
                config: {
                    url: '/catal-art-manuf-supp/create',
                    templateUrl: 'app/catal-art-manuf-supp/views/create.html',
                    controller: 'CatalArtManufSuppController',
                    controllerAs: 'vm',
                    title: 'Create CatalArtManufSupp'
                }
            },
            {
                state: 'viewCatalArtManufSupp',
                config: {
                    url: '/catal-art-manuf-supp/:catalArtManufSuppId',
                    templateUrl: 'app/catal-art-manuf-supp/views/view.html',
                    controller: 'CatalArtManufSuppController',
                    controllerAs: 'vm',
                    title: 'View CatalArtManufSupp'
                }
            },
            {
                state: 'editCatalArtManufSupp',
                config: {
                    url: '/catal-art-manuf-supp/:catalArtManufSuppId/edit',
                    templateUrl: 'app/catal-art-manuf-supp/views/edit.html',
                    controller: 'CatalArtManufSuppController',
                    controllerAs: 'vm',
                    title: 'Edit CatalArtManufSupp'
                }
            }
        ];
    }
})();
