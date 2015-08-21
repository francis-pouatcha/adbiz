(function () {
    'use strict';

    angular
        .module('app.bpbnsptnr')
        .controller('BpbnsptnrController', BpbnsptnrController);

    BpbnsptnrController.$inject = ['logger',
        '$stateParams',
        '$location',
        'Bpbnsptnr',
        'TableSettings',
        'BpbnsptnrForm'];
    /* @ngInject */
    function BpbnsptnrController(logger,
        $stateParams,
        $location,
        Bpbnsptnr,
        TableSettings,
        BpbnsptnrForm) {

        var vm = this;

        vm.tableParams = TableSettings.getParams(Bpbnsptnr);
        vm.bpbnsptnr = {};

        vm.setFormFields = function(disabled) {
            vm.formFields = BpbnsptnrForm.getFormFields(disabled);
        };

        vm.create = function() {
            // Create new Bpbnsptnr object
            var bpbnsptnr = new Bpbnsptnr(vm.bpbnsptnr);

            // Redirect after save
            bpbnsptnr.$save(function(response) {
                logger.success('Bpbnsptnr created');
                $location.path('bpbnsptnr/' + response.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing Bpbnsptnr
        vm.remove = function(bpbnsptnr) {

            if (bpbnsptnr) {
                bpbnsptnr = Bpbnsptnr.get({bpbnsptnrId:bpbnsptnr.id}, function() {
                    bpbnsptnr.$remove(function() {
                        logger.success('Bpbnsptnr deleted');
                        vm.tableParams.reload();
                    });
                });
            } else {
                vm.bpbnsptnr.$remove(function() {
                    logger.success('Bpbnsptnr deleted');
                    $location.path('/bpbnsptnr');
                });
            }

        };

        // Update existing Bpbnsptnr
        vm.update = function() {
            var bpbnsptnr = vm.bpbnsptnr;

            bpbnsptnr.$update(function() {
                logger.success('Bpbnsptnr updated');
                $location.path('bpbnsptnr/' + bpbnsptnr.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewBpbnsptnr = function() {
            vm.bpbnsptnr = Bpbnsptnr.get({bpbnsptnrId: $stateParams.bpbnsptnrId});
            vm.setFormFields(true);
        };

        vm.toEditBpbnsptnr = function() {
            vm.bpbnsptnr = Bpbnsptnr.get({bpbnsptnrId: $stateParams.bpbnsptnrId});
            vm.setFormFields(false);
        };


        activate();

        function activate() {
            //logger.info('Activated Bpbnsptnr View');
        }
    }

})();
