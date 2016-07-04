'use strict';
    
angular.module('AdCshdwr')

.factory('cdrPaymntManagerResource',['$http', function($http){
    var service = {};
    var urlBase = '/adinvtry.server/rest/inventory';
    service.update = function(invtryHolder){
        return $http.put(urlBase+'/update',invtryHolder);
    };
    service.close = function(invtryHolder){
        return $http.put(urlBase+'/close',invtryHolder);
    };
    return service;
}])
.factory('cdrUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adinvtry.server/rest/cdpaymnts';
    service.cdrpaymntsUrlBase='/adinvtry.server/rest/cdrpaymntitems';
    service.stksectionsUrlBase='/adstock.server/rest/stksections';
    service.stkarticlelotsUrlBase='/adstock.server/rest/stkarticlelots';
    service.catalarticlesUrlBase='/adcatal.server/rest/catalarticles';
    service.loginnamessUrlBase='/adbase.server/rest/loginnamess';
    service.stkarticlelot2strgsctnsUrlBase='/adstock.server/rest/stkarticlelot2strgsctns';

    service.language=sessionManager.language;
    
    service.currentWsUser=sessionManager.userWsData();
    
    service.translate = function(){
    	$translate([
                    'CdrCstmrVchr_amtUsed_description.text',
                    'CdrCstmrVchr_amtUsed_description.title',
                    'CdrCstmrVchr_amt_description.text',
                    'CdrCstmrVchr_amt_description.title',
                    'CdrCstmrVchr_canceled_description.text',
                    'CdrCstmrVchr_canceled_description.title',
                    'CdrCstmrVchr_cashier_description.text',
                    'CdrCstmrVchr_cashier_description.title',
                    'CdrCstmrVchr_cdrNbr_description.text',
                    'CdrCstmrVchr_cdrNbr_description.title',
                    'CdrCstmrVchr_cstmrName_description.text',
                    'CdrCstmrVchr_cstmrName_description.title',
                    'CdrCstmrVchr_cstmrNbr_description.text',
                    'CdrCstmrVchr_cstmrNbr_description.title',
                    'CdrCstmrVchr_description.text',
                    'CdrCstmrVchr_description.title',
                    'CdrCstmrVchr_dsNbr_description.text',
                    'CdrCstmrVchr_dsNbr_description.title',
                    'CdrCstmrVchr_prntDt_description.text',
                    'CdrCstmrVchr_prntDt_description.title',
                    'CdrCstmrVchr_restAmt_description.text',
                    'CdrCstmrVchr_restAmt_description.title',
                    'CdrCstmrVchr_settled_description.text',
                    'CdrCstmrVchr_settled_description.title',
                    'CdrCstmrVchr_vchrNbr_description.text',
                    'CdrCstmrVchr_vchrNbr_description.title',

                    'CdrDsPymntItem_amt_description.text',
                    'CdrDsPymntItem_amt_description.title',
                    'CdrDsPymntItem_description.text',
                    'CdrDsPymntItem_description.title',
                    'CdrDsPymntItem_diffAmt_description.text',
                    'CdrDsPymntItem_diffAmt_description.title',
                    'CdrDsPymntItem_dsNbr_description.text',
                    'CdrDsPymntItem_dsNbr_description.title',
                    'CdrDsPymntItem_pymntDocNbr_description.text',
                    'CdrDsPymntItem_pymntDocNbr_description.title',
                    'CdrDsPymntItem_pymntDocType_description.text',
                    'CdrDsPymntItem_pymntDocType_description.title',
                    'CdrDsPymntItem_pymntMode_description.text',
                    'CdrDsPymntItem_pymntMode_description.title',
                    'CdrDsPymntItem_rcvdAmt_description.text',
                    'CdrDsPymntItem_rcvdAmt_description.title',

                    'CdrPymnt_amt_description.text',
                    'CdrPymnt_amt_description.title',
                    'CdrPymnt_cashier_description.text',
                    'CdrPymnt_cashier_description.title',
                    'CdrPymnt_cdrNbr_description.text',
                    'CdrPymnt_cdrNbr_description.title',
                    'CdrPymnt_description.text',
                    'CdrPymnt_description.title',
                    'CdrPymnt_paidBy_description.text',
                    'CdrPymnt_paidBy_description.title',
                    'CdrPymnt_pymntDt_description.text',
                    'CdrPymnt_pymntDt_description.title',
                    'CdrPymnt_pymntNbr_description.text',
                    'CdrPymnt_pymntNbr_description.title',
                    'CdrPymnt_rcptNbr_description.text',
                    'CdrPymnt_rcptNbr_description.title',
                    'CdrPymnt_rcptPrntDt_description.text',
                    'CdrPymnt_rcptPrntDt_description.title',
                    'CdrPymnt_valueDt_description.text',
                    'CdrPymnt_valueDt_description.title',

                    'CdrPymntItem_amt_description.text',
                    'CdrPymntItem_amt_description.title',
                    'CdrPymntItem_description.text',
                    'CdrPymntItem_description.title',
                    'CdrPymntItem_diffAmt_description.text',
                    'CdrPymntItem_diffAmt_description.title',
                    'CdrPymntItem_pymntDocNbr_description.text',
                    'CdrPymntItem_pymntDocNbr_description.title',
                    'CdrPymntItem_pymntDocType_description.text',
                    'CdrPymntItem_pymntDocType_description.title',
                    'CdrPymntItem_pymntMode_description.text',
                    'CdrPymntItem_pymntMode_description.title',
                    'CdrPymntItem_pymntNbr_description.text',
                    'CdrPymntItem_pymntNbr_description.title',
                    'CdrPymntItem_rcvdAmt_description.text',
                    'CdrPymntItem_rcvdAmt_description.title',

                    'CdrPymntMode_CASH_description.text',
                    'CdrPymntMode_CASH_description.title',
                    'CdrPymntMode_CHECK_description.text',
                    'CdrPymntMode_CHECK_description.title',
                    'CdrPymntMode_CREDIT_CARD_description.text',
                    'CdrPymntMode_CREDIT_CARD_description.title',
                    'CdrPymntMode_VOUCHER_description.text',
                    'CdrPymntMode_VOUCHER_description.title',
                    'CdrPymntMode_description.text',
                    'CdrPymntMode_description.title',

                    'CdrPymntObject_amt_description.text',
                    'CdrPymntObject_amt_description.title',
                    'CdrPymntObject_description.text',
                    'CdrPymntObject_description.title',
                    'CdrPymntObject_orgUnit_description.text',
                    'CdrPymntObject_orgUnit_description.title',
                    'CdrPymntObject_origDocNbr_description.text',
                    'CdrPymntObject_origDocNbr_description.title',
                    'CdrPymntObject_origDocType_description.text',
                    'CdrPymntObject_origDocType_description.title',
                    'CdrPymntObject_origItemNbr_description.text',
                    'CdrPymntObject_origItemNbr_description.title',
                    'CdrPymntObject_pymntNbr_description.text',
                    'CdrPymntObject_pymntNbr_description.title',

                    'CdrPymtHstry_cdrNbr_description.text',
                    'CdrPymtHstry_cdrNbr_description.title',
                    'CdrPymtHstry_description.text',
                    'CdrPymtHstry_description.title',
                    'CdrPymtHstry_pymntNbr_description.text',
                    'CdrPymtHstry_pymntNbr_description.title',

                    'CdrVchrHstry_cdrNbr_description.text',
                    'CdrVchrHstry_cdrNbr_description.title',
                    'CdrVchrHstry_description.text',
                    'CdrVchrHstry_description.title',
                    'CdrVchrHstry_vchrNbr_description.text',
                    'CdrVchrHstry_vchrNbr_description.title',
            
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
    	            'Entity_By.title',
    	            'Entity_saveleave.title',
    	            'Entity_add.title'
    	            
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });
    };
    
    service.translate();
    return service;
}])
.factory('cdrState',['$rootScope', '$q',function($rootScope,$q){

    var service = {
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
        service.invInvtrys(entitySearchResult.resultList);
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
    service.invInvtry = function(index){
        if(index && index>=0 && index<invInvtrysVar.length)selectedIndexVar=index;
        if(selectedIndexVar<0 || selectedIndexVar>=invInvtrysVar.length) return;
        return invInvtrysVar[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(invInvtry){
        if(!invInvtrysVar || !invInvtry) return;
        var currentInvt = service.invInvtry();
        if(currentInvt && currentInvt.invtryNbr==invInvtry.invtryNbr){
            invInvtrysVar[selectedIndexVar]=invInvtry;
        } else {
            for (var index in invInvtrysVar) {
                if(invInvtrysVar[index].invtryNbr==invInvtry.invtryNbr){
                    invInvtrysVar[index]=invInvtry;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };
    service.set = function(invInvtry){
        if(!invInvtrysVar || !invInvtry) return false;
        invInvtrysVar[selectedIndexVar]=invInvtry;
        return true;
    };

    // CHeck if the business partner to be displayed is at the correct index.
    service.peek = function(invInvtry, index){
        if(!invInvtrysVar || !invInvtry) return false;
        if(index>=0 && index<invInvtrysVar.length){
            selectedIndexVar=index;
            return true;
        }
        return false;
    };

    service.push = function(invInvtry){
        if(!invInvtrysVar || !invInvtry) return false;
        var length = invInvtrysVar.push(invInvtry);
        selectedIndexVar=length-1;
        return true;
    };

    service.previous = function (){
    	if(invInvtrysVar.length<=0) return;

        if(selectedIndexVar<=0){
            selectedIndexVar=invInvtrysVar.length-1;
        } else {
            selectedIndexVar-=1;
        }
        return service.invInvtry();
    };

    service.next = function(){
    	if(invInvtrysVar.length<=0) return;
    	
    	if(selectedIndexVar>=invInvtrysVar.length-1 || selectedIndexVar<0){
    		selectedIndexVar=0;
    	} else {
            selectedIndexVar+=1;
    	}

        return service.invInvtry();
    };
    
    service.cdrPaymnt = function () {
        
    }
    return service;

}])
.controller('cdrPaymntsCtlr',['$scope','genericResource','cdrUtils','cdrState','$location','$rootScope',
function($scope,genericResource,cdrUtils,cdrState,$location,$rootScope){

    $scope.searchInput = cdrState.searchInput();
    $scope.itemPerPage=cdrState.itemPerPage;
    $scope.totalItems=cdrState.totalItems;
    $scope.currentPage=cdrState.currentPage();
    $scope.maxSize =cdrState.maxSize;
    $scope.cdrPaymnts =cdrState.cdrPaymnts;
    $scope.selectedIndex=cdrState.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.cdrUtils=cdrUtils;
    $scope.show=show;
    $scope.edit=edit;
    $scope.cur = "XAF";

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		cdrUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function  handleSearchRequestEvent(){
    	if($scope.searchInput.acsngUser){
    		$scope.searchInput.entity.acsngUser=$scope.searchInput.acsngUser.loginName;
    	} else {
    		$scope.searchInput.entity.acsngUser='';
    	}
    	findCustom($scope.searchInput);
    }
    function handlePrintRequestEvent(){
        // To do
	}
    
    function paginate(){
        $scope.searchInput = invInvtryState.paginate();
        findCustom($scope.searchInput);
    };

    function paginate(){
        $scope.searchInput = invInvtryState.paginate();
        findCustom($scope.searchInput);
    }
    
    function init(){
    }
    
	function show(cdrPymt, index){
		
	}

	function edit(cdrPymt, index){
		
	}

}])
.controller('cdrPaymntCreateCtlr',['$scope','cdrUtils','$translate','genericResource','$location','cdrState',
        function($scope,cdrUtils,$translate,genericResource,$location,cdrState){
    $scope.cdrPaymnt = cdrState.cdrPaymnt();
    $scope.create = create;
    $scope.error = "";
    $scope.stkSection = "";
    $scope.startRange = "";
    $scope.endRange = "";
    $scope.cdrUtils=cdrUtils;

    function create(){
    };

  
}])
.controller('cdrPaymntEditCtlr',['$scope','genericResource','$location','cdrUtils','cdrState',
                                 function($scope,genericResource,$location,cdrUtils,cdrState){
    $scope.cdrPaymnt = cdrState.cdrPaymnt();
    $scope.error = "";
    $scope.cdrUtils=cdrUtils;
    $scope.update = function (){
    	genericResource.update(cdrUtils.urlBase, $scope.cdrPaymnt)
    	.success(function(cdrPaymnt){
    		if(cdrState.replace(cdrPaymnt)){
    			$location.path('/CdrPaymnts/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}])
.controller('cdrPaymntShowCtlr',['$scope','cdrPaymntManagerResource','$location','cdrUtils','cdrState','$rootScope','genericResource','$routeParams',
                                 function($scope,cdrPaymntManagerResource,$location,cdrUtils,cdrState,$rootScope,genericResource,$routeParams){
    $scope.cdrPaymnt = cdrState.cdrPaymnt();
    $scope.error = "";
    if($scope.cdrPaymnt) {
        $scope.cdrPaymnt.acsngUser = cdrUtils.currentWsUser.userFullName;
    };
    function init(){
    }
}]);
