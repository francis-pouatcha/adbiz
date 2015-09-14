(function () {
    'use strict';

    angular
        .module('app.stkSection')
        .controller('StkSectionController', StkSectionController);

    StkSectionController.$inject = ['logger',
        '$stateParams',
        '$location',
        'StkSection',
        'TableSettings',
        'StkSectionForm',
        'utils'];
    /* @ngInject */
    function StkSectionController(logger,
        $stateParams,
        $location,
        StkSection,
        TableSettings,
        StkSectionForm,
        utils) {

        var vm = this;
        vm.data = {
            total: 0,
            list: []
        };
        
        //vm.tableParams = TableSettings.getParams(StkSection);
        vm.stkSection = {};

        vm.setFormFields = function(disabled) {
            vm.formFields = StkSectionForm.getFormFields(disabled);
        };
        
        vm.setSearchFormFields = function(disabled){
            vm.searchFormFields = StkSectionForm.getSearchFormFields(disabled);
        }
        
        // Initialize Search input and pagination
        vm.searchInput = utils.searchInputInit().searchInput;
        vm.searchInput.className = 'org.adorsys.adstock.jpa.StkSectionSearchInput';
        vm.pagination = utils.searchInputInit().pagination;
        

        vm.create = function() {
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
        
        vm.clear = function(){
            vm.stkSection = {};
            return;
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
        
        // Paginate over the list
        vm.paginate = function(newPage){
            utils.pagination(vm.searchInput, vm.pagination, newPage);
            vm.findCustomSections();
        };
        
        vm.search = function(){
            vm.searchInput = utils.processSearch(vm.searchInput, vm.stkSection);
            vm.pagination = utils.resetPagination(vm.pagination);
            vm.findCustomSections();
        };
        

        activate();

        function activate() {
            //logger.info('Activated StkSection View');
        };
        
        function findAllSections(){
            StkSection.listAll(function (response) {
                vm.data.list = response.resultList;
                vm.data.total = response.count;
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });  
        };
        
        vm.findCustomSections = function(){
            StkSection.findCustom(vm.searchInput, function(response){
                vm.data.list = response.resultList;
                vm.data.total = response.total;
            }, 
            function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });  
        };
        
    }

})();
