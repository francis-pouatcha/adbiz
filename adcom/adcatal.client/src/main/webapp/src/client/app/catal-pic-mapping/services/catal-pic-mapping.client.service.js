(function() {
    'use strict';

    angular
        .module('app.catalPicMapping')
        .factory('CatalPicMapping', CatalPicMapping);

    CatalPicMapping.$inject = ['$resource', 'API_BASE_ADCATAL_URL'];
    /* @ngInject */
    function CatalPicMapping($resource, API_BASE_ADCATAL_URL) {

        var params = {
            catalPicMappingId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findBy: {
                method: 'POST',
                url: API_BASE_ADCATAL_URL + '/catalpicmappings/findBy'
            }
        };

        var API_URL = API_BASE_ADCATAL_URL + '/catalpicmappings/:catalPicMappingId';

        return $resource(API_URL, params, actions);

    }

})();
