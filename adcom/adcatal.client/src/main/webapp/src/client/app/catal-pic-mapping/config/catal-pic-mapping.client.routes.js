(function() {
    'use strict';

    angular
        .module('app.catalPicMapping')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listCatalPicMapping',
                config: {
                    url: '/catal-pic-mapping',
                    templateUrl: 'app/catal-pic-mapping/views/list.html',
                    controller: 'CatalPicMappingController',
                    controllerAs: 'vm',
                    title: 'List CatalPicMappings'
                }
            },
            {
                state: 'createCatalPicMapping',
                config: {
                    url: '/catal-pic-mapping/create',
                    templateUrl: 'app/catal-pic-mapping/views/create.html',
                    controller: 'CatalPicMappingController',
                    controllerAs: 'vm',
                    title: 'Create CatalPicMapping'
                }
            },
            {
                state: 'viewCatalPicMapping',
                config: {
                    url: '/catal-pic-mapping/:catalPicMappingId',
                    templateUrl: 'app/catal-pic-mapping/views/view.html',
                    controller: 'CatalPicMappingController',
                    controllerAs: 'vm',
                    title: 'View CatalPicMapping'
                }
            },
            {
                state: 'editCatalPicMapping',
                config: {
                    url: '/catal-pic-mapping/:catalPicMappingId/edit',
                    templateUrl: 'app/catal-pic-mapping/views/edit.html',
                    controller: 'CatalPicMappingController',
                    controllerAs: 'vm',
                    title: 'Edit CatalPicMapping'
                }
            }
        ];
    }
})();
