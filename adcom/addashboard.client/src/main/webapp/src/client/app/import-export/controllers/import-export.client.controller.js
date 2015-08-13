(function () {
    'use strict';

    angular
        .module('app.importExport')
        .controller('ImportExportController', ImportExportController);

    ImportExportController.$inject = ['logger', 'ImportExport',
        'fileExtractor'
    ];
    /* @ngInject */
    function ImportExportController(logger, ImportExport, fileExtractor) {

        var vm = this;

        vm.download = function() {
            ImportExport.export({xlsType: vm.xlsType}, function(data) {
                fileExtractor.saveFile(data, 'sample.xls', 'application/vnd.ms-excel');
            }, function(error) {
                console.log(error);
            });
        };

        vm.xlsChange = function() {

        };

        vm.params = {
            query: function (flowFile, flowChunk) {
                return {
                    xlsType: vm.xlsType
                };
            }
        };
    }

})();
