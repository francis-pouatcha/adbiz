(function() {
    'use strict';

    angular
        .module('app.catalProdFmlyLangMap')
        .factory('CatalProdFmlyLangMap', CatalProdFmlyLangMap);

    CatalProdFmlyLangMap.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function CatalProdFmlyLangMap($resource, API_BASE_URL) {

        var params = {
            catalProdFmlyLangMapId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            }
        };

        var API_URL = API_BASE_URL + '/catalfamilyfeatmapings/:catalProdFmlyLangMapId';

        return $resource(API_URL, params, actions);

    }

})();
