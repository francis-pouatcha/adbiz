'use strict';
    
angular.module('AdStock')

.factory('stkArticleLotUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adstock.server/rest/stkarticlelots';

    service.urlCatalArticle='/adcatal.server/rest/catalarticles';

    service.language=sessionManager.language;
    
    service.translate = function(){
    	$translate([
    	            'StkArticleLot_artName_description.title',
    	            'StkArticleLot_familyName_description.title',
    	            'StkArticleLot_supplier_description.title',
    	            'StkArticleLot_expirDtFrom_description.title',
    	            'StkArticleLot_expirDtTo_description.title',
    	            'StkArticleLot_purchWrntyDtFrom_description.title',
    	            'StkArticleLot_purchWrntyDtTo_description.title',
    	            'StkArticleLot_purchRtrnDtFrom_description.title',
    	            'StkArticleLot_purchRtrnDtTo_description.title',
    	            'StkArticleLot_lotPic_description.title',
    	            'StkArticleLot_artPic_description.title',
    	            'StkArticleLot_lotQty_description.title',
    	            'StkArticleLot_lotQtyDt_description.title',
    	            'StkArticleLot_sppuTaxIncl_description.title',
    	            'StkArticleLot_expirDt_description.title',
                    'StkArticleLot_stkgDt_description.title',
                    'StkArticleLot_sppuHT_description.title',
                    'StkArticleLot_sppuCur_description.title',
                    'StkArticleLot_supplierPic_description.title',
                    'StkArticleLot_minSppuHT_description.title',
                    'StkArticleLot_vatSalesPct_description.title',
                    'StkArticleLot_salesWrntyDys_description.title',
                    'StkArticleLot_salesRtrnDays_description.title',
                    'StkArticleLot_pppuHT_description.title',
                    'StkArticleLot_pppuCur_description.title',
                    'StkArticleLot_vatPurchPct_description.title',
                    'StkArticleLot_purchWrntyDys_description.title',
                    'StkArticleLot_purchWrntyDt_description.title',
                    'StkArticleLot_purchRtrnDays_description.title',
                    'StkArticleLot_purchRtrnDt_description.title',
                    'StkArticleLot_closedDt_description.title',
                    'StkArticleLot_artFeatures_artName_description.title',

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
    	            'Entity_notfound.title',
    	            'Entity_To.title',
    	            'Entity_From.title'
    	            
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.loadArtPics = function(val){
        return loadGenericPromise(val,'artPic').then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    service.loadArtNames = function(val){
        return loadGenericPromise(val,'artName').then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    service.loadFamilyNames = function(val){
        return loadGenericPromise(val,'familyName').then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };
    
    service.loadSuppliers = function(val){
        return loadGenericPromise(val,'supplier').then(function(entitySearchResult){
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
.factory('stkArticleLotState',['$rootScope',function($rootScope){

    var service = {
    };

    var activeTabVar="stkSubSection";

    var stkSubSectionActiveVar=true;
    service.stkSubSectionActive=function(stkSubSectionActiveIn){
        if(stkSubSectionActiveIn)stkSubSectionActiveVar=stkSubSectionActiveIn;
        return stkSubSectionActiveVar;
    };

    var stkArticleLotArticleLotActiveVar=false;
    service.stkArticleLotArticleLotActive=function(stkArticleLotArticleLotActiveIn){
        if(stkArticleLotArticleLotActiveIn)stkArticleLotArticleLotActiveVar=stkArticleLotArticleLotActiveIn;
        return stkArticleLotArticleLotActiveVar;
    };

    // A cache of dependents by ptnrNbr
    var depCacheVar = {};

    // The search state.
    // The current list of business partners.
    var stkArticleLotsVar=[];
    service.hasStkArticleLots = function(){
        return stkArticleLotsVar && stkArticleLotsVar.length>0;
    };
    service.stkArticleLots = function(stkArticleLotsIn){
        if(stkArticleLotsIn){
            var length = stkArticleLotsVar.length;
            for	(var index = 0; index < length; index++) {
                stkArticleLotsVar.pop();
            }
            length = stkArticleLotsIn.length;
            for	(var index1 = 0; index1 < length; index1++) {
                stkArticleLotsVar.push(stkArticleLotsIn[index1]);
            }
        }
        return stkArticleLotsVar;
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
        service.stkArticleLots(entitySearchResult.resultList);
        service.totalItems(entitySearchResult.count);
        service.selectedIndex(-1);
        depCacheVar={};
    };

    service.paginate = function(){
        searchInputVar.start = ((currentPageVar - 1)  * itemPerPageVar);
        searchInputVar.max = itemPerPageVar;
        return service.searchInput();
    };

    service.stkArticleLot = function(index){
        if(index>=0 && index<stkArticleLotsVar.length)selectedIndexVar=index;
        if(selectedIndexVar<0 || selectedIndexVar>=stkArticleLotsVar.length) return;
        return stkArticleLotsVar[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(stkArticleLot){
        if(!stkArticleLotsVar || !stkArticleLot) return;
        var current = service.stkArticleLot();
        if(current && current.sectionCode==stkArticleLot.sectionCode){
            stkArticleLotsVar[selectedIndexVar]=stkArticleLot;
        } else {
            for (var index in stkArticleLotsVar) {
                if(stkArticleLotsVar[index].sectionCode==stkArticleLot.sectionCode){
                    stkArticleLotsVar[index]=stkArticleLot;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    // Check if the entity to be displayed is at the correct index.
    service.peek = function(stkArticleLot, index){
        if(!stkArticleLotsVar || !stkArticleLot) return false;
        if(index>=0 && index<stkArticleLotsVar.length){
            selectedIndexVar=index;
            return true;
        }
        return false;
    };

    service.push = function(stkArticleLot){
        if(!stkArticleLotsVar || !stkArticleLot) return false;
        var length = stkArticleLotsVar.push(stkArticleLot);
        selectedIndexVar=length-1;
        return true;
    };

    service.tabSelected=function(tabName){
        if(tabName){
            activeTabVar=tabName;
            stkSubSectionActiveVar= tabName=='stkSubSection';
            stkArticleLotArticleLotActiveVar= tabName=='stkArticleLotArticleLot';
        }
        $rootScope.$broadcast('StkArticleLotsSelected', {tabName:activeTabVar,stkArticleLot:service.stkArticleLot()});
    };

    service.previous = function (){
    	if(stkArticleLotsVar.length<=0) return;

        if(selectedIndexVar<=0){
            selectedIndexVar=stkArticleLotsVar.length-1;
        } else {
            selectedIndexVar-=1;
        }
        return service.stkArticleLot();
    };

    service.next = function(){
    	if(stkArticleLotsVar.length<=0) return;
    	
    	if(selectedIndexVar>=stkArticleLotsVar.length-1 || selectedIndexVar<0){
    		selectedIndexVar=0;
    	} else {
            selectedIndexVar+=1;
    	}

        return service.stkArticleLot();
    };

    return service;

}])
.controller('stkArticleLotsCtlr',['$scope','genericResource','stkArticleLotUtils','stkArticleLotState','$location','$rootScope', 'catalArticleResource',
function($scope,genericResource,stkArticleLotUtils,stkArticleLotState,$location,$rootScope,catalArticleResource){

    $scope.searchInput = stkArticleLotState.searchInput();
    $scope.itemPerPage=stkArticleLotState.itemPerPage;
    $scope.totalItems=stkArticleLotState.totalItems;
    $scope.currentPage=stkArticleLotState.currentPage();
    $scope.maxSize =stkArticleLotState.maxSize;
    $scope.stkArticleLots =stkArticleLotState.stkArticleLots;
    $scope.selectedIndex=stkArticleLotState.selectedIndex;
    $scope.error = "";
    $scope.stkArticleLotUtils=stkArticleLotUtils;
    $scope.stkArticleLotState = stkArticleLotState;


	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		stkArticleLotUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
    	var searchInput = stkArticleLotState.searchInput();
        if(stkArticleLotState.hasStkArticleLots())return;
        findCustom(searchInput);
    }

    function findByLike(searchInput){
		genericResource.findByLike(stkArticleLotUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search in state
			stkArticleLotState.consumeSearchResult(searchInput,entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }
    
    function findCustom(searchInput) {
        genericResource.findCustom(stkArticleLotUtils.urlBase, searchInput)
            .success(function (entitySearchResult) {
                // store search
            	stkArticleLotState.consumeSearchResult(searchInput,entitySearchResult);
            })
            .error(function (error) {
                $scope.error = error;
            });
    }

    /* function findArtNameLike(){
        var listStkArtLots = stkArticleLotState.stkArticleLots();
        var pic;
        for(var k in listStkArtLots){entitySearchResult
            pic = listStkArtLots[k].artPic;

            catalArticleResource.findByIdentif(pic)
                .success(function(data){
                    self.catalArticle = data.features.artName;   // *****************************************
                })
                .error(function(error){
                    self.error = error;
                });

        }
    } */

    function processSearchInput(){
        var fieldNames = [];
        if($scope.searchInput.entity.lotPic && !fieldNames['lotPic'])
        	fieldNames.push('lotPic');
        if($scope.searchInput.entity.artPic && !fieldNames['artPic'])
        	fieldNames.push('artPic');
        if($scope.searchInput.entity.supplierPic && !fieldNames['supplierPic'])
        	fieldNames.push('supplierPic');
        if($scope.searchInput.entity.supplier && !fieldNames['supplier'])
        	fieldNames.push('supplier');
        
        $scope.searchInput.fieldNames = fieldNames;
        return $scope.searchInput;
    };

    $scope.handleSearchRequestEvent = function(){
    	processSearchInput();
    	findCustom($scope.searchInput);
    };

    $scope.paginate = function(){
    	stkArticleLotState.currentPage($scope.currentPage);
        $scope.searchInput = stkArticleLotState.paginate();
    	findByLike($scope.searchInput);
    };

    $scope.handlePrintRequestEvent = function(){		
	};
	
	$scope.show = function(stkArticleLot, index){
		if(stkArticleLotState.peek(stkArticleLot, index)){
			$location.path('/StkArticleLots/show/');
		}
	}

	$scope.edit = function(stkArticleLot, index){
		if(stkArticleLotState.peek(stkArticleLot, index)){
			$location.path('/StkArticleLots/edit/');
		}
	}
}])
.controller('stkArticleLotShowCtlr',['$scope','genericResource','$location','stkArticleLotUtils','stkArticleLotState','$rootScope', '$modal',
                                 function($scope,genericResource,$location,stkArticleLotUtils,stkArticleLotState,$rootScope,$modal){
    $scope.stkArticleLot = stkArticleLotState.stkArticleLot();
    $scope.error = "";
    $scope.stkArticleLotUtils=stkArticleLotUtils;
    $scope.stkSubSectionActive=stkArticleLotState.stkSubSectionActive();
    $scope.stkArticleLotArticleLotActive=stkArticleLotState.stkArticleLotArticleLotActive();
    $scope.entityToEdit = { unitPrice : $scope.stkArticleLot.sppuHT, expirDt : $scope.stkArticleLot.expirDt};
    
    $scope.previous = function (){
        var ent = stkArticleLotState.previous();
        if(ent){
            $scope.stkArticleLot=ent;
            stkArticleLotState.tabSelected();
        }
    }

    $scope.next =  function (){
        var ent = stkArticleLotState.next();
        if(ent){
            $scope.stkArticleLot=ent;
            stkArticleLotState.tabSelected();
        }
    };
    $scope.tabSelected = function(tabName){
    	stkArticleLotState.tabSelected(tabName);
    };

    $scope.edit = function () {
          var modalInstance = $modal.open({
             templateUrl: 'views/StkArticleLot/UpdateModal.html',
             controller: EditController,
             windowClass: 'unit-price-modal',
             scope: $scope,
             resolve: {
            	 entityToEdit: function () {
                     return $scope.entityToEdit;
                 }
             }
         });
     };

     function EditController($scope, $modalInstance, entityToEdit) {
    	 $scope.entityToEdit = entityToEdit;
    	 
         $scope.updateStkArtLot = function () {
        	 $scope.stkArticleLot.sppuHT = $scope.entityToEdit.unitPrice;
        	 $scope.stkArticleLot.expirDt = $scope.entityToEdit.expirDt;
             genericResource.update(stkArticleLotUtils.urlBase, $scope.stkArticleLot)
                 .success(function (stkArticleLot) {
                	 $scope.stkArticleLot = stkArticleLot;
                	 $scope.cancel();
                 })
                 .error(function(data, status){
                     $scope.error= status + " " + data;
                 }); 
         };

         $scope.cancel = function () {
             $modalInstance.dismiss('cancel');
         };
     }

}])
.factory('stkSubSectionsState',['stkArticleLotState',function(stkArticleLotState){
	
	var service = {
	};
	
	service.stkArticleLot = stkArticleLotState.stkArticleLot;
    var searchResultsVar = {};
    service.searchResult = function(){
    	var stkArticleLot = stkArticleLotState.stkArticleLot();
    	if(!stkArticleLot) return;
    	var parentCode = stkArticleLot.sectionCode;
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

	service.stkSubSectionActive= stkArticleLotState.stkSubSectionActive;

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
                                  'stkArticleLotUtils','stkSubSectionsState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 stkArticleLotUtils,stkSubSectionsState,$rootScope){

    $scope.stkSubSections=stkSubSectionsState.stkSubSections;
    $scope.error = "";
    $scope.stkArticleLotUtils=stkArticleLotUtils;
    $scope.itemPerPage=stkSubSectionsState.itemPerPage;
    $scope.totalItems=stkSubSectionsState.totalItems;
    $scope.currentPage=stkSubSectionsState.currentPage();

    var sctnSelectedUnregisterHdl = $rootScope.$on('StkArticleLotsSelected', function(event, data){
        var stkArticleLot = stkSubSectionsState.stkArticleLot();
        if(!stkArticleLot || !data || !data.stkArticleLot || stkArticleLot.sectionCode!=data.stkArticleLot.sectionCode) return;
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
        var stkArticleLot = stkSubSectionsState.stkArticleLot();
        if(!stkArticleLot) return;
        if(!stkSubSectionsState.hasSearchResult(stkArticleLot.sectionCode)) {
        	var searchInput = stkSubSectionsState.searchInput();
        	if(!searchInput){
        		searchInput = stkSubSectionsState.nakedSearchInput();
        		searchInput.entity.parentCode=stkArticleLot.sectionCode;
        		searchInput.fieldNames.push('parentCode');
        	}
        	findBy(searchInput, stkArticleLot.sectionCode);
        }
    }

    function findBy(searchInput){
    	genericResource.findBy(stkArticleLotUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            stkSubSectionsState.consumeSearchResult(entitySearchResult);
        })
    	.error(function(error){
    		$scope.error = error;
    	});
    }

}]);

