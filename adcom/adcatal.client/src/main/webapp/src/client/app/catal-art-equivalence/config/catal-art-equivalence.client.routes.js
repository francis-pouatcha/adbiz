(function() {
    'use strict';

    angular
        .module('app.catalArtEquivalence')
        .run(appRun);

    appRun.$inject = ['routerHelper', 'BASE_VIEW'];
    /* @ngInject */
    function appRun(routerHelper, BASE_VIEW) {
        routerHelper.configureStates(getStates(BASE_VIEW));
    }

    function getStates(BASE_VIEW) {
        return [
            {
                state: 'listCatalArtEquivalence',
                config: {
                    url: '/catal-art-equivalence',
                    templateUrl: BASE_VIEW+'/app/catal-art-equivalence/views/list.html',
                    controller: 'CatalArtEquivalenceController',
                    controllerAs: 'vm',
                    title: 'List CatalArtEquivalences'
                }
            },
            {
                state: 'createCatalArtEquivalence',
                config: {
                    url: '/catal-art-equivalence/create',
                    templateUrl: BASE_VIEW+'/app/catal-art-equivalence/views/create.html',
                    controller: 'CatalArtEquivalenceController',
                    controllerAs: 'vm',
                    title: 'Create CatalArtEquivalence'
                }
            },
            {
                state: 'viewCatalArtEquivalence',
                config: {
                    url: '/catal-art-equivalence/:catalArtEquivalenceId',
                    templateUrl: BASE_VIEW+'/app/catal-art-equivalence/views/view.html',
                    controller: 'CatalArtEquivalenceController',
                    controllerAs: 'vm',
                    title: 'View CatalArtEquivalence'
                }
            },
            {
                state: 'editCatalArtEquivalence',
                config: {
                    url: '/catal-art-equivalence/:catalArtEquivalenceId/edit',
                    templateUrl: BASE_VIEW+'/app/catal-art-equivalence/views/edit.html',
                    controller: 'CatalArtEquivalenceController',
                    controllerAs: 'vm',
                    title: 'Edit CatalArtEquivalence'
                }
            }
        ];
    }
})();
