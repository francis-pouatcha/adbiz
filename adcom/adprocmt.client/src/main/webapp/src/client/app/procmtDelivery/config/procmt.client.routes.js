(function() {
    'use strict';

    angular
        .module('app.procmtDelivery')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'procmtDelivery',
                config: {
                    url: '/',
                    templateUrl: '/adprocmt.client/src/client/app/procmtDelivery/views/list.html',
                    controller: 'ProcmtCtlr',
                    //controllerAs: 'vm',
                    title: 'List Procurement'
                }
            },
            {
                state: 'listprocmtDelivery',
                config: {
                    url: '/procmtDelivery',
                    templateUrl: '/adprocmt.client/src/client/app/procmtDelivery/views/list.html',
                    controller: 'ProcmtCtlr',
                    //controllerAs: 'vm',
                    title: 'List Procurement',
                    settings: {
                        nav: 4,
                        content: '<i class="fa fa-folder-open"></i> Delivery'
                    }
                }
            },
            {
                state: 'createprocmtDelivery',
                config: {
                    url: '/procmtDelivery/create',
                    templateUrl: '/adprocmt.client/src/client/app/procmtDelivery/views/create.html',
                    controller: 'ProcmtCreateCtlr',
                    //controllerAs: 'vm',
                    title: 'Create Procurement'
                }
            },
            {
                state: 'viewprocmtDelivery',
                config: {
                    url: '/procmtDelivery/:procmtNbr/show/',
                    templateUrl: '/adprocmt.client/src/client/app/procmtDelivery/views/view.html',
                    controller: 'ProcmtShowCtlr',
                    //controllerAs: 'vm',
                    title: 'View Procurement'
                }
            },
            {
                state: 'editprocmtDelivery',
                config: {
                    url: '/procmtDelivery/:procmtId/edit',
                    templateUrl: '/adprocmt.client/src/client/app/procmtDelivery/views/edit.html',
                    controller: 'ProcmtCtlr',
                    //controllerAs: 'vm',
                    title: 'Edit Procurement'
                }
            }
        ];
    }
})();
