'use strict';
    
angular.module('AdStock')

.factory('stkSectionUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adstock.server/rest/stksections';

    service.language=sessionManager.language;
    
    service.translate = function(){
    	$translate(['StkSection_sectionCode_description.title',
    	            'StkSection_parentCode_description.title',
    	            'StkSection_name_description.title',
    	            'StkSection_position_description.title',
    	            'StkSection_geoCode_description.title',
    	            'StkSection_wharehouse_description.title',
    	            'StkSection_stkSubSection_description.title',
    	            'StkSection_stkArticleLot_description.title',
                    
                    'StkSections_description.title',

    	            'Entity_show.title',
    	            'Entity_previous.title',
    	            'Entity_list.title',
    	            'Entity_next.title',
    	            'Entity_edit.title',
    	            'Entity_create.title',
    	            'Entity_update.title',
    	            'Entity_Result.title',
    	            'Entity_search.title',
    	            'Entity_cancel.title',
    	            'Entity_save.title',
    	            'Entity_notfound.title'
    	            
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.loadSectionCodes = function(val){
        return loadGenericPromise(val,'sectionCode').then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    service.loadSectionNames = function(val){
        return loadGenericPromise(val,'name').then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    service.loadWharehouses = function(val){
        return loadGenericPromise(val,'wharehouse').then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };
    
    function loadGenericPromise(val,fieldName){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        if(val){
            searchInput.entity[fieldName] = val+'%';
            searchInput.fieldNames = [fieldName];
        }
        var deferred = $q.defer();
        genericResource.findByLike(service.urlBase, searchInput)
		.success(function(entitySearchResult) {
        	deferred.resolve(entitySearchResult);
		})
        .error(function(){
            deferred.reject(service.translations['Entity_notfound.title']);
        });
        return deferred.promise;
    }    
    
    service.translate();
    
    return service;
}])
.factory('stkSectionState',['$rootScope',function($rootScope){

    var service = {
    };
    var activeTabVar="stkSubSection";

    var stkSubSectionActiveVar=true;
    service.stkSubSectionActive=function(stkSubSectionActiveIn){
        if(stkSubSectionActiveIn)stkSubSectionActiveVar=stkSubSectionActiveIn;
        return stkSubSectionActiveVar;
    };

    var stkSectionArticleLotActiveVar=false;
    service.stkSectionArticleLotActive=function(stkSectionArticleLotActiveIn){
        if(stkSectionArticleLotActiveIn)stkSectionArticleLotActiveVar=stkSectionArticleLotActiveIn;
        return stkSectionArticleLotActiveVar;
    };

    // A cache of dependents by ptnrNbr
    var depCacheVar = {};

    // The search state.
    // The current list of business partners.
    var stkSectionsVar=[];
    service.hasStkSections = function(){
        return stkSectionsVar && stkSectionsVar.length>0;
    };
    service.stkSections = function(stkSectionsIn){
        if(stkSectionsIn){
            var length = stkSectionsVar.length;
            for	(var index = 0; index < length; index++) {
                stkSectionsVar.pop();
            }
            length = stkSectionsIn.length;
            for	(var index1 = 0; index1 < length; index1++) {
                stkSectionsVar.push(stkSectionsIn[index1]);
            }
        }
        return stkSectionsVar;
    };

    var selectedIndexVar=-1;
    service.selectedIndex= function(selectedIndexIn){
        if(selectedIndexIn)selectedIndexVar=selectedIndexIn;
        return selectedIndexVar;
    };

    var totalItemsVar=0;
    service.totalItems = function(totalItemsIn){
        if(totalItemsIn)totalItemsVar=totalItemsIn;
        return totalItemsVar;
    };

    var currentPageVar = 0;
    service.currentPage = function(currentPageIn){
        if(currentPageIn) currentPageVar=currentPageIn;
        return currentPageVar;
    };

    var maxSizeVar = 5;
    service.maxSize = function(maxSizeIn) {
        if(maxSizeIn) maxSizeVar=maxSizeIn;
        return maxSizeVar;
    };

    var itemPerPageVar = 10;
    var searchInputVar = {
        entity:{},
        fieldNames:[],
        start:0,
        max:itemPerPageVar
    };
    service.searchInput = function(searchInputIn){
        if(!searchInputIn)
            return angular.copy(searchInputVar);

        searchInputVar=angular.copy(searchInputIn);
        return searchInputIn;
    };
    service.searchInputChanged = function(searchInputIn){
        return angular.equals(searchInputVar, searchInputIn);
    };
    service.itemPerPage = function(itemPerPageIn){
        if(itemPerPageIn)itemPerPageVar=itemPerPageIn;
        return itemPerPageVar;
    };

    service.consumeSearchResult = function(searchInput, entitySearchResult) {
        // store search
        service.searchInput(searchInput);
        // Store result
        service.stkSections(entitySearchResult.resultList);
        service.totalItems(entitySearchResult.count);
        service.selectedIndex(-1);
        depCacheVar={};
    };

    service.paginate = function(){
        searchInputVar.start = ((currentPageVar - 1)  * itemPerPageVar);
        searchInputVar.max = itemPerPageVar;
        return service.searchInput();
    };

    service.stkSection = function(index){
        if(index>=0 && index<stkSectionsVar.length)selectedIndexVar=index;
        if(selectedIndexVar<0 || selectedIndexVar>=stkSectionsVar.length) return;
        return stkSectionsVar[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(stkSection){
        if(!stkSectionsVar || !stkSection) return;
        var current = service.stkSection();
        if(current && current.sectionCode==stkSection.sectionCode){
            stkSectionsVar[selectedIndexVar]=stkSection;
        } else {
            for (var index in stkSectionsVar) {
                if(stkSectionsVar[index].sectionCode==stkSection.sectionCode){
                    stkSectionsVar[index]=stkSection;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    // Check if the entity to be displayed is at the correct index.
    service.peek = function(stkSection, index){
        if(!stkSectionsVar || !stkSection) return false;
        if(index>=0 && index<stkSectionsVar.length){
            selectedIndexVar=index;
            return true;
        }
        return false;
    };

    service.push = function(stkSection){
        if(!stkSectionsVar || !stkSection) return false;
        var length = stkSectionsVar.push(stkSection);
        selectedIndexVar=length-1;
        return true;
    };

    service.tabSelected=function(tabName){
        if(tabName){
            activeTabVar=tabName;
            stkSubSectionActiveVar= tabName=='stkSubSection';
            stkSectionArticleLotActiveVar= tabName=='stkSectionArticleLot';
        }
        $rootScope.$broadcast('StkSectionsSelected', {tabName:activeTabVar,stkSection:service.stkSection()});
    };

    service.previous = function (){
    	if(stkSectionsVar.length<=0) return;

        if(selectedIndexVar<=0){
            selectedIndexVar=stkSectionsVar.length-1;
        } else {
            selectedIndexVar-=1;
        }
        return service.stkSection();
    };

    service.next = function(){
    	if(stkSectionsVar.length<=0) return;
    	
    	if(selectedIndexVar>=stkSectionsVar.length-1 || selectedIndexVar<0){
    		selectedIndexVar=0;
    	} else {
            selectedIndexVar+=1;
    	}

        return service.stkSection();
    };

    return service;

}])
.controller('stkSectionsCtlr',['$scope','genericResource','stkSectionUtils','stkSectionState','$location','$rootScope',
function($scope,genericResource,stkSectionUtils,stkSectionState,$location,$rootScope){

    $scope.searchInput = stkSectionState.searchInput();
    $scope.itemPerPage=stkSectionState.itemPerPage;
    $scope.totalItems=stkSectionState.totalItems;
    $scope.currentPage=stkSectionState.currentPage();
    $scope.maxSize =stkSectionState.maxSize;
    $scope.stkSections =stkSectionState.stkSections;
    $scope.selectedIndex=stkSectionState.selectedIndex;
    $scope.error = "";
    $scope.stkSectionUtils=stkSectionUtils;

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		stkSectionUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(stkSectionState.hasStkSections())return;
        findByLike($scope.searchInput);
    }

    function findByLike(searchInput){
		genericResource.findByLike(stkSectionUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			stkSectionState.consumeSearchResult(searchInput,entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }

    function processSearchInput(){
        var fieldNames = [];
        if($scope.searchInput.entity.sectionCode && !fieldNames['sectionCode'])
        	fieldNames.push('sectionCode');
        if($scope.searchInput.entity.name && !fieldNames['name'])
        	fieldNames.push('name');
        if($scope.searchInput.entity.wharehouse && !fieldNames['wharehouse'])
        	fieldNames.push('wharehouse');
        if($scope.searchInput.entity.parentCode && !fieldNames['parentCode'])
        	fieldNames.push('parentCode');
        $scope.searchInput.fieldNames = fieldNames;
        return $scope.searchInput;
    };

    $scope.handleSearchRequestEvent = function(){
    	processSearchInput();
    	findByLike($scope.searchInput);
    };

    $scope.paginate = function(){
    	stkSectionState.currentPage($scope.currentPage);
        $scope.searchInput = stkSectionState.paginate();
    	findByLike($scope.searchInput);
    };

    $scope.handlePrintRequestEvent = function(){		
	};
	
	$scope.show = function(stkSection, index){
		if(stkSectionState.peek(stkSection, index)){
			$location.path('/StkSections/show/');
		}
	}

	$scope.edit = function(stkSection, index){
		if(stkSectionState.peek(stkSection, index)){
			$location.path('/StkSections/edit/');
		}
	}
}])
.controller('stkSectionCreateCtlr',['$scope','stkSectionUtils','$translate','genericResource','$location','stkSectionState',
        function($scope,stkSectionUtils,$translate,genericResource,$location,stkSectionState){
    $scope.stkSection = stkSectionState.stkSection();
    $scope.create = create;
    $scope.error = "";
    $scope.stkSectionUtils=stkSectionUtils;

    function create(){
    	genericResource.create(stkSectionUtils.urlBase, $scope.stkSection)
    	.success(function(stkSection){
    		if(stkSectionState.push(stkSection)){
    			$location.path('/StkSections/show/');
    		}
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
}])
.controller('stkSectionEditCtlr',['$scope','genericResource','$location','stkSectionUtils','stkSectionState',
                                 function($scope,genericResource,$location,stkSectionUtils,stkSectionState){
    $scope.stkSection = stkSectionState.stkSection();
    $scope.error = "";
    $scope.stkSectionUtils=stkSectionUtils;
    $scope.update = function (){
    	genericResource.update(stkSectionUtils.urlBase, $scope.stkSection)
    	.success(function(stkSection){
    		if(stkSectionState.replace(stkSection)){
    			$location.path('/StkSections/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}])
.controller('stkSectionShowCtlr',['$scope','genericResource','$location','stkSectionUtils','stkSectionState','$rootScope',
                                 function($scope,genericResource,$location,stkSectionUtils,stkSectionState,$rootScope){
    $scope.stkSection = stkSectionState.stkSection();
    $scope.error = "";
    $scope.stkSectionUtils=stkSectionUtils;
    $scope.stkSubSectionActive=stkSectionState.stkSubSectionActive();
    $scope.stkSectionArticleLotActive=stkSectionState.stkSectionArticleLotActive();
    
    $scope.previous = function (){
        var ent = stkSectionState.previous();
        if(ent){
            $scope.stkSection=ent;
            stkSectionState.tabSelected();
        }
    }

    $scope.next =  function (){
        var ent = stkSectionState.next();
        if(ent){
            $scope.stkSection=ent;
            stkSectionState.tabSelected();
        }
    };
    $scope.tabSelected = function(tabName){
    	stkSectionState.tabSelected(tabName);
    };
    $scope.edit = function(){
        $location.path('/StkSections/edit/');
    };
}])
.factory('stkSubSectionsState',['stkSectionState',function(stkSectionState){
	
	var service = {
	};
	
	service.stkSection = stkSectionState.stkSection;
    var searchResultsVar = {};
    service.searchResult = function(){
    	var stkSection = stkSectionState.stkSection();
    	if(!stkSection) return;
    	var parentCode = stkSection.sectionCode;
        if(parentCode && searchResultsVar[parentCode]) 
        	return searchResultsVar[parentCode];
    };
    var nakedSearchInputVar = {
        entity:{},
        fieldNames:[],
        start: 0,
        max: 5        
    };
    service.nakedSearchInput = function(){
    	return angular.copy(nakedSearchInputVar);
    };
    service.stkSubSections = function(){
    	var sr = service.searchResult();
    	if(sr) return sr.resultList;
    };
    service.searchInput = function(){
    	var sr = service.searchResult();
    	if(sr) return sr.searchInput;
    };
    service.itemPerPage = function(){
    	var sr = service.searchResult();
    	if(sr) return sr.searchInput.max;
    };
    service.totalItems = function(){
    	var sr = service.searchResult();
    	if(sr) return sr.count;
    };
    service.currentPage = function(){
    	var sr = service.searchResult();
    	if(sr && sr.searchInput && sr.searchInput.start && sr.searchInput.max){
    		if(sr.searchInput.start<0)sr.searchInput.start=0;
    		return 1 + (sr.searchInput.start/sr.searchInput.max)
    	}
    };

    service.consumeSearchResult = function(entitySearchResult){
    	if(entitySearchResult && 
    			entitySearchResult.searchInput && 
    			entitySearchResult.searchInput.entity &&
    			entitySearchResult.searchInput.entity.parentCode)
    		searchResultsVar[entitySearchResult.searchInput.entity.parentCode] = entitySearchResult;
    };
    
    service.hasSearchResult = function(parentCode){
    	return (searchResultsVar[parentCode] && searchResultsVar[parentCode].resultList);
    };

	service.stkSubSectionActive= stkSectionState.stkSubSectionActive;

    service.searchInputChanged = function(searchInputIn){
        return angular.equals(service.searchInput(), searchInputIn);
    };

    // start = currentPage-1 * itemPerPage
    // currentPage = (start/itemPerPage)+1
    service.paginate = function(currentPage){
    	var searchInputVar = service.searchInput();
    	if(currentPage){
            searchInputVar.start = ((currentPage - 1)  * searchInputVar.max);
    	}
        return searchInputVar;
    };
    
	return service;

}])
.controller('stkSubSectionsCtlr',['$scope','genericResource','$modal','$routeParams',
                                  'stkSectionUtils','stkSubSectionsState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 stkSectionUtils,stkSubSectionsState,$rootScope){

    $scope.stkSubSections=stkSubSectionsState.stkSubSections;
    $scope.error = "";
    $scope.stkSectionUtils=stkSectionUtils;
    $scope.itemPerPage=stkSubSectionsState.itemPerPage;
    $scope.totalItems=stkSubSectionsState.totalItems;
    $scope.currentPage=stkSubSectionsState.currentPage();

    var sctnSelectedUnregisterHdl = $rootScope.$on('StkSectionsSelected', function(event, data){
        var stkSection = stkSubSectionsState.stkSection();
        if(!stkSection || !data || !data.stkSection || stkSection.sectionCode!=data.stkSection.sectionCode) return;
        loadSubSections();
    });
    $scope.$on('$destroy', function () {
    	sctnSelectedUnregisterHdl();
    });
    $scope.paginate = function(){
        $scope.searchInput = stkSubSectionsState.paginate($scope.currentPage);
    	findBy($scope.searchInput);
    };
    loadSubSections();
    function loadSubSections (){
    	if(!stkSubSectionsState.stkSubSectionActive()) return;
        var stkSection = stkSubSectionsState.stkSection();
        if(!stkSection) return;
        if(!stkSubSectionsState.hasSearchResult(stkSection.sectionCode)) {
        	var searchInput = stkSubSectionsState.searchInput();
        	if(!searchInput){
        		searchInput = stkSubSectionsState.nakedSearchInput();
        		searchInput.entity.parentCode=stkSection.sectionCode;
        		searchInput.fieldNames.push('parentCode');
        	}
        	findBy(searchInput, stkSection.sectionCode);
        }
    }

    function findBy(searchInput){
    	genericResource.findBy(stkSectionUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            stkSubSectionsState.consumeSearchResult(entitySearchResult);
        })
    	.error(function(error){
    		$scope.error = error;
    	});
    }

}]);

