(function() {
    'use strict';

    angular
        .module('app.stockArticlelot')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listStock-articlelot',
                config: {
                    url: '/stock-articlelot',
                    templateUrl: 'app/stock-articlelot/views/list.html',
                    controller: 'Stock-articlelotController',
                    controllerAs: 'vm',
                    title: 'List Stock-articlelots',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> Stock-articlelots'
                    }
                }
            },
            {
                state: 'createStock-articlelot',
                config: {
                    url: '/stock-articlelot/create',
                    templateUrl: 'app/stock-articlelot/views/create.html',
                    controller: 'Stock-articlelotController',
                    controllerAs: 'vm',
                    title: 'Create Stock-articlelot'
                }
            },
            {
                state: 'viewStock-articlelot',
                config: {
                    url: '/stock-articlelot/:stockArticlelotId',
                    templateUrl: 'app/stock-articlelot/views/view.html',
                    controller: 'Stock-articlelotController',
                    controllerAs: 'vm',
                    title: 'View Stock-articlelot'
                }
            },
            {
                state: 'editStock-articlelot',
                config: {
                    url: '/stock-articlelot/:stockArticlelotId/edit',
                    templateUrl: 'app/stock-articlelot/views/edit.html',
                    controller: 'Stock-articlelotController',
                    controllerAs: 'vm',
                    title: 'Edit Stock-articlelot'
                }
            }
        ];
    }
})();
