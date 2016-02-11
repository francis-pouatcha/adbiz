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
        'utils',
        'ArticleForm',
        '$translate'];
    /* @ngInject */
    function ArticleController(logger,
                               $stateParams,
                               $location,
                               Article,
                               CatalArtLangMapping,
                               utils,
                               ArticleForm,
                               $translate) {
        var vm = this;
        vm.data = [];
        vm.article = {};

        function initSearchInput(){
            // Initialize Search input and pagination
            vm.searchInput = utils.searchInputInit().searchInput;
            vm.searchInput.className = 'org.adorsys.adcatal.jpa.CatalArtLangMappingSearchInput';
            vm.searchInput.sortFieldNames.push({fieldName:'valueDt'});
            //Number of entries showed per page.
            vm.itemsByPage = utils.searchInputInit().stPagination.number;
        }

        vm.setFormFields = function(disabled, hideName) {
            vm.formFields = ArticleForm.getFormFields(disabled, hideName);
        };
        vm.setFormSearchFields = function() {
            vm.formSearchFields = ArticleForm.getFormSearchFields();
        };

        initSearchInput();

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
                        //vm.tableParams.reload();
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
                vm.setFormFields(true, false);

                CatalArtLangMapping.findBy(coreSearchInputInit(), function (response) {
                    vm.article.artName = response.resultList[0].artName;
                    vm.article.shortName = response.resultList[0].shortName;
                });
            });

        };
        vm.toEditArticle = function() {
            vm.article = Article.get({articleId: $stateParams.articleId});
            vm.setFormFields(false, true);
        };
        
        vm.callServer = function(tableState) {
    	    var pagination = tableState.pagination;
    	    var start = pagination.start || 0, number = pagination.number || utils.searchInputInit().stPagination.number;
    	    processSearch(start, tableState.search);
        	
        	Article.findByLike(vm.searchInput, function(response){
                vm.data.list = response.resultList;
                tableState.pagination.numberOfPages = Math.ceil(response.count / number)
            },
            function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };
        
        function processSearch(start, searchObject) {
        	// First initialize SearchInput-Object and then set Search-Params
        	vm.searchInput = utils.processSearch(vm.searchInput, searchObject.predicateObject);
        	vm.searchInput.start = start;
        }
    }
})();
