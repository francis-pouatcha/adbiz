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
                    url: '/procmtDelivery',
                    templateUrl: '/adprocmt.client/src/client/app/procmtDelivery/views/list.html',
                    controller: 'ProcmtDeliveryCtlr',
                    //controllerAs: 'vm',
                    title: 'List Procurement Delivery'
                }
            },
            {
                state: 'listprocmtDelivery',
                config: {
                    url: '/procmtDelivery',
                    templateUrl: '/adprocmt.client/src/client/app/procmtDelivery/views/list.html',
                    controller: 'ProcmtDeliveryCtlr',
                    //controllerAs: 'vm',
                    title: 'List Procurement Delivery',
                    settings: {
                        nav: 2,
                        content: '<i class="fa fa-folder-open"></i> Delivery'
                    }
                }
            },
            {
                state: 'createprocmtDelivery',
                config: {
                    url: '/procmtDelivery/create',
                    templateUrl: '/adprocmt.client/src/client/app/procmtDelivery/views/create.html',
                    controller: 'ProcmtDeliveryCreateCtlr',
                    //controllerAs: 'vm',
                    title: 'Create Procurement Delivery'
                }
            },
            {
                state: 'viewprocmtDelivery',
                config: {
                    url: '/procmtDelivery/:procmtNbr/show/',
                    templateUrl: '/adprocmt.client/src/client/app/procmtDelivery/views/view.html',
                    controller: 'ProcmtDeliveryShowCtlr',
                    //controllerAs: 'vm',
                    title: 'View Procurement Delivery'
                }
            },
            {
                state: 'editprocmtDelivery',
                config: {
                    url: '/procmtDelivery/:procmtId/edit',
                    templateUrl: '/adprocmt.client/src/client/app/procmtDelivery/views/edit.html',
                    controller: 'ProcmtDeliveryCtlr',
                    //controllerAs: 'vm',
                    title: 'Edit Procurement Delivery'
                }
            }
        ];
    }
})();
