(function () {
    'use strict';

    angular
        .module('app.stkMvnt')
        .controller('StkMvntController', StkMvntController);

    StkMvntController.$inject = ['logger',
        '$stateParams',
        '$location',
        'StkMvnt',
        'StkMvntForm',
        'utils'];
    /* @ngInject */
    function StkMvntController(logger,
        $stateParams,
        $location,
        StkMvnt,
        StkMvntForm,
        utils) {

        var vm = this;
         vm.data = {
            list: []
        };
        vm.stkMvnt = {};
        
        function initSearchInput(){
            // Initialize Search input and pagination
            vm.searchInput = utils.searchInputInit().searchInput;
            vm.searchInput.className = 'org.adorsys.adstock.jpa.StkMvntSearchInput';
            vm.searchInput.sortFieldNames.push({fieldName:'valueDt'});
            // Number of entries showed per page.
            vm.itemsByPage = utils.searchInputInit().stPagination.number;
        }

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

        //findAllStkmvnts();
//        findCustomStkmvnts();
        
        function findAllStkmvnts(){
            StkMvnt.listAll(function (response) {
                vm.data.list = response.resultList;
                vm.data.total = response.total;
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });  
        }

//        function findCustomStkmvnts(){
//            StkMvnt.findCustom(vm.searchInput, function(response){
//                vm.data.list = response.resultList;
//                vm.data.total = response.total;
//            }, 
//            function(errorResponse) {
//                vm.error = errorResponse.data.summary;
//            });  
//        }
        
        vm.callServer = function(tableState) {
    	    var pagination = tableState.pagination;
    	    var start = pagination.start || 0, number = pagination.number || utils.searchInputInit().stPagination.number;
    	    processSearch(start, tableState.search);
        	
    	    StkMvnt.findByLike(vm.searchInput, function(response) {
                vm.data.list = response.resultList;
                tableState.pagination.numberOfPages = Math.ceil(response.count / number)
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
