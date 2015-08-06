(function () {
    'use strict';

    angular
        .module('app.stockArticlelot')
        .controller('StockArticlelotController', StockArticlelotController);

    StockArticlelotController.$inject = ['logger',
        '$stateParams',
        '$location',
        'StockArticlelot',
        'TableSettings',
        'StockArticlelotForm'];
    /* @ngInject */
    function StockArticlelotController(logger,
        $stateParams,
        $location,
        StockArticlelot,
        TableSettings,
        StockArticlelotForm) {

        var vm = this;

        vm.tableParams = TableSettings.getParams(StockArticlelot);
        vm.stockArticlelot = {};

        vm.setFormFields = function(disabled) {
            vm.formFields = StockArticlelotForm.getFormFields(disabled);
        };

        vm.create = function() {

            // Create new StockArticlelot object

            var stockArticlelot = new StockArticlelot(vm.stockArticlelot);

            // Redirect after save
            stockArticlelot.$save(function(response) {
                logger.success('StockArticlelot created');
                $location.path('StockArticlelot/' + response.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing StockArticlelot
        vm.remove = function(stockArticlelot) {

            if (stockArticlelot) {
<<<<<<< HEAD
                stockArticlelot = StockArticlelot.get({stockArticlelotId:stockArticlelot.id}, function() {
=======
                stockArticlelot = StockArticlelot({stockArticlelotId:stockArticlelot.id}, function() {
>>>>>>> cbf35b40cda026d8afeba0f31ddcce55993ceed4
                    stockArticlelot.$remove(function() {
                        logger.success('StockArticlelot deleted');
                        vm.tableParams.reload();
                    });
                });
            } else {
                vm.stockArticlelot.$remove(function() {
                    logger.success('StockArticlelot deleted');
                    $location.path('/StockArticlelot');
                });
            }

        };

        // Update existing StockArticlelot
        vm.update = function() {
            var stockArticlelot = vm.stockArticlelot;

            stockArticlelot.$update(function() {
                logger.success('StockArticlelot updated');
                $location.path('StockArticlelot/' + stockArticlelot.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewStockArticlelot = function() {
            vm.stockArticlelot = StockArticlelot.get({stockArticlelotId: $stateParams.stockArticlelotId});
            vm.setFormFields(true);
        };

        vm.toEditStockArticlelot = function() {
            vm.stockArticlelot = StockArticlelot.get({stockArticlelotId: $stateParams.stockArticlelotId});
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated StockArticlelot View');
        }
    }

})();
