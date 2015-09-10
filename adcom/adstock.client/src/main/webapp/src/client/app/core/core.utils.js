/**
 * @author guymoyo
 * @name utils
 */
(function() {
    'use strict';

    angular
        .module('app.core')
        .factory('utils', utils);

    utils.$inject = ['$modal'];
    /* @ngInject */
    function utils($modal) {


        var service = {};

        function ModalInstanceCtrl($modalInstance,model) {
            var vm = this;
            vm.model = model;
            // function assignment
            vm.ok = ok;
            vm.cancel = cancel;

            // function definition
            function ok() {
                $modalInstance.close(vm.model);
            }

            function cancel() {
                $modalInstance.dismiss('cancel');
            }
        }

        service.templateModal = function(model, typeForm, templateUrl, parentCtrl){
            var result = $modal.open({
                templateUrl: templateUrl,
                controller: ModalInstanceCtrl,
                controllerAs: 'vmModal',
                resolve: {
                    model: function() {
                        return model;
                    }
                }
            }).result;

            if (typeForm === 'createForm') {
                result.then(function(model) {
                    parentCtrl.create(model);
                });
            }
            if (typeForm === 'editForm') {
                result.then(function(model) {
                    parentCtrl.update(model);
                });
            }
        };
        
        
        service.searchInputInit= function(){
            var itemsPerPageVar = 10;
            var currentVar = 1;
            var maxSizeVar = 10;
            var searchInputVar = {
                   searchInput:{
                       entity: {},
                       start: 0,
                       max: itemsPerPageVar,
                       fieldNames: [],
                       className: '' 
                   },
                   pagination:{
                       current: currentVar,
                       itemsPerPage: itemsPerPageVar,
                       maxSize: maxSizeVar
                   }
                   
            };
           return searchInputVar;
        };
        
        service.resetPagination = function(pagination){
            var itemsPerPageVar = 10;
            var currentVar = 1;
            var maxSizeVar = 10;
            pagination.current = currentVar;
            pagination.itemsPerPage = itemsPerPageVar;
            pagination.maxSize = maxSizeVar;
            return pagination;
        }
        
        service.pagination = function(searchInput, pagination, currentIn){
            searchInput.start = ((pagination.current -1) * pagination.itemsPerPage);
            pagination.current = currentIn;
        };
        
        service.getFieldsFromObject = function(object){
            var props = Object.keys(object);
            return props;
        };
        
        
        
        service.processSearch = function(searchInput, object){
            for(var key in object){
               if(object.hasOwnProperty(key)){    
                    var keyValue = Object.getOwnPropertyDescriptor(object, key).value;
                    if(keyValue){
                       Object.defineProperty(searchInput.entity, key, 
                                             {value: keyValue, writable: true, enumerable: true, configurable: true});
                       searchInput.fieldNames.push(key);
                    }
               }
            }
            return searchInput;
        };
        
        return service;

    }

})();
