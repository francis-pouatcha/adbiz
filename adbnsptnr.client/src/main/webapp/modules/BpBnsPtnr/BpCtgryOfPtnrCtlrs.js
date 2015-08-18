'use strict';
    
angular.module('AdBnsptnr')
.factory('bpCtgryOfPtnrUtils',['$translate','$rootScope','bpBnsPtnrUtils',
                function($translate,$rootScope,bpBnsPtnrUtils){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/bpctgryofptnrs';
    service.loadCountryNames=bpBnsPtnrUtils.loadCountryNames;
    
    service.whenInRole18nMsgTitleKey = function(enumKey){
    	return "BpPtnrRole_"+enumKey+"_description.title";
    };
    service.whenInRole18nMsgTitleValue = function(enumKey){
    	return service.translations[service.whenInRole18nMsgTitleKey(enumKey)];
    };
    
    service.bpCtgryOfPtnrRoles = [
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
    	            
    	            'BpCtgryOfPtnr_ctgryCode_description.title',
    	            'BpCtgryOfPtnr_fullName_description.title',
    	            'BpCtgryOfPtnr_whenInRole_description.title',
    	            'BpCtgryOfPtnr_nbrInCtgry_description.title',
    	            'BpCtgryOfPtnr_titleInCtgry_description.title',
    	            'BpCtgryOfPtnr_description_description.title',
    	            
    	            'BpBnsPtnr_ptnrNbr_description.title',
    	            
    	            'Entity_create.title',
    	            'Entity_required.title',
    	            'Entity_save.title',
    	            'Entity_cancel.title',
    	            'Entity_edit.title',
    	            'BpBnsPtnr_findACountry_description.title'
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.translate();
    
    return service;
}])
.factory('bpCtgryOfPtnrsState',['bpBnsPtnrState',function(bpBnsPtnrState){
	
	var service = {
	};
	
	service.bpBnsPtnr = bpBnsPtnrState.resultHandler.entity;
    var searchResultsVar = {};

    // The search state.
    // The current list of business partners.
    service.bpCtgryOfPtnrs = function(ptnrNbr){
        var nbr = ptnrNbr;
        if(!ptnrNbr) {
            var bpBnsPtnr = bpBnsPtnrState.resultHandler.entity();
            if(bpBnsPtnr)
                nbr = bpBnsPtnr.ptnrNbr;
        }
        if(nbr && searchResultsVar[nbr]) return searchResultsVar[nbr];
        return [];
    };

    var selectedIndexVar=-1;
    service.selectedIndex= function(selectedIndexIn){
        if(selectedIndexIn)selectedIndexVar=selectedIndexIn;
        return selectedIndexVar;
    };

    service.consumeSearchResult = function(ptnrNbr, entitySearchResult){
        if(entitySearchResult.resultList){
            searchResultsVar[ptnrNbr] = entitySearchResult.resultList;
        } else {
            searchResultsVar[ptnrNbr] = [];
        }
    };
    
    service.hasSearchResult = function(ptnrNbr){
    	var res = searchResultsVar[ptnrNbr];
    	if(res) return true;
    	return false;
    }
    
    service.bpCtgryOfPtnr = function(index,ptnrNbr){
        var list = service.bpCtgryOfPtnrs(ptnrNbr);
        if(!index || index<0 || index>=list.length) return;
        selectedIndexVar=index;
        return list[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(bpCtgryOfPtnr){
        if(!bpCtgryOfPtnr) return;
        var list = service.bpCtgryOfPtnrs(bpCtgryOfPtnr.ptnrNbr);

        if(selectedIndexVar>=0 && selectedIndexVar<list.length && list[selectedIndexVar].id==bpCtgryOfPtnr.id){
            list[selectedIndexVar]=bpCtgryOfPtnr;
        }else {
            for (var index in list) {
                if(list[index].id==bpCtgryOfPtnr.id){
                    list[index]=bpCtgryOfPtnr;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    service.push = function(bpCtgryOfPtnr){
        if(!bpCtgryOfPtnr) return false;
        var list = searchResultsVar[bpCtgryOfPtnr.ptnrNbr];
        if(!list){
            list = [];
            searchResultsVar[bpCtgryOfPtnr.ptnrNbr]=list;
        }
        var length = list.push(bpCtgryOfPtnr);
        selectedIndexVar = length-1;
    };

	service.bpCtgryOfPtnrActive= bpBnsPtnrState.bpCtgryOfPtnrActive;

    service.searchInput = {
        entity:{},
        fieldNames:[]
    };

	return service;

}])

.controller('bpCtgryOfPtnrsCtlr',['$scope','genericResource','$modal','$routeParams',
                                 'bpCtgryOfPtnrUtils','bpCtgryOfPtnrsState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 bpCtgryOfPtnrUtils,bpCtgryOfPtnrsState,$rootScope){
	
    $scope.bpCtgryOfPtnrs=bpCtgryOfPtnrsState.bpCtgryOfPtnrs;
    $scope.error = "";
    $scope.bpCtgryOfPtnrUtils=bpCtgryOfPtnrUtils;
    $scope.genericResource=genericResource;

    var ptnrSelectedUnregisterHdl = $rootScope.$on('BpBnsPtnrsSelected', function(event, data){
        var bpBnsPtnr = bpCtgryOfPtnrsState.bpBnsPtnr();
        if(!bpBnsPtnr || !data || !data.bpBnsPtnr || bpBnsPtnr.ptnrNbr!=data.bpBnsPtnr.ptnrNbr) return;
        loadPtnrIdDtlss(data.bpBnsPtnr.ptnrNbr);
    });
    $scope.$on('$destroy', function () {
    	ptnrSelectedUnregisterHdl();
    });
    init();
    function init(){
        var bpBnsPtnr = bpCtgryOfPtnrsState.bpBnsPtnr();
        if(bpBnsPtnr && bpBnsPtnr.ptnrNbr)loadPtnrIdDtlss(bpBnsPtnr.ptnrNbr);
    }
    function loadPtnrIdDtlss(ptnrNbr){
        if(!bpCtgryOfPtnrsState.bpCtgryOfPtnrActive()) return;
        if(!bpCtgryOfPtnrsState.hasSearchResult(ptnrNbr)) {
            findByLike(bpCtgryOfPtnrsState.searchInput, ptnrNbr);
        }
    }

    function findByLike(searchInput,ptnrNbr){
    	searchInput.entity.ptnrNbr=ptnrNbr;
    	if(searchInput.fieldNames.indexOf('ptnrNbr')<0){
    		searchInput.fieldNames.push('ptnrNbr');
    	}
    	genericResource.findByLike(bpCtgryOfPtnrUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
    		bpCtgryOfPtnrsState.consumeSearchResult(ptnrNbr, entitySearchResult);
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

    $scope.openCreateForm =function(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpBnsPtnr/BpCtgryOfPtnrForm.html',
            controller: ModalInstanceCreateCtrl,
            size: size,
            resolve: {
                bpBnsPtnr: function(){
                    return bpCtgryOfPtnrsState.bpBnsPtnr();
                }
            }
        });
    };

    function ModalInstanceCreateCtrl($scope, $modalInstance,bpBnsPtnr) {
        $scope.formCreate = true;
        $scope.bpCtgryOfPtnr={
        	issuedDt:new Date(),
        	expirdDt:new Date()
        };
        $scope.currentAction=bpCtgryOfPtnrUtils.translations["Entity_create.title"];
        $scope.bpCtgryOfPtnrUtils=bpCtgryOfPtnrUtils;
        $scope.error="";
        $scope.bpBnsPtnr=bpBnsPtnr;
        $scope.loadCountryNames = bpCtgryOfPtnrUtils.loadCountryNames;
        
        $scope.save = function () {
            $scope.bpCtgryOfPtnr.ptnrNbr = bpBnsPtnr.ptnrNbr;
        	genericResource.create(bpCtgryOfPtnrUtils.urlBase, $scope.bpCtgryOfPtnr)
        	.success(function (bpCtgryOfPtnr) {
        		bpCtgryOfPtnrsState.push(bpCtgryOfPtnr);
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
        
    }

    function openEditForm(size,index,bpCtgryOfPtnr){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpBnsPtnr/BpCtgryOfPtnrForm.html',
            controller: self.ModalInstanceEditCtrl,
            size: size,
            resolve:{
            	bpCtgryOfPtnr: function(){
                    return bpCtgryOfPtnr;
                },
                bpBnsPtnr: function(){
                    return bpCtgryOfPtnrsState.bpBnsPtnr();
                }
            }
        });
    };

    function ModalInstanceEditCtrl($scope, $modalInstance,bpCtgryOfPtnr,bpBnsPtnr) {
    	$scope.formEdit = true;
        $scope.bpCtgryOfPtnr = angular.copy(bpCtgryOfPtnr);
        $scope.currentAction=bpCtgryOfPtnrUtils.translations["Entity_edit.title"];
        $scope.bpCtgryOfPtnrUtils=bpCtgryOfPtnrUtils;
        $scope.bpBnsPtnr=bpBnsPtnr;
        $scope.loadCountryNames = bpCtgryOfPtnrUtils.loadCountryNames;
        
        $scope.isClean = function() {
            return angular.equals(bpCtgryOfPtnr, $scope.bpCtgryOfPtnr);
        };
        $scope.save = function () {
            genericResource.update(bpCtgryOfPtnrUtils.urlBase, $scope.bpCtgryOfPtnr)
            .success(function(data){
        		bpCtgryOfPtnrsState.replace(data);
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

    function deleteItem(bpCtgryOfPtnr){
        genericResource.deleteById(bpCtgryOfPtnrUtils.urlBase, bpCtgryOfPtnr.id).success(function(){
            findByLike(bpCtgryOfPtnrsState.searchInput, bpCtgryOfPtnr.ptnrNbr);
        })
    }
}]);

