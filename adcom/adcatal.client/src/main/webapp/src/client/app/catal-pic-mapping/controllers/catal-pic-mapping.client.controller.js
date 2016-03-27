(function () {
    'use strict';

    angular
        .module('app.catalPicMapping')
        .controller('CatalPicMappingController', CatalPicMappingController);

    CatalPicMappingController.$inject = ['logger',
        'CatalPicMapping',
        'utils',
        'CatalPicMappingForm',
        'ArticleForm', '$stateParams'];
    /* @ngInject */
    function CatalPicMappingController(logger, CatalPicMapping,
                                       utils, CatalPicMappingForm, ArticleForm, $stateParams) {

        var vm = this;
        vm.data = [];
        vm.catalPicMapping = {};

        vm.setFormFields = function (disabled) {
            vm.catalArticleId = $stateParams.articleId;
            vm.formFields = CatalPicMappingForm.getFormFields(disabled);
            vm.formFields[0]['fieldGroup'][0].defaultValue = vm.catalArticleId;
        };
        vm.createForm = function (model) {
            utils.templateModal(model, 'createForm',
                'src/client/app/catal-pic-mapping/views/create.html', vm);
        };
        vm.editForm = function (model) {
            utils.templateModal(model, 'editForm',
                'src/client/app/catal-pic-mapping/views/edit.html', vm);
        };
        vm.showForm = function (model) {
            utils.templateModal(model, 'showForm',
                'src/client/app/catal-pic-mapping/views/view.html', vm);
        };

        vm.init = function () {
            vm.catalArticleId = $stateParams.articleId;
            CatalPicMapping.findBy(coreSearchInputInit(), function (response) {
                vm.data = response.resultList;
            });
        };

        function coreSearchInputInit() {
            var searchInput = {};
            searchInput.entity = {};
            searchInput.entity.cntnrIdentif = vm.catalArticleId;
            searchInput.fieldNames = [];
            searchInput.fieldNames.push('cntnrIdentif');
            searchInput.className = 'org.adorsys.adcatal.jpa.CatalPicMappingSearchInput';
            return searchInput;
        }

        vm.create = function (catalPicMapping) {
            vm.catalArticleId = $stateParams.articleId;
            catalPicMapping.cntnrIdentif = vm.catalArticleId;
            // Create new catalPicMapping object
            var catalPicMappingRes = new CatalPicMapping(catalPicMapping);
            catalPicMappingRes.$save(function (response) {
                logger.success('CatalPicMapping created');
                vm.data.push(response);
            }, function (errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing catalPicMapping
        vm.remove = function (catalPicMapping) {
            var index = vm.data.indexOf(catalPicMapping);
            if (catalPicMapping) {
                catalPicMapping = CatalPicMapping
                    .get({catalPicMappingId: catalPicMapping.id}, function () {
                        catalPicMapping.$remove(function () {
                            logger.success('CatalPicMapping deleted');
                            vm.data.splice(index, 1);
                        });
                    });
            } else {
                index = vm.data.indexOf(vm.catalPicMapping);
                vm.catalPicMapping.$remove(function () {
                    logger.success('CatalPicMapping deleted');
                    vm.data.splice(index, 1);
                });
            }

        };

        // Update existing catalPicMapping
        vm.update = function (catalPicMapping) {
            var index = vm.data.indexOf(catalPicMapping);
            var catalPicMappingRes = new CatalPicMapping(catalPicMapping);
            catalPicMappingRes.$update(function () {
                logger.success('catalPicMapping updated');
                vm.data.splice(index, 1);
                vm.data.push(catalPicMapping);
            }, function (errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewCatalPicMapping = function () {
            vm.setFormFields(true);
        };

        vm.toEditCatalPicMapping = function () {
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated CatalPicMapping View');
        }
    }

})();
