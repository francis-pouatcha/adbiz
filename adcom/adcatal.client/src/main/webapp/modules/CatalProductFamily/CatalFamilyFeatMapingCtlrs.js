'use strict';
    
angular.module('AdCatal')

.controller('catalArtFeatMappingsCtlr',['$scope','catalFamilyFeatMapingResource','$modal','$routeParams',
                                        function($scope,catalFamilyFeatMapingResource,$modal,$routeParams){
	
    var self = this ;
    $scope.catalArtFeatMappingsCtlr = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.catalArtFeatMappings = [];
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.openEditForm = openEditForm;
    self.openCreateForm = openCreateForm;
    self.ModalInstanceEditCtrl = ModalInstanceEditCtrl ;
    self.ModalInstanceCreateCtrl = ModalInstanceCreateCtrl ;
    self.handleSelectedItem = handleSelectedItem;
    self.error = "";
    
    init();
    function init(){
        self.searchInput = {
            entity:{},
            fieldNames:[]
        }
        findByLike(self.searchInput);
    }
    function findByLike(searchInput){
    	searchInput.entity.pfIdentif=$routeParams.famCode;
    	searchInput.fieldNames.push('pfIdentif');
    	catalFamilyFeatMapingResource.findByLike(searchInput)
    	.success(function(entitySearchResult) {
            self.catalArtFeatMappings = entitySearchResult.resultList;
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

        function handleSelectedItem(index){
            index = index ? index : 0 ;
            self.selectedIndex = index ;
            angular.copy(self.catalArtFeatMappings[self.selectedIndex],self.selectedItem ) ;
        };


        function openCreateForm(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/CatalProductFamily/CatalFamilyFeatMapingForm.html',
                controller: self.ModalInstanceCreateCtrl,
                size: size
            });
        };

        function ModalInstanceCreateCtrl($scope, $modalInstance) {
            $scope.formCreate = false;
            $scope.currentAction="Entity_create.title";
            $scope.catalFeatMapping;

            $scope.save = function () {
            	catalFamilyFeatMapingResource.create($scope.catalFeatMapping).success(function () {
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
                templateUrl: 'views/CatalProductFamily/CatalFamilyFeatMapingForm.html',
                controller: self.ModalInstanceEditCtrl,
                size: size,
                resolve:{
                	catalFeatMapping: function(){
                        return self.selectedItem;
                    }
                }
            });
        };

        function ModalInstanceEditCtrl($scope, $modalInstance,catalFeatMapping,$timeout) {
            $scope.formCreate = false;
            $scope.currentAction="Entity_edit.title";
            $scope.catalFeatMapping = angular.copy(catalFeatMapping);

            $scope.isClean = function() {
                return angular.equals(catalFeatMapping, $scope.catalFeatMapping);
            };


            $scope.save = function () {
            	catalFamilyFeatMapingResource.update($scope.catalFeatMapping).success(function(){
                   init();
                });
                $modalInstance.dismiss('cancel');
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        };
    }]);

