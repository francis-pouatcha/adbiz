(function () {
    'use strict';

    angular
        .module('app.stkSection')
        .controller('StkSectionController', StkSectionController);

    StkSectionController.$inject = ['logger',
        '$stateParams',
        '$location',
        'StkSection',
        'StkSectionForm',
        'utils'];
    /* @ngInject */
    function StkSectionController(logger,
        $stateParams,
        $location,
        StkSection,
        StkSectionForm,
        utils) {

        var vm = this;
        vm.data = {
            list: []
        };
        
        vm.stkSection = {};

        vm.setFormFields = function(disabled) {
            vm.formFields = StkSectionForm.getFormFields(disabled);
        };
        
        vm.setSearchFormFields = function(disabled){
            vm.searchFormFields = StkSectionForm.getSearchFormFields(disabled);
        };
        
        function initSearchInput(){
            // Initialize Search input and pagination
            vm.searchInput = utils.searchInputInit().searchInput;
            vm.searchInput.className = 'org.adorsys.adstock.jpa.StkSectionSearchInput';
            vm.searchInput.sortFieldNames.push({fieldName:'valueDt'});
            // Number of entries showed per page.
            vm.itemsByPage = utils.searchInputInit().stPagination.number;
        }
        
        vm.create = function() {

            //vm.stkSection.path = vm.stkSection.cntnrIdentif.path+"/"+vm.stkSection.name;
            if(vm.stkSection.cntnrIdentif){
                vm.stkSection.cntnrIdentif = vm.stkSection.cntnrIdentif.identif;
            }

            // Create new StkSection object
            var stkSection = new StkSection(vm.stkSection);

            // Redirect after save
            stkSection.$save(function(response) {
                logger.success('StkSection created');
                $location.path('stk-section/' + response.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
                logger.warn(vm.error);
            });
        };

        // Remove existing StkSection
        vm.remove = function(stkSection) {
            if (stkSection) {
                stkSection = StkSection.get({stkSectionId:stkSection.id}, function() {
                    stkSection.$remove(function() {
                        logger.success('Section deleted');
                        $location.path('/stk-section');
                        //vm.tableParams.reload();
                    });
                });
            } else {
                vm.stkSection.$remove(function() {
                    logger.success('Section deleted');
                    $location.path('/stk-section');
                });
            }
        };

        // Update existing StkSection
        vm.update = function() {
            var stkSection = vm.stkSection;

            stkSection.$update(function() {
                logger.success('StkSection updated');
                $location.path('stk-section/' + stkSection.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewStkSection = function() {
            vm.stkSection = StkSection.get({stkSectionId: $stateParams.stkSectionId});
            vm.setFormFields(true);
        };

        vm.toEditStkSection = function() {
            vm.stkSection = StkSection.get({stkSectionId: $stateParams.stkSectionId});
            vm.setFormFields(false);
        };
        
        initSearchInput();
        
        vm.callServer = function(tableState) {
    	    var pagination = tableState.pagination;
    	    var start = pagination.start || 0, number = pagination.number || utils.searchInputInit().stPagination.number;
    	    processSearch(start, tableState.search);
        	
    	    StkSection.findByLike(vm.searchInput, function(response) {
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
