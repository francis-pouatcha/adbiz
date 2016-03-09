(function() {
    'use strict';

    angular
        .module('app.catalArtLangMapping')
        .run(appRun);

    appRun.$inject = ['routerHelper','BASE_VIEW_ADCATAL'];
    /* @ngInject */
    function appRun(routerHelper,BASE_VIEW_ADCATAL) {
        routerHelper.configureStates(getStates(BASE_VIEW_ADCATAL));
    }

    function getStates(BASE_VIEW_ADCATAL) {
        return [
            {
                state: 'listCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-lang-mapping/views/list.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'List CatalArtLangMappings'
                }
            },
            {
                state: 'createCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping/create/:catalArticleId',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-lang-mapping/views/create.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'Create CatalArtLangMapping'
                }
            },
            {
                state: 'viewCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping/:catalArtLangMappingId/:catalArticleId',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-lang-mapping/views/view.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'View CatalArtLangMapping'
                }
            },
            {
                state: 'editCatalArtLangMapping',
                config: {
                    url: '/catal-art-lang-mapping/:catalArtLangMappingId/edit/:catalArticleId',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-art-lang-mapping/views/edit.html',
                    controller: 'CatalArtLangMappingController',
                    controllerAs: 'vm',
                    title: 'Edit CatalArtLangMapping'
                }
            }
        ];
    }
})();
