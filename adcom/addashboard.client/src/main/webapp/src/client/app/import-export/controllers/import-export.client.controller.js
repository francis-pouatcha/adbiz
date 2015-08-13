(function () {
    'use strict';

    angular
        .module('app.importExport')
        .controller('ImportExportController', ImportExportController);

    ImportExportController.$inject = ['logger', 'ImportExport',
        'fileExtractor'
    ];
    /* @ngInject */
    function ImportExportController(logger, fileExtractor, ImportExport) {

        var vm = this;

        vm.download = function() {
            console.log('download start');
            ImportExport.download ({xlsType: vm.xlsType})
                .$promise.then(
                    function(value) {
                        fileExtractor.extractFile(value, 'xls');
                    },
                    function(error) {
                        vm.error = error;
                    }
            );

        };

        vm.xlsChange = function() {

        };

        vm.params = {
            query: function (flowFile, flowChunk) {
                // function will be called for every request
                return {
                    xlsType: vm.xlsType
                };
            }
        };
    }

})();
