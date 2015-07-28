(function() {
    'use strict';

    angular
        .module('app.catalArt2ProductFamily')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listCatalArt2ProductFamily',
                config: {
                    url: '/catal-art-2-product-family',
                    templateUrl: 'app/catal-art-2-product-family/views/list.html',
                    controller: 'CatalArt2ProductFamilyController',
                    controllerAs: 'vm',
                    title: 'List CatalArt2ProductFamilies'
                }
            },
            {
                state: 'createCatalArt2ProductFamily',
                config: {
                    url: '/catal-art-2-product-family/create',
                    templateUrl: 'app/catal-art-2-product-family/views/create.html',
                    controller: 'CatalArt2ProductFamilyController',
                    controllerAs: 'vm',
                    title: 'Create CatalArt2ProductFamily'
                }
            },
            {
                state: 'viewCatalArt2ProductFamily',
                config: {
                    url: '/catal-art-2-product-family/:catalArt2ProductFamilyId',
                    templateUrl: 'app/catal-art-2-product-family/views/view.html',
                    controller: 'CatalArt2ProductFamilyController',
                    controllerAs: 'vm',
                    title: 'View CatalArt2ProductFamily'
                }
            },
            {
                state: 'editCatalArt2ProductFamily',
                config: {
                    url: '/catal-art-2-product-family/:catalArt2ProductFamilyId/edit',
                    templateUrl: 'app/catal-art-2-product-family/views/edit.html',
                    controller: 'CatalArt2ProductFamilyController',
                    controllerAs: 'vm',
                    title: 'Edit CatalArt2ProductFamily'
                }
            }
        ];
    }
})();
