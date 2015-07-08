'use strict';
    
angular.module('AdBnsptnr')

.factory('bpPtnrCtgryUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adbnsptnr.server/rest/bpptnrctgrys';
    service.bpptnrctgrydtlssUrlBase='/adbnsptnr.server/rest/bpptnrctgrydtlss';

    service.language=sessionManager.language;

    service.ptnrRole18nMsgTitleKey = function(enumKey){
    	return "BpPtnrRole_"+enumKey+"_description.title";
    };
    service.ptnrRole18nMsgTitleValue = function(enumKey){
    	return service.translations[service.ptnrRole18nMsgTitleKey(enumKey)];
    };
    
    service.ptnrRoles = [
      {enumKey:'CUSTOMER', translKey:'BpPtnrRole_CUSTOMER_description.title'},
      {enumKey:'SUPPLIER', translKey:'BpPtnrRole_SUPPLIER_description.title'},
      {enumKey:'EMPLOYER', translKey:'BpPtnrRole_EMPLOYER_description.title'},
      {enumKey:'STAFF', translKey:'BpPtnrRole_STAFF_description.title'},
      {enumKey:'MANUFACTURER', translKey:'BpPtnrRole_MANUFACTURER_description.title'},
      {enumKey:'GOVERNMENT', translKey:'BpPtnrRole_GOVERNMENT_description.title'},
      {enumKey:'BROKER', translKey:'BpPtnrRole_BROKER_description.title'},
      {enumKey:'SHAREHOLDER', translKey:'BpPtnrRole_SHAREHOLDER_description.title'},
      {enumKey:'BANKER', translKey:'BpPtnrRole_BANKER_description.title'},
      {enumKey:'INSURANCE', translKey:'BpPtnrRole_INSURANCE_description.title'}
    ];
    
    service.translate = function(){
    	$translate(['BpPtnrRole_CUSTOMER_description.title',
    	            'BpPtnrRole_SUPPLIER_description.title',
    	            'BpPtnrRole_EMPLOYER_description.title',
    	            'BpPtnrRole_STAFF_description.title',
    	            'BpPtnrRole_MANUFACTURER_description.title',
    	            'BpPtnrRole_GOVERNMENT_description.title',
    	            'BpPtnrRole_BROKER_description.title',
    	            'BpPtnrRole_SHAREHOLDER_description.title',
    	            'BpPtnrRole_BANKER_description.title',
    	            'BpPtnrRole_INSURANCE_description.title',
    	            
    	            'BpPtnrCtgry_ctgryCode_description.title',
    	            'BpPtnrCtgryDtls_name_description.title',
    	            'BpPtnrCtgry_ptnrRole_description.title',
    	            'BpPtnrCtgry_parentCode_description.title',
    	            'BpPtnrCtgryDtls_description.title',
    	            'BpPtnrContract_description.title',
    	            'BpPtnrCtgryDtls_description_description.title',

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
    	            'Entity_type_to_select.title'
    	            
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };

    service.loadCtgryDtls = function(val){
        return loadCtgryDtlsPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    function loadCtgryDtlsPromise(ctgryName){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        if(ctgryName){
            searchInput.entity.name = ctgryName+'%';
            searchInput.fieldNames.push('name');
        }
        var deferred = $q.defer();
        genericResource.findByLike(service.bpptnrctgrydtlssUrlBase, searchInput)
		.success(function(entitySearchResult) {
        	deferred.resolve(entitySearchResult);
		})
        .error(function(error){
            deferred.reject(error);
        });
        return deferred.promise;
    }    
    
    service.translate();
    
    return service;
}])
.factory('bpPtnrCtgryState',['$rootScope',function($rootScope){

    var service = {
    };
    var activeTabVar="bpPtnrCtgryDtls";

    var bpPtnrCtgryDtlsActiveVar=true;
    service.bpPtnrCtgryDtlsActive=function(bpPtnrCtgryDtlsActiveIn){
        if(bpPtnrCtgryDtlsActiveIn)bpPtnrCtgryDtlsActiveVar=bpPtnrCtgryDtlsActiveIn;
        return bpPtnrCtgryDtlsActiveVar;
    };

    var bpCtgryContractActiveVar=false;
    service.bpCtgryContractActive=function(bpCtgryContractActiveIn){
        if(bpCtgryContractActiveIn)bpCtgryContractActiveVar=bpCtgryContractActiveIn;
        return bpCtgryContractActiveVar;
    };

    // The search state.
    // The current list of business partners.
    var bpPtnrCtgrysVar=[];
    service.hasBnsPtnrs = function(){
        return bpPtnrCtgrysVar && bpPtnrCtgrysVar.length>0;
    };
    service.bpPtnrCtgrys = function(bpPtnrCtgrysIn){
        if(bpPtnrCtgrysIn){
            var length = bpPtnrCtgrysVar.length;
            for	(var index = 0; index < length; index++) {
                bpPtnrCtgrysVar.pop();
            }
            length = bpPtnrCtgrysIn.length;
            for	(var index1 = 0; index1 < length; index1++) {
                bpPtnrCtgrysVar.push(bpPtnrCtgrysIn[index1]);
            }
        }
        return bpPtnrCtgrysVar;
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

    var itemPerPageVar = 25;
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

    //
    service.consumeSearchResult = function(searchInput, entitySearchResult) {
        // store search
        service.searchInput(searchInput);
        // Store result
        service.bpPtnrCtgrys(entitySearchResult.resultList);
        service.totalItems(entitySearchResult.count);
        service.selectedIndex(-1);
    };

    service.paginate = function(){
        searchInputVar.start = ((currentPageVar - 1)  * itemPerPageVar);
        searchInputVar.max = itemPerPageVar;
        return service.searchInput();
    };


    // returns sets and returns the business partner at the passed index or
    // if not set the business partner at the currently selected index.
    service.bpPtnrCtgry = function(index){
        if(index>=0 && index<bpPtnrCtgrysVar.length)selectedIndexVar=index;
        if(selectedIndexVar<0 || selectedIndexVar>=bpPtnrCtgrysVar.length) return;
        return bpPtnrCtgrysVar[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(bpPtnrCtgry){
        if(!bpPtnrCtgrysVar || !bpPtnrCtgry) return;
        var currentCtgry = service.bpPtnrCtgry();
        if(currentCtgry && currentCtgry.ctgryCode==bpPtnrCtgry.ctgryCode){
            bpPtnrCtgrysVar[selectedIndexVar]=bpPtnrCtgry;
        } else {
            for (var index in bpPtnrCtgrysVar) {
                if(bpPtnrCtgrysVar[index].ctgryCode==bpPtnrCtgry.ctgryCode){
                    bpPtnrCtgrysVar[index]=bpPtnrCtgry;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    // CHeck if the business partner to be displayed is at the correct index.
    service.peek = function(bpPtnrCtgry, index){
        if(!bpPtnrCtgrysVar || !bpPtnrCtgry) return false;
        if(index>=0 && index<bpPtnrCtgrysVar.length){
            selectedIndexVar=index;
            return true;
        }
        return false;
    };

    service.push = function(bpPtnrCtgry){
        if(!bpPtnrCtgrysVar || !bpPtnrCtgry) return false;
        var length = bpPtnrCtgrysVar.push(bpPtnrCtgry);
        selectedIndexVar=length-1;
        return true;
    };

    service.tabSelected=function(tabName){
        if(tabName){
            activeTabVar=tabName;
            bpPtnrCtgryDtlsActiveVar= tabName=='bpPtnrCtgryDtls';
            bpCtgryContractActiveVar= tabName=='bpCtgryContract';
        }
        $rootScope.$broadcast('BpPtnrCtgrysSelected', {tabName:activeTabVar,bpPtnrCtgry:service.bpPtnrCtgry()});
    };

    service.previous = function (){
    	if(bpPtnrCtgrysVar.length<=0) return;

        if(selectedIndexVar<=0){
            selectedIndexVar=bpPtnrCtgrysVar.length-1;
        } else {
            selectedIndexVar-=1;
        }
        return service.bpPtnrCtgry();
    };

    service.next = function(){
    	if(bpPtnrCtgrysVar.length<=0) return;
    	
    	if(selectedIndexVar>=bpPtnrCtgrysVar.length-1 || selectedIndexVar<0){
    		selectedIndexVar=0;
    	} else {
            selectedIndexVar+=1;
    	}

        return service.bpPtnrCtgry();
    };

    return service;

}])
.controller('bpPtnrCtgrysCtlr',['$scope','genericResource','bpPtnrCtgryUtils','bpPtnrCtgryState','$location','$rootScope',
function($scope,genericResource,bpPtnrCtgryUtils,bpPtnrCtgryState,$location,$rootScope){

    $scope.searchInput = bpPtnrCtgryState.searchInput();
    $scope.itemPerPage=bpPtnrCtgryState.itemPerPage;
    $scope.totalItems=bpPtnrCtgryState.totalItems;
    $scope.currentPage=bpPtnrCtgryState.currentPage();
    $scope.maxSize =bpPtnrCtgryState.maxSize;
    $scope.bpPtnrCtgrys =bpPtnrCtgryState.bpPtnrCtgrys;
    $scope.selectedIndex=bpPtnrCtgryState.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.bpPtnrCtgryUtils=bpPtnrCtgryUtils;
    $scope.show=show;
    $scope.edit=edit;

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		bpPtnrCtgryUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(bpPtnrCtgryState.hasBnsPtnrs())return;
        findByLike($scope.searchInput);
    }

    function findByLike(searchInput){
		genericResource.findByLike(bpPtnrCtgryUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			bpPtnrCtgryState.consumeSearchResult(searchInput,entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }

    function processSearchInput(){
        var fieldNames = [];
        if($scope.searchInput.entity.ctgryCode){
        	fieldNames.push('ctgryCode') ;
        }
        if($scope.searchInput.entity.ptnrRole){
        	if($scope.searchInput.entity.ptnrRole==''){
        		$scope.searchInput.entity.ptnrRole=undefined;
        	} else {
            	fieldNames.push('ptnrRole') ;
        	}
        }

        $scope.searchInput.fieldNames = fieldNames;
        return $scope.searchInput ;
    };

    function  handleSearchRequestEvent(){
    	processSearchInput();
    	findByLike($scope.searchInput);
    };

    function paginate(){
        $scope.searchInput = bpPtnrCtgryState.paginate();
    	findByLike($scope.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(bpPtnrCtgry, index){
		if(bpPtnrCtgryState.peek(bpPtnrCtgry, index)){
			$location.path('/BpPtnrCtgrys/show/');
		}
	}

	function edit(bpPtnrCtgry, index){
		if(bpPtnrCtgryState.peek(bpPtnrCtgry, index)){
			$location.path('/BpPtnrCtgrys/edit/');
		}
	}
}])
.controller('bpPtnrCtgryCreateCtlr',['$scope','bpPtnrCtgryUtils','$translate','genericResource','$location','bpPtnrCtgryState',
        function($scope,bpPtnrCtgryUtils,$translate,genericResource,$location,bpPtnrCtgryState){
    $scope.bpPtnrCtgry = bpPtnrCtgryState.bpPtnrCtgry();
    $scope.create = create;
    $scope.error = "";
    $scope.bpPtnrCtgryUtils=bpPtnrCtgryUtils;
    $scope.loadCtgryDtls=bpPtnrCtgryUtils.loadCtgryDtls;

    function create(){
    	genericResource.create(bpPtnrCtgryUtils.urlBase, $scope.bpPtnrCtgry)
    	.success(function(bpPtnrCtgry){
    		if(bpPtnrCtgryState.push(bpPtnrCtgry)){
    			$location.path('/BpPtnrCtgrys/show/');
    		}
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
}])
.controller('bpPtnrCtgryEditCtlr',['$scope','genericResource','$location','bpPtnrCtgryUtils','bpPtnrCtgryState',
                                 function($scope,genericResource,$location,bpPtnrCtgryUtils,bpPtnrCtgryState){
    $scope.bpPtnrCtgry = bpPtnrCtgryState.bpPtnrCtgry();
    $scope.error = "";
    $scope.bpPtnrCtgryUtils=bpPtnrCtgryUtils;
    $scope.update = function (){
    	genericResource.update(bpPtnrCtgryUtils.urlBase, $scope.bpPtnrCtgry)
    	.success(function(bpPtnrCtgry){
    		if(bpPtnrCtgryState.replace(bpPtnrCtgry)){
    			$location.path('/BpPtnrCtgrys/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}])
.controller('bpPtnrCtgryShowCtlr',['$scope','genericResource','$location','bpPtnrCtgryUtils','bpPtnrCtgryState','$rootScope',
                                 function($scope,genericResource,$location,bpPtnrCtgryUtils,bpPtnrCtgryState,$rootScope){
    $scope.bpPtnrCtgry = bpPtnrCtgryState.bpPtnrCtgry();
    $scope.error = "";
    $scope.bpPtnrCtgryUtils=bpPtnrCtgryUtils;
    $scope.bpPtnrCtgryDtlsActive=bpPtnrCtgryState.bpPtnrCtgryDtlsActive();
    $scope.bpCtgryContractActive=bpPtnrCtgryState.bpCtgryContractActive();
    
    $scope.previous = function (){
        var bp = bpPtnrCtgryState.previous();
        if(bp){
            $scope.bpPtnrCtgry=bp;
            bpPtnrCtgryState.tabSelected();
        }
    }

    $scope.next =  function (){
        var bp = bpPtnrCtgryState.next();
        if(bp){
            $scope.bpPtnrCtgry=bp;
            bpPtnrCtgryState.tabSelected();
        }
    };
    $scope.tabSelected = function(tabName){
    	bpPtnrCtgryState.tabSelected(tabName);
    };
    $scope.edit =function(){
        $location.path('/BpPtnrCtgrys/edit/');
    };

}]);
