(function () {
    'use strict';

    angular
        .module('app.catalProdFmlyLangMap')
        .controller('CatalProdFmlyLangMapController', CatalProdFmlyLangMapController);

    CatalProdFmlyLangMapController.$inject = ['logger',
        '$stateParams',
        '$location',
        'CatalProdFmlyLangMap',
        'TableSettings',
        'CatalProdFmlyLangMapForm'];
    /* @ngInject */
    function CatalProdFmlyLangMapController(logger,
        $stateParams,
        $location,
        CatalProdFmlyLangMap,
        TableSettings,
        CatalProdFmlyLangMapForm) {

        var vm = this;

        vm.tableParams = TableSettings.getParams(CatalProdFmlyLangMap);
        vm.catalProdFmlyLangMap = {};

        vm.setFormFields = function(disabled) {
            vm.formFields = CatalProdFmlyLangMapForm.getFormFields(disabled);
        };

        vm.create = function() {
            // Create new CatalProdFmlyLangMap object
            var catalProdFmlyLangMap = new CatalProdFmlyLangMap(vm.catalProdFmlyLangMap);

            // Redirect after save
            catalProdFmlyLangMap.$save(function(response) {
                logger.success('CatalProdFmlyLangMap created');
                $location.path('catal-prod-fmly-lang-map/' + response.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing CatalProdFmlyLangMap
        vm.remove = function(catalProdFmlyLangMap) {

            if (catalProdFmlyLangMap) {
                catalProdFmlyLangMap = CatalProdFmlyLangMap
                    .get({catalProdFmlyLangMapId:catalProdFmlyLangMap.id}, function() {
                    catalProdFmlyLangMap.$remove(function() {
                        logger.success('CatalProdFmlyLangMap deleted');
                        vm.tableParams.reload();
                    });
                });
            } else {
                vm.catalProdFmlyLangMap.$remove(function() {
                    logger.success('CatalProdFmlyLangMap deleted');
                    $location.path('/catal-prod-fmly-lang-map');
                });
            }

        };

        // Update existing CatalProdFmlyLangMap
        vm.update = function() {
            var catalProdFmlyLangMap = vm.catalProdFmlyLangMap;

            catalProdFmlyLangMap.$update(function() {
                logger.success('CatalProdFmlyLangMap updated');
                $location.path('catal-prod-fmly-lang-map/' + catalProdFmlyLangMap.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewCatalProdFmlyLangMap = function() {
            vm.catalProdFmlyLangMap = CatalProdFmlyLangMap
                .get({catalProdFmlyLangMapId: $stateParams.catalProdFmlyLangMapId});
            vm.setFormFields(true);
        };

        vm.toEditCatalProdFmlyLangMap = function() {
            vm.catalProdFmlyLangMap = CatalProdFmlyLangMap
                .get({catalProdFmlyLangMapId: $stateParams.catalProdFmlyLangMapId});
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated CatalProdFmlyLangMap View');
        }
    }

})();
