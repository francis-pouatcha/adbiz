(function() {
    'use strict';

    angular
        .module('app.sales')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listsales',
                config: {
                    url: '/sales',
                    templateUrl: '/adsales.client/src/client/app/sales/views/list.html',
                    controller: 'salesCtlr',
                    //controllerAs: 'vm',
                    title: 'List Ticket',
                    settings: {
                        nav: 2,
                        content: '<i class="fa fa-folder-open"></i> Sales'
                    }
                }
            },
            {
                state: 'createsales',
                config: {
                    url: '/sales/create',
                    templateUrl: '/adsales.client/src/client/app/sales/views/create.html',
                    controller: 'salesCreateCtlr',
                    //controllerAs: 'vm',
                    title: 'create sales'
                }
            },
            {
                state: 'sales',
                config: {
                    url: '/',
                    templateUrl: '/adsales.client/src/client/app/sales/views/view.html',
                    controller: 'salesShowCtlr',
                    //controllerAs: 'vm',
                    title: 'Point of sales',
                    settings: {
                        nav: 1,
                        content: '<i class="fa fa-folder-open"></i> Point of sales'
                    }
                }
            },
            {
                state: 'editsales',
                config: {
                    url: '/sales/:procmtId/edit',
                    templateUrl: '/adsales.client/src/client/app/sales/views/edit.html',
                    controller: 'salesCtlr',
                    //controllerAs: 'vm',
                    title: 'Edit sales'
                }
            }
        ];
    }
})();
