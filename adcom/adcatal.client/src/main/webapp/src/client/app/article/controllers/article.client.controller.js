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
        'CatalArtLangMappingForm',
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
                               CatalArtLangMappingForm,
                               CatalArt2ProductFamily,
                               CatalProdFmly,
                               CatalProdFmlyLangMap,
                               utils,
                               ArticleForm,
                               $translate) {
    	
    	var userLangIso2=$translate.use();
    	
    	var buildArtLangMappingDisplay = function(article, artLangMappings){
    		angular.forEach(artLangMappings, function(artLangMapping){
    			if(angular.equals(this.identif, artLangMapping.cntnrIdentif)){
    				if(userLangIso2==artLangMapping.langIso2){// override entry with user language version
    					rebuildDisplay(this,{"artLangMapping":artLangMapping});
    				} else {// only set if not yet set.
    					this.langMappingId = this.langMappingId || artLangMapping.id;
    					this.langIso2 = this.langIso2 ||  artLangMapping.langIso2;
    					this.artName = this.artName || artLangMapping.artName;
    					this.shortName = this.shortName || artLangMapping.shortName;
    					this.purpose = this.purpose || artLangMapping.purpose;
    					this.usage = this.usage || artLangMapping.usage;
    					this.warnings = this.warnings || artLangMapping.warnings;
    					this.substances = this.substances || artLangMapping.substances;
    				}
    			}
    		}, article);
    	};

    	var buildFamLangMappingDisplay = function(artList, art2ProductFamilies, famLangList){
    		var holder = {"artList":artList, "art2ProductFamilies":art2ProductFamilies, "famLangList":famLangList};
    		angular.forEach(holder.artList, function(article){
    			this.article = article;
    			angular.forEach(this.art2ProductFamilies, function(art2ProductFamily){
    				this.art2ProductFamily = art2ProductFamily;
    				if(this.article.identif==this.art2ProductFamily.artPic){
    					this.article.famMappings = this.article.famMappings || [];
    					angular.forEach(this.famLangList, function(prodFmlyLang){
    						if(this.art2ProductFamily.famCode==prodFmlyLang.cntnrIdentif){
    							this.article.famMappings.push(prodFmlyLang);
    						}
    					}, this);
    				}
    			}, this);
    		},holder);
    	};
    	
    	var cleanDisplay = function(article){
    		var display = {};
    		cleanArtLangMappingDisplay(article, display);
    		display.famMappings = article.famMappings;
    		article.famMappings = undefined;
    		return display;
    	};
    	
    	var cleanArtLangMappingDisplay = function(article, display){
    		display.artLangMapping = {};
			display.artLangMapping.id = article.langMappingId;
			article.langMappingId = undefined;
    		display.artLangMapping.langIso2 = article.langIso2;
    		article.langIso2 = undefined;
    		display.artLangMapping.artName = article.artName;
    		article.artName = undefined;
    		display.artLangMapping.shortName = article.shortName;
    		article.shortName = undefined;
    		display.artLangMapping.purpose = article.purpose;
    		article.purpose = undefined;
    		display.artLangMapping.usage = article.usage;
    		article.usage = undefined;
    		display.artLangMapping.warnings = article.warnings;
    		article.warnings = undefined;
    		display.artLangMapping.substances = article.substances;
    		article.substances = undefined;
    	};
    	
    	var rebuildDisplay = function(article, display){
    		if(display.artLangMapping){
    			article.langMappingId = display.artLangMapping.id;
    			article.langIso2 = display.artLangMapping.langIso2;
    			article.artName = display.artLangMapping.artName;
    			article.shortName = display.artLangMapping.shortName;
    			article.purpose = display.artLangMapping.purpose;
    			article.usage = display.artLangMapping.usage;
    			article.warnings = display.artLangMapping.warnings;
    			article.substances = display.artLangMapping.substances;
    		}
    		
    		if(display.famMappings){
    			article.famMappings = display.famMappings;
    		}
    	};
    	
    	var copyLangMapping = function(source, destination){
    		destination.artName = source.artName;
    		destination.shortName = source.shortName;
    		destination.purpose = source.purpose;
    		destination.usage = source.usage;
    		destination.warnings = source.warnings;
    		destination.substances = source.substances;
    	};
    	
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

        vm.setFormFields = function(disabled) {
            vm.formFields = ArticleForm.getFormFields(disabled);
        };
        vm.setFormSearchFields = function() {
            vm.formSearchFields = ArticleForm.getFormSearchFields();
        };

        initSearchInput();

        vm.create = function() {
        	var display = cleanDisplay(vm.article);
            var catalArtLangMapping = display.artLangMapping;
            // Create new Article object
            var articleService = new Article(vm.article);
            // Redirect after save
            articleService.$save(function(response) {
                logger.success('Article created');
                catalArtLangMapping.cntnrIdentif = response.id;
                catalArtLangMapping.langIso2 = userLangIso2;
                vm.data.push(response);

                var catalArtLangMappingService = new CatalArtLangMapping(catalArtLangMapping);
                catalArtLangMappingService.$save(function () {
                    $location.path('/article/' + response.id);
                }, function (errorResponseTwo) {
                    vm.error = errorResponseTwo.data.summary;
                });

            }, function(errorResponse) {
                vm.error = errorResponse;
                logger.error(errorResponse);
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
        	var display = cleanDisplay(vm.article);
            var catalArtLangMapping = display.artLangMapping;
            var article = vm.article;

            article.$update(function() {
                logger.success('Article updated');
                var catalArtLangMappingRes = CatalArtLangMapping.get({catalArtLangMappingId:catalArtLangMapping.id}, function(){
                	copyLangMapping(catalArtLangMapping, catalArtLangMappingRes);
                	catalArtLangMappingRes.$update(function () {
                        $location.path('/article/' + article.id);
                    }, function(errorResponse3){
                        vm.error = errorResponse3.data.summary;
                    });
                }, function(errorResponse2){
                    vm.error = errorResponse2.data.summary;
                });
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toDisplayArticle = function(disabled) {
            Article.get({articleId: $stateParams.articleId}, function(data){
                vm.article = data;

                ArticleForm.catalArticleId = $stateParams.articleId;
                vm.setFormFields(disabled);
                CatalArtLangMapping.findByCntnrIdentifIn({"list":[vm.article.identif], "start":0, "max":10, "langIso2":userLangIso2}, function(langResp){
                	buildArtLangMappingDisplay(vm.article, langResp.resultList);   
      
                    CatalArt2ProductFamily.findByCntnrIdentifIn({"list":[vm.article.identif], "start":0, "max":10, "langIso2":userLangIso2},function(art2ProductFamilyResp){
                    	var art2ProductFamilies = [];
                    	angular.forEach(art2ProductFamilyResp.resultList,function(art2ProductFamily){
                    		this.push(art2ProductFamily.famCode);
                    	},art2ProductFamilies);
                    	
                    	CatalProdFmlyLangMap.findByCntnrIdentifIn({"list":art2ProductFamilies, "start":0, "max":1, "langIso2":userLangIso2}, function (langResp) {
                        	buildFamLangMappingDisplay([vm.article], art2ProductFamilyResp.resultList, langResp.resultList);
                        });
                    });                	
                }, function(errorResponse2){
                	vm.error = errorResponse2.data.summary;
                });
            });

        };
        
        vm.callServer = function(tableState) {
    	    var pagination = tableState.pagination;
    	    var start = pagination.start || 0, number = pagination.number || utils.searchInputInit().stPagination.number;
    	    processSearch(start, tableState.search);
            console.log(tableState.search);

        	
    	   if(vm.searchInput.artName && vm.searchInput.artName.length>0){
           	Article.findByArtName({"artName":vm.searchInput.artName, "start":0, "max":20}, function(response){
           		return processSearchResult(tableState, number, response);
            },
            function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
    	   } else if (vm.searchInput.prodFmly && vm.searchInput.prodFmly.length>0){
    		   // ignore this first
    	   } else {
           	Article.findByLike(vm.searchInput, function(response){
           		return processSearchResult(tableState, number, response);
            },
            function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
    	   }
        };
        
        function processSearchResult(tableState, number, response){
    		vm.data.list = response.resultList;
            tableState.pagination.numberOfPages = Math.ceil(response.count / number);
            // copy the identif in a list
            var identifs = [];
            angular.forEach(response.resultList, function(article){
            	this.push(article.identif);
            }, identifs);
            CatalArtLangMapping.findByCntnrIdentifIn({"list":identifs, "start":0, "max":10, "langIso2":userLangIso2}, function(langResp){
            	angular.forEach(response.resultList, function(article){
            		buildArtLangMappingDisplay(article, langResp.resultList);                    	
            	}, this);
            }, function(errorResponse2){
            	vm.error = errorResponse2.data.summary;
            });                
            
            CatalArt2ProductFamily.findByCntnrIdentifIn({"list":identifs, "start":0, "max":10, "langIso2":userLangIso2},function(art2ProductFamilyResp){
            	var art2ProductFamilies = [];
            	angular.forEach(art2ProductFamilyResp.resultList,function(art2ProductFamily){
            		this.push(art2ProductFamily.famCode);
            	},art2ProductFamilies);
            	
            	CatalProdFmlyLangMap.findByCntnrIdentifIn({"list":art2ProductFamilies, "start":0, "max":1, "langIso2":userLangIso2}, function (langResp) {
                	buildFamLangMappingDisplay(response.resultList, art2ProductFamilyResp.resultList, langResp.resultList);
                });
            });        	
        }
        function processSearch(start, searchObject) {
        	// First initialize SearchInput-Object and then set Search-Params
        	vm.searchInput = utils.processSearch(vm.searchInput, searchObject.predicateObject);
        	vm.searchInput.start = start;
            if(vm.searchInput.entity.artName){
                vm.searchInput.artName = vm.searchInput.entity.artName;
                vm.searchInput.entity.artName = undefined;
            }
            if(vm.searchInput.entity.prodFmly){
                vm.searchInput.prodFmly = vm.searchInput.entity.prodFmly;
                vm.searchInput.entity.prodFmly = undefined;
            }
        }
    }
})();
