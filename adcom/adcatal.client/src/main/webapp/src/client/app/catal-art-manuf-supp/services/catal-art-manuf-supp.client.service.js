(function() {
    'use strict';

    angular
        .module('app.catalArtManufSupp')
        .factory('CatalArtManufSupp', CatalArtManufSupp);

    CatalArtManufSupp.$inject = ['$resource', 'API_BASE_ADCATAL_URL'];
    /* @ngInject */
    function CatalArtManufSupp($resource, API_BASE_ADCATAL_URL) {

        var params = {
            catalArtManufSuppId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findBy: {
                method: 'POST',
                url: API_BASE_ADCATAL_URL + '/catalartmanufsupps/findBy'
            }
        };

        var API_URL = API_BASE_ADCATAL_URL + '/catalartmanufsupps/:catalArtManufSuppId';

        return $resource(API_URL, params, actions);

    }

})();
