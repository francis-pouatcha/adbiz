(function() {
    'use strict';

    angular
        .module('app.catalProdFmly')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listCatalProdFmly',
                config: {
                    url: '/catal-prod-fmly',
                    templateUrl: 'app/catal-prod-fmly/views/list.html',
                    controller: 'CatalProdFmlyController',
                    controllerAs: 'vm',
                    title: 'List CatalProdFmlies',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> CatalProdFmlies'
                    }
                }
            },
            {
                state: 'createCatalProdFmly',
                config: {
                    url: '/catal-prod-fmly/create',
                    templateUrl: 'app/catal-prod-fmly/views/create.html',
                    controller: 'CatalProdFmlyController',
                    controllerAs: 'vm',
                    title: 'Create CatalProdFmly'
                }
            },
            {
                state: 'viewCatalProdFmly',
                config: {
                    url: '/catal-prod-fmly/:catalProdFmlyId',
                    templateUrl: 'app/catal-prod-fmly/views/view.html',
                    controller: 'CatalProdFmlyController',
                    controllerAs: 'vm',
                    title: 'View CatalProdFmly'
                }
            },
            {
                state: 'editCatalProdFmly',
                config: {
                    url: '/catal-prod-fmly/:catalProdFmlyId/edit',
                    templateUrl: 'app/catal-prod-fmly/views/edit.html',
                    controller: 'CatalProdFmlyController',
                    controllerAs: 'vm',
                    title: 'Edit CatalProdFmly'
                }
            }
        ];
    }
})();
