(function() {
    'use strict';

    angular
        .module('app.stkSection')
        .run(appRun);

    appRun.$inject = ['routerHelper', 'BASE_VIEW_STOCK'];
    /* @ngInject */
    function appRun(routerHelper, BASE_VIEW_STOCK) {
        routerHelper.configureStates(getStates(BASE_VIEW_STOCK));
    }

    function getStates(BASE_VIEW_STOCK) {
        return [
            {
                state: 'listStkSection',
                config: {
                    url: '/stk-section',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-section/views/list.html',
                    controller: 'StkSectionController',
                    controllerAs: 'vm',
                    title: 'List StkSections',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> StkSections'
                    }
                }
            },
            {
                state: 'createStkSection',
                config: {
                    url: '/stk-section/create',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-section/views/create.html',
                    controller: 'StkSectionController',
                    controllerAs: 'vm',
                    title: 'Create StkSection'
                }
            },
            {
                state: 'viewStkSection',
                config: {
                    url: '/stk-section/:stkSectionId',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-section/views/view.html',
                    controller: 'StkSectionController',
                    controllerAs: 'vm',
                    title: 'View StkSection'
                }
            },
            {
                state: 'editStkSection',
                config: {
                    url: '/stk-section/:stkSectionId/edit',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-section/views/edit.html',
                    controller: 'StkSectionController',
                    controllerAs: 'vm',
                    title: 'Edit StkSection'
                }
            }
        ];
    }
})();
