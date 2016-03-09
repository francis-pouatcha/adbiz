(function() {
    'use strict';

    angular
        .module('app.bpbnsptnr')
        .factory('Bpbnsptnr', Bpbnsptnr);

    Bpbnsptnr.$inject = ['$resource','API_BASE_ADBNSPTNR_URL'];
    /* @ngInject */
    function Bpbnsptnr($resource, API_BASE_ADBNSPTNR_URL) {

        var params = {
            bpbnsptnrId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            }
        };

        return $resource(API_BASE_ADBNSPTNR_URL + '/bpbnsptnr/:bpbnsptnrId', params, actions);

    }

})();
