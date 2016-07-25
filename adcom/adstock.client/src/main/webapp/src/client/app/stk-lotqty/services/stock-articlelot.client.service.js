(function() {
    'use strict';

    angular
        .module('app.stkLotStockQty')
        .factory('StockArticlelotQty', StockArticlelotQty);

    StockArticlelotQty.$inject = ['$resource', 'API_BASE_ADSTOCK_URL'];
    /* @ngInject */
    function StockArticlelotQty($resource, API_BASE_ADSTOCK_URL) {

        var params = {
            stockArticlelotId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findCustom:{
                method: 'POST',
                url: API_BASE_ADSTOCK_URL + '/stklotstockqtys/findCustom'
            },
            findByLike:{
            	method: 'POST',
            	url: API_BASE_ADSTOCK_URL + '/stklotstockqtys/findByLike'
            },
            listAll:{
                method: 'GET',
                url: API_BASE_ADSTOCK_URL + '/stklotstockqtys',
                params:{
                    start: -1,
                    max: 20
                }
            }
        };

        var API_URL = API_BASE_ADSTOCK_URL + '/stklotstockqtys/:stockArticlelotId';

        return $resource(API_URL, params, actions);

    }

})();
