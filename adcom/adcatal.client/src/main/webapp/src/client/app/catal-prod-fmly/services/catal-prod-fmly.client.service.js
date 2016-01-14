(function() {
    'use strict';

    angular
        .module('app.catalProdFmly')
        .factory('CatalProdFmly', CatalProdFmly);

    CatalProdFmly.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function CatalProdFmly($resource, API_BASE_URL) {

        var params = {
            catalProdFmlyId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findByLike: {
                method: 'POST',
                url: API_BASE_URL + '/catalproductfamilys/findByLike'
            },
            findCustom:{
                method: 'POST',
                url: API_BASE_URL + '/catalproductfamilys/findCustom'
            }
        };

        var API_URL = API_BASE_URL + '/catalproductfamilys/:catalProdFmlyId';

        return $resource(API_URL, params, actions);

    }

})();
