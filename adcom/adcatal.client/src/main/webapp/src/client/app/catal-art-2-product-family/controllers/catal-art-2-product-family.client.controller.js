(function () {
    'use strict';

    angular
        .module('app.catalArt2ProductFamily')
        .controller('CatalArt2ProductFamilyController', CatalArt2ProductFamilyController);

    CatalArt2ProductFamilyController.$inject = ['logger',
        'CatalArt2ProductFamily',
        'CatalArt2ProductFamilyForm',
        'ArticleForm',
        'utils'
        ];
    /* @ngInject */
    function CatalArt2ProductFamilyController(logger,
        CatalArt2ProductFamily,
        CatalArt2ProductFamilyForm,
        ArticleForm,
        utils
        ) {

        var vm = this;
        vm.data = [];
        vm.catalArt2ProductFamily = {};

        vm.createForm = function(model) {
            utils.templateModal(model, 'createForm',
                'app/catal-art-2-product-family/views/create.html', vm);
        };
        vm.editForm = function(model) {
            utils.templateModal(model, 'editForm',
                'app/catal-art-2-product-family/views/edit.html', vm);
        };
        vm.showForm = function(model) {
            utils.templateModal(model, 'showForm',
                'app/catal-art-2-product-family/views/view.html', vm);
        };
        vm.init = function() {
            CatalArt2ProductFamily.findBy(coreSearchInputInit(), function(response) {
                vm.data = response.resultList;
            });
        };

        function coreSearchInputInit() {
            vm.catalArticleId = ArticleForm.catalArticleId;
            var coreSearchInput = {};
            coreSearchInput.entity = {};
            coreSearchInput.entity.cntnrIdentif = vm.catalArticleId;
            coreSearchInput.fieldNames = [];
            coreSearchInput.fieldNames.push('cntnrIdentif');
            coreSearchInput.className = 'org.adorsys.adcatal.jpa.CatalArt2ProductFamilySearchInput';
            return coreSearchInput;
        }

        vm.setFormFields = function(disabled) {
            vm.catalArticleId = ArticleForm.catalArticleId;
            vm.formFields = CatalArt2ProductFamilyForm.getFormFields(disabled);
            vm.formFields[0].defaultValue = vm.catalArticleId;
        };

        vm.create = function(catalArt2ProductFamily) {
            vm.catalArticleId = ArticleForm.catalArticleId;
            catalArt2ProductFamily.cntnrIdentif = vm.catalArticleId;
            // Create new CatalArt2ProductFamily object
            var catalArt2ProductFamilyRes = new CatalArt2ProductFamily(catalArt2ProductFamily);
            catalArt2ProductFamilyRes.$save(function(response) {

                logger.success('CatalArt2ProductFamily created');
                vm.data.push(response);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing CatalArt2ProductFamily
        vm.remove = function(catalArt2ProductFamily) {
            var index = vm.data.indexOf(catalArt2ProductFamily);
            if (catalArt2ProductFamily) {
                catalArt2ProductFamily = CatalArt2ProductFamily
                    .get({catalArt2ProductFamilyId:catalArt2ProductFamily.id}, function() {
                    catalArt2ProductFamily.$remove(function() {
                        logger.success('CatalArt2ProductFamily deleted');
                        vm.data.splice(index, 1);
                    });
                });
            } else {
                index = vm.data.indexOf(vm.catalArt2ProductFamily);
                vm.catalArt2ProductFamily.$remove(function() {
                    logger.success('CatalArt2ProductFamily deleted');
                    vm.data.splice(index, 1);
                });
            }

        };

        // Update existing CatalArt2ProductFamily
        vm.update = function(catalArt2ProductFamily) {
            var catalArt2ProductFamilyRes = new CatalArt2ProductFamily(catalArt2ProductFamily);
            var index = vm.data.indexOf(vm.model);
            catalArt2ProductFamilyRes.$update(function() {
                logger.success('CatalArt2ProductFamily updated');
                vm.data.splice(index, 1);
                vm.data.push(catalArt2ProductFamily);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewCatalArt2ProductFamily = function() {
            vm.setFormFields(true);
        };

        vm.toEditCatalArt2ProductFamily = function() {
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated CatalArt2ProductFamily View');
        }
    }

})();
