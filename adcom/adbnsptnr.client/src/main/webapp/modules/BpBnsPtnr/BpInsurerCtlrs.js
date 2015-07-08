'use strict';
    
angular.module('AdBnsptnr')
.factory('bpPtnrContactUtils',function(){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/bpptnrcontacts';
    
    service.contactRoleI18nMsgTitleKey = function(enumKey){
    	return "BpPtnrContactRole_"+enumKey+"_description.title";
    }
    
    service.bpPtnrContactRoles = [
      {enumKey:'MAIN_CONTACT', translKey:'BpPtnrContactRole_MAIN_CONTACT_description.title'},
      {enumKey:'ALT_CONTACT', translKey:'BpPtnrContactRole_ALT_CONTACT_description.title'},
      {enumKey:'HOME_ADDRESS', translKey:'BpPtnrContactRole_HOME_ADDRESS_description.title'},
      {enumKey:'PRIVATE_ADDRESS', translKey:'BpPtnrContactRole_PRIVATE_ADDRESS_description.title'},
      {enumKey:'DELIVERY_ADDRESS', translKey:'BpPtnrContactRole_DELIVERY_ADDRESS_description.title'},
      {enumKey:'INVOICE_ADDRESS', translKey:'BpPtnrContactRole_INVOICE_ADDRESS_description.title'},
      {enumKey:'SUPPORT_CONTACT', translKey:'BpPtnrContactRole_SUPPORT_CONTACT_description.title'},
      {enumKey:'EMERGENCY_CONTACT', translKey:'BpPtnrContactRole_EMERGENCY_CONTACT_description.title'}
    ];
    return service;
})
.controller('bpPtnrContactsCtlr',['$scope','genericResource','$modal','$routeParams','bpPtnrContactUtils',
                                        function($scope,genericResource,$modal,$routeParams,bpPtnrContactUtils){
	
    var self = this ;
    $scope.bpPtnrContactsCtlr = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.bpPtnrContacts = [];
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.ptnrNbr;
    self.openEditForm = openEditForm;
    self.openCreateForm = openCreateForm;
    self.ModalInstanceEditCtrl = ModalInstanceEditCtrl ;
    self.ModalInstanceCreateCtrl = ModalInstanceCreateCtrl ;
    self.handleSelectedItem = handleSelectedItem;
    self.error = "";
    self.deleteItem = deleteItem;
    self.bpPtnrContactUtils=bpPtnrContactUtils;
    
    init();
    function init(){
        self.ptnrNbr = $routeParams.ptnrNbr;
        self.searchInput = {
            entity:{},
            fieldNames:[]
        }
        findByLike(self.searchInput);
    }
    function findByLike(searchInput){
    	searchInput.entity.ptnrNbr=self.ptnrNbr;
    	searchInput.fieldNames.push('ptnrNbr');
    	genericResource.findByLike(bpPtnrContactUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            self.bpPtnrContacts = entitySearchResult.resultList;
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

        function handleSelectedItem(index){
            index = index ? index : 0 ;
            self.selectedIndex = index ;
            angular.copy(self.bpPtnrContacts[self.selectedIndex],self.selectedItem ) ;
        };


        function openCreateForm(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/BpBnsPtnr/BpPtnrContactForm.html',
                controller: self.ModalInstanceCreateCtrl,
                size: size,
                resolve:{
                    bpPtnrContactUtils: function(){
                    	return self.bpPtnrContactUtils;
                    }
                }
            });
        };

        function ModalInstanceCreateCtrl($scope, $modalInstance,bpPtnrContactUtils) {
            $scope.formCreate = false;
            $scope.bpPtnrContact;
            $scope.currentAction="Entity_create.title";
            $scope.bpPtnrContactUtils=bpPtnrContactUtils;

            $scope.save = function () {
                $scope.bpPtnrContact.ptnrNbr = self.ptnrNbr;
            	genericResource.create(bpPtnrContactUtils.urlBase, $scope.bpPtnrContact).success(function () {
                    init();
                });
                $modalInstance.dismiss('cancel');
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        }


        function openEditForm(size,index){
            handleSelectedItem(index);
            var modalInstance = $modal.open({
                templateUrl: 'views/BpBnsPtnr/BpPtnrContactForm.html',
                controller: self.ModalInstanceEditCtrl,
                size: size,
                resolve:{
                	bpPtnrContact: function(){
                        return self.selectedItem;
                    },
                    bpPtnrContactUtils: function(){
                    	return self.bpPtnrContactUtils;
                    }
                }
            });
        };

        function ModalInstanceEditCtrl($scope, $modalInstance,bpPtnrContact,$timeout,bpPtnrContactUtils) {
            $scope.formCreate = false;
            $scope.bpPtnrContact = bpPtnrContact;
            $scope.currentAction="Entity_edit.title";
            $scope.bpPtnrContactUtils=bpPtnrContactUtils;

            $scope.isClean = function() {
                return !angular.equals(bpPtnrContact, $scope.bpPtnrContact);
            };


            $scope.save = function () {
                $scope.bpPtnrContact.ptnrNbr = self.ptnrNbr;
            	genericResource.update(bpPtnrContactUtils.urlBase, $scope.bpPtnrContact).success(function(){
                   init();
                });
                $modalInstance.dismiss('cancel');
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        };

        function deleteItem(index){
            handleSelectedItem();
            genericResource.deleteById(bpPtnrContactUtils.urlBase, self.selectedItem.id).success(function(){
                init();
            })
        }

    }]);

