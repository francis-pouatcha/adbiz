'use strict';
    
angular.module('AdBnsptnr')
.factory('bpInsuredUtils',['$translate','$rootScope','bpBnsPtnrUtils',
                function($translate,$rootScope,bpBnsPtnrUtils){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/insurrances';
    
    service.ptnrIdTypeI18nMsgTitleKey = function(enumKey){
    	return "BpPtnrIdType_"+enumKey+"_description.title";
    };
    service.ptnrIdTypeI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.ptnrIdTypeI18nMsgTitleKey(enumKey)];
    };
    
    service.bpPtnrIdDtlsRoles = [
      {enumKey:'IDCARDNBR', translKey:'BpPtnrIdType_IDCARDNBR_description.title'},
      {enumKey:'RESDTCARDNBR', translKey:'BpPtnrIdType_RESDTCARDNBR_description.title'},
      {enumKey:'DRIVERLICNBR', translKey:'BpPtnrIdType_DRIVERLICNBR_description.title'},
      {enumKey:'PASSPORTNBR', translKey:'BpPtnrIdType_PASSPORTNBR_description.title'},
      {enumKey:'EMPLOYEENBR', translKey:'BpPtnrIdType_EMPLOYEENBR_description.title'},
      {enumKey:'MEMBERSHIP', translKey:'BpPtnrIdType_MEMBERSHIP_description.title'},
      {enumKey:'INSURER', translKey:'BpPtnrIdType_INSURER_description.title'},
      {enumKey:'INSURED', translKey:'BpPtnrIdType_INSURED_description.title'}
    ];

    service.translate = function(){
    	$translate(['BpPtnrIdType_IDCARDNBR_description.title',
    	            'BpPtnrIdType_RESDTCARDNBR_description.title',
    	            'BpPtnrIdType_DRIVERLICNBR_description.title',
    	            'BpPtnrIdType_PASSPORTNBR_description.title',
    	            'BpPtnrIdType_EMPLOYEENBR_description.title',
    	            'BpPtnrIdType_MEMBERSHIP_description.title',
    	            'BpPtnrIdType_INSURER_description.title',
    	            'BpPtnrIdType_INSURED_description.title',
    	            
    	            'BpPtnrIdDtls_ptnrIdType_description.title',
    	            'BpPtnrIdDtls_idNbr_description.title',
    	            'BpPtnrIdDtls_issuedDt_description.title',
    	            'BpPtnrIdDtls_expirdDt_description.title',
    	            'BpPtnrIdDtls_issuedBy_description.title',
    	            'BpPtnrIdDtls_issuedIn_description.title',
    	            'BpPtnrIdDtls_issuingCtry_description.title',
    	            'BpPtnrCtgry_create.title',
    	            
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
.factory('bpPtnrIdDtlssState',['bpBnsPtnrState',function(bpBnsPtnrState){
	
	var service = {
	};
	
	service.bpBnsPtnr = bpBnsPtnrState.resultHandler.entity;
    var searchResultsVar = {};

    // The search state.
    // The current list of business partners.
    service.bpPtnrIdDtlss = function(ptnrNbr){
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
    
    service.bpPtnrIdDtls = function(index,ptnrNbr){
        var list = service.bpPtnrIdDtlss(ptnrNbr);
        if(!index || index<0 || index>=list.length) return;
        selectedIndexVar=index;
        return list[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(bpPtnrIdDtls){
        if(!bpPtnrIdDtls) return;
        var list = service.bpPtnrIdDtlss(bpPtnrIdDtls.ptnrNbr);

        if(selectedIndexVar>=0 && selectedIndexVar<list.length && list[selectedIndexVar].id==bpPtnrIdDtls.id){
            list[selectedIndexVar]=bpPtnrIdDtls;
        }else {
            for (var index in list) {
                if(list[index].id==bpPtnrIdDtls.id){
                    list[index]=bpPtnrIdDtls;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    service.push = function(bpPtnrIdDtls){
        if(!bpPtnrIdDtls) return false;
        var list = searchResultsVar[bpPtnrIdDtls.ptnrNbr];
        if(!list){
            list = [];
            searchResultsVar[bpPtnrIdDtls.ptnrNbr]=list;
        }
        var length = list.push(bpPtnrIdDtls);
        selectedIndexVar = length-1;
    };

	service.bpPtnrIdDtlsActive= bpBnsPtnrState.bpPtnrIdDtlsActive;

    service.searchInput = {
        entity:{},
        fieldNames:[]
    };

	return service;

}])

.controller('bpPtnrIdDtlssCtlr',['$scope','genericResource','$modal','$routeParams',
                                 'bpInsuredUtils','bpPtnrIdDtlssState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 bpInsuredUtils,bpPtnrIdDtlssState,$rootScope){
	
    $scope.bpPtnrIdDtlss=bpPtnrIdDtlssState.bpPtnrIdDtlss;
    $scope.error = "";
    $scope.bpInsuredUtils=bpInsuredUtils;
    $scope.genericResource=genericResource;

    var ptnrSelectedUnregisterHdl = $rootScope.$on('BpBnsPtnrsSelected', function(event, data){
        var bpBnsPtnr = bpPtnrIdDtlssState.bpBnsPtnr();
        if(!bpBnsPtnr || !data || !data.bpBnsPtnr || bpBnsPtnr.ptnrNbr!=data.bpBnsPtnr.ptnrNbr) return;
        loadPtnrIdDtlss(data.bpBnsPtnr.ptnrNbr);
    });
    $scope.$on('$destroy', function () {
    	ptnrSelectedUnregisterHdl();
    });
    init();
    function init(){
        var bpBnsPtnr = bpPtnrIdDtlssState.bpBnsPtnr();
        if(bpBnsPtnr && bpBnsPtnr.ptnrNbr)loadPtnrIdDtlss(bpBnsPtnr.ptnrNbr);
    }
    function loadPtnrIdDtlss(ptnrNbr){
        if(!bpPtnrIdDtlssState.bpPtnrIdDtlsActive()) return;
        if(!bpPtnrIdDtlssState.hasSearchResult(ptnrNbr)) {
            findByLike(bpPtnrIdDtlssState.searchInput, ptnrNbr);
        }
    }

    function findByLike(searchInput,ptnrNbr){
    	searchInput.entity.ptnrNbr=ptnrNbr;
    	if(searchInput.fieldNames.indexOf('ptnrNbr')<0){
    		searchInput.fieldNames.push('ptnrNbr');
    	}
    	genericResource.findByLike(bpInsuredUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
    		bpPtnrIdDtlssState.consumeSearchResult(ptnrNbr, entitySearchResult);
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

    $scope.openCreateForm =function(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpBnsPtnr/BpPtnrIdDtlsForm.html',
            controller: ModalInstanceCreateCtrl,
            size: size,
            resolve: {
                bpBnsPtnr: function(){
                    return bpPtnrIdDtlssState.bpBnsPtnr();
                }
            }
        });
    };

    function ModalInstanceCreateCtrl($scope, $modalInstance,bpBnsPtnr) {
        $scope.formCreate = true;
        $scope.bpPtnrIdDtls={
        	issuedDt:new Date(),
        	expirdDt:new Date()
        };
        $scope.currentAction=bpInsuredUtils.translations["Entity_create.title"];
        $scope.bpInsuredUtils=bpInsuredUtils;
        $scope.error="";
        $scope.bpBnsPtnr=bpBnsPtnr;
        $scope.loadCountryNames = bpInsuredUtils.loadCountryNames;
        
        $scope.save = function () {
            $scope.bpPtnrIdDtls.ptnrNbr = bpBnsPtnr.ptnrNbr;
        	genericResource.create(bpInsuredUtils.urlBase, $scope.bpPtnrIdDtls)
        	.success(function (bpPtnrIdDtls) {
        		bpPtnrIdDtlssState.push(bpPtnrIdDtls);
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

    function openEditForm(size,index,bpPtnrIdDtls){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpBnsPtnr/BpPtnrIdDtlsForm.html',
            controller: self.ModalInstanceEditCtrl,
            size: size,
            resolve:{
            	bpPtnrIdDtls: function(){
                    return bpPtnrIdDtls;
                },
                bpBnsPtnr: function(){
                    return bpPtnrIdDtlssState.bpBnsPtnr();
                }
            }
        });
    };

    function ModalInstanceEditCtrl($scope, $modalInstance,bpPtnrIdDtls,bpBnsPtnr) {
    	$scope.formEdit = true;
        $scope.bpPtnrIdDtls = angular.copy(bpPtnrIdDtls);
        $scope.currentAction=bpInsuredUtils.translations["Entity_edit.title"];
        $scope.bpInsuredUtils=bpInsuredUtils;
        $scope.bpBnsPtnr=bpBnsPtnr;
        $scope.loadCountryNames = bpInsuredUtils.loadCountryNames;
        
        $scope.isClean = function() {
            return angular.equals(bpPtnrIdDtls, $scope.bpPtnrIdDtls);
        };
        $scope.save = function () {
            genericResource.update(bpInsuredUtils.urlBase, $scope.bpPtnrIdDtls)
            .success(function(data){
        		bpPtnrIdDtlssState.replace(data);
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

    function deleteItem(bpPtnrIdDtls){
        genericResource.deleteById(bpInsuredUtils.urlBase, bpPtnrIdDtls.id).success(function(){
            findByLike(bpPtnrIdDtlssState.searchInput, bpPtnrIdDtls.ptnrNbr);
        })
    }
}]);

