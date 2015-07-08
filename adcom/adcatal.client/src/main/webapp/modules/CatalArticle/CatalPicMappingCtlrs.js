'use strict';
    
angular.module('AdCatal')

.controller('catalPicMappingsCtlr',['$scope','catalPicMappingResource','$modal','$routeParams',function($scope,catalPicMappingResource,$modal,$routeParams){
	
    var self = this ;
    $scope.catalPicMappingsCtlr = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.catalPicMappings = [];
    self.cipOrigines = [];
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.artIdentif;
    self.openEditForm = openEditForm;
    self.openCreateForm = openCreateForm;
    self.ModalInstanceEditCtrl = ModalInstanceEditCtrl ;
    self.ModalInstanceCreateCtrl = ModalInstanceCreateCtrl ;
    self.handleSelectedItem = handleSelectedItem;
    self.error = "";
    self.deleteItem = deleteItem;
    
    init();
    function init(){
        self.artIdentif = $routeParams.pic;
        self.searchInput = {
            entity:{},
            fieldNames:[]
        }
        findByLike(self.searchInput);
    }
    function findByLike(searchInput){
    	searchInput.entity.artIdentif=self.artIdentif;
    	searchInput.fieldNames.push('artIdentif');
    	catalPicMappingResource.findByLike(searchInput)
    	.success(function(entitySearchResult) {
            self.catalPicMappings = entitySearchResult.resultList;
            self.cipOrigines = entitySearchResult.cipOrigines;
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

    function handleSelectedItem(index){
        index = index ? index : 0 ;
        self.selectedIndex = index ;
        angular.copy(self.catalPicMappings[self.selectedIndex],self.selectedItem) ;
    };


    function openCreateForm(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/CatalArticle/CatalPicMappingForm.html',
            controller: self.ModalInstanceCreateCtrl,
            size: size,
            resolve:{
            	cipOrigines: function(){
                    return self.cipOrigines;
                }
            }
            
        });
    };

    function ModalInstanceCreateCtrl($scope, $modalInstance,cipOrigines) {
        $scope.formCreate = false;
        $scope.catalPicMapping;
        $scope.cipOrigines=cipOrigines;
        $scope.selectedCipOrigine=cipOrigines.length>0?cipOrigines[0]:null;
        $scope.currentAction="Entity_create.title";
        $scope.save = function () {
        	$scope.catalPicMapping.codeOrigin=$scope.selectedCipOrigine.enumKey;
            $scope.catalPicMapping.artIdentif = self.artIdentif;
            catalPicMappingResource.create($scope.catalPicMapping).success(function () {
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
            templateUrl: 'views/CatalArticle/CatalPicMappingForm.html',
            controller: self.ModalInstanceEditCtrl,
            size: size,
            resolve:{
                catalPicMapping: function(){
                    return self.selectedItem;
                },
		    	cipOrigines: function(){
		            return self.cipOrigines;
		        }
            }
        });
    };

    function ModalInstanceEditCtrl($scope, $modalInstance,catalPicMapping,$timeout,cipOrigines) {
        $scope.formCreate = false;
        $scope.catalPicMapping = catalPicMapping;
        $scope.cipOrigines=cipOrigines;
        $scope.selectedCipOrigine=function(){
        	for (var i = 0; i < cipOrigines.length; i++) {
        		if(cipOrigines[i].enumKey==catalPicMapping.codeOrigin) return cipOrigines[i]; 
        	}
        	if(cipOrigines.length>0)return cipOrigines[0];
        	return null;
        }();
        $scope.currentAction="Entity_edit.title";
        $scope.isClean = function() {
            return !angular.equals(catalPicMapping, $scope.catalPicMapping);
        };


        $scope.save = function () {
        	$scope.catalPicMapping.codeOrigin=$scope.selectedCipOrigine.enumKey;
            $scope.catalPicMapping.artIdentif = self.artIdentif;
            catalPicMappingResource.update($scope.catalPicMapping).success(function(){
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
            catalPicMappingResource.deleteById(self.selectedItem.identif).success(function(){
                init();
            })
     }
}]);

