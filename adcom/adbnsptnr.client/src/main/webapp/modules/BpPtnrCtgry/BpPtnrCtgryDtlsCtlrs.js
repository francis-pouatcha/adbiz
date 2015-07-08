'use strict';
    
angular.module('AdBnsptnr')
.factory('bpPtnrCtgryDtlsUtils',['$translate','sessionManager',
                function($translate,sessionManager){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/bpptnrctgrydtlss';
    
    service.languageI18nMsgTitleKey = function(enumKey){
    	return "BaseLanguage_"+enumKey+"_description.title";
    };
    service.languageI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.languageI18nMsgTitleKey(enumKey)];
    };

    service.baseLanguages = [
      {enumKey:'fr', translKey:'BaseLanguage_fr_description.title'},
      {enumKey:'en', translKey:'BaseLanguage_en_description.title'}
    ];
    
    service.translate = function(){
    	$translate(['BpPtnrCtgryDtls_ctgryCode_description.title',
    	            'BpPtnrCtgryDtls_langIso2_description.title',
    	            'BpPtnrCtgryDtls_name_description.title',
    	            'BpPtnrCtgryDtls_description_description.title',
    	            'BpPtnrCtgry_ctgryCode_description.title',
    	            
    	            'BaseLanguage_fr_description.title',
    	            'BaseLanguage_en_description.title',
    	            
    	            'Entity_create.title',
    	            'Entity_edit.title',
    	            'Entity_required.title',
    	            'Entity_save.title',
    	            'Entity_cancel.title'
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.translate();

    return service;
}])
.factory('bpPtnrCtgryDtlssState',['bpPtnrCtgryState',function(bpPtnrCtgryState){
	
	var service = {
	};
	
	service.bpPtnrCtgry = bpPtnrCtgryState.bpPtnrCtgry;
    var searchResultsVar = {};

    // The search state.
    // The current list of business partners.
    service.bpPtnrCtgryDtlss = function(ctgryCode){
        var nbr = ctgryCode;
        if(!ctgryCode) {
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

    service.consumeSearchResult = function(ctgryCode, entitySearchResult){
        if(entitySearchResult.resultList){
            searchResultsVar[ctgryCode] = entitySearchResult.resultList;
        } else {
            searchResultsVar[ctgryCode] = [];
        }
    };
    
    service.hasSearchResult = function(ctgryCode){
    	var res = searchResultsVar[ctgryCode];
    	if(res) return true;
    	return false;
    }

    service.bpPtnrCtgryDtls = function(index,ctgryCode){
        var list = service.bpPtnrCtgryDtlss(ctgryCode);
        if(!index || index<0 || index>=list.length) return;
        selectedIndexVar=index;
        return list[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(bpPtnrCtgryDtls){
        if(!bpPtnrCtgryDtls) return;
        var list = service.bpPtnrCtgryDtlss(bpPtnrCtgryDtls.ctgryCode);

        if(selectedIndexVar>=0 && selectedIndexVar<list.length && list[selectedIndexVar].id==bpPtnrCtgryDtls.id){
            list[selectedIndexVar]=bpPtnrCtgryDtls;
        }else {
            for (var index in list) {
                if(list[index].id==bpPtnrCtgryDtls.id){
                    list[index]=bpPtnrCtgryDtls;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    service.push = function(bpPtnrCtgryDtls){
        if(!bpPtnrCtgryDtls) return false;
        var list = searchResultsVar[bpPtnrCtgryDtls.ctgryCode];
        if(!list){
            list = [];
            searchResultsVar[bpPtnrCtgryDtls.ctgryCode]=list;
        }
        var length = list.push(bpPtnrCtgryDtls);
        selectedIndexVar = length-1;
    };

	service.bpPtnrCtgryDtlsActive= bpPtnrCtgryState.bpPtnrCtgryDtlsActive;

    service.searchInput = {
        entity:{},
        fieldNames:[]
    };

	return service;

}])
.controller('bpPtnrCtgryDtlssCtlr',['$scope','genericResource','$modal','$routeParams',
                                  'bpPtnrCtgryDtlsUtils','bpPtnrCtgryDtlssState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 bpPtnrCtgryDtlsUtils,bpPtnrCtgryDtlssState,$rootScope){

    $scope.bpPtnrCtgryDtlss=bpPtnrCtgryDtlssState.bpPtnrCtgryDtlss;
    $scope.error = "";
    $scope.bpPtnrCtgryDtlsUtils=bpPtnrCtgryDtlsUtils;
    $scope.genericResource=genericResource;

    var ptnrSelectedUnregisterHdl = $rootScope.$on('BpPtnrCtgrysSelected', function(event, data){
        var bpPtnrCtgry = bpPtnrCtgryDtlssState.bpPtnrCtgry();
        if(!bpPtnrCtgry || !data || !data.bpPtnrCtgry || bpPtnrCtgry.ctgryCode!=data.bpPtnrCtgry.ctgryCode) return;
        loadCtgryDtls(data.bpPtnrCtgry.ctgryCode);
    });
    $scope.$on('$destroy', function () {
        ptnrSelectedUnregisterHdl();
    });
    init();
    function init(){
        var bpPtnrCtgry = bpPtnrCtgryDtlssState.bpPtnrCtgry();
        if(bpPtnrCtgry && bpPtnrCtgry.ctgryCode)loadCtgryDtls(bpPtnrCtgry.ctgryCode);
    }
    function loadCtgryDtls (ctgryCode){
        if(!bpPtnrCtgryDtlssState.bpPtnrCtgryDtlsActive()) return;
        if(!bpPtnrCtgryDtlssState.hasSearchResult(ctgryCode)) {
            findByLike(bpPtnrCtgryDtlssState.searchInput, ctgryCode);
        }
    }

    function findByLike(searchInput,ctgryCode){
    	searchInput.entity.ctgryCode=ctgryCode;
    	if(searchInput.fieldNames.indexOf('ctgryCode')<0){
    		searchInput.fieldNames.push('ctgryCode');
    	}
    	genericResource.findByLike(bpPtnrCtgryDtlsUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            bpPtnrCtgryDtlssState.consumeSearchResult(ctgryCode, entitySearchResult);
        })
    	.error(function(error){
    		$scope.error = error;
    	});
    }

    $scope.openCreateForm =function(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpPtnrCtgry/BpPtnrCtgryDtlsForm.html',
            controller: ModalInstanceCreateCtrl,
            size: size,
            resolve: {
                bpPtnrCtgry: function(){
                    return bpPtnrCtgryDtlssState.bpPtnrCtgry();
                }
            }
        });
    };

    var ModalInstanceCreateCtrl = function($scope, $modalInstance,bpPtnrCtgry) {
        $scope.formCreate = true;
        $scope.bpPtnrCtgryDtls;
        $scope.currentAction=bpPtnrCtgryDtlsUtils.translations["Entity_create.title"];
        $scope.bpPtnrCtgryDtlsUtils=bpPtnrCtgryDtlsUtils;
        $scope.error="";
        $scope.bpPtnrCtgry=bpPtnrCtgry;

        $scope.save = function () {
            $scope.bpPtnrCtgryDtls.ctgryCode = bpPtnrCtgry.ctgryCode;
        	genericResource.create(bpPtnrCtgryDtlsUtils.urlBase, $scope.bpPtnrCtgryDtls)
        	.success(function (bpPtnrCtgryDtls) {
        		bpPtnrCtgryDtlssState.push(bpPtnrCtgryDtls);
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

    $scope.openEditForm = function(size,bpPtnrCtgryDtls){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpPtnrCtgry/BpPtnrCtgryDtlsForm.html',
            controller: ModalInstanceEditCtrl,
            size: size,
            resolve:{
            	bpPtnrCtgryDtls: function(){
                    return bpPtnrCtgryDtls;
                },
                bpPtnrCtgry: function(){
                    return bpPtnrCtgryDtlssState.bpPtnrCtgry();
                }
            }
        });
    };

    var ModalInstanceEditCtrl = function($scope, $modalInstance,bpPtnrCtgryDtls,bpPtnrCtgry) {
    	$scope.formEdit = true;
        $scope.bpPtnrCtgryDtls = angular.copy(bpPtnrCtgryDtls);
        $scope.currentAction=bpPtnrCtgryDtlsUtils.translations["Entity_edit.title"];
        $scope.bpPtnrCtgryDtlsUtils=bpPtnrCtgryDtlsUtils;
        $scope.bpPtnrCtgry=bpPtnrCtgry;

        $scope.isClean = function() {
            return angular.equals(bpPtnrCtgryDtls, $scope.bpPtnrCtgryDtls);
        };
        $scope.save = function () {
            genericResource.update(bpPtnrCtgryDtlsUtils.urlBase, $scope.bpPtnrCtgryDtls)
            .success(function(data){
        		bpPtnrCtgryDtlssState.replace(data);
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

    $scope.deleteItem = function(bpPtnrCtgryDtls){
        genericResource.deleteById(bpPtnrCtgryDtlsUtils.urlBase, bpPtnrCtgryDtls.id).success(function(){
            findByLike(bpPtnrCtgryDtlssState.searchInput, bpPtnrCtgryDtls.ctgryCode);
        })
    };

}]);

