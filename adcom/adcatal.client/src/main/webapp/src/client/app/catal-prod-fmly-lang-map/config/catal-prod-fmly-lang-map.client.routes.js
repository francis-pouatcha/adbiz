(function() {
    'use strict';

    angular
        .module('app.catalProdFmlyLangMap')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listCatalProdFmlyLangMap',
                config: {
                    url: '/catal-prod-fmly-lang-map',
                    templateUrl: 'app/catal-prod-fmly-lang-map/views/list.html',
                    controller: 'CatalProdFmlyLangMapController',
                    controllerAs: 'vm',
                    title: 'List CatalProdFmlyLangMaps'
                }
            },
            {
                state: 'createCatalProdFmlyLangMap',
                config: {
                    url: '/catal-prod-fmly-lang-map/create',
                    templateUrl: 'app/catal-prod-fmly-lang-map/views/create.html',
                    controller: 'CatalProdFmlyLangMapController',
                    controllerAs: 'vm',
                    title: 'Create CatalProdFmlyLangMap'
                }
            },
            {
                state: 'viewCatalProdFmlyLangMap',
                config: {
                    url: '/catal-prod-fmly-lang-map/:catalProdFmlyLangMapId',
                    templateUrl: 'app/catal-prod-fmly-lang-map/views/view.html',
                    controller: 'CatalProdFmlyLangMapController',
                    controllerAs: 'vm',
                    title: 'View CatalProdFmlyLangMap'
                }
            },
            {
                state: 'editCatalProdFmlyLangMap',
                config: {
                    url: '/catal-prod-fmly-lang-map/:catalProdFmlyLangMapId/edit',
                    templateUrl: 'app/catal-prod-fmly-lang-map/views/edit.html',
                    controller: 'CatalProdFmlyLangMapController',
                    controllerAs: 'vm',
                    title: 'Edit CatalProdFmlyLangMap'
                }
            }
        ];
    }
})();
