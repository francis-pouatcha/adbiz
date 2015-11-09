(function() {
    'use strict';

    angular
        .module('app.catalArtLangMapping')
        .run(appRun);

    appRun.$inject = ['routerHelper','BASE_VIEW'];
    /* @ngInject */
    function appRun(routerHelper,BASE_VIEW) {
        routerHelper.configureStates(getStates(BASE_VIEW));
    }

    function getStates(BASE_VIEW) {
        return [
            {
                state: 'listCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping',
                    templateUrl: BASE_VIEW+'/app/catal-art-lang-mapping/views/list.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'List CatalArtLangMappings'
                }
            },
            {
                state: 'createCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping/create/:catalArticleId',
                    templateUrl: BASE_VIEW+'/app/catal-art-lang-mapping/views/create.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'Create CatalArtLangMapping'
                }
            },
            {
                state: 'viewCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping/:catalArtLangMappingId/:catalArticleId',
                    templateUrl: BASE_VIEW+'/app/catal-art-lang-mapping/views/view.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'View CatalArtLangMapping'
                }
            },
            {
                state: 'editCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping/:catalArtLangMappingId/edit/:catalArticleId',
                    templateUrl: BASE_VIEW+'/app/catal-art-lang-mapping/views/edit.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'Edit CatalArtLangMapping'
                }
            }
        ];
    }
})();
