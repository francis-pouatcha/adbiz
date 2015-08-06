(function() {
    'use strict';

    angular
        .module('app.stockArticlelot')
        .factory('Stock-articlelot', Stock-articlelot);

    Stock-articlelot.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function Stock-articlelot($resource, API_BASE_URL) {

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
