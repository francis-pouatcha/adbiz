'use strict';
    
angular.module('AdBnsptnr')
.factory('bpPtnrContactUtils',['$translate','sessionManager',
                function($translate,sessionManager){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/bpptnrcontacts';
    
    service.contactRoleI18nMsgTitleKey = function(enumKey){
    	return "BpPtnrContactRole_"+enumKey+"_description.title";
    };
    service.contactRoleI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.contactRoleI18nMsgTitleKey(enumKey)];
    };
    
    service.bpPtnrContactRoles = [
      {enumKey:'MAIN_CONTACT', translKey:'BpPtnrContactRole_MAIN_CONTACT_description.title'},
      {enumKey:'ALT_CONTACT', translKey:'BpPtnrContactRole_ALT_CONTACT_description.title'},
      {enumKey:'OFFICE_ADDRESS', translKey:'BpPtnrContactRole_OFFICE_ADDRESS_description.title'},
      {enumKey:'HOME_ADDRESS', translKey:'BpPtnrContactRole_HOME_ADDRESS_description.title'},
      {enumKey:'PRIVATE_ADDRESS', translKey:'BpPtnrContactRole_PRIVATE_ADDRESS_description.title'},
      {enumKey:'DELIVERY_ADDRESS', translKey:'BpPtnrContactRole_DELIVERY_ADDRESS_description.title'},
      {enumKey:'INVOICE_ADDRESS', translKey:'BpPtnrContactRole_INVOICE_ADDRESS_description.title'},
      {enumKey:'SUPPORT_CONTACT', translKey:'BpPtnrContactRole_SUPPORT_CONTACT_description.title'},
      {enumKey:'EMERGENCY_CONTACT', translKey:'BpPtnrContactRole_EMERGENCY_CONTACT_description.title'}
    ];
    
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
    	$translate(['BpPtnrContactRole_MAIN_CONTACT_description.title',
    	            'BpPtnrContactRole_ALT_CONTACT_description.title',
    	            'BpPtnrContactRole_OFFICE_ADDRESS_description.title',
    	            'BpPtnrContactRole_HOME_ADDRESS_description.title',
    	            'BpPtnrContactRole_PRIVATE_ADDRESS_description.title',
    	            'BpPtnrContactRole_DELIVERY_ADDRESS_description.title',
    	            'BpPtnrContactRole_INVOICE_ADDRESS_description.title',
    	            'BpPtnrContactRole_SUPPORT_CONTACT_description.title',
    	            'BpPtnrContactRole_EMERGENCY_CONTACT_description.title',
    	            
    	            'BpBnsPtnr_ptnrNbr_description.title',
    	            'BpPtnrContact_langIso2_description.title',
    	            'BpPtnrContact_cntctRole_description.title',
    	            'BpPtnrContact_cntctIndex_description.title',
    	            'BpPtnrContact_description_description.title',
    	            'BpPtnrContact_langIso2_description.text',
    	            'BpPtnrContact_cntctRole_description.text',
    	            'BpPtnrContact_cntctIndex_description.text',
    	            'BpPtnrContact_description_description.text',
    	            
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
.factory('bpPtnrContactsState',['bpBnsPtnrState',function(bpBnsPtnrState){
	
	var service = {
	};
	
	service.bpBnsPtnr = bpBnsPtnrState.resultHandler.entity;
    var searchResultsVar = {};

    // The search state.
    // The current list of business partners.
    service.bpPtnrContacts = function(ptnrNbr){
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

    service.bpPtnrContact = function(index,ptnrNbr){
        var list = service.bpPtnrContacts(ptnrNbr);
        if(!index || index<0 || index>=list.length) return;
        selectedIndexVar=index;
        return list[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(bpPtnrContact){
        if(!bpPtnrContact) return;
        var list = service.bpPtnrContacts(bpPtnrContact.ptnrNbr);

        if(selectedIndexVar>=0 && selectedIndexVar<list.length && list[selectedIndexVar].id==bpPtnrContact.id){
            list[selectedIndexVar]=bpPtnrContact;
        }else {
            for (var index in list) {
                if(list[index].id==bpPtnrContact.id){
                    list[index]=bpPtnrContact;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    service.push = function(bpPtnrContact){
        if(!bpPtnrContact) return false;
        var list = searchResultsVar[bpPtnrContact.ptnrNbr];
        if(!list){
            list = [];
            searchResultsVar[bpPtnrContact.ptnrNbr]=list;
        }
        var length = list.push(bpPtnrContact);
        selectedIndexVar = length-1;
    };

	service.bpPtnrContactActive = function(){
		bpBnsPtnrState.tabManager.isActive(bpBnsPtnrState.bpPtnrContactActive);	
	} 

    service.searchInput = {
        entity:{},
        fieldNames:[]
    };

	return service;

}])
.controller('bpPtnrContactsCtlr',['$scope','genericResource','$modal','$routeParams',
                                  'bpPtnrContactUtils','bpPtnrContactsState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 bpPtnrContactUtils,bpPtnrContactsState,$rootScope){

    $scope.bpPtnrContacts=bpPtnrContactsState.bpPtnrContacts;
    $scope.error = "";
    $scope.bpPtnrContactUtils=bpPtnrContactUtils;
    $scope.genericResource=genericResource;

    var ptnrSelectedUnregisterHdl = $rootScope.$on('BpBnsPtnrsSelected', function(event, data){
        var bpBnsPtnr = bpPtnrContactsState.bpBnsPtnr();
        if(!bpBnsPtnr || !data || !data.bpBnsPtnr || bpBnsPtnr.ptnrNbr!=data.bpBnsPtnr.ptnrNbr) return;
        loadContacts(data.bpBnsPtnr.ptnrNbr);
    });
    $scope.$on('$destroy', function () {
        ptnrSelectedUnregisterHdl();
    });
    init();
    function init(){
        var bpBnsPtnr = bpPtnrContactsState.bpBnsPtnr();
        if(bpBnsPtnr && bpBnsPtnr.ptnrNbr)loadContacts(bpBnsPtnr.ptnrNbr);
    }
    function loadContacts (ptnrNbr){
        if(!bpPtnrContactsState.bpPtnrContactActive()) return;
        if(!bpPtnrContactsState.hasSearchResult(ptnrNbr)) {
            findByLike(bpPtnrContactsState.searchInput, ptnrNbr);
        }
    }

    function findByLike(searchInput,ptnrNbr){
    	searchInput.entity.ptnrNbr=ptnrNbr;
    	if(searchInput.fieldNames.indexOf('ptnrNbr')<0){
    		searchInput.fieldNames.push('ptnrNbr');
    	}
    	genericResource.findByLike(bpPtnrContactUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            bpPtnrContactsState.consumeSearchResult(ptnrNbr, entitySearchResult);
        })
    	.error(function(error){
    		$scope.error = error;
    	});
    }

    $scope.openCreateForm =function(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpBnsPtnr/BpPtnrContactForm.html',
            controller: ModalInstanceCreateCtrl,
            size: size,
            resolve: {
                bpBnsPtnr: function(){
                    return bpPtnrContactsState.bpBnsPtnr();
                }
            }
        });
    };

    var ModalInstanceCreateCtrl = function($scope, $modalInstance,bpBnsPtnr) {
        $scope.formCreate = true;
        $scope.bpPtnrContact;
        $scope.currentAction=bpPtnrContactUtils.translations["Entity_create.title"];
        $scope.bpPtnrContactUtils=bpPtnrContactUtils;
        $scope.error="";
        $scope.bpBnsPtnr=bpBnsPtnr;

        $scope.save = function () {
            $scope.bpPtnrContact.ptnrNbr = bpBnsPtnr.ptnrNbr;
        	genericResource.create(bpPtnrContactUtils.urlBase, $scope.bpPtnrContact)
        	.success(function (bpPtnrContact) {
        		bpPtnrContactsState.push(bpPtnrContact);
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

    $scope.openEditForm = function(size,bpPtnrContact){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpBnsPtnr/BpPtnrContactForm.html',
            controller: ModalInstanceEditCtrl,
            size: size,
            resolve:{
            	bpPtnrContact: function(){
                    return bpPtnrContact;
                },
                bpBnsPtnr: function(){
                    return bpPtnrContactsState.bpBnsPtnr();
                }
            }
        });
    };

    var ModalInstanceEditCtrl = function($scope, $modalInstance,bpPtnrContact,bpBnsPtnr) {
    	$scope.formEdit = true;
        $scope.bpPtnrContact = angular.copy(bpPtnrContact);
        $scope.currentAction=bpPtnrContactUtils.translations["Entity_edit.title"];
        $scope.bpPtnrContactUtils=bpPtnrContactUtils;
        $scope.bpBnsPtnr=bpBnsPtnr;

        $scope.isClean = function() {
            return angular.equals(bpPtnrContact, $scope.bpPtnrContact);
        };
        $scope.save = function () {
            genericResource.update(bpPtnrContactUtils.urlBase, $scope.bpPtnrContact)
            .success(function(data){
        		bpPtnrContactsState.replace(data);
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

    $scope.deleteItem = function(bpPtnrContact){
        genericResource.deleteById(bpPtnrContactUtils.urlBase, bpPtnrContact.id).success(function(){
            findByLike(bpPtnrContactsState.searchInput, bpPtnrContact.ptnrNbr);
        })
    };

}]);

