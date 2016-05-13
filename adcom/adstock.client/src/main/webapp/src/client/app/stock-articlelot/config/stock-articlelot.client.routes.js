(function() {
    'use strict';

    angular
        .module('app.stockArticlelot')
        .run(appRun);

    appRun.$inject = ['routerHelper','BASE_VIEW_STOCK','$translate'];
    /* @ngInject */
    function appRun(routerHelper,BASE_VIEW_STOCK,$translate) {
        $translate('Stock-articlelots.title').then(function(StockArticlelots){
            routerHelper.configureStates(getStates(BASE_VIEW_STOCK, StockArticlelots));
        });
    }

    function getStates(BASE_VIEW_STOCK,StockArticlelots){
        return [
            {
                state: 'Articlelot',
                config: {
                    url: '/',
                    templateUrl: BASE_VIEW_STOCK+'/app/stock-articlelot/views/list.html',
                    controller: 'StockArticlelotController',
                    controllerAs: 'vm',
                    title: 'List Stock-articlelots'
                }
            },
            {
                state: 'listStock-articlelot',
                config: {
                    url: '/stock-articlelot',
                    templateUrl: BASE_VIEW_STOCK+'/app/stock-articlelot/views/list.html',
                    controller: 'StockArticlelotController',
                    controllerAs: 'vm',
                    title: 'List Stock-articlelots',
                    settings: {
                        nav: 2,
                        content: '<i class="fa fa-folder-open"></i>'+StockArticlelots
                    }
                }
            },
            {
                state: 'createStock-articlelot',
                config: {
                    url: '/stock-articlelot/create',
                    templateUrl: BASE_VIEW_STOCK+'/app/stock-articlelot/views/create.html',
                    controller: 'StockArticlelotController',
                    controllerAs: 'vm',
                    title: 'Create Stock-articlelot'
                }
            },
            {
                state: 'viewStock-articlelot',
                config: {
                    url: '/stock-articlelot/:stockArticlelotId',
                    templateUrl: BASE_VIEW_STOCK+'/app/stock-articlelot/views/view.html',
                    controller: 'StockArticlelotController',
                    controllerAs: 'vm',
                    title: 'View Stock-articlelot'
                }
            },
            {
                state: 'editStock-articlelot',
                config: {
                    url: '/stock-articlelot/:stockArticlelotId/edit',
                    templateUrl: BASE_VIEW_STOCK+'/app/stock-articlelot/views/edit.html',
                    controller: 'StockArticlelotController',
                    controllerAs: 'vm',
                    title: 'Edit Stock-articlelot'
                }
            }
        ];
    }
})();
