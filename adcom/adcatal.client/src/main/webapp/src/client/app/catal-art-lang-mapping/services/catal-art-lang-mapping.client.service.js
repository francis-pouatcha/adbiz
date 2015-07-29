(function() {
    'use strict';

    angular
        .module('app.catalArtLangMapping')
        .factory('CatalArtLangMapping', CatalArtLangMapping);

    CatalArtLangMapping.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function CatalArtLangMapping($resource, API_BASE_URL) {

        var params = {
            catalArtLangMappingId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findBy: {
                method: 'POST',
                url: API_BASE_URL + '/catalartfeatmappings/findBy'
            }
        };

        var API_URL = API_BASE_URL + '/catalartfeatmappings/:catalArtLangMappingId';

        return $resource(API_URL, params, actions);

    }

})();
