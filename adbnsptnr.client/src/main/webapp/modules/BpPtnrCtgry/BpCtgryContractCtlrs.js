'use strict';
    
angular.module('AdBnsptnr')
.factory('bpCtgryContractUtils',['$translate','sessionManager',
                function($translate,sessionManager){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/bpptnrcontracts';

    service.contractEltI18nMsgTitleKey = function(enumKey){
    	return "BpPtnrContractElt_"+enumKey+"_description.title";
    };
    service.contractEltI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.contractEltI18nMsgTitleKey(enumKey)];
    };
    
    service.contractElts = [
       {enumKey:'SALES_DISCOUNT', translKey:'BpPtnrContractElt_SALES_DISCOUNT_description.title'},
       {enumKey:'PURCHASE_DISCOUNT', translKey:'BpPtnrContractElt_PURCHASE_DISCOUNT_description.title'},
       {enumKey:'ROYALTY_DISCOUNT', translKey:'BpPtnrContractElt_ROYALTY_DISCOUNT_description.title'},
       {enumKey:'QUANTITY_DISCOUNT', translKey:'BpPtnrContractElt_QUANTITY_DISCOUNT_description.title'},
       {enumKey:'PAYMENT_DISCOUNT', translKey:'BpPtnrContractElt_PAYMENT_DISCOUNT_description.title'},
       {enumKey:'WAIVER_DISCOUNT', translKey:'BpPtnrContractElt_WAIVER_DISCOUNT_description.title'},
       {enumKey:'SALES_COMMISSION', translKey:'BpPtnrContractElt_SALES_COMMISSION_description.title'},
       {enumKey:'PURCHASE_COMMISSION', translKey:'BpPtnrContractElt_PURCHASE_COMMISSION_description.title'},
       {enumKey:'BROKERAGE_COMMISSION', translKey:'BpPtnrContractElt_BROKERAGE_COMMISSION_description.title'},
       {enumKey:'COLLECTION_COMMISSION', translKey:'BpPtnrContractElt_COLLECTION_COMMISSION_description.title'},
       {enumKey:'PAYMENT_COMMISSION', translKey:'BpPtnrContractElt_PAYMENT_COMMISSION_description.title'},
       {enumKey:'REIMBURSEMENT_COMMISSION', translKey:'BpPtnrContractElt_REIMBURSEMENT_COMMISSION_description.title'},
       {enumKey:'ANNULATION_COMMISSION', translKey:'BpPtnrContractElt_ANNULATION_COMMISSION_description.title'},
       {enumKey:'SERVICE_COMMISSION', translKey:'BpPtnrContractElt_SERVICE_COMMISSION_description.title'},
       {enumKey:'SERVICE_FEES', translKey:'BpPtnrContractElt_SERVICE_FEES_description.title'},
       {enumKey:'RESTOCKING_FEES', translKey:'BpPtnrContractElt_RESTOCKING_FEES_description.title'},
       {enumKey:'HANDLING_FEES', translKey:'BpPtnrContractElt_HANDLING_FEES_description.title'},
       {enumKey:'MANAGEMENT_FEES', translKey:'BpPtnrContractElt_MANAGEMENT_FEES_description.title'}
     ];

    service.translate = function(){
    	$translate(['BpPtnrContractElt_SALES_DISCOUNT_description.title',
    	            'BpPtnrContractElt_PURCHASE_DISCOUNT_description.title',
    	            'BpPtnrContractElt_ROYALTY_DISCOUNT_description.title',
    	            'BpPtnrContractElt_QUANTITY_DISCOUNT_description.title',
    	            'BpPtnrContractElt_PAYMENT_DISCOUNT_description.title',
    	            'BpPtnrContractElt_WAIVER_DISCOUNT_description.title',
    	            'BpPtnrContractElt_SALES_COMMISSION_description.title',
    	            'BpPtnrContractElt_PURCHASE_COMMISSION_description.title',
    	            'BpPtnrContractElt_BROKERAGE_COMMISSION_description.title',
    	            'BpPtnrContractElt_COLLECTION_COMMISSION_description.title',
    	            'BpPtnrContractElt_PAYMENT_COMMISSION_description.title',
    	            'BpPtnrContractElt_REIMBURSEMENT_COMMISSION_description.title',
    	            'BpPtnrContractElt_ANNULATION_COMMISSION_description.title',
    	            'BpPtnrContractElt_SERVICE_COMMISSION_description.title',
    	            'BpPtnrContractElt_SERVICE_FEES_description.title',
    	            'BpPtnrContractElt_RESTOCKING_FEES_description.title',
    	            'BpPtnrContractElt_HANDLING_FEES_description.title',
    	            'BpPtnrContractElt_MANAGEMENT_FEES_description.title',
    	            
    	            'BpPtnrContract_contractElt_description.title',
    	            'BpPtnrContract_qualif_description.title',
    	            'BpPtnrContract_eltValue_description.title',
    	            'BpPtnrContract_eltConstraint_description.title',
    	            'BpPtnrCtgry_holderIdentif_description.title',
    	            
    	            'BaseLanguage_fr_description.title',
    	            'BaseLanguage_en_description.title',
    	            
    	            'Entity_create.title',
    	            'Entity_edit.title',
    	            'Entity_required.title',
    	            'Entity_save.title',
    	            'Entity_cancel.title',
    	            'Entity_yes.title',
    	            'Entity_no.title'
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.translate();

    return service;
}])
.factory('bpCtgryContractsState',['bpPtnrCtgryState',function(bpPtnrCtgryState){
	
	var service = {
	};
	
	service.bpPtnrCtgry = bpPtnrCtgryState.bpPtnrCtgry;
    var searchResultsVar = {};

    // The search state.
    // The current list of business partners.
    service.bpCtgryContracts = function(holderIdentif){
        var nbr = holderIdentif;
        if(!holderIdentif) {
            var bpPtnrCtgry = bpPtnrCtgryState.bpPtnrCtgry();
            if(bpPtnrCtgry)
                nbr = bpPtnrCtgry.ctgryCode;
        }
        if(nbr && searchResultsVar[nbr]) return searchResultsVar[nbr];
        return [];
    };

    var selectedIndexVar=-1;
    service.selectedIndex= function(selectedIndexIn){
        if(selectedIndexIn)selectedIndexVar=selectedIndexIn;
        return selectedIndexVar;
    };

    service.consumeSearchResult = function(holderIdentif, entitySearchResult){
        if(entitySearchResult.resultList){
            searchResultsVar[holderIdentif] = entitySearchResult.resultList;
        } else {
            searchResultsVar[holderIdentif] = [];
        }
    };
    
    service.hasSearchResult = function(holderIdentif){
    	var res = searchResultsVar[holderIdentif];
    	if(res) return true;
    	return false;
    }

    service.bpCtgryContract = function(index,holderIdentif){
        var list = service.bpCtgryContracts(holderIdentif);
        if(!index || index<0 || index>=list.length) return;
        selectedIndexVar=index;
        return list[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(bpCtgryContract){
        if(!bpCtgryContract) return;
        var list = service.bpCtgryContracts(bpCtgryContract.holderIdentif);

        if(selectedIndexVar>=0 && selectedIndexVar<list.length && list[selectedIndexVar].id==bpCtgryContract.id){
            list[selectedIndexVar]=bpCtgryContract;
        }else {
            for (var index in list) {
                if(list[index].id==bpCtgryContract.id){
                    list[index]=bpCtgryContract;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    service.push = function(bpCtgryContract){
        if(!bpCtgryContract) return false;
        var list = searchResultsVar[bpCtgryContract.holderIdentif];
        if(!list){
            list = [];
            searchResultsVar[bpCtgryContract.holderIdentif]=list;
        }
        var length = list.push(bpCtgryContract);
        selectedIndexVar = length-1;
    };

	service.bpCtgryContractActive= bpPtnrCtgryState.bpCtgryContractActive;

    service.searchInput = {
        entity:{},
        fieldNames:[]
    };

	return service;

}])
.controller('bpCtgryContractsCtlr',['$scope','genericResource','$modal','$routeParams',
                                  'bpCtgryContractUtils','bpCtgryContractsState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 bpCtgryContractUtils,bpCtgryContractsState,$rootScope){

    $scope.bpCtgryContracts=bpCtgryContractsState.bpCtgryContracts;
    $scope.error = "";
    $scope.bpCtgryContractUtils=bpCtgryContractUtils;
    $scope.genericResource=genericResource;

    var ptnrSelectedUnregisterHdl = $rootScope.$on('BpPtnrCtgrysSelected', function(event, data){
        var bpPtnrCtgry = bpCtgryContractsState.bpPtnrCtgry();
        if(!bpPtnrCtgry || !data || !data.bpPtnrCtgry || bpPtnrCtgry.ctgryCode!=data.bpPtnrCtgry.ctgryCode) return;
        loadBpCtgryContracts(data.bpPtnrCtgry.ctgryCode);
    });
    $scope.$on('$destroy', function () {
        ptnrSelectedUnregisterHdl();
    });
    init();
    function init(){
        var bpPtnrCtgry = bpCtgryContractsState.bpPtnrCtgry();
        if(bpPtnrCtgry && bpPtnrCtgry.ctgryCode)loadBpCtgryContracts(bpPtnrCtgry.ctgryCode);
    }
    function loadBpCtgryContracts (holderIdentif){
        if(!bpCtgryContractsState.bpCtgryContractActive()) return;
        if(!bpCtgryContractsState.hasSearchResult(holderIdentif)) {
            findByLike(bpCtgryContractsState.searchInput, holderIdentif);
        }
    }

    function findByLike(searchInput,holderIdentif){
    	searchInput.entity.holderIdentif=holderIdentif;
    	if(searchInput.fieldNames.indexOf('holderIdentif')<0){
    		searchInput.fieldNames.push('holderIdentif');
    	}
    	genericResource.findByLike(bpCtgryContractUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            bpCtgryContractsState.consumeSearchResult(holderIdentif, entitySearchResult);
        })
    	.error(function(error){
    		$scope.error = error;
    	});
    }

    $scope.openCreateForm =function(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpPtnrCtgry/BpCtgryContractForm.html',
            controller: ModalInstanceCreateCtrl,
            size: size,
            resolve: {
                bpPtnrCtgry: function(){
                    return bpCtgryContractsState.bpPtnrCtgry();
                }
            }
        });
    };

    var ModalInstanceCreateCtrl = function($scope, $modalInstance,bpPtnrCtgry) {
        $scope.formCreate = true;
        $scope.bpCtgryContract;
        $scope.currentAction=bpCtgryContractUtils.translations["Entity_create.title"];
        $scope.bpCtgryContractUtils=bpCtgryContractUtils;
        $scope.error="";
        $scope.bpPtnrCtgry=bpPtnrCtgry;

        $scope.save = function () {
            $scope.bpCtgryContract.holderIdentif = bpPtnrCtgry.ctgryCode;
        	genericResource.create(bpCtgryContractUtils.urlBase, $scope.bpCtgryContract)
        	.success(function (bpCtgryContract) {
        		bpCtgryContractsState.push(bpCtgryContract);
        		$modalInstance.dismiss('cancel');
            })
            .error(function(data, status){
            	$scope.error= status + " " + data;
            });

        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.isClean = function() {
            return false;
        };
    };

    $scope.openEditForm = function(size,bpCtgryContract){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpPtnrCtgry/BpCtgryContractForm.html',
            controller: ModalInstanceEditCtrl,
            size: size,
            resolve:{
            	bpCtgryContract: function(){
                    return bpCtgryContract;
                },
                bpPtnrCtgry: function(){
                    return bpCtgryContractsState.bpPtnrCtgry();
                }
            }
        });
    };

    var ModalInstanceEditCtrl = function($scope, $modalInstance,bpCtgryContract,bpPtnrCtgry) {
    	$scope.formEdit = true;
        $scope.bpCtgryContract = angular.copy(bpCtgryContract);
        $scope.currentAction=bpCtgryContractUtils.translations["Entity_edit.title"];
        $scope.bpCtgryContractUtils=bpCtgryContractUtils;
        $scope.bpPtnrCtgry=bpPtnrCtgry;

        $scope.isClean = function() {
            return angular.equals(bpCtgryContract, $scope.bpCtgryContract);
        };
        $scope.save = function () {
            genericResource.update(bpCtgryContractUtils.urlBase, $scope.bpCtgryContract)
            .success(function(data){
        		bpCtgryContractsState.replace(data);
        		$modalInstance.dismiss('cancel');
            })
            .error(function(data, status){
            	$scope.error= status + " " + data;
            });
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };

    $scope.deleteItem = function(bpCtgryContract){
        genericResource.deleteById(bpCtgryContractUtils.urlBase, bpCtgryContract.id).success(function(){
            findByLike(bpCtgryContractsState.searchInput, bpCtgryContract.holderIdentif);
        })
    };

}]);

