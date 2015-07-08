'use strict';
    
angular.module('AdStock')

.factory('stkMvntUtils',['sessionManager','$translate', 'commonTranslations','$filter', 'adUtils','genericResource','$q',function(sessionManager,$translate,commonTranslations,$filter,adUtils,genericResource,$q){
    var service = {};

    service.urlBase='/adstock.server/rest/stkmvnts';
    service.loginnamessUrlBase='/adbase.server/rest/loginnamess';
    service.artNamesUrlBase='/adcatal.server/rest/catalarticles';
    
    
    service.formatDate= function(fieldName, inPattern){
        return adUtils.formatDate(fieldName, inPattern);
    }
    
    service.stkMvntTypeI18nMsgTitleKey = function(enumKey){
    	return "StkMvtType_"+enumKey+"_description.title";
    };
    
    service.stkMvntTypeI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.stkMvntTypeI18nMsgTitleKey(enumKey)];
    };
    
    service.stkMvntTypes = [
      {enumKey:'IN', translKey:'StkMvtType_IN_description.title'},
      {enumKey:'OUT', translKey:'StkMvtType_OUT_description.title'},
    ];
    
    service.language=sessionManager.language;
    
    service.translate = function(){
    	$translate([
                    'StkMvnt_artPic_description.text',
                    'StkMvnt_artPic_description.title',
                    'StkMvnt_description.text',
                    'StkMvnt_description.title',
                    'StkMvnts_description.text',
                    'StkMvnts_description.title',
                    'StkMvnt_artName_description.title',
                    'StkMvnt_lotPic_description.text',
                    'StkMvnt_lotPic_description.title',
                    'StkMvnt_movedQty_description.text',
                    'StkMvnt_movedQty_description.title',
                    'StkMvnt_mvgUser_description.text',
                    'StkMvnt_mvgUser_description.title',
                    'StkMvnt_mvntDest_description.text',
                    'StkMvnt_mvntDest_description.title',
                    'StkMvnt_mvntDt_description.text',
                    'StkMvnt_mvntDt_description.title',
                    'StkMvnt_mvntOrigin_description.text',
                    'StkMvnt_mvntOrigin_description.title',
                    'StkMvnt_mvntType_description.text',
                    'StkMvnt_mvntType_description.title',
                    'StkMvnt_origDocNbrs_description.text',
                    'StkMvnt_origDocNbrs_description.title',
                    'StkMvnt_mvntDtFrom_description.title',
                    'StkMvnt_mvntDtTo_description.title',
                
                    'StkMvtType_IN_description.text',
                    'StkMvtType_IN_description.title',
                    'StkMvtType_OUT_description.text',
                    'StkMvtType_OUT_description.title',
                    'StkMvtType_description.text',
                    'StkMvtType_description.title',
                    
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
                    'Entity_first.title',
                    'Entity_last.title'
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.loadUsers = function(loginName){
        return genericResource.findByLikePromissed(service.loginnamessUrlBase, 'loginName', loginName)
        .then(function(entitySearchResult){
            if(!angular.isUndefined(entitySearchResult))
            return entitySearchResult.resultList;
            else return "No user";
        });
    };

    service.loadUsersByName = function(fullName){
        return genericResource.findByLikePromissed(service.loginnamessUrlBase, 'fullName', fullName)
        .then(function(entitySearchResult){
            if(!angular.isUndefined(entitySearchResult))
            return entitySearchResult.resultList;
            else return "";
        });
    };
    
    service.loadArticlesByname = function(article){
       var searchInput={
	    		entity:{},
	    		start:0, 
                max:10,
	    		fieldNames:[]
	    	};
        searchInput.entity.features.artName = article.features.artName;
        return genericResource.findCustom(service.artNamesUrlBase, searchInput)
        .then(function(entitySearchResult){
            if(!angular.isUndefined(entitySearchResult))
            return entitySearchResult.resultList;
            else return "No article";
        });
    };
    
    service.translate();
    
    return service;
}])
.factory('stkMvntState',['$rootScope','searchResultHandler','dependentTabManager',function($rootScope,searchResultHandler,dependentTabManager){

    var service = {};
    service.resultHandler = searchResultHandler.newResultHandler('lotPic');
    return service;

}])
.controller('stkMvntsCtlr',['$scope','genericResource','stkMvntUtils','stkMvntState','$location' ,'$translate','$filter','$rootScope',
function($scope,genericResource,stkMvntUtils,stkMvntState,$location,$translate,$filter,$rootScope){


    $scope.searchInput = stkMvntState.resultHandler.searchInput();
    $scope.itemPerPage=stkMvntState.resultHandler.itemPerPage;
    $scope.totalItems=stkMvntState.resultHandler.totalItems;
    $scope.currentPage=stkMvntState.resultHandler.currentPage();
    $scope.maxSize =stkMvntState.resultHandler.maxResult;
    $scope.stkMvnts =stkMvntState.resultHandler.entities;
    $scope.selectedIndex=stkMvntState.resultHandler.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.stkMvntUtils=stkMvntUtils;
    $scope.show=show;


	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		stkMvntUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(stkMvntState.resultHandler.hasEntities())return;
        findByLike($scope.searchInput); 
    }

    function findByLike(searchInput){
		genericResource.findByLike(stkMvntUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			stkMvntState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }
    

    function handleSearchRequestEvent(){
    	processSearchInput();
    	findCustom($scope.searchInput);
    };
    
    
    function findCustom(searchInput){
        genericResource.findCustom(stkMvntUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			stkMvntState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    };
    
    function processSearchInput(){
        var fieldNames = [];
        if($scope.searchInput.entity.lotPic){
            $scope.searchInput.entity.lotPic= $scope.searchInput.entity.lotPic;
        	fieldNames.push('lotPic');
        }
        if($scope.searchInput.entity.artPic){
            $scope.searchInput.entity.artPic= $scope.searchInput.entity.artPic;
        	fieldNames.push('artPic');
        }
        if($scope.searchInput.entity.mvntType){
            $scope.searchInput.entity.mvntType= $scope.searchInput.entity.mvntType;
        	fieldNames.push('mvntType');
        }
        if($scope.searchParam.acsngUser){
            $scope.searchInput.entity.mvgUser = $scope.searchParam.mvgUser.loginName;
            fieldNames.push('mvgUser');
        }
        if($scope.searchInput.mvntDtFrom){
            $scope.searchInput.mvntDtFrom = $scope.searchInput.mvntDtFrom;
        }
        if($scope.searchInput.mvntDtTo){
            $scope.searchInput.mvntDtTo = $scope.searchInput.mvntDtTo;
        }
        if($scope.searchInput.artName){
            $scope.searchInput.artName= $scope.searchParam.article.features.artName;
        }
        $scope.searchInput.fieldNames = fieldNames;
        return $scope.searchInput ;
      };

    function paginate(){
    	stkMvntState.resultHandler.currentPage($scope.currentPage);
        $scope.searchInput = stkMvntState.resultHandler.paginate();
    	findByLike($scope.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(stkMvnt, index){
		if(stkMvntState.resultHandler.selectedObject(stkMvnt) != -1){
			$location.path('/StkMvnts/show/');
		}
	}
}])
.controller('stkMvntShowCtlr',['$scope','genericResource','$location','stkMvntUtils','stkMvntState', '$filter','$rootScope', function($scope,genericResource,$location,stkMvntUtils,stkMvntState,$filter,$rootScope){
    $scope.stkMvnt = stkMvntState.resultHandler.entity();
    $scope.itemPerPage=stkMvntState.resultHandler.itemPerPage;
    $scope.currentPage=stkMvntState.resultHandler.currentPage();
    $scope.maxSize =stkMvntState.resultHandler.maxResult;
    $scope.error = "";
    $scope.stkMvntUtils=stkMvntUtils;
    


}]);
