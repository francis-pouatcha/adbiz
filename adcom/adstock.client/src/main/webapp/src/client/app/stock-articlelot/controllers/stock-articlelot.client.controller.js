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
        'StockArticlelotForm',
        '$translate',
        'utils'];
    /* @ngInject */
    function StockArticlelotController(logger,
        $stateParams,
        $location,
        StockArticlelot,
        TableSettings,
        StockArticlelotForm,
        $translate,
        utils) {

        var vm = this;
        vm.data = {
            total: 0,
            list: []
        };
        
        // Initialize Search input and pagination
        vm.searchInput = utils.searchInputInit().searchInput;
        vm.searchInput.className = 'org.adorsys.adstock.jpa.StkArticleLotSearchInput';
        vm.pagination = utils.searchInputInit().pagination;

        //vm.tableParams = TableSettings.getParams(StockArticlelot);
        vm.stockArticlelot = {};

        vm.setFormFields = function(disabled) {
            vm.formFields = StockArticlelotForm.getFormFields(disabled);
        };
        
        vm.setTabsFormFields = function(disabled, model){
            vm.tabs = StockArticlelotForm.getTabsFormFields(disabled, model);
        }

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
                stockArticlelot = StockArticlelot.get({stockArticlelotId:stockArticlelot.id}, function() {
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
            vm.setTabsFormFields(true, vm.stockArticlelot);
        };

        vm.toEditStockArticlelot = function() {
            vm.stockArticlelot = StockArticlelot.get({stockArticlelotId: $stateParams.stockArticlelotId});
            vm.setFormFields(false);
        };
        
        // Paginate over the list
        vm.paginate = function(newPage){
            utils.pagination(vm.searchInput, vm.pagination, newPage);
            findCustomStkArticlesLots();
        };

        activate();
        findCustomStkArticlesLots();
        //findAllStkArticlesLots();

        function activate() {
            //logger.info('Activated StockArticlelot View');
        }
        
        function findAllStkArticlesLots(){
            StockArticlelot.listAll(function (response) {
                vm.data.list = response.resultList;
                vm.data.total = response.total;  
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });  
        }
        
        function findCustomStkArticlesLots(){
            StockArticlelot.findCustom(vm.searchInput, function(response){
                vm.data.list = response.resultList;
                vm.data.total = response.total;
                    console.log(vm.data);
            }, 
            function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });  
        }
    }

})();
