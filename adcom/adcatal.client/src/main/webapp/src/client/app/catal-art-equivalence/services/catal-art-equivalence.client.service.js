(function() {
    'use strict';

    angular
        .module('app.catalArtEquivalence')
        .factory('CatalArtEquivalence', CatalArtEquivalence);

    CatalArtEquivalence.$inject = ['$resource', 'API_BASE_ADCATAL_URL'];
    /* @ngInject */
    function CatalArtEquivalence($resource, API_BASE_ADCATAL_URL) {

        var params = {
            catalArtEquivalenceId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findBy: {
                method: 'POST',
                url: API_BASE_ADCATAL_URL + '/catalartequivalences/findBy'
            }
        };

        var API_URL = API_BASE_ADCATAL_URL + '/catalartequivalences/:catalArtEquivalenceId';

        return $resource(API_URL, params, actions);

    }

})();
