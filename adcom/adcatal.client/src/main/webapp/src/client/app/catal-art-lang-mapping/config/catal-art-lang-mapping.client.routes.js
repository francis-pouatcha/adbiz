(function() {
    'use strict';

    angular
        .module('app.catalArtLangMapping')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping',
                    templateUrl: 'app/catal-art-lang-mapping/views/list.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'List CatalArtLangMappings'
                }
            },
            {
                state: 'createCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping/create/:catalArticleId',
                    templateUrl: 'app/catal-art-lang-mapping/views/create.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'Create CatalArtLangMapping'
                }
            },
            {
                state: 'viewCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping/:catalArtLangMappingId/:catalArticleId',
                    templateUrl: 'app/catal-art-lang-mapping/views/view.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'View CatalArtLangMapping'
                }
            },
            {
                state: 'editCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping/:catalArtLangMappingId/edit/:catalArticleId',
                    templateUrl: 'app/catal-art-lang-mapping/views/edit.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'Edit CatalArtLangMapping'
                }
            }
        ];
    }
})();
