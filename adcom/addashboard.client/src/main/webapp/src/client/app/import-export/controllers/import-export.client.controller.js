(function () {
    'use strict';

    angular
        .module('app.importExport')
        .controller('ImportExportController', ImportExportController);

    ImportExportController.$inject = ['logger',
        '$stateParams',
        '$location'
        ];
    /* @ngInject */
    function ImportExportController(logger,
        $stateParams,
        $location
        ) {

        var vm = this;

    }

})();
