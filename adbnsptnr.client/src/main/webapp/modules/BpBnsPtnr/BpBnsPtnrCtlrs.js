'use strict';
    
angular.module('AdBnsptnr')

.factory('bpBnsPtnrUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adbnsptnr.server/rest/bpbnsptnrs';
    service.countryNamesUrlBase='/adbnsptnr.server/rest/basecountrynames';
    service.bpctgryofptnrsUrlBase='/adbnsptnr.server/rest/bpctgryofptnrs';
    service.bpptnrctgrydtlssUrlBase='/adbnsptnr.server/rest/bpptnrctgrydtlss';

    service.isIndividual = function(bpBnsPtnr){
    	return bpBnsPtnr.ptnrType=='INDIVIDUAL';
    };
    service.isInstitution = function(bpBnsPtnr){
    	return bpBnsPtnr.ptnrType=='LEGAL';
    };
    
    service.ptnrTypeI18nMsgTitleKey = function(enumKey){
    	return "BpPtnrType_"+enumKey+"_description.title";
    };
    service.ptnrTypeI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.ptnrTypeI18nMsgTitleKey(enumKey)];
    };
    
    service.bpBnsPtnrTypes = [
      {enumKey:'INDIVIDUAL', translKey:'BpPtnrType_INDIVIDUAL_description.title'},
      {enumKey:'LEGAL', translKey:'BpPtnrType_LEGAL_description.title'}
    ];
    
    service.genderI18nMsgTitleKey = function(enumKey){
    	return "BaseGender_"+enumKey+"_description.title";
    };
    service.genderI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.genderI18nMsgTitleKey(enumKey)];
    };

    service.baseGenders = [
      {enumKey:'FEMALE', translKey:'BaseGender_FEMALE_description.title'},
      {enumKey:'MALE', translKey:'BaseGender_MALE_description.title'}
    ];
    
    service.language=sessionManager.language;
    
    service.translate = function(){
    	$translate(['BpPtnrType_INDIVIDUAL_description.title',
    	            'BpPtnrType_LEGAL_description.title',
    	            'BaseGender_FEMALE_description.title',
    	            'BaseGender_MALE_description.title',
    	            'BpBnsPtnr_ptnrNbr_description.title',
    	            'BpBnsPtnr_fullName_description.title',
    	            'BpBnsPtnr_ptnrType_description.title',
    	            'BpBnsPtnr_ctryOfRsdnc_description.title',
    	            'BpIndivPtnrName_firstName_description.title',
    	            'BpIndivPtnrName_lastName_description.title',
    	            'BpIndivPtnrName_gender_description.title',
    	            'BpIndivPtnrName_brthDt_description.title',
    	            'BpLegalPtnrId_cpnyName_description.title',
    	            'BpLegalPtnrId_legalForm_description.title',
    	            'BpLegalPtnrId_equity_description.title',
    	            'BpLegalPtnrId_cmrcRgstrNbr_description.title',
    	            'BpLegalPtnrId_taxPayerIdNbr_description.title',
    	            'BpBnsPtnr_ctryOfRsdnc_required.title',
    	            'BpIndivPtnrName_lastName_required.title',
    	            'BpIndivPtnrName_gender_required.title',
    	            'BpIndivPtnrName_cpnyName_required.title',
    	            'BpBnsPtnr_NoCountryFound_description.title',
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
    	            'BpPtnrContact_description.title',
    	            'BpPtnrIdDtls_description.title',
    	            'BpInsurrance_description.title'
    	            
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.loadCountryNames = function(val){
        return loadCountryNamesPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    function loadCountryNamesPromise(countryName){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        if(countryName){
            searchInput.entity.name = countryName+'%';
            if(searchInput.fieldNames.indexOf('name')==-1)
            	searchInput.fieldNames.push('name');
        }
        var deferred = $q.defer();
        genericResource.findByLike(service.countryNamesUrlBase, searchInput)
		.success(function(entitySearchResult) {
        	deferred.resolve(entitySearchResult);
		})
        .error(function(){
            deferred.reject(service.translations['BpBnsPtnr_NoCountryFound_description.title']);
        });
        return deferred.promise;
    }    

    service.loadInsurances = function(val){
        return loadInsurancesPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    function loadInsurancesPromise(insuranceName){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        if(insuranceName){
            searchInput.entity.fullName = insuranceName+'%';
            searchInput.entity.whenInRole = 'INSURANCE';
            if(searchInput.fieldNames.indexOf('fullName')==-1)
            	searchInput.fieldNames.push('fullName');
            if(searchInput.fieldNames.indexOf('whenInRole')==-1)
            	searchInput.fieldNames.push('whenInRole');
        }
        var deferred = $q.defer();
        genericResource.findByLike(service.bpctgryofptnrsUrlBase, searchInput)
		.success(function(entitySearchResult) {
        	deferred.resolve(entitySearchResult);
		})
        .error(function(error){
            deferred.reject(error);
        });
        return deferred.promise;
    }    
    
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
            if(searchInput.fieldNames.indexOf('name')==-1)
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
.factory('bpBnsPtnrState',['$rootScope','searchResultHandler','dependentTabManager',function($rootScope,searchResultHandler,dependentTabManager){

    var service = {
    };
    service.resultHandler = searchResultHandler.newResultHandler('ptnrNbr');
    service.resultHandler.maxResult=10;
    // TBA FUNCTIONS 
    service.bpPtnrContactTabName = 'bpPtnrContact';
    service.bpPtnrIdDtlsTabName='bpPtnrIdDtls';
    service.bpInsurranceTabName='bpInsurrance';
    service.tabManager = dependentTabManager.newTabManager([service.bpPtnrContactTabName,service.bpPtnrIdDtlsTabName,service.bpInsurranceTabName]);
    service.tabSelected=function(tabName){
    	var activeTabName = service.tabManager.activeTab(tabName);
        $rootScope.$broadcast('BpBnsPtnrsSelected', {tabName:activeTabName,bpBnsPtnr:service.resultHandler.entity()});
    };
    return service;

}])
.controller('bpBnsPtnrsCtlr',['$scope','genericResource','bpBnsPtnrUtils','bpBnsPtnrState','$location','$rootScope',
function($scope,genericResource,bpBnsPtnrUtils,bpBnsPtnrState,$location,$rootScope){

    $scope.searchInput = bpBnsPtnrState.resultHandler.searchInput();
    $scope.itemPerPage=bpBnsPtnrState.resultHandler.itemPerPage;
    $scope.totalItems=bpBnsPtnrState.resultHandler.totalItems;
    $scope.currentPage=bpBnsPtnrState.resultHandler.currentPage();
    $scope.maxSize =bpBnsPtnrState.resultHandler.maxResult;
    $scope.bpBnsPtnrs =bpBnsPtnrState.resultHandler.entities;
    $scope.selectedIndex=bpBnsPtnrState.resultHandler.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
    $scope.show=show;
    $scope.edit=edit;

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		bpBnsPtnrUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(bpBnsPtnrState.resultHandler.hasEntities())return;
        findByLike($scope.searchInput);
    }

    function findByLike(searchInput){
		genericResource.findByLike(bpBnsPtnrUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			bpBnsPtnrState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }

    function processSearchInput(){
        var fieldNames = [];
        if($scope.searchInput.entity.ptnrNbr){
        	fieldNames.push('ptnrNbr') ;
        }
        if($scope.searchInput.entity.fullName){
        	fieldNames.push('fullName') ;
        }
        if($scope.searchInput.entity.ptnrType && $scope.searchInput.entity.ptnrType=='')
        	$scope.searchInput.entity.ptnrType=undefined;
        
        if($scope.searchInput.entity.ptnrType){
        	fieldNames.push('ptnrType') ;
        }
        $scope.searchInput.fieldNames = fieldNames;
        return $scope.searchInput ;
    };

    function  handleSearchRequestEvent(){
    	processSearchInput();
    	findByLike($scope.searchInput);
    };

    function paginate(){
    	bpBnsPtnrState.resultHandler.currentPage($scope.currentPage);
        $scope.searchInput = bpBnsPtnrState.resultHandler.paginate();
    	findByLike($scope.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(bpBnsPtnr, index){
		if(bpBnsPtnrState.resultHandler.selectedObject(bpBnsPtnr)){
			$location.path('/BpBnsPtnrs/show/');
		}
	}

	function edit(bpBnsPtnr, index){
		if(bpBnsPtnrState.resultHandler.selectedObject(bpBnsPtnr)){
			$location.path('/BpBnsPtnrs/edit/');
		}
	}
}])
.controller('bpBnsPtnrCreateCtlr',['$scope','bpBnsPtnrUtils','$translate','genericResource','$location','bpBnsPtnrState',
        function($scope,bpBnsPtnrUtils,$translate,genericResource,$location,bpBnsPtnrState){
    $scope.bpBnsPtnr = bpBnsPtnrState.resultHandler.entity();
    $scope.create = create;
    $scope.error = "";
    $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
    $scope.loadCountryNames=bpBnsPtnrUtils.loadCountryNames;

    function create(){
    	genericResource.create(bpBnsPtnrUtils.urlBase, $scope.bpBnsPtnr)
    	.success(function(bpBnsPtnr){
    		var index = bpBnsPtnrState.resultHandler.push(bpBnsPtnr);
    		if(bpBnsPtnrState.resultHandler.selectedIndex(index)){
    			$location.path('/BpBnsPtnrs/show/');
    		}
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
}])
.controller('bpBnsPtnrEditCtlr',['$scope','genericResource','$location','bpBnsPtnrUtils','bpBnsPtnrState',
                                 function($scope,genericResource,$location,bpBnsPtnrUtils,bpBnsPtnrState){
    $scope.bpBnsPtnr = bpBnsPtnrState.resultHandler.entity();
    $scope.error = "";
    $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
    $scope.loadCountryNames=bpBnsPtnrUtils.loadCountryNames;
    $scope.update = function (){
    	genericResource.update(bpBnsPtnrUtils.urlBase, $scope.bpBnsPtnr)
    	.success(function(bpBnsPtnr){
    		var index = bpBnsPtnrState.resultHandler.replace(bpBnsPtnr);
    		if(index && bpBnsPtnrState.resultHandler.selectedIndex(index)){
    			$location.path('/BpBnsPtnrs/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}])
.controller('bpBnsPtnrShowCtlr',['$scope','genericResource','$location','bpBnsPtnrUtils','bpBnsPtnrState','$rootScope',
                                 function($scope,genericResource,$location,bpBnsPtnrUtils,bpBnsPtnrState,$rootScope){
    $scope.bpBnsPtnr = bpBnsPtnrState.resultHandler.entity();
    $scope.error = "";
    $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
    $scope.bpPtnrContactTabName = bpBnsPtnrState.bpPtnrContactTabName;
    $scope.bpPtnrIdDtlsTabName=bpBnsPtnrState.bpPtnrIdDtlsTabName;
    $scope.bpInsurranceTabName=bpBnsPtnrState.bpInsurranceTabName;
    $scope.bpPtnrContactActive=bpBnsPtnrState.tabManager.isActive($scope.bpPtnrContactTabName);
    $scope.bpPtnrIdDtlsActive=bpBnsPtnrState.tabManager.isActive($scope.bpPtnrIdDtlsTabName);
    $scope.bpInsurranceActive=bpBnsPtnrState.tabManager.isActive($scope.bpInsurranceTabName);
    
    $scope.previous = function (){
        var bp = bpBnsPtnrState.resultHandler.previous();
        if(bp){
            $scope.bpBnsPtnr=bp;
            bpBnsPtnrState.tabSelected();
        }
    }

    $scope.next =  function (){
        var bp = bpBnsPtnrState.resultHandler.next();
        if(bp){
            $scope.bpBnsPtnr=bp;
            bpBnsPtnrState.tabSelected();
        }
    };
    $scope.tabSelected = function(tabName){
    	bpBnsPtnrState.tabSelected(tabName);
    };
    $scope.edit =function(){
        $location.path('/BpBnsPtnrs/edit/');
    };

}]);
