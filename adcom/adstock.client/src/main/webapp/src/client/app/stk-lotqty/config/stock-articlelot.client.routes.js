(function() {
    'use strict';

    angular
        .module('app.stkLotStockQty')
        .run(appRun);

    appRun.$inject = ['routerHelper','BASE_VIEW_STOCK','$translate'];
    /* @ngInject */
    function appRun(routerHelper,BASE_VIEW_STOCK,$translate) {
        $translate('stk-lotqtys.title').then(function(StockArticlelots){
            routerHelper.configureStates(getStates(BASE_VIEW_STOCK, StockArticlelots));
        });
    }

    function getStates(BASE_VIEW_STOCK,StockArticlelots){
        return [
            {
                state: 'Articlelot',
                config: {
                    url: '/',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-lotqty/views/list.html',
                    controller: 'StockArticlelotController',
                    controllerAs: 'vm',
                    title: 'List stk-lotqtys'
                }
            },
            {
                state: 'liststk-lotqty',
                config: {
                    url: '/stk-lotqty',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-lotqty/views/list.html',
                    controller: 'StockArticlelotController',
                    controllerAs: 'vm',
                    title: 'List stk-lotqtys',
                    settings: {
                        nav: 2,
                        content: '<i class="fa fa-folder-open"></i>'+StockArticlelots
                    }
                }
            },
            {
                state: 'createstk-lotqty',
                config: {
                    url: '/stk-lotqty/create',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-lotqty/views/create.html',
                    controller: 'StockArticlelotController',
                    controllerAs: 'vm',
                    title: 'Create stk-lotqty'
                }
            },
            {
                state: 'viewstk-lotqty',
                config: {
                    url: '/stk-lotqty/:stockArticlelotId',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-lotqty/views/view.html',
                    controller: 'StockArticlelotController',
                    controllerAs: 'vm',
                    title: 'View stk-lotqty'
                }
            },
            {
                state: 'editstk-lotqty',
                config: {
                    url: '/stk-lotqty/:stockArticlelotId/edit',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-lotqty/views/edit.html',
                    controller: 'StockArticlelotController',
                    controllerAs: 'vm',
                    title: 'Edit stk-lotqty'
                }
            }
        ];
    }
})();
