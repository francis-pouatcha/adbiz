'use strict';
    
angular.module('AdBnsptnr')
.factory('bpInsurranceUtils',['$translate','$rootScope','bpBnsPtnrUtils',
                function($translate,$rootScope,bpBnsPtnrUtils){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/insurrances';
    service.loadInsurances=bpBnsPtnrUtils.loadInsurances;

    service.translate = function(){
    	$translate(['BpBnsPtnr_ptnrNbr_description.title',
    	            'BpInsurrance_insurerNbr_description.title',
    	            'BpInsurrance_findAnInsurance_description.title',
    	            'BpInsurrance_contractNbr_description.title',
    	            'BpInsurrance_coverRatePct_description.title',
    	            'BpInsurrance_decutible_description.title',
    	            'BpInsurrance_description_description.title',
    	            'BpInsurrance_beginDate_description.title',
    	            'BpInsurrance_endDate_description.title',
    	            
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
.factory('bpInsurrancesState',['bpBnsPtnrState',function(bpBnsPtnrState){
	
	var service = {
	};
	
	service.bpBnsPtnr = bpBnsPtnrState.resultHandler.entity;
    var searchResultsVar = {};

    // The search state.
    // The current list of business partners.
    service.bpInsurrances = function(ptnrNbr){
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
    
    service.bpInsurrance = function(index,ptnrNbr){
        var list = service.bpInsurrances(ptnrNbr);
        if(!index || index<0 || index>=list.length) return;
        selectedIndexVar=index;
        return list[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(bpInsurrance){
        if(!bpInsurrance) return;
        var list = service.bpInsurrances(bpInsurrance.insuredNbr);

        if(selectedIndexVar>=0 && selectedIndexVar<list.length && list[selectedIndexVar].id==bpInsurrance.id){
            list[selectedIndexVar]=bpInsurrance;
        }else {
            for (var index in list) {
                if(list[index].id==bpInsurrance.id){
                    list[index]=bpInsurrance;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    service.push = function(bpInsurrance){
        if(!bpInsurrance) return false;
        var list = searchResultsVar[bpInsurrance.insuredNbr];
        if(!list){
            list = [];
            searchResultsVar[bpInsurrance.insuredNbr]=list;
        }
        var length = list.push(bpInsurrance);
        selectedIndexVar = length-1;
    };

	service.bpInsurranceActive= function(){
		bpBnsPtnrState.tabManager.isActive(bpBnsPtnrState.bpInsurranceTabName);	
	} 

    service.searchInput = {
        entity:{},
        fieldNames:[]
    };

	return service;

}])

.controller('bpInsurrancesCtlr',['$scope','genericResource','$modal','$routeParams',
                                 'bpInsurranceUtils','bpInsurrancesState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 bpInsurranceUtils,bpInsurrancesState,$rootScope){
	
    $scope.bpInsurrances=bpInsurrancesState.bpInsurrances;
    $scope.error = "";
    $scope.bpInsurranceUtils=bpInsurranceUtils;
    $scope.genericResource=genericResource;

    var ptnrSelectedUnregisterHdl = $rootScope.$on('BpBnsPtnrsSelected', function(event, data){
        var bpBnsPtnr = bpInsurrancesState.bpBnsPtnr();
        if(!bpBnsPtnr || !data || !data.bpBnsPtnr || bpBnsPtnr.ptnrNbr!=data.bpBnsPtnr.ptnrNbr) return;
        loadPtnrIdDtlss(data.bpBnsPtnr.ptnrNbr);
    });
    $scope.$on('$destroy', function () {
    	ptnrSelectedUnregisterHdl();
    });
    init();
    function init(){
        var bpBnsPtnr = bpInsurrancesState.bpBnsPtnr();
        if(bpBnsPtnr && bpBnsPtnr.ptnrNbr)loadPtnrIdDtlss(bpBnsPtnr.ptnrNbr);
    }
    function loadPtnrIdDtlss(ptnrNbr){
        if(!bpInsurrancesState.bpInsurranceActive()) return;
        if(!bpInsurrancesState.hasSearchResult(ptnrNbr)) {
            findByLike(bpInsurrancesState.searchInput, ptnrNbr);
        }
    }

    function findByLike(searchInput,ptnrNbr){
    	searchInput.entity.ptnrNbr=ptnrNbr;
    	if(searchInput.fieldNames.indexOf('insuredNbr')<0){
    		searchInput.fieldNames.push('insuredNbr');
    	}
    	genericResource.findByLike(bpInsurranceUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
    		bpInsurrancesState.consumeSearchResult(ptnrNbr, entitySearchResult);
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

    $scope.openCreateForm =function(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpBnsPtnr/BpInsurranceForm.html',
            controller: ModalInstanceCreateCtrl,
            size: size,
            resolve: {
                bpBnsPtnr: function(){
                    return bpInsurrancesState.bpBnsPtnr();
                }
            }
        });
    };

    function ModalInstanceCreateCtrl($scope, $modalInstance,bpBnsPtnr) {
        $scope.formCreate = true;
        $scope.bpInsurrance={
        	issuedDt:new Date(),
        	expirdDt:new Date()
        };
        $scope.currentAction=bpInsurranceUtils.translations["Entity_create.title"];
        $scope.bpInsurranceUtils=bpInsurranceUtils;
        $scope.error="";
        $scope.bpBnsPtnr=bpBnsPtnr;
        $scope.loadCountryNames = bpInsurranceUtils.loadCountryNames;
        
        $scope.save = function () {
            $scope.bpInsurrance.insuredNbr = bpBnsPtnr.ptnrNbr;
        	genericResource.create(bpInsurranceUtils.urlBase, $scope.bpInsurrance)
        	.success(function (bpInsurrance) {
        		bpInsurrancesState.push(bpInsurrance);
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

    function openEditForm(size,index,bpInsurrance){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpBnsPtnr/BpInsurranceForm.html',
            controller: self.ModalInstanceEditCtrl,
            size: size,
            resolve:{
            	bpInsurrance: function(){
                    return bpInsurrance;
                },
                bpBnsPtnr: function(){
                    return bpInsurrancesState.bpBnsPtnr();
                }
            }
        });
    };

    function ModalInstanceEditCtrl($scope, $modalInstance,bpInsurrance,bpBnsPtnr) {
    	$scope.formEdit = true;
        $scope.bpInsurrance = angular.copy(bpInsurrance);
        $scope.currentAction=bpInsurranceUtils.translations["Entity_edit.title"];
        $scope.bpInsurranceUtils=bpInsurranceUtils;
        $scope.bpBnsPtnr=bpBnsPtnr;
        $scope.loadInsurances = bpInsurranceUtils.loadInsurances;
        
        $scope.isClean = function() {
            return angular.equals(bpInsurrance, $scope.bpInsurrance);
        };
        $scope.save = function () {
            genericResource.update(bpInsurranceUtils.urlBase, $scope.bpInsurrance)
            .success(function(data){
        		bpInsurrancesState.replace(data);
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

    function deleteItem(bpInsurrance){
        genericResource.deleteById(bpInsurranceUtils.urlBase, bpInsurrance.id).success(function(){
            findByLike(bpInsurrancesState.searchInput, bpInsurrance.insuredNbr);
        })
    }
}]);

