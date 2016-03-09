(function() {
    'use strict';

    angular
        .module('app.catalArt2ProductFamily')
        .run(appRun);

    appRun.$inject = ['routerHelper', 'BASE_VIEW_ADCATAL'];
    /* @ngInject */
    function appRun(routerHelper, BASE_VIEW_ADCATAL) {
        routerHelper.configureStates(getStates(BASE_VIEW_ADCATAL));
    }

    function getStates(BASE_VIEW_ADCATAL) {
        return [
            {
                state: 'listCatalArt2ProductFamily',
                config: {
                    url: '/catal-art-2-product-family',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-2-product-family/views/list.html',
                    controller: 'CatalArt2ProductFamilyController',
                    controllerAs: 'vm',
                    title: 'List CatalArt2ProductFamilies'
                }
            },
            {
                state: 'createCatalArt2ProductFamily',
                config: {
                    url: '/catal-art-2-product-family/create',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-2-product-family/views/create.html',
                    controller: 'CatalArt2ProductFamilyController',
                    controllerAs: 'vm',
                    title: 'Create CatalArt2ProductFamily'
                }
            },
            {
                state: 'viewCatalArt2ProductFamily',
                config: {
                    url: '/catal-art-2-product-family/:catalArt2ProductFamilyId',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-2-product-family/views/view.html',
                    controller: 'CatalArt2ProductFamilyController',
                    controllerAs: 'vm',
                    title: 'View CatalArt2ProductFamily'
                }
            },
            {
                state: 'editCatalArt2ProductFamily',
                config: {
                    url: '/catal-art-2-product-family/:catalArt2ProductFamilyId/edit',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-2-product-family/views/edit.html',
                    controller: 'CatalArt2ProductFamilyController',
                    controllerAs: 'vm',
                    title: 'Edit CatalArt2ProductFamily'
                }
            }
        ];
    }
})();
