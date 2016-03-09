(function() {
    'use strict';

    angular
        .module('app.catalArt2ProductFamily')
        .factory('CatalArt2ProductFamily', CatalArt2ProductFamily);

    CatalArt2ProductFamily.$inject = ['$resource', 'API_BASE_ADCATAL_URL'];
    /* @ngInject */
    function CatalArt2ProductFamily($resource, API_BASE_ADCATAL_URL) {

        var params = {
            catalArt2ProductFamilyId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findBy: {
                method: 'POST',
                url: API_BASE_ADCATAL_URL + '/catalart2productfamilys/findBy'
            }
        };

        var API_URL = API_BASE_ADCATAL_URL + '/catalart2productfamilys/:catalArt2ProductFamilyId';

        return $resource(API_URL, params, actions);

    }

})();
