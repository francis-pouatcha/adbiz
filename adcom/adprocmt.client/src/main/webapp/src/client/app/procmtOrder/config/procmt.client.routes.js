(function() {
    'use strict';

    angular
        .module('app.procmt')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        var otherwise = '/procmt';
        routerHelper.configureStates(getStates(),otherwise);
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
                    title: 'List Procurement Order'
                }
            },
            {
                state: 'listProcmt',
                config: {
                    url: '/procmt',
                    templateUrl: '/adprocmt.client/src/client/app/procmtOrder/views/list.html',
                    controller: 'ProcmtCtlr',
                    //controllerAs: 'vm',
                    title: 'List Procurement Order',
                    settings: {
                        nav: 1,
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
                    title: 'Create Procurement Order'
                }
            },
            {
                state: 'viewProcmt',
                config: {
                    url: '/procmt/:procmtNbr/show/',
                    templateUrl: '/adprocmt.client/src/client/app/procmtOrder/views/view.html',
                    controller: 'ProcmtShowCtlr',
                    //controllerAs: 'vm',
                    title: 'View Procurement Order'
                }
            },
            {
                state: 'editProcmt',
                config: {
                    url: '/procmt/:procmtId/edit',
                    templateUrl: '/adprocmt.client/src/client/app/procmtOrder/views/edit.html',
                    controller: 'ProcmtController',
                    //controllerAs: 'vm',
                    title: 'Edit Procurement Order'
                }
            }
        ];
    }
})();
