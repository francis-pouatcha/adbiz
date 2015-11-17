(function () {
    'use strict';

    angular
        .module('app.stkMvnt')
        .controller('StkMvntController', StkMvntController);

    StkMvntController.$inject = ['logger',
        '$stateParams',
        '$location',
        'StkMvnt',
        'TableSettings',
        'StkMvntForm',
        'utils'];
    /* @ngInject */
    function StkMvntController(logger,
        $stateParams,
        $location,
        StkMvnt,
        TableSettings,
        StkMvntForm,
        utils) {

        var vm = this;
         vm.data = {
            total: 0,
            list: []
        };
        //vm.tableParams = TableSettings.getParams(StkMvnt);
        vm.stkMvnt = {};
        
        // Initialize Search input and pagination
        vm.searchInput = utils.searchInputInit().searchInput;
        vm.searchInput.className = 'org.adorsys.adstock.jpa.StkMvntSearchInput';
        vm.pagination = utils.searchInputInit().pagination;

        vm.setFormFields = function(disabled) {
            vm.formFields = StkMvntForm.getFormFields(disabled);
        };

        vm.create = function() {
            // Create new StkMvnt object
            var stkMvnt = new StkMvnt(vm.stkMvnt);

            // Redirect after save
            stkMvnt.$save(function(response) {
                logger.success('StkMvnt created');
                $location.path('stk-mvnt/' + response.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing StkMvnt
        vm.remove = function(stkMvnt) {

            if (stkMvnt) {
                stkMvnt = StkMvnt.get({stkMvntId:stkMvnt.id}, function() {
                    stkMvnt.$remove(function() {
                        logger.success('StkMvnt deleted');

                    });
                });
            } else {
                vm.stkMvnt.$remove(function() {
                    logger.success('StkMvnt deleted');
                    $location.path('/stk-mvnt');
                });
            }

        };

        // Update existing StkMvnt
        vm.update = function() {
            var stkMvnt = vm.stkMvnt;

            stkMvnt.$update(function() {
                logger.success('StkMvnt updated');
                $location.path('stk-mvnt/' + stkMvnt.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewStkMvnt = function() {
            vm.stkMvnt = StkMvnt.get({stkMvntId: $stateParams.stkMvntId});
            vm.setFormFields(true);
        };

        vm.toEditStkMvnt = function() {
            vm.stkMvnt = StkMvnt.get({stkMvntId: $stateParams.stkMvntId});
            vm.setFormFields(false);
        };
        
        // Paginate over the list
        vm.paginate = function(newPage){
            utils.pagination(vm.searchInput, vm.pagination, newPage);
            findCustomStkArticlesLots();
        };

        activate();
        //findAllStkmvnts();
        findCustomStkmvnts();

        function activate() {
            //logger.info('Activated StkMvnt View');
        }
        
        function findAllStkmvnts(){
            StkMvnt.listAll(function (response) {
                vm.data.list = response.resultList;
                vm.data.total = response.total;
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });  
        }
        
        function findCustomStkmvnts(){
            StkMvnt.findCustom(vm.searchInput, function(response){
                vm.data.list = response.resultList;
                vm.data.total = response.total;
            }, 
            function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });  
        }

    }

})();
