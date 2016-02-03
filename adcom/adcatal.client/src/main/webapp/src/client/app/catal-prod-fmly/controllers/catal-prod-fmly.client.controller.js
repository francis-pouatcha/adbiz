(function () {
    'use strict';

    angular
        .module('app.catalProdFmly')
        .controller('CatalProdFmlyController', CatalProdFmlyController);

    CatalProdFmlyController.$inject = ['logger',
        '$stateParams',
        '$location',
        'CatalProdFmly',
        'utils',
        'CatalProdFmlyForm'];
    /* @ngInject */
    function CatalProdFmlyController(logger,
        $stateParams,
        $location,
        CatalProdFmly,
        utils,
        CatalProdFmlyForm) {

        var vm = this;
        vm.data = [];
        vm.catalProdFmly = {};
        
        function initSearchInput(){
            // Initialize Search input and pagination
            vm.searchInput = utils.searchInputInit().searchInput;
            vm.searchInput.className = 'org.adorsys.adcatal.jpa.CatalProdFmlySearchInput';
            vm.searchInput.sortFieldNames.push({fieldName:'valueDt'});
        }
        
        vm.itemsByPage = utils.searchInputInit().pagination.itemsPerPageVar;

        vm.setFormFields = function(disabled) {
            vm.formFields = CatalProdFmlyForm.getFormFields(disabled);
        };
        
        initSearchInput();

        vm.create = function() {
            vm.catalProdFmly.famPath = '/'+vm.catalProdFmly.identif+'/'+vm.catalProdFmly.parentIdentif+'/'
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

        vm.callServer = function(tableState) {
    	    var pagination = tableState.pagination;
    	    var start = pagination.start || 0, number = pagination.number || utils.searchInputInit().stPagination.number;
    	    processSearch(start, tableState.search);
        	
        	CatalProdFmly.findByLike(vm.searchInput, function(response) {
                vm.data.list = response.resultList;
                tableState.pagination.numberOfPages = Math.ceil(response.count / number)
                console.log(vm.data.list);
            },
            function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        function processSearch(start, searchObject) {
        	// First initialize SearchInput-Object and then set Search-Params
            vm.searchInput = utils.searchInputInit().searchInput;
        	vm.searchInput.start = start;
        	vm.searchInput = utils.processSearch(vm.searchInput, searchObject.predicateObject);
        }
    }

})();
