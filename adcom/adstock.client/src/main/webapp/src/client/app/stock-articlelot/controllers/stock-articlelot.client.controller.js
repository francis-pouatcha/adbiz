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
        'utils',
        'StkSection',
    'fileExtractor'];
    /* @ngInject */
    function StockArticlelotController(logger,
        $stateParams,
        $location,
        StockArticlelot,
        TableSettings,
        StockArticlelotForm,
        utils,
        StkSection,
        fileExtractor) {

        var vm = this;
        vm.data = {
            list: []
        };
        
        function initSearchInput(){
            // Initialize Search input and pagination
            vm.searchInput = utils.searchInputInit().searchInput;
            vm.searchInput.className = 'org.adorsys.adstock.jpa.StkArticleLotSearchInput';
            // Number of entries showed per page.
            vm.itemsByPage = utils.searchInputInit().stPagination.number;
        }

        vm.stockArticlelot = {};

        vm.setFormFields = function(disabled) {
            vm.formFields = StockArticlelotForm.getFormFields(disabled);
        };
        
        vm.setTabsFormFields = function(disabled, model){
            vm.tabs = StockArticlelotForm.getTabsFormFields(disabled, model);
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

            var searchInput = utils.searchInputInit().searchInput;
            searchInput.className = 'org.adorsys.adstock.jpa.StkSectionSearchInput';
            searchInput.entity.identif = vm.stockArticlelot.section;
            searchInput.fieldNames.push('identif');
            StkSection.findByLike(searchInput, function(response) {
                    vm.stockArticlelot.section = response.resultList[0].name;
                },
                function(errorResponse) {
                    vm.error = errorResponse;
                    logger.warn(vm.error);
                });
        };

        vm.toEditStockArticlelot = function() {
            vm.stockArticlelot = StockArticlelot.get({stockArticlelotId: $stateParams.stockArticlelotId});
            vm.setFormFields(false);
        };


        initSearchInput();
        
        vm.callServer = function(tableState) {
    	    var pagination = tableState.pagination;
    	    var start = pagination.start || 0, number = pagination.number || utils.searchInputInit().stPagination.number;
    	    processSearch(start, tableState.search);
        	
    	    StockArticlelot.findByLike(vm.searchInput, function(response) {
                vm.data.list = response.resultList;
                tableState.pagination.numberOfPages = Math.ceil(response.count / number)
            },
            function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        function processSearch(start, searchObject) {
        	// First initialize SearchInput-Object and then set Search-Params
        	vm.searchInput = utils.processSearch(vm.searchInput, searchObject.predicateObject);
        	vm.searchInput.start = start;
        }
    }

})();
