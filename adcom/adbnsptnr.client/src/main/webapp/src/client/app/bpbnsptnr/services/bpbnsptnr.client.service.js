(function() {
    'use strict';

    angular
        .module('app.bpbnsptnr')
        .factory('Bpbnsptnr', Bpbnsptnr);

    Bpbnsptnr.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function Bpbnsptnr($resource, API_BASE_URL) {

        var params = {
            bpbnsptnrId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            }
        };

        var API_URL = API_BASE_URL + '/bpbnsptnr/:bpbnsptnrId';

        return $resource(API_URL, params, actions);

    }

})();
