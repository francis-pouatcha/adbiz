//catalProdFmlyLangMap
(function () {
    'use strict';

    angular
        .module('app.catalProdFmlyLangMap')
        .controller('CatalProdFmlyLangMapController', CatalProdFmlyLangMapController);

    CatalProdFmlyLangMapController.$inject = ['logger',
        'CatalProdFmlyLangMap',
        'utils',
        'CatalProdFmlyLangMapForm',
        'CatalProdFmlyForm'];
    /* @ngInject */
    function CatalProdFmlyLangMapController(logger, CatalProdFmlyLangMap,
                                            utils, CatalProdFmlyLangMapForm, CatalProdFmlyForm) {

        var vm = this;
        vm.data = [];
        vm.catalProdFmlyLangMap = {};

        vm.setFormFields = function (disabled) {
            vm.catalProdFmlyId = CatalProdFmlyForm.catalProdFmlyId;
            console.log(vm.catalProdFmlyId);
            vm.formFields = CatalProdFmlyLangMapForm.getFormFields(disabled);
            vm.formFields[0].defaultValue = vm.catalProdFmlyId;
        };
        vm.createForm = function (model) {
            utils.templateModal(model, 'createForm',
                'app/catal-prod-fmly-lang-map/views/create.html', vm);
        };
        vm.editForm = function (model) {
            utils.templateModal(model, 'editForm',
                'app/catal-prod-fmly-lang-map/views/edit.html', vm);
        };
        vm.showForm = function (model) {
            utils.templateModal(model, 'showForm',
                'app/catal-prod-fmly-lang-map/views/view.html', vm);
        };
        vm.init = function () {
            CatalProdFmlyLangMap.findBy(coreSearchInputInit(), function (response) {
                vm.data = response.resultList;
            });
        };

        function coreSearchInputInit() {
            vm.catalProdFmlyId = CatalProdFmlyForm.catalProdFmlyId;
            var coreSearchInput = {};
            coreSearchInput.entity = {};
            coreSearchInput.entity.cntnrIdentif = vm.catalProdFmlyId;
            coreSearchInput.fieldNames = [];
            coreSearchInput.fieldNames.push('cntnrIdentif');
            coreSearchInput.className = 'org.adorsys.adcatal.jpa.CatalProdFmlyLangMapSearchInput';
            return coreSearchInput;
        }

        vm.create = function (catalProdFmlyLangMap) {
            vm.catalProdFmlyId = CatalProdFmlyForm.catalProdFmlyId;
            catalProdFmlyLangMap.cntnrIdentif = vm.catalProdFmlyId;
            // Create new catalProdFmlyLangMap object
            var catalProdFmlyLangMapRes = new CatalProdFmlyLangMap(catalProdFmlyLangMap);
            catalProdFmlyLangMapRes.$save(function (response) {
                logger.success('CatalProdFmlyLangMap created');
                vm.data.push(response);
            }, function (errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing catalProdFmlyLangMap
        vm.remove = function (catalProdFmlyLangMap) {
            var index = vm.data.indexOf(catalProdFmlyLangMap);
            if (catalProdFmlyLangMap) {
                catalProdFmlyLangMap = CatalProdFmlyLangMap
                    .get({catalProdFmlyLangMapId: catalProdFmlyLangMap.id}, function () {
                        catalProdFmlyLangMap.$remove(function () {
                            logger.success('CatalProdFmlyLangMap deleted');
                            vm.data.splice(index, 1);
                        });
                    });
            } else {
                index = vm.data.indexOf(vm.catalProdFmlyLangMap);
                vm.catalProdFmlyLangMap.$remove(function () {
                    logger.success('CatalProdFmlyLangMap deleted');
                    vm.data.splice(index, 1);
                });
            }

        };

        // Update existing catalProdFmlyLangMap
        vm.update = function (catalProdFmlyLangMap) {
            var index = vm.data.indexOf(vm.model);
            var catalProdFmlyLangMapRes = new CatalProdFmlyLangMap(catalProdFmlyLangMap);
            catalProdFmlyLangMapRes.$update(function () {
                logger.success('catalProdFmlyLangMap updated');
                vm.data.splice(index, 1);
                vm.data.push(catalProdFmlyLangMap);
            }, function (errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewCatalProdFmlyLangMap = function () {
            vm.setFormFields(true);
        };

        vm.toEditCatalProdFmlyLangMap = function () {
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated CatalProdFmlyLangMap View');
        }
    }

})();
