
(function () {
    'use strict';

    angular
        .module('app.catalArtLangMapping')
        .controller('CatalArtLangMappingController', CatalArtLangMappingController);

    CatalArtLangMappingController.$inject = ['logger',
        'CatalArtLangMapping',
        'utils',
        'CatalArtLangMappingForm',
        'ArticleForm',
        '$stateParams', '$translate'];

    /* @ngInject */
    function CatalArtLangMappingController(logger, CatalArtLangMapping,
                                           utils, CatalArtLangMappingForm, ArticleForm, $stateParams, $translate) {
        var vm = this;
        vm.data = [];
        vm.catalArtLangMapping = {};

        vm.setFormFields = function (disabled) {
            vm.catalArticleId = $stateParams.articleId;
            vm.formFields = CatalArtLangMappingForm.getFormFields(disabled);
            vm.formFields[0].defaultValue = vm.catalArticleId;
        };
        vm.createForm = function (model) {
            utils.templateModal(model, 'createForm',
                'src/client/app/catal-art-lang-mapping/views/create.html', vm);
        };
        vm.editForm = function (model) {
            utils.templateModal(model, 'editForm',
                'src/client/app/catal-art-lang-mapping/views/edit.html', vm);
        };
        vm.showForm = function (model) {
            utils.templateModal(model, 'showForm',
                'src/client/app/catal-art-lang-mapping/views/view.html', vm);
        };

        vm.init = function () {
            vm.catalArticleId = $stateParams.articleId;
            CatalArtLangMapping.findBy(coreSearchInputInit(), function (response) {
                vm.data = response.resultList;
            });
        };

        function coreSearchInputInit() {
            var searchInput = {};
            searchInput.entity = {};
            searchInput.entity.cntnrIdentif = vm.catalArticleId;
            searchInput.fieldNames = [];
            searchInput.fieldNames.push('cntnrIdentif');
            searchInput.className = 'org.adorsys.adcatal.jpa.CatalArtLangMappingSearchInput';
            return searchInput;
        }

        vm.create = function (catalArtLangMapping) {
            vm.catalArticleId = $stateParams.articleId;
            catalArtLangMapping.cntnrIdentif = vm.catalArticleId;
            // Create new catalArtLangMapping object
            var langs = [];
            angular.forEach(vm.data, function(catalArtLang){
                langs.push(catalArtLang.langIso2);
            });
            if(langs.indexOf(catalArtLangMapping.langIso2)!=-1){
                logger.error($translate.instant('existin_lang'));
                return;
            }
            var catalArtLangMappingRes = new CatalArtLangMapping(catalArtLangMapping);
            catalArtLangMappingRes.$save(function (response) {
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
                index = vm.data.indexOf(vm.catalArtLangMapping);
                vm.catalArtLangMapping.$remove(function () {
                    logger.success('CatalArtLangMapping deleted');
                    vm.data.splice(index, 1);
                });
            }

        };

        // Update existing catalArtLangMapping
        vm.update = function (catalArtLangMapping) {
            var index = vm.data.indexOf(catalArtLangMapping);
            var catalArtLangMappingRes = new CatalArtLangMapping(catalArtLangMapping);
            catalArtLangMappingRes.$update(function () {
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
