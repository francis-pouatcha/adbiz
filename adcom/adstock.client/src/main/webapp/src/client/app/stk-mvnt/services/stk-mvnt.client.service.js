(function() {
    'use strict';

    angular
        .module('app.stkMvnt')
        .factory('StkMvnt', StkMvnt);

    StkMvnt.$inject = ['$resource', 'API_BASE_ADSTOCK_URL'];
    /* @ngInject */
    function StkMvnt($resource, API_BASE_ADSTOCK_URL) {

        var params = {
            stkMvntId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findCustom:{
                method: 'POST',
                url: API_BASE_ADSTOCK_URL + '/stkmvnts/findCustom'
            },
            listAll:{
                method: 'GET',
                url: API_BASE_ADSTOCK_URL + '/stkmvnts',
                params:{
                    start: -1,
                    max: 20
                }
            }
        };

        var API_URL = API_BASE_ADSTOCK_URL + '/stkmvnts/:stkMvntId';

        return $resource(API_URL, params, actions);

    }

})();
