(function () {
    'use strict';

    angular
        .module('app.catalArt2ProductFamily')
        .controller('CatalArt2ProductFamilyController', CatalArt2ProductFamilyController);

    CatalArt2ProductFamilyController.$inject = ['logger',
        'CatalArt2ProductFamily',
        'CatalArt2ProductFamilyForm',
        'ArticleForm',
        'utils',
        'CatalProdFmlyLangMap',
        '$stateParams', '$translate'
        ];
    /* @ngInject */
    function CatalArt2ProductFamilyController(logger,
        CatalArt2ProductFamily,
        CatalArt2ProductFamilyForm,
        ArticleForm,
        utils, 
        CatalProdFmlyLangMap,
        $stateParams, $translate
        ) {

    	var userLangIso2=$translate.use();
    	
    	var buildLangMappingDisplay = function(art2ProdFmly, langMappings){
    		angular.forEach(langMappings, function(langMapping){
    			if(angular.equals(this.famCode, langMapping.cntnrIdentif)){
    				if(userLangIso2==langMapping.langIso2){// override entry with user language version
    					rebuildDisplay(this,{"langMapping":langMapping});
    				} else {// only set if not yet set.
    					this.langMappingId = this.langMappingId || langMapping.id;
    					this.langIso2 = this.langIso2 ||  langMapping.langIso2;
    					this.artName = this.artName || langMapping.artName;
    					this.shortName = this.shortName || langMapping.shortName;
    					this.purpose = this.purpose || langMapping.purpose;
    					this.usage = this.usage || langMapping.usage;
    					this.warnings = this.warnings || langMapping.warnings;
    					this.substances = this.substances || langMapping.substances;
    				}
    			}
    		}, art2ProdFmly);
    	};
    	var cleanDisplay = function(art2ProdFmly){
    		var display = {};
    		display.langMapping = {};
			display.langMapping.id = art2ProdFmly.langMappingId;
			art2ProdFmly.langMappingId = undefined;
    		display.langMapping.langIso2 = art2ProdFmly.langIso2;
    		art2ProdFmly.langIso2 = undefined;
    		display.langMapping.artName = art2ProdFmly.artName;
    		art2ProdFmly.artName = undefined;
    		display.langMapping.shortName = art2ProdFmly.shortName;
    		art2ProdFmly.shortName = undefined;
    		display.langMapping.purpose = art2ProdFmly.purpose;
    		art2ProdFmly.purpose = undefined;
    		display.langMapping.usage = art2ProdFmly.usage;
    		art2ProdFmly.usage = undefined;
    		display.langMapping.warnings = art2ProdFmly.warnings;
    		art2ProdFmly.warnings = undefined;
    		display.langMapping.substances = art2ProdFmly.substances;
    		art2ProdFmly.substances = undefined;
    		return display;
    	};
    	
    	var rebuildDisplay = function(art2ProdFmly, display){
    		if(display.langMapping){
    			art2ProdFmly.langMappingId = display.langMapping.id;
    			art2ProdFmly.langIso2 = display.langMapping.langIso2;
    			art2ProdFmly.artName = display.langMapping.artName;
    			art2ProdFmly.shortName = display.langMapping.shortName;
    			art2ProdFmly.purpose = display.langMapping.purpose;
    			art2ProdFmly.usage = display.langMapping.usage;
    			art2ProdFmly.warnings = display.langMapping.warnings;
    			art2ProdFmly.substances = display.langMapping.substances;
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
        vm.catalArt2ProductFamily = {};

        vm.createForm = function(model) {
            utils.templateModal(model, 'createForm',
                'src/client/app/catal-art-2-product-family/views/create.html', vm);
        };
        vm.editForm = function(model) {
            utils.templateModal(model, 'editForm',
                'src/client/app/catal-art-2-product-family/views/edit.html', vm);
        };
        vm.showForm = function(model) {
            utils.templateModal(model, 'showForm',
                'src/client/app/catal-art-2-product-family/views/view.html', vm);
        };
        vm.init = function() {
            vm.catalArticleId = $stateParams.articleId;
            CatalArt2ProductFamily.findBy(coreSearchInputInit(), function(response) {
                vm.data = response.resultList;

                var identifs = [];
                angular.forEach(response.resultList, function(art2ProdFmly){
                	this.push(art2ProdFmly.famCode);
                }, identifs);
        	
                CatalProdFmlyLangMap.findByCntnrIdentifIn({"list":identifs, "start":0, "max":10, "langIso2":userLangIso2}, function(langResp){
                	angular.forEach(response.resultList, function(art2ProdFmly){
                		buildLangMappingDisplay(art2ProdFmly, langResp.resultList);                    	
                	}, this);
                }, function(errorResponse2){
                	vm.error = errorResponse2.data.summary;
                });                
            });
        };

        function coreSearchInputInit() {
            var coreSearchInput = {};
            coreSearchInput.entity = {};
            coreSearchInput.entity.cntnrIdentif = vm.catalArticleId;
            coreSearchInput.fieldNames = [];
            coreSearchInput.fieldNames.push('cntnrIdentif');
            coreSearchInput.className = 'org.adorsys.adcatal.jpa.CatalArt2ProductFamilySearchInput';
            return coreSearchInput;
        }

        vm.setFormFields = function(disabled) {
            vm.catalArticleId = $stateParams.articleId;
            vm.formFields = CatalArt2ProductFamilyForm.getFormFields(disabled);
            vm.formFields[0].defaultValue = vm.catalArticleId;
        };

        vm.create = function(catalArt2ProductFamily) {
        	var display = cleanDisplay(catalArt2ProductFamily);
            var langMapping = display.langMapping;
        	
            vm.catalArticleId = $stateParams.articleId;
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
        	var display = cleanDisplay(catalArt2ProductFamily);
            var langMapping = display.langMapping;
        	
            var catalArt2ProductFamilyRes = new CatalArt2ProductFamily(catalArt2ProductFamily);
            var index = vm.data.indexOf(catalArt2ProductFamily);
            catalArt2ProductFamilyRes.$update(function() {
                logger.success('CatalArt2ProductFamily updated');
                vm.data.splice(index, 1);
                vm.data.push(catalArt2ProductFamily);
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        vm.toViewCatalArt2ProductFamily = function() {
        	CatalProdFmlyLangMap.findByCntnrIdentifIn({"list":[vm.catalArt2ProductFamily.famCode], "start":0, "max":10, "langIso2":userLangIso2}, function(langResp){
        		buildLangMappingDisplay(vm.catalArt2ProductFamily, langResp.resultList);   
                vm.setFormFields(true);
            }, function(errorResponse2){
            	vm.error = errorResponse2.data.summary;
            });
        };

        vm.toEditCatalArt2ProductFamily = function() {
        	CatalProdFmlyLangMap.findByCntnrIdentifIn({"list":[vm.catalArt2ProductFamily.famCode], "start":0, "max":10, "langIso2":userLangIso2}, function(langResp){
        		buildLangMappingDisplay(vm.catalArt2ProductFamily, langResp.resultList);   
                vm.setFormFields(false);
            }, function(errorResponse2){
            	vm.error = errorResponse2.data.summary;
            });
        };

        activate();

        function activate() {
            //logger.info('Activated CatalArt2ProductFamily View');
        }
    }

})();
