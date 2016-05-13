(function() {
    'use strict';

    angular
        .module('app.stkMvnt')
        .run(appRun);

    appRun.$inject = ['routerHelper', 'BASE_VIEW_STOCK','$translate'];
    /* @ngInject */
    function appRun(routerHelper, BASE_VIEW_STOCK,$translate) {
        $translate('StkMvnts.title').then(function(StkMvnts){
            routerHelper.configureStates(getStates(BASE_VIEW_STOCK, StkMvnts));
        });
    }

    function getStates(BASE_VIEW_STOCK,StkMvnts) {
        return [
            {
                state: 'listStkMvnt',
                config: {
                    url: '/stk-mvnt',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-mvnt/views/list.html',
                    controller: 'StkMvntController',
                    controllerAs: 'vm',
                    title: 'List StkMvnts',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> '+StkMvnts
                    }
                }
            },
            {
                state: 'createStkMvnt',
                config: {
                    url: '/stk-mvnt/create',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-mvnt/views/create.html',
                    controller: 'StkMvntController',
                    controllerAs: 'vm',
                    title: 'Create StkMvnt'
                }
            },
            {
                state: 'viewStkMvnt',
                config: {
                    url: '/stk-mvnt/:stkMvntId',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-mvnt/views/view.html',
                    controller: 'StkMvntController',
                    controllerAs: 'vm',
                    title: 'View StkMvnt'
                }
            },
            {
                state: 'editStkMvnt',
                config: {
                    url: '/stk-mvnt/:stkMvntId/edit',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-mvnt/views/edit.html',
                    controller: 'StkMvntController',
                    controllerAs: 'vm',
                    title: 'Edit StkMvnt'
                }
            }
        ];
    }
})();
