
(function () {
    'use strict';

    angular
        .module('app.catalArtLangMapping')
        .controller('CatalArtLangMappingController', CatalArtLangMappingController);

    CatalArtLangMappingController.$inject = ['logger',
        'CatalArtLangMapping',
        'utils',
        'CatalArtLangMappingForm',
        'ArticleForm'];
    /* @ngInject */
    function CatalArtLangMappingController(logger, CatalArtLangMapping, utils, CatalArtLangMappingForm, ArticleForm) {

        var vm = this;
        vm.data = [];
        vm.catalArtLangMapping = {};

        vm.setFormFields = function (disabled) {
            vm.catalArticleId = ArticleForm.catalArticleId;
            vm.formFields = CatalArtLangMappingForm.getFormFields(disabled);
            vm.formFields[0].defaultValue = vm.catalArticleId;
        };


        vm.createForm = function (model) {
            utils.templateModal(model, 'createForm',
                'app/catal-art-lang-mapping/views/create.html', vm);
        }
        vm.editForm = function (model) {
            utils.templateModal(model, 'editForm',
                'app/catal-art-lang-mapping/views/edit.html', vm);
        }
        vm.showForm = function (model) {
            utils.templateModal(model, 'showForm',
                'app/catal-art-lang-mapping/views/view.html', vm);
        }

        vm.init = function () {
            CatalArtLangMapping.findBy(coreSearchInput(), function (response) {
                vm.data = response.resultList;
            });
        };

        function coreSearchInput() {
            vm.catalArticleId = ArticleForm.catalArticleId;
            var coreSearchInput = {};
            coreSearchInput.entity = {};
            coreSearchInput.entity.cntnrIdentif = vm.catalArticleId;
            coreSearchInput.fieldNames = [];
            coreSearchInput.fieldNames.push('cntnrIdentif');
            coreSearchInput.className = 'org.adorsys.adcatal.jpa.CatalArtLangMappingSearchInput';
            return coreSearchInput;
        }

        vm.create = function (catalArtLangMapping) {
            vm.catalArticleId = ArticleForm.catalArticleId;
            catalArtLangMapping.cntnrIdentif = vm.catalArticleId;
            // Create new catalArtLangMapping object
            var catalArtLangMapping = new CatalArtLangMapping(catalArtLangMapping);
            catalArtLangMapping.$save(function (response) {
                logger.success('CatalArtLangMapping created');
                vm.data.push(response);
            }, function (errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing catalArtLangMapping
        vm.remove = function (catalArtLangMapping) {
            var index = vm.data.indexOf(catalArtLangMapping);
            if (catalArtLangMapping) {
                catalArtLangMapping = CatalArtLangMapping
                    .get({catalArtLangMappingId: catalArtLangMapping.id}, function () {
                        catalArtLangMapping.$remove(function () {
                            logger.success('CatalArtLangMapping deleted');
                            vm.data.splice(index, 1);
                        });
                    });
            } else {
                var index = vm.data.indexOf(vm.catalArtLangMapping);
                vm.catalArtLangMapping.$remove(function () {
                    logger.success('CatalArtLangMapping deleted');
                    vm.data.splice(index, 1);
                });
            }

        };

        // Update existing catalArtLangMapping
        vm.update = function (catalArtLangMapping) {
            var index = vm.data.indexOf(vm.model);
            var catalArtLangMapping = new CatalArtLangMapping(catalArtLangMapping);
            catalArtLangMapping.$update(function () {
                logger.success('catalArtLangMapping updated');
                vm.data.splice(index, 1);
                vm.data.push(catalArtLangMapping);
            }, function (errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewCatalArtLangMapping = function () {
            vm.setFormFields(true);
        };

        vm.toEditCatalArtLangMapping = function () {
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated CatalArtLangMapping View');
        }
    }

})();
