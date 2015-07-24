(function() {
    'use strict';

    angular
        .module('app.catalArtDetailConfig')
        .factory('CatalArtDetailConfig', CatalArtDetailConfig);

    CatalArtDetailConfig.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function CatalArtDetailConfig($resource, API_BASE_URL) {

        var params = {
            catalArtDetailConfigId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findBy: {
                method: 'POST',
                url: API_BASE_URL + '/catalartdetailconfigs/findBy'
            }
        };

        var API_URL = API_BASE_URL + '/catalartdetailconfigs/:catalArtDetailConfigId';

        return $resource(API_URL, params, actions);

    }

})();
