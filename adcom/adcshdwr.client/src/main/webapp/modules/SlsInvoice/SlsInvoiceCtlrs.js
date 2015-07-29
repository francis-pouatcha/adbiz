'use strict';
    
angular.module('AdCshdwr')

.factory('cdrSlsInvoicesUtils',['sessionManager','$translate','adUtils','genericResource','$q', '$http',function(sessionManager,$translate,adUtils,genericResource,$q,$http){
    var service = {};

    service.urlBase='/adsales.server/rest/slsinvoices';
    service.bnsptnrUrlBase='/adbnsptnr.server/rest/bpbnsptnrs';
    service.urlCdrpymntManager='/adcshdwr.server/rest/cdrpymntmanager';
    
    service.formatDate= function(fieldName, inPattern){
        return adUtils.formatDate(fieldName, inPattern);
    }
    
    service.slsInvceStatusI18nMsgTitleKey = function(enumKey){
    	return "SlsInvceStatus_"+enumKey+"_description.title";
    };
    
    service.slsInvceStatusI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.slsInvceStatusI18nMsgTitleKey(enumKey)];
    };
    
    service.slsInvceStatus = [
      {enumKey:'SUSPENDED', translKey:'SlsInvceStatus_SUSPENDED_description.title'},
      {enumKey:'ONGOING', translKey:'SlsInvceStatus_ONGOING_description.title'},
      {enumKey:'RESUMED', translKey:'SlsInvceStatus_RESUMED_description.title'},
      {enumKey:'CLOSED', translKey:'SlsInvceStatus_CLOSED_description.title'}
    ];

        service.cdrPymntModeI18nMsgTitleKey = function(enumKey){
            return "CdrPymntMode"+enumKey+"_description.title";
        };

        service.cdrPymntModeI18nMsgTitleValue = function(enumKey){
            return service.translations[service.cdrPymntModeI18nMsgTitleKey(enumKey)];
        };

        service.cdrPymntModes = [
            {enumKey:'CASH', translKey:'CdrPymntMode_CASH_description.title'},
            {enumKey:'CHECK', translKey:'CdrPymntMode_CHECK_description.title'},
            {enumKey:'CREDIT_CARD', translKey:'CdrPymntMode_CREDIT_CARD_description.title'},
            {enumKey:'VOUCHER', translKey:'CdrPymntMode_VOUCHER_description.title'}
        ];
    
    service.language=sessionManager.language;
    
    service.translate = function(){
    	$translate(['SlsInvoice_description.text',
                    'SlsInvoice_description.title',
                    'SlsInvoices_description.text',
                    'SlsInvoices_description.title',
                    'SlsInvoice_grossSPPreTax_description.text',
                    'SlsInvoice_grossSPPreTax_description.title',
                    'SlsInvoice_invceCur_description.text',
                    'SlsInvoice_invceCur_description.title',
                    'SlsInvoice_invceDt_description.text',
                    'SlsInvoice_invceDt_description.title',
                    'SlsInvoice_invceNbr_description.text',
                    'SlsInvoice_invceNbr_description.title',
                    'SlsInvoice_invceStatus_description.text',
                    'SlsInvoice_invceStatus_description.title',
                    'SlsInvoice_invceType_description.text',
                    'SlsInvoice_invceType_description.title',
                    'SlsInvoice_netAmtToPay_description.text',
                    'SlsInvoice_netAmtToPay_description.title',
                    'SlsInvoice_netSPPreTax_description.text',
                    'SlsInvoice_netSPPreTax_description.title',
                    'SlsInvoice_netSPTaxIncl_description.text',
                    'SlsInvoice_netSPTaxIncl_description.title',
                    'SlsInvoice_netSalesAmt_description.text',
                    'SlsInvoice_netSalesAmt_description.title',
                    'SlsInvoice_pymtDscntAmt_description.text',
                    'SlsInvoice_pymtDscntAmt_description.title',
                    'SlsInvoice_pymtDscntPct_description.text',
                    'SlsInvoice_pymtDscntPct_description.title',
                    'SlsInvoice_rdngDscntAmt_description.text',
                    'SlsInvoice_rdngDscntAmt_description.title',
                    'SlsInvoice_rebate_description.text',
                    'SlsInvoice_rebate_description.title',
                    'SlsInvoice_soNbr_description.text',
                    'SlsInvoice_soNbr_description.title',
                    'SlsInvoice_vatAmount_description.text',
                    'SlsInvoice_vatAmount_description.title',
                    'SlsInvoice_invceDtFrom_description.text',
                    'SlsInvoice_invceDtFrom_description.title',
                    'SlsInvoice_invceDtTo_description.text',
                    'SlsInvoice_invceDtTo_description.title',
                    'SlsInvoice_holdingAmount_description.text',
                    'SlsInvoice_holdingAmount_description.title',
                    
                    'SlsInvceItem_artPic_description.text',
                    'SlsInvceItem_artPic_description.title',
                    'SlsInvceItem_artName_description.text',
                    'SlsInvceItem_artName_description.title',
                    'SlsInvceItem_description.text',
                    'SlsInvceItem_description.title',
                    'SlsInvceItems_description.text',
                    'SlsInvceItems_description.title',
                    'SlsInvceItem_grossSPPreTax_description.text',
                    'SlsInvceItem_grossSPPreTax_description.title',
                    'SlsInvceItem_invNbr_description.text',
                    'SlsInvceItem_invNbr_description.title',
                    'SlsInvceItem_lotPic_description.text',
                    'SlsInvceItem_lotPic_description.title',
                    'SlsInvceItem_netSPPreTax_description.text',
                    'SlsInvceItem_netSPPreTax_description.title',
                    'SlsInvceItem_netSPTaxIncl_description.text',
                    'SlsInvceItem_netSPTaxIncl_description.title',
                    'SlsInvceItem_objctOrgUnit_description.text',
                    'SlsInvceItem_objctOrgUnit_description.title',
                    'SlsInvceItem_qty_description.text',
                    'SlsInvceItem_qty_description.title',
                    'SlsInvceItem_rebate_description.text',
                    'SlsInvceItem_rebate_description.title',
                    'SlsInvceItem_sppuCur_description.text',
                    'SlsInvceItem_sppuCur_description.title',
                    'SlsInvceItem_sppuPreTax_description.text',
                    'SlsInvceItem_sppuPreTax_description.title',
                    'SlsInvceItem_vatAmount_description.text',
                    'SlsInvceItem_vatAmount_description.title',
                    'SlsInvceItem_vatPct_description.text',
                    'SlsInvceItem_vatPct_description.title',
                    
                    'SlsInvcePtnr_description.text',
                    'SlsInvcePtnr_description.title',
                    'SlsInvcePtnrs_description.title',
                    'SlsInvcePtnr_invceNbr_description.text',
                    'SlsInvcePtnr_invceNbr_description.title',
                    'SlsInvcePtnr_ptnrNbr_description.text',
                    'SlsInvcePtnr_ptnrNbr_description.title',
                    'SlsInvcePtnr_roleInInvce_description.text',
                    'SlsInvcePtnr_roleInInvce_description.title',
                    
                    'SlsInvceStatus_CLOSED_description.title',
                    'SlsInvceStatus_ONGOING_description.title',
                    'SlsInvceStatus_RESUMED_description.title',
                    'SlsInvceStatus_SUSPENDED_description.title',

                    'CdrPymntMode_CASH_description.title',
                    'CdrPymntMode_CHECK_description.title',
                    'CdrPymntMode_CREDIT_CARD_description.title',
                    'CdrPymntMode_VOUCHER_description.title',
                    
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
                    'Entity_print.title',

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

            'CdrCstmrVchr_vchrNbr_description.title',

            'CdrPymnt_description.title',
            'CdrPymnt_amt_description.title'
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.loadPartnersByName = function(fullName){
        return genericResource.findByLikePromissed(service.bnsptnrUrlBase, 'fullName', fullName)
        .then(function(entitySearchResult){
            if(!angular.isUndefined(entitySearchResult))
            return entitySearchResult.resultList;
            else return "";
        });
    };
    
    service.translate();
    
    service.loadPayInvce = function(searchInput){
        var deferred = $q.defer();
        
        $http.post(service.urlBase + "/close/findByLikePay", searchInput)
            .success(function(data) {
                deferred.resolve(data);
            })
            .error(function(data){
                deferred.reject("no more SlsInvce !")
        });
        
        return deferred.promise;
    }
    
    
    return service;
}])
.factory('cdrSlsInvoicesState',['$rootScope','searchResultHandler','dependentTabManager',function($rootScope,searchResultHandler,dependentTabManager){

    var service = {};
    service.resultHandler = searchResultHandler.newResultHandler('invceNbr');
    service.resultHandler.maxResult=10;
    return service;

}])
.controller('cdrSlsInvoicesCtlr',['$scope','genericResource','cdrSlsInvoicesUtils','cdrSlsInvoicesState','$location','$rootScope',
function($scope,genericResource,cdrSlsInvoicesUtils,cdrSlsInvoicesState,$location,$rootScope){

    $scope.searchInput = cdrSlsInvoicesState.resultHandler.searchInput();
    $scope.itemPerPage=cdrSlsInvoicesState.resultHandler.itemPerPage;
    $scope.totalItems=cdrSlsInvoicesState.resultHandler.totalItems;
    $scope.currentPage=cdrSlsInvoicesState.resultHandler.currentPage();
    $scope.maxSize =cdrSlsInvoicesState.resultHandler.maxResult;
    $scope.slsInvoices =cdrSlsInvoicesState.resultHandler.entities;
    $scope.selectedIndex=cdrSlsInvoicesState.resultHandler.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.cdrSlsInvoicesUtils=cdrSlsInvoicesUtils;
    $scope.show=show;
    $scope.edit=edit;

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		cdrSlsInvoicesUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(cdrSlsInvoicesState.resultHandler.hasEntities())return;
        findByLike($scope.searchInput);
    }

    function findByLike(searchInput){
        cdrSlsInvoicesUtils.loadPayInvce(searchInput)
		.then(function(entitySearchResult){
            cdrSlsInvoicesState.resultHandler.searchResult(entitySearchResult);
        },function(error){
        	$scope.error = error;
        });                  		
    }
    

    function processSearchInput(){
        var fieldNames = [];
        if($scope.searchInput.entity.soNbr){
            $scope.searchInput.entity.soNbr= $scope.searchInput.entity.soNbr;
        	fieldNames.push('soNbr');
        }
        if($scope.searchInput.entity.invceNbr){
            $scope.searchInput.entity.invceNbr= $scope.searchInput.entity.invceNbr;
        	fieldNames.push('invceNbr');
        }
        if($scope.searchInput.invceDtFrom) $scope.searchInput.invceDtFrom= $scope.searchInput.invceDtFrom;
        if($scope.searchInput.invceDtTo) $scope.searchInput.invceDtTo= $scope.searchInput.invceDtTo;
        if($scope.searchInput.ptnr){
            $scope.searchInput.ptnrNbr= $scope.searchInput.ptnr.ptnrNbr;
        }
        
        $scope.searchInput.fieldNames = fieldNames;
        return $scope.searchInput ;
    };

    function handleSearchRequestEvent(){
    	processSearchInput();
    	findCustom($scope.searchInput);
    };
    
    function handleListRequestEvent(){
        processSearchInput();
    	findCustom($scope.searchInput);
        $location.path('/SlsInvoices');
    }
    
    function findCustom(searchInput){
        genericResource.findCustom(cdrSlsInvoicesUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			cdrSlsInvoicesState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }

    function paginate(){
    	cdrSlsInvoicesState.resultHandler.currentPage($scope.currentPage);
        $scope.searchInput = cdrSlsInvoicesState.resultHandler.paginate();
    	findByLike($scope.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(slsInv, index){
		if(cdrSlsInvoicesState.resultHandler.selectedObject(slsInv)!= -1){
			$location.path('/SlsInvoices/show/');
		}
	}

	function edit(slsInv, index){
		if(cdrSlsInvoicesState.resultHandler.selectedObject(slsInv)){
			$location.path('/SlsInvoices/edit/');
		}
	}
}])
.controller('cdrSlsInvoicesEditCtlr',['$scope','genericResource','$location','cdrSlsInvoicesUtils','cdrSlsInvoicesState',
                                 function($scope,genericResource,$location,cdrSlsInvoicesUtils,cdrSlsInvoicesState){
    $scope.slsInvce = cdrSlsInvoicesState.resultHandler.entity();
    $scope.error = "";
    $scope.cdrSlsInvoicesUtils=cdrSlsInvoicesUtils;
    $scope.update = function (){
    	genericResource.update(cdrSlsInvoicesUtils.urlBase, $scope.slsInvce)
    	.success(function(slsInv){
    		var index = cdrSlsInvoicesState.resultHandler.replace(slsInv);
    		if(index && cdrSlsInvoicesState.resultHandler.selectedIndex(index)){
    			$location.path('/SlsInvoices/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}])
.controller('cdrSlsInvoicesShowCtlr',['$scope','genericResource','$location','cdrSlsInvoicesUtils','cdrSlsInvoicesState','$rootScope',
                                 function($scope,genericResource,$location,cdrSlsInvoicesUtils,cdrSlsInvoicesState,$rootScope){
    $scope.slsInvoice = cdrSlsInvoicesState.resultHandler.entity();
    $scope.itemPerPage=cdrSlsInvoicesState.resultHandler.itemPerPage;
    $scope.currentPage=cdrSlsInvoicesState.resultHandler.currentPage();
    $scope.maxSize =cdrSlsInvoicesState.resultHandler.maxResult;
    $scope.error = "";
    $scope.cdrSlsInvoicesUtils=cdrSlsInvoicesUtils;
    $scope.cdrPymntHolder = {cdrPymntItems:[]};
    $scope.voucherMode = false;

    init();
    function init(){
        var invNbr = $scope.slsInvoice.invceNbr;

        genericResource.findById(cdrSlsInvoicesUtils.urlCdrpymntManager, invNbr)
            .success(function(cdrPymntHolder){
                $scope.cdrPymntHolder=cdrPymntHolder;
                if(!$scope.cdrPymntHolder.cdrPymnt){
                    $scope.cdrPymntHolder.cdrPymnt = {};
                    $scope.cdrPymntHolder.cdrPymnt.amt = 0.0;
                }
                $scope.cdrPymntHolder.amt = $scope.slsInvoice.netAmtToPay - $scope.cdrPymntHolder.cdrPymnt.amt;
            })
            .error(function(error){
                $scope.error = error;
            });
    }

    $scope.addPymt = function(){
        if($scope.slsInvoice.invceStatus!='CLOSED'){
            $scope.error = "La facture n'est pas cloture";
            return;
        }

        if(!$scope.cdrPymntHolder.amt || $scope.cdrPymntHolder.amt == 0) return;
        var totalPymt = $scope.cdrPymntHolder.cdrPymnt.amt + parseInt($scope.cdrPymntHolder.amt);
        if(totalPymt > $scope.slsInvoice.netAmtToPay){
            $scope.error = "montant paiement superieur au montant net a payer";
            return ;
        }
        genericResource.create(cdrSlsInvoicesUtils.urlCdrpymntManager, $scope.cdrPymntHolder)
            .success(function(cdrPymntHolder){
                $scope.cdrPymntHolder=cdrPymntHolder;
                $scope.cdrPymntHolder.amt = $scope.slsInvoice.netAmtToPay - $scope.cdrPymntHolder.cdrPymnt.amt;
                if($scope.cdrPymntHolder.amt == 0){
                    $scope.slsInvoice.invcePymntStatus = 'PAYE';
                    genericResource.update(cdrSlsInvoicesUtils.urlBase, $scope.slsInvoice)
                        .success(function(invoice){
                            $scope.slsInvoice =invoice;
                        })
                }else{
                    $scope.slsInvoice.invcePymntStatus = 'AVANCE';
                    genericResource.update(cdrSlsInvoicesUtils.urlBase, $scope.slsInvoice)
                        .success(function(invoice){
                            $scope.slsInvoice =invoice;
                        })
                }
                $scope.error = "";
            })
            .error(function(error){
                $scope.error = error;
            });
    }

    $scope.pymtModeChanged = function(){
        if($scope.cdrPymntHolder.pymntMode=='VOUCHER'){
            $scope.voucherMode = true;
        }else{
            $scope.voucherMode = false;
        }

    }

    $scope.pageChangeHandler = function(num) {
      //Simple Pagination
    };
    $scope.previous = function (){
        var bp = cdrSlsInvoicesState.resultHandler.previous();
        if(bp){
            $scope.bpBnsPtnr=bp;
            cdrSlsInvoicesState.tabSelected();
        }
    }

    $scope.next =  function (){
        var bp = cdrSlsInvoicesState.resultHandler.next();
        if(bp){
            $scope.slsSalesOrder=bp;
            cdrSlsInvoicesState.tabSelected();
        }
    };
    $scope.tabSelected = function(tabName){
    	cdrSlsInvoicesState.tabSelected(tabName);
    };
    $scope.edit =function(){
        $location.path('/SlsInvoices/edit/');
    };

}]);
