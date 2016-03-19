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
        'CatalArt2ProductFamily',
        'CatalProdFmly',
        'CatalProdFmlyLangMap',
        'utils',
        'ArticleForm',
        '$translate'];
    /* @ngInject */
    function ArticleController(logger,
                               $stateParams,
                               $location,
                               Article,
                               CatalArtLangMapping,
                               CatalArt2ProductFamily,
                               CatalProdFmly,
                               CatalProdFmlyLangMap,
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
                tableState.pagination.numberOfPages = Math.ceil(response.count / number);
                // copy the identif in a list
                var identifs = [];
                angular.forEach(response.resultList, function(article){
                	this.push(article.identif);
                }, identifs);
                
                // read the corresponding art language mapping for the user language. Actualy we use.
                CatalArtLangMapping.findByCntnrIdentifIn({"list":identifs, "start":0, "max":10}, function (langResp) {
                	angular.forEach(response.resultList, function(article){
                		angular.forEach(langResp.resultList, function(artLangMapping){
                			if(angular.equals(this.identif, artLangMapping.cntnrIdentif)){
                				if(angular.isUndefined(this.langMappings))this.langMappings=[];

                				this.langMappings.push(this);
                    			// Take the value of the first entry
                                this.artName = this.artName || artLangMapping.artName;
                                this.shortName = this.shortName || artLangMapping.shortName;
                			}
                		}, article);
                	}, this);
                });
                
                
                CatalArt2ProductFamily.findByCntnrIdentifIn({"list":identifs, "start":0, "max":10},function(art2ProductFamilyResp){
                	var famCodes = [];
                	angular.forEach(art2ProductFamilyResp.resultList,function(art2ProductFamily){
                		this.push(art2ProductFamily.famCode);
                	},famCodes);
                	
                	CatalProdFmlyLangMap.findByCntnrIdentifIn({"list":famCodes, "start":0, "max":10}, function (langResp) {
                		var holder = {"artList":response.resultList, "famLangList":langResp.resultList, "art2ProductFamilies":art2ProductFamilyResp.resultList};
                		angular.forEach(holder.artList, function(article){
                			this.article = article;
                			angular.forEach(this.art2ProductFamilies, function(art2ProductFamily){
                				this.art2ProductFamily = art2ProductFamily;
                				if(this.article.identif==this.art2ProductFamily.artPic){
                					this.article.productFamilies = this.article.productFamilies || [];
                					angular.forEach(this.famLangList, function(prodFmlyLang){
                						if(this.art2ProductFamily.famCode==prodFmlyLang.cntnrIdentif){
                							this.article.productFamilies.push(prodFmlyLang);
                							this.article.famNames = this.article.famNames || '';
                							this.article.famNames += prodFmlyLang.artName + '\n';
                						}
                					}, this);
                				}
                			}, this);
                		},holder);
                    });
                });
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
