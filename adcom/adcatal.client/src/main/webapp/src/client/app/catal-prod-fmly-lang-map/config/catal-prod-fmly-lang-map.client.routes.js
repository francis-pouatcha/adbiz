(function() {
    'use strict';

    angular
        .module('app.catalProdFmlyLangMap')
        .run(appRun);

    appRun.$inject = ['routerHelper','BASE_VIEW'];
    /* @ngInject */
    function appRun(routerHelper,BASE_VIEW) {
        routerHelper.configureStates(getStates(BASE_VIEW));
    }

    function getStates(BASE_VIEW) {
        return [
            {
                state: 'listCatalProdFmlyLangMap',
                config: {
                    url: '/catal-prod-fmly-lang-map',
                    templateUrl: BASE_VIEW+'/app/catal-prod-fmly-lang-map/views/list.html',
                    controller: 'CatalProdFmlyLangMapController',
                    controllerAs: 'vm',
                    title: 'List CatalProdFmlyLangMaps'
                }
            },
            {
                state: 'createCatalProdFmlyLangMap',
                config: {
                    url: '/catal-prod-fmly-lang-map/create',
                    templateUrl: BASE_VIEW+'/app/catal-prod-fmly-lang-map/views/create.html',
                    controller: 'CatalProdFmlyLangMapController',
                    controllerAs: 'vm',
                    title: 'Create CatalProdFmlyLangMap'
                }
            },
            {
                state: 'viewCatalProdFmlyLangMap',
                config: {
                    url: '/catal-prod-fmly-lang-map/:catalProdFmlyLangMapId',
                    templateUrl: BASE_VIEW+'/app/catal-prod-fmly-lang-map/views/view.html',
                    controller: 'CatalProdFmlyLangMapController',
                    controllerAs: 'vm',
                    title: 'View CatalProdFmlyLangMap'
                }
            },
            {
                state: 'editCatalProdFmlyLangMap',
                config: {
                    url: '/catal-prod-fmly-lang-map/:catalProdFmlyLangMapId/edit',
                    templateUrl: BASE_VIEW+'/app/catal-prod-fmly-lang-map/views/edit.html',
                    controller: 'CatalProdFmlyLangMapController',
                    controllerAs: 'vm',
                    title: 'Edit CatalProdFmlyLangMap'
                }
            }
        ];
    }
})();
