(function() {
    'use strict';

    angular
        .module('app.stkLotStockQty')
        .factory('StockArticlelot', StockArticlelot);

    StockArticlelot.$inject = ['$resource', 'API_BASE_ADSTOCK_URL'];
    /* @ngInject */
    function StockArticlelot($resource, API_BASE_ADSTOCK_URL) {

        var params = {
            stockArticlelotId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findCustom:{
                method: 'POST',
                url: API_BASE_ADSTOCK_URL + '/stkartstockqtys/findCustom'
            },
            findByLike:{
            	method: 'POST',
            	url: API_BASE_ADSTOCK_URL + '/stkartstockqtys/findByLike'
            },
            listAll:{
                method: 'GET',
                url: API_BASE_ADSTOCK_URL + '/stkartstockqtys',
                params:{
                    start: -1,
                    max: 20
                }
            }
        };

        var API_URL = API_BASE_ADSTOCK_URL + '/stkartstockqtys/:stockArticlelotId';

        return $resource(API_URL, params, actions);

    }

})();
