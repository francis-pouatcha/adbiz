(function () {
    'use strict';

    angular
        .module('app.catalArtDetailConfig')
        .controller('CatalArtDetailConfigController', CatalArtDetailConfigController);

    CatalArtDetailConfigController.$inject = ['logger',
        'CatalArtDetailConfig',
        'utils',
        'CatalArtDetailConfigForm',
        'ArticleForm', '$stateParams'];
    /* @ngInject */
    function CatalArtDetailConfigController(logger,
        CatalArtDetailConfig,
        utils,
        CatalArtDetailConfigForm,
        ArticleForm, $stateParams) {

        var vm = this;
        vm.data = [];
        vm.catalArtDetailConfig = {};

        vm.setFormFields = function(disabled) {
            vm.catalArticleId = $stateParams.articleId;
            vm.formFields = CatalArtDetailConfigForm.getFormFields(disabled);
            vm.formFields[0].defaultValue = vm.catalArticleId;
        };
        vm.createForm = function(model) {
            utils.templateModal(model, 'createForm',
                'src/client/app/catal-art-detail-config/views/create.html', vm);
        };

        vm.editForm = function(model) {
            utils.templateModal(model, 'editForm',
                'src/client/app/catal-art-detail-config/views/edit.html', vm);
        };
        vm.showForm = function(model) {
            utils.templateModal(model, 'showForm',
                'src/client/app/catal-art-detail-config/views/view.html', vm);
        };
        vm.init = function() {
            vm.catalArticleId = $stateParams.articleId;
            CatalArtDetailConfig.findBy(coreSearchInputInit(), function(response) {
                vm.data = response.resultList;
            });
        };

        function coreSearchInputInit() {
            var searchInput = {};
            searchInput.entity = {};
            searchInput.entity.cntnrIdentif = vm.catalArticleId;
            searchInput.fieldNames = [];
            searchInput.fieldNames.push('cntnrIdentif');
            searchInput.className = 'org.adorsys.adcatal.jpa.CatalArtDetailConfigSearchInput';
            return searchInput;
        }

        vm.create = function(catalArtDetailConfig) {
            vm.catalArticleId = $stateParams.articleId;
            catalArtDetailConfig.cntnrIdentif = vm.catalArticleId;
            // Create new CatalArtDetailConfig object
            var catalArtDetailConfigRes = new CatalArtDetailConfig(catalArtDetailConfig);
            catalArtDetailConfigRes.$save(function(response) {
                logger.success('CatalArtDetailConfig created');
                vm.data.push(response);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing CatalArtDetailConfig
        vm.remove = function(catalArtDetailConfig) {
            var index = vm.data.indexOf(catalArtDetailConfig);
            if (catalArtDetailConfig) {
                catalArtDetailConfig = CatalArtDetailConfig
                    .get({catalArtDetailConfigId:catalArtDetailConfig.id}, function() {
                    catalArtDetailConfig.$remove(function() {
                        logger.success('CatalArtDetailConfig deleted');
                        vm.data.splice(index, 1);
                    });
                });
            } else {
                index = vm.data.indexOf(vm.catalArtDetailConfig);
                vm.catalArtDetailConfig.$remove(function() {
                    logger.success('CatalArtDetailConfig deleted');
                    vm.data.splice(index, 1);
                });
            }

        };

        // Update existing CatalArtDetailConfig
        vm.update = function(catalArtDetailConfig) {
            var index = vm.data.indexOf(catalArtDetailConfig);
            var catalArtDetailConfigRes = CatalArtDetailConfig
            .get({catalArtDetailConfigId:catalArtDetailConfig.id}, function() {
            	catalArtDetailConfigRes.qualifier = catalArtDetailConfig.qualifier;
            	catalArtDetailConfigRes.sppu = catalArtDetailConfig.sppu;
            	catalArtDetailConfigRes.qtyOfDtldInMain=catalArtDetailConfig.qtyOfDtldInMain;
            	catalArtDetailConfigRes.mngInPptn=catalArtDetailConfig.mngInPptn;
                catalArtDetailConfigRes.$update(function() {
                    logger.success('CatalArtDetailConfig updated');
                    vm.data.splice(index, 1);
                    vm.data.push(catalArtDetailConfigRes);
                }, function(errorResponse) {
                    vm.error = errorResponse.data.summary;
                });
            });
            
        };

        vm.toViewCatalArtDetailConfig = function() {
            vm.setFormFields(true);
        };

        vm.toEditCatalArtDetailConfig = function() {
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated CatalArtDetailConfig View');
        }
    }

})();
