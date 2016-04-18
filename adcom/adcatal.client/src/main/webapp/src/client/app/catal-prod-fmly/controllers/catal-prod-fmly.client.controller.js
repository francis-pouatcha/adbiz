(function () {
    'use strict';

    angular
        .module('app.catalProdFmly')
        .controller('CatalProdFmlyController', CatalProdFmlyController);

    CatalProdFmlyController.$inject = ['logger',
        '$stateParams',
        '$location',
        'CatalProdFmly',
        'utils',
        'CatalProdFmlyLangMap',
        'CatalProdFmlyForm', '$translate'];
    /* @ngInject */
    function CatalProdFmlyController(logger,
        $stateParams,
        $location,
        CatalProdFmly,
        utils,
        CatalProdFmlyLangMap,
        CatalProdFmlyForm, $translate) {

    	var userLangIso2=$translate.use();
    	
    	var buildLangMappingDisplay = function(prodFmly, langMappings){
    		angular.forEach(langMappings, function(langMapping){
    			if(angular.equals(this.identif, langMapping.cntnrIdentif)){
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
    		}, prodFmly);
    	};
    	var cleanDisplay = function(prodFmly){
    		var display = {};
    		display.langMapping = {};
			display.langMapping.id = prodFmly.langMappingId;
			prodFmly.langMappingId = undefined;
    		display.langMapping.langIso2 = prodFmly.langIso2;
    		prodFmly.langIso2 = undefined;
    		display.langMapping.artName = prodFmly.artName;
    		prodFmly.artName = undefined;
    		display.langMapping.shortName = prodFmly.shortName;
    		prodFmly.shortName = undefined;
    		display.langMapping.purpose = prodFmly.purpose;
    		prodFmly.purpose = undefined;
    		display.langMapping.usage = prodFmly.usage;
    		prodFmly.usage = undefined;
    		display.langMapping.warnings = prodFmly.warnings;
    		prodFmly.warnings = undefined;
    		display.langMapping.substances = prodFmly.substances;
    		prodFmly.substances = undefined;
    		return display;
    	};
    	
    	var rebuildDisplay = function(prodFmly, display){
    		if(display.langMapping){
    			prodFmly.langMappingId = display.langMapping.id;
    			prodFmly.langIso2 = display.langMapping.langIso2;
    			prodFmly.artName = display.langMapping.artName;
    			prodFmly.shortName = display.langMapping.shortName;
    			prodFmly.purpose = display.langMapping.purpose;
    			prodFmly.usage = display.langMapping.usage;
    			prodFmly.warnings = display.langMapping.warnings;
    			prodFmly.substances = display.langMapping.substances;
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
        vm.catalProdFmly = {};
        
        function initSearchInput(){
            // Initialize Search input and pagination
            vm.searchInput = utils.searchInputInit().searchInput;
            vm.searchInput.className = 'org.adorsys.adcatal.jpa.CatalProdFmlySearchInput';
            vm.searchInput.sortFieldNames.push({fieldName:'valueDt'});
            // Number of entries showed per page.
            vm.itemsByPage = utils.searchInputInit().stPagination.number;
        }
        

        vm.setFormFields = function(disabled) {
            vm.formFields = CatalProdFmlyForm.getFormFields(disabled);
        };
        
        initSearchInput();

        vm.create = function() {
        	var display = cleanDisplay(vm.catalProdFmly);
            var langMapping = display.langMapping;

            if(vm.catalProdFmly.parentIdentif){
                vm.catalProdFmly.famPath = '/'+vm.catalProdFmly.identif+'/'+vm.catalProdFmly.parentIdentif+'/';
            } else {
                vm.catalProdFmly.famPath = '/'+vm.catalProdFmly.identif+'/';            	
            }
            // Create new CatalProdFmly object
            var catalProdFmlyService = new CatalProdFmly(vm.catalProdFmly);
            // Redirect after save
            catalProdFmlyService.$save(function(response) {
                logger.success('CatalProdFmly created');
                langMapping.cntnrIdentif = response.id;
                langMapping.langIso2 = userLangIso2;
                vm.data.push(response);

                var langMappingService = new CatalProdFmlyLangMap(langMapping);
                langMappingService.$save(function () {
                    $location.path('/catal-prod-fmly/' + response.id);
                }, function (errorResponseTwo) {
                    vm.error = errorResponseTwo.data.summary;
                });
            }, function(errorResponse) {
                vm.error = errorResponse.data.summary;
            });
        };

        // Remove existing CatalProdFmly
        vm.remove = function(catalProdFmly) {
            if (catalProdFmly) {
                catalProdFmly = CatalProdFmly.get({catalProdFmlyId:catalProdFmly.id}, function() {
                    catalProdFmly.$remove(function() {
                        logger.success('CatalProdFmly deleted');
                        vm.tableParams.reload();
                    });
                });
            } else {
                vm.catalProdFmly.$remove(function() {
                    logger.success('CatalProdFmly deleted');
                    $location.path('/catal-prod-fmly');
                });
            }

        };

        // Update existing CatalProdFmly
        vm.update = function() {
        	var display = cleanDisplay(vm.catalProdFmly);
            var langMapping = display.langMapping;
            var catalProdFmly = vm.catalProdFmly;
            
            catalProdFmly.$update(function() {
                logger.success('CatalProdFmly updated');
                var langMappingRes = CatalProdFmlyLangMap.get({catalProdFmlyLangMapId:langMapping.id}, function(){
                	copyLangMapping(langMapping, langMappingRes);
                	langMappingRes.$update(function () {
                        $location.path('catal-prod-fmly/' + catalProdFmly.id);
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

        vm.toDisplayCatalProdFmly = function(disabled) {
            vm.catalProdFmly = CatalProdFmly.get({catalProdFmlyId: $stateParams.catalProdFmlyId}, function(){
            	CatalProdFmlyForm.catalProdFmlyId = $stateParams.catalProdFmlyId;
            	vm.setFormFields(disabled);
            	
            	CatalProdFmlyLangMap.findByCntnrIdentifIn({"list":[vm.catalProdFmly.identif], "start":0, "max":10, "langIso2":userLangIso2}, function(langResp){
            		buildLangMappingDisplay(vm.catalProdFmly, langResp.resultList);   
                }, function(errorResponse2){
                	vm.error = errorResponse2.data.summary;
                });
            	
            }, function(errorResponse){
                vm.error = errorResponse.data.summary;
            });
        };

        vm.callServer = function(tableState) {
    	    var pagination = tableState.pagination;
    	    var start = pagination.start || 0, number = pagination.number || utils.searchInputInit().stPagination.number;
    	    processSearch(start, tableState.search);
        	
        	CatalProdFmly.findByLike(vm.searchInput, function(response) {
                vm.data.list = response.resultList;
                tableState.pagination.numberOfPages = Math.ceil(response.count / number)

                var identifs = [];
                angular.forEach(response.resultList, function(prodFmly){
                	this.push(prodFmly.identif);
                }, identifs);
        	
                CatalProdFmlyLangMap.findByCntnrIdentifIn({"list":identifs, "start":0, "max":10, "langIso2":userLangIso2}, function(langResp){
                	angular.forEach(response.resultList, function(prodFmly){
                		buildLangMappingDisplay(prodFmly, langResp.resultList);                    	
                	}, this);
                }, function(errorResponse2){
                	vm.error = errorResponse2.data.summary;
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
