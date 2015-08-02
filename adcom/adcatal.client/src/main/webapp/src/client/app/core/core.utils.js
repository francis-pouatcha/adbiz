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
            };

        };

        service.templateModal = function(model, typeForm, templateUrl, parentCtrl){
            var result = $modal.open({
                templateUrl: templateUrl,
                controller: ModalInstanceCtrl,
                controllerAs: 'vmModal',
                resolve: {
                    model: function() {
                        return model
                    }
                }
            }).result;

            if (typeForm == 'createForm') {
                result.then(function(model) {
                    parentCtrl.create(model);
                });
            }
            if (typeForm == 'editForm') {
                result.then(function(model) {
                    parentCtrl.update(model);
                });
            }
        }

        return service;

    }

})();
