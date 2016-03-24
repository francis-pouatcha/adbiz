(function() {
    'use strict';

    angular
        .module('app.catalArtLangMapping')
        .factory('CatalArtLangMapping', CatalArtLangMapping);

    CatalArtLangMapping.$inject = ['$resource', 'API_BASE_ADCATAL_URL'];
    /* @ngInject */
    function CatalArtLangMapping($resource, API_BASE_ADCATAL_URL) {

        var params = {
            catalArtLangMappingId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findBy: {
                method: 'POST',
                url: API_BASE_ADCATAL_URL + '/catalartfeatmappings/findBy'
            },
            findByCntnrIdentifIn: {
                method: 'POST',
                url: API_BASE_ADCATAL_URL + '/catalartfeatmappings/findByCntnrIdentifIn'
            }
        };

        var API_URL = API_BASE_ADCATAL_URL + '/catalartfeatmappings/:catalArtLangMappingId';

        return $resource(API_URL, params, actions);

    }

})();
