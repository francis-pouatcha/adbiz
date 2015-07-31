(function () {
    'use strict';

    angular
        .module('app.catalProdFmly')
        .controller('CatalProdFmlyController', CatalProdFmlyController);

    CatalProdFmlyController.$inject = ['logger',
        '$stateParams',
        '$location',
        'CatalProdFmly',
        'TableSettings',
        'CatalProdFmlyForm'];
    /* @ngInject */
    function CatalProdFmlyController(logger,
        $stateParams,
        $location,
        CatalProdFmly,
        TableSettings,
        CatalProdFmlyForm) {

        var vm = this;

        vm.tableParams = TableSettings.getParams(CatalProdFmly);
        vm.catalProdFmly = {};

        vm.setFormFields = function(disabled) {
            vm.formFields = CatalProdFmlyForm.getFormFields(disabled);
        };

        vm.create = function() {
            // Create new CatalProdFmly object
            var catalProdFmly = new CatalProdFmly(vm.catalProdFmly);

            // Redirect after save
            catalProdFmly.$save(function(response) {
                logger.success('CatalProdFmly created');
                $location.path('catal-prod-fmly/' + response.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing CatalProdFmly
        vm.remove = function(catalProdFmly) {

            if (catalProdFmly) {
                catalProdFmly = CatalProdFmly.get({catalProdFmlyId:catalProdFmly.id}, function() {
                    catalProdFmly.$remove(function() {
                        logger.success('CatalProdFmly deleted');
                        vm.tableParams.reload();
                    });
                });
            } else {
                vm.catalProdFmly.$remove(function() {
                    logger.success('CatalProdFmly deleted');
                    $location.path('/catal-prod-fmly');
                });
            }

        };

        // Update existing CatalProdFmly
        vm.update = function() {
            var catalProdFmly = vm.catalProdFmly;

            catalProdFmly.$update(function() {
                logger.success('CatalProdFmly updated');
                $location.path('catal-prod-fmly/' + catalProdFmly.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewCatalProdFmly = function() {
            vm.catalProdFmly = CatalProdFmly.get({catalProdFmlyId: $stateParams.catalProdFmlyId});
            CatalProdFmlyForm.catalProdFmlyId = $stateParams.catalProdFmlyId;
            vm.setFormFields(true);
        };

        vm.toEditCatalProdFmly = function() {
            vm.catalProdFmly = CatalProdFmly.get({catalProdFmlyId: $stateParams.catalProdFmlyId});
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated CatalProdFmly View');
        }
    }

})();
