(function() {
    'use strict';

    angular
        .module('app.catalPicMapping')
        .run(appRun);

    appRun.$inject = ['routerHelper','BASE_VIEW_ADCATAL'];
    /* @ngInject */
    function appRun(routerHelper,BASE_VIEW_ADCATAL) {
        routerHelper.configureStates(getStates(BASE_VIEW_ADCATAL));
    }

    function getStates(BASE_VIEW_ADCATAL) {
        return [
            {
                state: 'listCatalPicMapping',
                config: {
                    url: '/catal-pic-mapping',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-pic-mapping/views/list.html',
                    controller: 'CatalPicMappingController',
                    controllerAs: 'vm',
                    title: 'List CatalPicMappings'
                }
            },
            {
                state: 'createCatalPicMapping',
                config: {
                    url: '/catal-pic-mapping/create',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-pic-mapping/views/create.html',
                    controller: 'CatalPicMappingController',
                    controllerAs: 'vm',
                    title: 'Create CatalPicMapping'
                }
            },
            {
                state: 'viewCatalPicMapping',
                config: {
                    url: '/catal-pic-mapping/:catalPicMappingId',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-pic-mapping/views/view.html',
                    controller: 'CatalPicMappingController',
                    controllerAs: 'vm',
                    title: 'View CatalPicMapping'
                }
            },
            {
                state: 'editCatalPicMapping',
                config: {
                    url: '/catal-pic-mapping/:catalPicMappingId/edit',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-pic-mapping/views/edit.html',
                    controller: 'CatalPicMappingController',
                    controllerAs: 'vm',
                    title: 'Edit CatalPicMapping'
                }
            }
        ];
    }
})();
