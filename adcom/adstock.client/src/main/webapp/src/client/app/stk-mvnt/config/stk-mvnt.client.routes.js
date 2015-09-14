(function() {
    'use strict';

    angular
        .module('app.stkMvnt')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listStkMvnt',
                config: {
                    url: '/stk-mvnt',
                    templateUrl: 'app/stk-mvnt/views/list.html',
                    controller: 'StkMvntController',
                    controllerAs: 'vm',
                    title: 'List StkMvnts',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> StkMvnts'
                    }
                }
            },
            {
                state: 'createStkMvnt',
                config: {
                    url: '/stk-mvnt/create',
                    templateUrl: 'app/stk-mvnt/views/create.html',
                    controller: 'StkMvntController',
                    controllerAs: 'vm',
                    title: 'Create StkMvnt'
                }
            },
            {
                state: 'viewStkMvnt',
                config: {
                    url: '/stk-mvnt/:stkMvntId',
                    templateUrl: 'app/stk-mvnt/views/view.html',
                    controller: 'StkMvntController',
                    controllerAs: 'vm',
                    title: 'View StkMvnt'
                }
            },
            {
                state: 'editStkMvnt',
                config: {
                    url: '/stk-mvnt/:stkMvntId/edit',
                    templateUrl: 'app/stk-mvnt/views/edit.html',
                    controller: 'StkMvntController',
                    controllerAs: 'vm',
                    title: 'Edit StkMvnt'
                }
            }
        ];
    }
})();
