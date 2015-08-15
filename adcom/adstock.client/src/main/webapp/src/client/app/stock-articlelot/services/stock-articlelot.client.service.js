(function() {
    'use strict';

    angular
        .module('app.stockArticlelot')
        .factory('StockArticlelot', StockArticlelot);

    StockArticlelot.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function StockArticlelot($resource, API_BASE_URL) {

        var params = {
            stockArticlelotId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            }
        };

        var API_URL = API_BASE_URL + '/stockArticlelot/:stockArticlelotId';

        return $resource(API_URL, params, actions);

    }

})();
