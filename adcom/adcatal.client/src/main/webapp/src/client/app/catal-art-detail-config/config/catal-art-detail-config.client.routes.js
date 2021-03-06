(function() {
    'use strict';

    angular
        .module('app.catalArtDetailConfig')
        .run(appRun);

    appRun.$inject = ['routerHelper', 'BASE_VIEW'];
    /* @ngInject */
    function appRun(routerHelper, BASE_VIEW) {
        routerHelper.configureStates(getStates(BASE_VIEW));
    }

    function getStates(BASE_VIEW) {
        return [
            {
                state: 'listCatalArtDetailConfig',
                config: {
                    url: '/catal-art-detail-config',
                    templateUrl: BASE_VIEW+'/app/catal-art-detail-config/views/list.html',
                    controller: 'CatalArtDetailConfigController',
                    controllerAs: 'vm',
                    title: 'List CatalArtDetailConfigs'
                }
            },
            {
                state: 'createCatalArtDetailConfig',
                config: {
                    url: '/catal-art-detail-config/create',
                    templateUrl: BASE_VIEW+'/app/catal-art-detail-config/views/create.html',
                    controller: 'CatalArtDetailConfigController',
                    controllerAs: 'vm',
                    title: 'Create CatalArtDetailConfig'
                }
            },
            {
                state: 'viewCatalArtDetailConfig',
                config: {
                    url: '/catal-art-detail-config/:catalArtDetailConfigId',
                    templateUrl: BASE_VIEW+'/app/catal-art-detail-config/views/view.html',
                    controller: 'CatalArtDetailConfigController',
                    controllerAs: 'vm',
                    title: 'View CatalArtDetailConfig'
                }
            },
            {
                state: 'editCatalArtDetailConfig',
                config: {
                    url: '/catal-art-detail-config/:catalArtDetailConfigId/edit',
                    templateUrl: BASE_VIEW+'/app/catal-art-detail-config/views/edit.html',
                    controller: 'CatalArtDetailConfigController',
                    controllerAs: 'vm',
                    title: 'Edit CatalArtDetailConfig'
                }
            }
        ];
    }
})();
