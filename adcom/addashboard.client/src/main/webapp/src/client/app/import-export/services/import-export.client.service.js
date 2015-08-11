(function() {
    'use strict';

    angular
        .module('app.importExport')
        .factory('ImportExport', ImportExport);

    ImportExport.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function ImportExport($resource, API_BASE_URL) {

        var params = {
            importExportId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            }
        };

        var API_URL = API_BASE_URL + '/importExport/:importExportId';

        return $resource(API_URL, params, actions);

    }

})();
