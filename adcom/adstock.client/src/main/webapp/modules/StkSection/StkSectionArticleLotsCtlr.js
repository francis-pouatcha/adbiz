'use strict';
    
angular.module('AdStock')

.factory('stkSectionArticleLotsUtils',['sessionManager','$translate','genericResource','$q',
                                      function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adstock.server/rest/stkarticlelot2strgsctns';

    service.language=sessionManager.language;
    
    service.translate = function(){
    	$translate([
    	            'StkSectionArticleLot_artName_description.title',
    	            'StkSectionArticleLot_stockQty_description.title',
    	            'StkSectionArticleLot_stockQtyDt_description.title',
    	            'StkSectionArticleLot_lotQty_description.title',
    	            'StkSectionArticleLot_lotQtyDt_description.title',
    	            'StkSectionArticleLot_salesPrice_description.title',
    	            'StkSectionArticleLot_lotPic_description.title',
    	            'StkSectionArticleLot_artPic_description.title',
    	            'StkSectionArticleLot_sppuTaxIncl_description.title',

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

    service.translate();
    
    return service;
}])
.factory('stkSectionArticleLotsState',['stkSectionState',function(stkSectionState){
	
	var service = {
	};
	
	service.stkSection = stkSectionState.stkSection;
    var searchResultsVar = {};
    service.searchResult = function(){
    	var stkSection = stkSectionState.stkSection();
    	if(!stkSection) return;
    	var strgSection = stkSection.sectionCode;
        if(strgSection && searchResultsVar[strgSection]) 
        	return searchResultsVar[strgSection];
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
    service.stkSectionArticleLot = function(){
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
    	if(sr && sr.searchInput && searchInput.start && searchInput.max){
    		if(searchInput.start<0)searchInput.start=0;
    		return 1 + (searchInput.start/searchInput.max)
    	}
    };

    service.consumeSearchResult = function(entitySearchResult){
    	if(entitySearchResult && 
    			entitySearchResult.searchInput && 
    			entitySearchResult.searchInput.entity &&
    			entitySearchResult.searchInput.entity.strgSection)
    		searchResultsVar[entitySearchResult.searchInput.entity.strgSection] = entitySearchResult;
    };
    
    service.hasSearchResult = function(strgSection){
    	return (searchResultsVar[strgSection] && searchResultsVar[strgSection].resultList);
    };

	service.stkSectionArticleLotActive= stkSectionState.stkSectionArticleLotActive;

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
.controller('stkSectionArticleLotsCtlr',['$scope','genericResource','$modal','$routeParams',
                                  'stkSectionArticleLotsUtils','stkSectionArticleLotsState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 stkSectionArticleLotsUtils,stkSectionArticleLotsState,$rootScope){

    $scope.stkSectionArticleLots=stkSectionArticleLotsState.stkSectionArticleLots;
    $scope.error = "";
    $scope.stkSectionArticleLotsUtils=stkSectionArticleLotsUtils;
    $scope.itemPerPage=stkSectionArticleLotsState.itemPerPage;
    $scope.totalItems=stkSectionArticleLotsState.totalItems;
    $scope.currentPage=stkSectionArticleLotsState.currentPage();

    var sctnSelectedUnregisterHdl = $rootScope.$on('StkSectionsSelected', function(event, data){
        var stkSection = stkSectionArticleLotsState.stkSection();
        if(!stkSection || !data || !data.stkSection || stkSection.sectionCode!=data.stkSection.sectionCode) return;
        loadStkSectionArticleLots();
    });
    $scope.$on('$destroy', function () {
    	sctnSelectedUnregisterHdl();
    });
    $scope.paginate = function(){
        $scope.searchInput = stkSectionArticleLotsState.paginate($scope.currentPage);
    	findBy($scope.searchInput);
    };
    $scope.stkSectionArticleLots = function(){
    	if(stkSectionArticleLotsState.searchResult())
    		return stkSectionArticleLotsState.searchResult().resultList;
    	return [];
    };
    loadStkSectionArticleLots();
    function loadStkSectionArticleLots (){
    	if(!stkSectionArticleLotsState.stkSectionArticleLotActive()) return;
        var stkSection = stkSectionArticleLotsState.stkSection();
        if(!stkSection) return;
        if(!stkSectionArticleLotsState.hasSearchResult(stkSection.sectionCode)) {
        	var searchInput = stkSectionArticleLotsState.searchInput();
        	if(!searchInput){
        		searchInput = stkSectionArticleLotsState.nakedSearchInput();
        		searchInput.entity.strgSection=stkSection.sectionCode;
        		searchInput.fieldNames.push('strgSection');
        	}
        	findBy(searchInput);
        }
    }

    function findBy(searchInput){
    	genericResource.findBy(stkSectionArticleLotsUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            stkSectionArticleLotsState.consumeSearchResult(entitySearchResult);
        })
    	.error(function(error){
    		$scope.error = error;
    	});
    }

}]);

