'use strict';
    
angular.module('AdSales')

.factory('slsInvoicesUtils',['sessionManager','$translate','adUtils','genericResource','$q',function(sessionManager,$translate,adUtils,genericResource,$q){
    var service = {};

    service.urlBase='/adsales.server/rest/slsinvoices';
    service.bnsptnrUrlBase='/adbnsptnr.server/rest/bpbnsptnrs';
    service.invceManager = '/adsales.server/rest/invoice';
    
    service.formatDate= function(fieldName, inPattern){
        return adUtils.formatDate(fieldName, inPattern);
    }
    
    service.actualDate = function(inPattern){
        var currentDate = new Date();
        return adUtils.formatDate(currentDate, inPattern);
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
                    'SlsInvoice_invcePaid_description.text',
                    'SlsInvoice_invcePaid_description.title',
                    'SlsInvoice_invceDelivered_description.text',
                    'SlsInvoice_invceDelivered_description.title',
                    'SlsInvoice_delivered_description.text',
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
                    'SlsInvoice_holdingPct_description.text',
                    'SlsInvoice_holdingPct_description.title',
                    
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
    
    return service;
}])
.factory('slsInvoicesState',['$rootScope','searchResultHandler','dependentTabManager',function($rootScope,searchResultHandler,dependentTabManager){

    var service = {};
    service.resultHandler = searchResultHandler.newResultHandler('soNbr');
    service.resultHandler.maxResult=10;
    return service;

}])
.controller('slsInvoicesCtlr',['$scope','genericResource','slsInvoicesUtils','slsInvoicesState','$location','$rootScope',
function($scope,genericResource,slsInvoicesUtils,slsInvoicesState,$location,$rootScope){

    $scope.searchInput = slsInvoicesState.resultHandler.searchInput();
    $scope.itemPerPage=slsInvoicesState.resultHandler.itemPerPage;
    $scope.totalItems=slsInvoicesState.resultHandler.totalItems;
    $scope.currentPage=slsInvoicesState.resultHandler.currentPage();
    $scope.maxSize =slsInvoicesState.resultHandler.maxResult;
    $scope.slsInvoices =slsInvoicesState.resultHandler.entities;
    $scope.selectedIndex=slsInvoicesState.resultHandler.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.slsInvoicesUtils=slsInvoicesUtils;
    $scope.show=show;
    $scope.edit=edit;

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		slsInvoicesUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        //if(slsInvoicesState.resultHandler.hasEntities())
        	//{$scope.slsInvoices = slsInvoicesState.resultHandler.entities; return;}
        findByLike($scope.searchInput);
    }

    function findByLike(searchInput){
		genericResource.findByLike(slsInvoicesUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			slsInvoicesState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
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
        if($scope.searchInput.entity.invcePymntStatus){
            $scope.searchInput.entity.invcePymntStatus = $scope.searchInput.entity.invcePymntStatus;
            fieldNames.push('invcePymntStatus');
        }
        if($scope.searchInput.entity.slsInvceStatus){
            $scope.searchInput.entity.slsInvceStatus = $scope.searchInput.entity.slsInvceStatus;
            fieldNames.push('slsInvceStatus');
        }
        if($scope.searchInput.entity.invceDelivered === true){
            $scope.searchInput.entity.invceDelivered= $scope.searchInput.entity.invceDelivered;
            fieldNames.push('invceDelivered');
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
        genericResource.findCustom(slsInvoicesUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			slsInvoicesState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }

    function paginate(){
    	slsInvoicesState.resultHandler.currentPage($scope.currentPage);
        $scope.searchInput = slsInvoicesState.resultHandler.paginate();
    	findByLike($scope.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(slsInv, index){
		if(slsInvoicesState.resultHandler.selectedObject(slsInv)!= -1){
			$location.path('/SlsInvoices/show/');
		}
	}

	function edit(slsInv, index){
		if(slsInvoicesState.resultHandler.selectedObject(slsInv)!= -1){
			$location.path('/SlsInvoices/edit/');
		}
	}
}])
.controller('slsInvoicesEditCtlr',['$scope','genericResource','$location','slsInvoicesUtils','slsInvoicesState',
                                 function($scope,genericResource,$location,slsInvoicesUtils,slsInvoicesState){
    $scope.slsInvce = slsInvoicesState.resultHandler.entity();
    $scope.error = "";
    $scope.slsInvoicesUtils=slsInvoicesUtils;
    $scope.update = function (){
    	genericResource.update(slsInvoicesUtils.urlBase, $scope.slsInvce)
    	.success(function(slsInv){
    		var index = slsInvoicesState.resultHandler.replace(slsInv);
    		if(index && slsInvoicesState.resultHandler.selectedIndex(index)){
    			$location.path('/SlsInvoices/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}])
.controller('slsInvoicesShowCtlr',['$scope','genericResource','$location','slsInvoicesUtils', 'adUtils', 'slsInvoicesState','$rootScope','$routeParams',
                                 function($scope,genericResource,$location,slsInvoicesUtils,adUtils,slsInvoicesState,$rootScope,$routeParams){
    //$scope.slsInvoice = slsInvoicesState.resultHandler.entity();
    $scope.itemPerPage=slsInvoicesState.resultHandler.itemPerPage;
    $scope.currentPage=slsInvoicesState.resultHandler.currentPage();
    $scope.maxSize =slsInvoicesState.resultHandler.maxResult;
    $scope.error = "";
    $scope.slsInvoicesUtils=slsInvoicesUtils;
    $scope.handlePrintPreviewInvoice=handlePrintPreviewInvoice;
    $scope.returnSlsInvce = returnSlsInvce;
    $scope.printPdf=printPdf;
    $scope.resume = resume;
    $scope.suspend = suspend;
    $scope.slsInvoiceHolder = {};

       init();
    $scope.pageChangeHandler = function(num) {
      //Simple Pagination
    };

    function init(){
        var invceNbr = $routeParams.invceNbr;
        if(invceNbr){
            genericResource.findById(slsInvoicesUtils.invceManager,invceNbr).success(function(slsInvoiceHolder){
                $scope.slsInvoiceHolder = slsInvoiceHolder;
                $scope.slsInvoice = slsInvoiceHolder.slsInvoice;
                $scope.slsInvoice.slsInvceItems = slsInvoiceHolder.slsInvceItemsholder;
                $scope.slsInvoice.slsInvcePtnrs = slsInvoiceHolder.slsInvcePtnrsHolder;
            }).error(function(error){
                $scope.error = error;
            });
        }
    }
    
                                     
    function handlePrintPreviewInvoice(slsInvce){

        $location.path('/SlsInvoices/print/preview/'+$scope.slsInvoice.invceNbr);
	}

    function resume(){
        genericResource.get(slsInvoicesUtils.invceManager+'/resumeInvoice/'+$scope.slsInvoice.invceNbr)
            .success(function(slsInvoice){
                $scope.slsInvoice = slsInvoice;
            })
            .error(function(error){
                $scope.error = error;
            });
    }

     function suspend(){
         genericResource.get(slsInvoicesUtils.invceManager+'/cancelInvoice/'+$scope.slsInvoice.invceNbr)
             .success(function(slsInvoice){
                 $scope.slsInvoice = slsInvoice;
             })
             .error(function(error){
                 $scope.error = error;
             });
     }
    function returnSlsInvce(slsInvce){

			$location.path('/SlsInvoices/show/'+$scope.slsInvoice.invceNbr);

    }
                                     
     function printPdf(el){                      
           return adUtils.printPdfs(el);
     }
    
    $scope.previous = function (){
        var bp = slsInvoicesState.resultHandler.previous();
        if(bp){
            $scope.bpBnsPtnr=bp;
            slsInvoicesState.tabSelected();
        }
    }
    
    $scope.checkInvcePartners = function(invcePtnrs){
        if(invcePtnrs && invcePtnrs.length>0){
            return true;
        }
        else{
            return false;
        }
    }

    $scope.next =  function (){
        var bp = slsInvoicesState.resultHandler.next();
        if(bp){
            $scope.slsSalesOrder=bp;
            slsInvoicesState.tabSelected();
        }
    };
    $scope.tabSelected = function(tabName){
    	slsInvoicesState.tabSelected(tabName);
    };
    $scope.edit =function(){
        $location.path('/SlsInvoices/edit/');
    };

     $scope.delivered = function(){
         genericResource.get(slsInvoicesUtils.invceManager+'/deliveredInvoice/'+$scope.slsInvoice.id)
             .success(function(slsInvoice){
                 $scope.slsInvoice.invceDelivered = true;
             })
             .error(function(error){
                 $scope.error = error;
             });
     };
}]);
