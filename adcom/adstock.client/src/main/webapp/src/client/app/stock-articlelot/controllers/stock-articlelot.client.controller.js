(function () {
    'use strict';

    angular
        .module('app.stockArticlelot')
        .controller('Stock-articlelotController', Stock-articlelotController);

    Stock-articlelotController.$inject = ['logger',
        '$stateParams',
        '$location',
        'Stock-articlelot',
        'TableSettings',
        'Stock-articlelotForm'];
    /* @ngInject */
    function Stock-articlelotController(logger,
        $stateParams,
        $location,
        Stock-articlelot,
        TableSettings,
        Stock-articlelotForm) {

        var vm = this;

        vm.tableParams = TableSettings.getParams(Stock-articlelot);
        vm.stockArticlelot = {};

        vm.setFormFields = function(disabled) {
            vm.formFields = Stock-articlelotForm.getFormFields(disabled);
        };

        vm.create = function() {
            // Create new Stock-articlelot object
            var stockArticlelot = new Stock-articlelot(vm.stockArticlelot);

            // Redirect after save
            stockArticlelot.$save(function(response) {
                logger.success('Stock-articlelot created');
                $location.path('stock-articlelot/' + response.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing Stock-articlelot
        vm.remove = function(stockArticlelot) {

            if (stockArticlelot) {
                stockArticlelot = Stock-articlelot.get({stockArticlelotId:stockArticlelot.id}, function() {
                    stockArticlelot.$remove(function() {
                        logger.success('Stock-articlelot deleted');
                        vm.tableParams.reload();
                    });
                });
            } else {
                vm.stockArticlelot.$remove(function() {
                    logger.success('Stock-articlelot deleted');
                    $location.path('/stock-articlelot');
                });
            }

        };

        // Update existing Stock-articlelot
        vm.update = function() {
            var stockArticlelot = vm.stockArticlelot;

            stockArticlelot.$update(function() {
                logger.success('Stock-articlelot updated');
                $location.path('stock-articlelot/' + stockArticlelot.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewStock-articlelot = function() {
            vm.stockArticlelot = Stock-articlelot.get({stockArticlelotId: $stateParams.stockArticlelotId});
            vm.setFormFields(true);
        };

        vm.toEditStock-articlelot = function() {
            vm.stockArticlelot = Stock-articlelot.get({stockArticlelotId: $stateParams.stockArticlelotId});
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated Stock-articlelot View');
        }
    }

})();
