(function() {
    'use strict';

    angular
        .module('app.stkLotStockQty')
        .run(appRun);

    appRun.$inject = ['routerHelper', 'BASE_VIEW_STOCK','$translate'];
    /* @ngInject */
    function appRun(routerHelper, BASE_VIEW_STOCK,$translate) {
        routerHelper.configureStates(getStates(BASE_VIEW_STOCK));
        console.log("config article lot QTy route");
    }

    function getStates(BASE_VIEW_STOCK) {
        return [
            {
                state: 'liststk-lotqty',
                config: {
                    url: '/stk-lotqty',
                    templateUrl: BASE_VIEW_STOCK+'/app/stk-lotqty/views/list.html',
                    controller: 'StockArticlelotQtyController',
                    controllerAs: 'vm',
                    title: 'List stk-lotqtys',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> Stock Lot Mvnt'
                    }
                }
            },
        ];
    }
})();
