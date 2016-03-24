//catalArtEquivalence
(function () {
    'use strict';

    angular
        .module('app.catalArtEquivalence')
        .controller('CatalArtEquivalenceController', CatalArtEquivalenceController);

    CatalArtEquivalenceController.$inject = ['logger',
        'CatalArtEquivalence',
        'utils',
        'CatalArtLangMapping',        
        'CatalArtEquivalenceForm',
        'ArticleForm',
        'Article', '$stateParams'];
    /* @ngInject */
    function CatalArtEquivalenceController(logger, CatalArtEquivalence, utils,CatalArtLangMapping,
                                           CatalArtEquivalenceForm, ArticleForm, Article, $stateParams) {

    	var buildDisplay = function(articleEquiv, artLangMappings){
    		angular.forEach(artLangMappings, function(artLangMapping){
    			if(angular.equals(this.mainArtIdentif, artLangMapping.cntnrIdentif)){
    				// We can use this later to show more info on the article.
    				// if(angular.isUndefined(this.langMappings))this.langMappings=[];
    				// this.langMappings.push(this);
    				
        			// Take the value of the first entry
    				this.displayMainArtName = this.displayMainArtName || (artLangMapping.cntnrIdentif + ' - '+ artLangMapping.artName);
    			}
    			if(angular.equals(this.equivArtIdentif, artLangMapping.cntnrIdentif)){
        			// Take the value of the first entry
    				this.displayEquivArtName = this.displayEquivArtName || (artLangMapping.cntnrIdentif + ' - '+ artLangMapping.artName);
    			}
    		}, articleEquiv);
    		
    	};
    	
    	var cleanDisplay = function(articleEquiv){
    		var display = {};
    		display.displayMainArtName = articleEquiv.displayMainArtName;
    		display.displayEquivArtName = articleEquiv.displayEquivArtName;
    		
    		return display;
    	};
    	
    	var rebuildDisplay = function(articleEquiv, display){
    		articleEquiv.displayMainArtName = display.displayMainArtName;
    		articleEquiv.displayEquivArtName = display.displayEquivArtName;
    	};
    	
        var vm = this;
        vm.data = [];
        vm.catalArtEquivalence = {};
        

        vm.setCreateFields = function () {
            vm.catalArticleId = $stateParams.articleId;
        	vm.createFields = CatalArtEquivalenceForm.getCreateFields();
            vm.createFields[0].defaultValue = vm.catalArticleId;
        };
        vm.setFormFields = function (disabled) {
            vm.formFields = CatalArtEquivalenceForm.getFormFields(disabled);
        };
        vm.createForm = function (model) {
            utils.templateModal(model, 'createForm',
                'src/client/app/catal-art-equivalence/views/create.html', vm);
        };
        vm.editForm = function (model) {
            utils.templateModal(model, 'editForm',
                'src/client/app/catal-art-equivalence/views/edit.html', vm);
        };
        vm.showForm = function (model) {
            utils.templateModal(model, 'showForm',
                'src/client/app/catal-art-equivalence/views/view.html', vm);
        };

        vm.init = function () {
            vm.catalArticleId = $stateParams.articleId;
            CatalArtEquivalence.findBy(coreSearchInputInit(), function (response) {
                vm.data = response.resultList;
                // we can find the name of the article and set them.
                var identifs = [];
                angular.forEach(response.resultList, function(articleEquiv){
                	this.push(articleEquiv.mainArtIdentif);
                	this.push(articleEquiv.equivArtIdentif);
                }, identifs);

                // read the corresponding art language mapping for the user language. Actualy we use.
                CatalArtLangMapping.findByCntnrIdentifIn({"list":identifs, "start":0, "max":10}, function (langResp) {
                	angular.forEach(response.resultList, function(articleEquiv){
                		buildDisplay(articleEquiv, langResp.resultList);
                	}, this);
                });
                
            });
        };

        function coreSearchInputInit() {
            var searchInput = {};
            searchInput.entity = {};
            searchInput.entity.cntnrIdentif = vm.catalArticleId;
            searchInput.fieldNames = [];
            searchInput.fieldNames.push('cntnrIdentif');
            searchInput.className = 'org.adorsys.adcatal.jpa.CatalArtEquivalenceSearchInput';
            return searchInput;
        }

        vm.create = function (catalArtEquivalence) {
            vm.catalArticleId = $stateParams.articleId;
            catalArtEquivalence.cntnrIdentif = vm.catalArticleId;
            // Create new catalArtEquivalence object
            var catalArtEquivalenceRes = new CatalArtEquivalence(catalArtEquivalence);
            catalArtEquivalenceRes.$save(function (response) {
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
                index = vm.data.indexOf(vm.catalArtEquivalence);
                vm.catalArtEquivalence.$remove(function () {
                    logger.success('CatalArtEquivalence deleted');
                    vm.data.splice(index, 1);
                });
            }

        };

        // Update existing catalArtEquivalence
        vm.update = function (catalArtEquivalence) {
            var index = vm.data.indexOf(vm.model);
            var display = cleanDisplay(catalArtEquivalence);
            var catalArtEquivalenceRes = new CatalArtEquivalence(catalArtEquivalence);
            catalArtEquivalenceRes.$update(function () {
                logger.success('catalArtEquivalence updated');
                vm.data.splice(index, 1);
                vm.data.push(catalArtEquivalence);
                rebuildDisplay(catalArtEquivalence,display);
            }, function (errorResponse) {
                rebuildDisplay(catalArtEquivalence,display);
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewCatalArtEquivalence = function () {
            vm.setFormFields(true);
        };

        vm.toEditCatalArtEquivalence = function () {
            vm.setFormFields(false);
        };

        activate();

        function activate() {
            //logger.info('Activated CatalArtEquivalence View');
        }
    }

})();
