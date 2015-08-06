(function () {
    'use strict';

    angular
        .module('app.catalArtDetailConfig')
        .controller('CatalArtDetailConfigController', CatalArtDetailConfigController);

    CatalArtDetailConfigController.$inject = ['logger',
        'CatalArtDetailConfig',
        'utils',
        'CatalArtDetailConfigForm',
        'ArticleForm'];
    /* @ngInject */
    function CatalArtDetailConfigController(logger,
        CatalArtDetailConfig,
        utils,
        CatalArtDetailConfigForm,
        ArticleForm) {

        var vm = this;
        vm.data = [];
        vm.catalArtDetailConfig = {};

        vm.setFormFields = function(disabled) {
            vm.catalArticleId = ArticleForm.catalArticleId;
            vm.formFields = CatalArtDetailConfigForm.getFormFields(disabled);
            vm.formFields[0].defaultValue = vm.catalArticleId;
        };



        vm.createForm = function(model){
            utils.templateModal(model, 'createForm',
                'app/catal-art-detail-config/views/create.html', vm);
        };
        vm.editForm = function(model){
            utils.templateModal(model, 'editForm',
                'app/catal-art-detail-config/views/edit.html', vm);
        };
        vm.showForm = function(model){
            utils.templateModal(model, 'showForm',
                'app/catal-art-detail-config/views/view.html', vm);
        };

        vm.init = function() {
            CatalArtDetailConfig.findBy(coreSearchInput(), function(response) {
                vm.data = response.resultList;
            });
        };

        function coreSearchInput (){
            vm.catalArticleId = ArticleForm.catalArticleId;
            var searchInput = {};
            searchInput.entity = {};
            searchInput.entity.cntnrIdentif = vm.catalArticleId;
            searchInput.fieldNames = [];
            searchInput.fieldNames.push('cntnrIdentif');
            searchInput.className = 'org.adorsys.adcatal.jpa.CatalArtDetailConfigSearchInput';
            return searchInput;
        }

        vm.create = function(catalArtDetailConfig) {
            vm.catalArticleId = ArticleForm.catalArticleId;
            catalArtDetailConfig.cntnrIdentif = vm.catalArticleId;
            // Create new CatalArtDetailConfig object
            catalArtDetailConfig = new CatalArtDetailConfig(catalArtDetailConfig);
            catalArtDetailConfig.$save(function(response) {
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
            var index = vm.data.indexOf(vm.model);
            catalArtDetailConfig = new CatalArtDetailConfig(catalArtDetailConfig);
            catalArtDetailConfig.$update(function() {
                logger.success('CatalArtDetailConfig updated');
                vm.data.splice(index, 1);
                vm.data.push(catalArtDetailConfig);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
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
