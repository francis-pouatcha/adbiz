(function() {
    'use strict';

    angular
        .module('app.catalArtEquivalence')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listCatalArtEquivalence',
                config: {
                    url: '/catal-art-equivalence',
                    templateUrl: 'app/catal-art-equivalence/views/list.html',
                    controller: 'CatalArtEquivalenceController',
                    controllerAs: 'vm',
                    title: 'List CatalArtEquivalences'
                }
            },
            {
                state: 'createCatalArtEquivalence',
                config: {
                    url: '/catal-art-equivalence/create',
                    templateUrl: 'app/catal-art-equivalence/views/create.html',
                    controller: 'CatalArtEquivalenceController',
                    controllerAs: 'vm',
                    title: 'Create CatalArtEquivalence'
                }
            },
            {
                state: 'viewCatalArtEquivalence',
                config: {
                    url: '/catal-art-equivalence/:catalArtEquivalenceId',
                    templateUrl: 'app/catal-art-equivalence/views/view.html',
                    controller: 'CatalArtEquivalenceController',
                    controllerAs: 'vm',
                    title: 'View CatalArtEquivalence'
                }
            },
            {
                state: 'editCatalArtEquivalence',
                config: {
                    url: '/catal-art-equivalence/:catalArtEquivalenceId/edit',
                    templateUrl: 'app/catal-art-equivalence/views/edit.html',
                    controller: 'CatalArtEquivalenceController',
                    controllerAs: 'vm',
                    title: 'Edit CatalArtEquivalence'
                }
            }
        ];
    }
})();
