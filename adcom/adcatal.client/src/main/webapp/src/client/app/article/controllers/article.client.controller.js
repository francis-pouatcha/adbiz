(function () {
    'use strict';
    angular
        .module('app.article')
        .controller('ArticleController', ArticleController);

    ArticleController.$inject = ['logger',
        '$stateParams',
        '$location',
        'Article',
        'CatalArtLangMapping',
        'TableSettings',
        'ArticleForm',
        '$translate'];
    /* @ngInject */
    function ArticleController(logger,
                               $stateParams,
                               $location,
                               Article,
                               CatalArtLangMapping,
                               TableSettings,
                               ArticleForm,
                               $translate) {
        var vm = this;
        vm.data = [];
        vm.tableParams = TableSettings.getParams(Article);
        vm.article = {};
        vm.setFormFields = function(disabled) {
            vm.formFields = ArticleForm.getFormFields(disabled);
        };

        init();

        function init() {
            Article.query({start:0, max:20} , function(data) {
                vm.data = data.resultList;
            });
        }

        vm.create = function() {
            var catalArtLangMapping = {};
            catalArtLangMapping.artName = vm.article.artName;
            catalArtLangMapping.shortName = vm.article.shortName;
            catalArtLangMapping.langIso2 = $translate.use();
            delete vm.article.artName;
            delete vm.article.shortName;
            // Create new Article object
            var article = new Article(vm.article);
            // Redirect after save
            article.$save(function(response) {
                logger.success('Article created');
                catalArtLangMapping.cntnrIdentif = response.id;
                var catalArtLangMappingRes = new CatalArtLangMapping(catalArtLangMapping);
                vm.data.push(response);

                catalArtLangMappingRes.$save(function (responseTwo) {
                    $location.path('/article/' + response.id);

                }, function (errorResponseTwo) {
                    vm.error = errorResponseTwo.data.summary;
                });

            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };
        // Remove existing Article
        vm.remove = function(article) {
            if (article) {
                article = Article.get({articleId:article.id}, function() {
                    article.$remove(function() {
                        logger.success('Article deleted');
                        vm.tableParams.reload();
                    });
                });
            } else {
                vm.article.$remove(function() {
                    logger.success('Article deleted');
                    $location.path('/article');
                });
            }
        };
        // Update existing Article
        vm.update = function() {
            var article = vm.article;

            article.$update(function() {
                logger.success('Article updated');
                $location.path('article/' + article.id);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        function coreSearchInputInit() {

            vm.articleId = $stateParams.articleId;
            var coreSearchInput = {};
            coreSearchInput.entity = {};
            coreSearchInput.entity.cntnrIdentif = vm.articleId;
            //coreSearchInput.entity.langIso2 = $translate.use();
            coreSearchInput.fieldNames = [];
            coreSearchInput.fieldNames.push('cntnrIdentif');
            //coreSearchInput.fieldNames.push('langIso2');
            coreSearchInput.className = 'org.adorsys.adcatal.jpa.CatalArtLangMappingSearchInput';
            return coreSearchInput;
        }

        vm.toViewArticle = function() {
             Article.get({articleId: $stateParams.articleId}, function(data){
                vm.article = data;

                 ArticleForm.catalArticleId = $stateParams.articleId;
                 vm.setFormFields(true);

                 CatalArtLangMapping.findBy(coreSearchInputInit(), function (response) {
                     console.log(response.resultList);
                     vm.article.artName = response.resultList[0].artName;
                     vm.article.shortName = response.resultList[0].shortName;
                 });
            });

        };
        vm.toEditArticle = function() {
            vm.article = Article.get({articleId: $stateParams.articleId});
            vm.setFormFields(false);
        };
        activate();
        function activate() {
            //logger.info('Activated Article View');
        }
    }
})();
