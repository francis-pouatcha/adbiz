(function() {
    'use strict';

    angular
        .module('app.catalProdFmlyLangMap')
        .factory('CatalProdFmlyLangMap', CatalProdFmlyLangMap);

    CatalProdFmlyLangMap.$inject = ['$resource', 'API_BASE_ADCATAL_URL'];
    /* @ngInject */
    function CatalProdFmlyLangMap($resource, API_BASE_ADCATAL_URL) {

        var params = {
            catalProdFmlyLangMapId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findBy: {
                method: 'POST',
                url: API_BASE_ADCATAL_URL + '/catalfamilyfeatmapings/findBy'
            },
            findByCntnrIdentifIn: {
                method: 'POST',
                url: API_BASE_ADCATAL_URL + '/catalfamilyfeatmapings/findByCntnrIdentifIn'
            }
        };

        var API_URL = API_BASE_ADCATAL_URL + '/catalfamilyfeatmapings/:catalProdFmlyLangMapId';

        return $resource(API_URL, params, actions);

    }

})();
