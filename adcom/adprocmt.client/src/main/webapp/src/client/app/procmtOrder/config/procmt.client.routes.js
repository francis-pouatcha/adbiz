(function() {
    'use strict';

    angular
        .module('app.procmt')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'Procmt',
                config: {
                    url: '/',
                    templateUrl: '/adprocmt.client/src/client/app/procmtOrder/views/list.html',
                    controller: 'ProcmtCtlr',
                    //controllerAs: 'vm',
                    title: 'List Procurement'
                }
            },
            {
                state: 'listProcmt',
                config: {
                    url: '/procmt',
                    templateUrl: '/adprocmt.client/src/client/app/procmtOrder/views/list.html',
                    controller: 'ProcmtCtlr',
                    //controllerAs: 'vm',
                    title: 'List Procurement',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> Orders'
                    }
                }
            },
            {
                state: 'createProcmt',
                config: {
                    url: '/procmt/create',
                    templateUrl: '/adprocmt.client/src/client/app/procmtOrder/views/create.html',
                    controller: 'ProcmtCreateCtlr',
                    //controllerAs: 'vm',
                    title: 'Create Procurement'
                }
            },
            {
                state: 'viewProcmt',
                config: {
                    url: '/procmt/:procmtNbr/show/',
                    templateUrl: '/adprocmt.client/src/client/app/procmtOrder/views/view.html',
                    controller: 'ProcmtShowCtlr',
                    //controllerAs: 'vm',
                    title: 'View Procurement'
                }
            },
            {
                state: 'editProcmt',
                config: {
                    url: '/procmt/:procmtId/edit',
                    templateUrl: '/adprocmt.client/src/client/app/procmtOrder/views/edit.html',
                    controller: 'ProcmtController',
                    //controllerAs: 'vm',
                    title: 'Edit Procurement'
                }
            }
        ];
    }
})();
