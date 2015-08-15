(function() {
    'use strict';

    angular
        .module('app.importExport')
        .factory('ImportExport', ImportExport);

    ImportExport.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function ImportExport($resource, API_BASE_URL) {

        var params = {
            importExportId: '@id',
            xlsType: '@xlsType'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            export: {
                method: 'POST',
                url: API_BASE_URL + '/importExport/download/:xlsType',
                responseType: 'ArrayBuffer'
            }
        };

        var API_URL = API_BASE_URL + '/importExport/:importExportId';

        return $resource(API_URL, params, actions);

    }

})();
