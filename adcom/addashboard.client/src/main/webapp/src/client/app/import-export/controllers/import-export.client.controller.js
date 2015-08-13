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
<<<<<<< HEAD
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

=======
            ImportExport.export({xlsType: vm.xlsType}, function(data) {
                fileExtractor.saveFile(data, 'sample.xls', 'application/vnd.ms-excel');
            }, function(error) {
                console.log(error);
            });
>>>>>>> deea8acc685284bdae6468267524a75306d90189
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
