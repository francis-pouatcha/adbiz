(function () {
    'use strict';

    angular
        .module('app.catalArtManufSupp')
        .controller('CatalArtManufSuppController', CatalArtManufSuppController);

    CatalArtManufSuppController.$inject = ['logger',
        'CatalArtManufSupp',
        'utils',
        'CatalArtManufSuppForm',
        'ArticleForm', '$stateParams'];
    /* @ngInject */
    function CatalArtManufSuppController(logger, CatalArtManufSupp,
                                         utils, CatalArtManufSuppForm, ArticleForm, $stateParams) {

        var vm = this;
        vm.data = [];
        vm.catalArtManufSupp = {};

        vm.setFormFields = function (disabled) {
            vm.catalArticleId = $stateParams.articleId;
            vm.formFields = CatalArtManufSuppForm.getFormFields(disabled);
            vm.formFields[0].defaultValue = vm.catalArticleId;
        };
        vm.createForm = function (model) {
            utils.templateModal(model, 'createForm',
                'src/client/app/catal-art-manuf-supp/views/create.html', vm);
        };
        vm.editForm = function (model) {
            utils.templateModal(model, 'editForm',
                'src/client/app/catal-art-manuf-supp/views/edit.html', vm);
        };
        vm.showForm = function (model) {
            utils.templateModal(model, 'showForm',
                'src/client/app/catal-art-manuf-supp/views/view.html', vm);
        };

        vm.init = function () {
            vm.catalArticleId = $stateParams.articleId;        	
            CatalArtManufSupp.findBy(coreSearchInputInit(), function (response) {
                vm.data = response.resultList;
            });
        };

        function coreSearchInputInit() {
            var searchInput = {};
            searchInput.entity = {};
            searchInput.entity.cntnrIdentif = vm.catalArticleId;
            searchInput.fieldNames = [];
            searchInput.fieldNames.push('cntnrIdentif');
            searchInput.className = 'org.adorsys.adcatal.jpa.CatalArtManufSuppSearchInput';
            return searchInput;
        }

        vm.create = function (catalArtManufSupp) {
            vm.catalArticleId = $stateParams.articleId;
            catalArtManufSupp.cntnrIdentif = vm.catalArticleId;
            // Create new catalArtManufSupp object
            var catalArtManufSuppRes = new CatalArtManufSupp(catalArtManufSupp);
            catalArtManufSuppRes.$save(function (response) {
                logger.success('CatalArtManufSupp created');
                vm.data.push(response);
            }, function (errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing catalArtManufSupp
        vm.remove = function (catalArtManufSupp) {
            var index = vm.data.indexOf(catalArtManufSupp);
            if (catalArtManufSupp) {
                catalArtManufSupp = CatalArtManufSupp
                    .get({catalArtManufSuppId: catalArtManufSupp.id}, function () {
                        catalArtManufSupp.$remove(function () {
                            logger.success('CatalArtManufSupp deleted');
                            vm.data.splice(index, 1);
                        });
                    });
            } else {
                index = vm.data.indexOf(vm.catalArtManufSupp);
                vm.catalArtManufSupp.$remove(function () {
                    logger.success('CatalArtManufSupp deleted');
                    vm.data.splice(index, 1);
                });
            }

        };

        // Update existing catalArtManufSupp
        vm.update = function (catalArtManufSupp) {
            var index = vm.data.indexOf(vm.model);
            var catalArtManufSuppRes = new CatalArtManufSupp(catalArtManufSupp);
            catalArtManufSuppRes.$update(function () {
                logger.success('catalArtManufSupp updated');
                vm.data.splice(index, 1);
                vm.data.push(catalArtManufSupp);
            }, function (errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewCatalArtManufSupp = function () {
            vm.setFormFields(true);
        };

        vm.toEditCatalArtManufSupp = function () {
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated CatalArtManufSupp View');
        }
    }

})();
