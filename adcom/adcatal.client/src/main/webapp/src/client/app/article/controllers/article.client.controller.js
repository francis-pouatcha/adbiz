(function () {
    'use strict';

    angular
        .module('app.article')
        .controller('ArticleController', ArticleController);

    ArticleController.$inject = ['logger',
        '$stateParams',
        '$location',
        'Article',
        'TableSettings',
        'ArticleForm'];
    /* @ngInject */
    function ArticleController(logger,
        $stateParams,
        $location,
        Article,
        TableSettings,
        ArticleForm) {

        var vm = this;

        vm.tableParams = TableSettings.getParams(Article);
        vm.article = {};

        vm.setFormFields = function(disabled) {
            vm.formFields = ArticleForm.getFormFields(disabled);
        };

        vm.create = function() {
            // Create new Article object
            var article = new Article(vm.article);

            // Redirect after save
            article.$save(function(response) {
                logger.success('Article created');
                $location.path('article/' + response.id);
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

        vm.toViewArticle = function() {
            vm.article = Article.get({articleId: $stateParams.articleId});
            ArticleForm.catalArticleId = $stateParams.articleId;
            vm.setFormFields(true);
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
