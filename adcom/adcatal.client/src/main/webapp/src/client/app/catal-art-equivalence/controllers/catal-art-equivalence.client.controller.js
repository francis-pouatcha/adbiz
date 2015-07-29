//catalArtEquivalence
(function () {
    'use strict';

    angular
        .module('app.catalArtEquivalence')
        .controller('CatalArtEquivalenceController', CatalArtEquivalenceController);

    CatalArtEquivalenceController.$inject = ['logger',
        'CatalArtEquivalence',
        'utils',
        'CatalArtEquivalenceForm',
        'ArticleForm',
        'Article'];
    /* @ngInject */
    function CatalArtEquivalenceController(logger, CatalArtEquivalence, utils, CatalArtEquivalenceForm, ArticleForm, Article) {

        var vm = this;
        vm.data = [];
        vm.catalArtEquivalence = {};

        vm.setFormFields = function (disabled) {
            vm.catalArticleId = ArticleForm.catalArticleId;
            vm.formFields = CatalArtEquivalenceForm.getFormFields(disabled);
            vm.formFields[0].defaultValue = vm.catalArticleId;
        };


        vm.createForm = function (model) {
            utils.templateModal(model, 'createForm',
                'app/catal-art-equivalence/views/create.html', vm);
        }
        vm.editForm = function (model) {
            utils.templateModal(model, 'editForm',
                'app/catal-art-equivalence/views/edit.html', vm);
        }
        vm.showForm = function (model) {
            utils.templateModal(model, 'showForm',
                'app/catal-art-equivalence/views/view.html', vm);
        }

        vm.init = function () {
            CatalArtEquivalence.findBy(coreSearchInput(), function (response) {
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
            coreSearchInput.className = 'org.adorsys.adcatal.jpa.CatalArtEquivalenceSearchInput';
            return coreSearchInput;
        }

        vm.create = function (catalArtEquivalence) {
            vm.catalArticleId = ArticleForm.catalArticleId;
            catalArtEquivalence.cntnrIdentif = vm.catalArticleId;
            // Create new catalArtEquivalence object
            var catalArtEquivalence = new CatalArtEquivalence(catalArtEquivalence);
            catalArtEquivalence.$save(function (response) {
                logger.success('CatalArtEquivalence created');
                vm.data.push(response);
            }, function (errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing catalArtEquivalence
        vm.remove = function (catalArtEquivalence) {
            var index = vm.data.indexOf(catalArtEquivalence);
            if (catalArtEquivalence) {
                catalArtEquivalence = CatalArtEquivalence
                    .get({catalArtEquivalenceId: catalArtEquivalence.id}, function () {
                        catalArtEquivalence.$remove(function () {
                            logger.success('CatalArtEquivalence deleted');
                            vm.data.splice(index, 1);
                        });
                    });
            } else {
                var index = vm.data.indexOf(vm.catalArtEquivalence);
                vm.catalArtEquivalence.$remove(function () {
                    logger.success('CatalArtEquivalence deleted');
                    vm.data.splice(index, 1);
                });
            }

        };

        // Update existing catalArtEquivalence
        vm.update = function (catalArtEquivalence) {
            var index = vm.data.indexOf(vm.model);
            var catalArtEquivalence = new CatalArtEquivalence(catalArtEquivalence);
            catalArtEquivalence.$update(function () {
                logger.success('catalArtEquivalence updated');
                vm.data.splice(index, 1);
                vm.data.push(catalArtEquivalence);
            }, function (errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewCatalArtEquivalence = function () {
            vm.setFormFields(true);
        };

        vm.toEditCatalArtEquivalence = function () {
            vm.setFormFields(false);
        };

        vm.getData = function(val) {
                console.log(toto);
                console.log(val);
                return Article.query({max:10,start:0})
                          .$promise.then(function (response) {
                        console.log(response.resultList);
                        return response.resultList;
        });

        }

        activate();

        function activate() {
            //logger.info('Activated CatalArtEquivalence View');
        }
    }

})();
