(function() {
    'use strict';

    angular
        .module('app.stkSection')
        .factory('StkSection', StkSection);

    StkSection.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function StkSection($resource, API_BASE_URL) {

        var params = {
            stkSectionId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findCustom:{
                method: 'POST',
                url: API_BASE_URL + '/stksections/findCustom'
            },
            listAll:{
                method: 'GET',
                url: API_BASE_URL + '/stksections',
                params:{
                    start: -1,
                    max: 20
                }
            }
        };

        var API_URL = API_BASE_URL + '/stksections/:stkSectionId';

        return $resource(API_URL, params, actions);

    }

})();
