'use strict';
    
angular.module('AdCatal')

.controller('catalArtFeatMappingsCtlr',['$scope','catalArtFeatMappingResource','$modal','$routeParams',
                                        function($scope,catalArtFeatMappingResource,$modal,$routeParams){
	
    var self = this ;
    $scope.catalArtFeatMappingsCtlr = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.catalArtFeatMappings = [];
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
    	catalArtFeatMappingResource.findByLike(searchInput)
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
                templateUrl: 'views/CatalArticle/CatalArtFeatMappingForm.html',
                controller: self.ModalInstanceCreateCtrl,
                size: size
            });
        };

        function ModalInstanceCreateCtrl($scope, $modalInstance) {
            $scope.formCreate = false;
            $scope.catalFeatMapping;
            $scope.currentAction="Entity_create.title";

            $scope.save = function () {
                $scope.catalFeatMapping.artIdentif = self.artIdentif;
            	catalArtFeatMappingResource.create($scope.catalFeatMapping).success(function () {
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
                templateUrl: 'views/CatalArticle/CatalArtFeatMappingForm.html',
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
            $scope.catalFeatMapping = catalFeatMapping;
            $scope.currentAction="Entity_edit.title";

            $scope.isClean = function() {
                return !angular.equals(catalFeatMapping, $scope.catalFeatMapping);
            };


            $scope.save = function () {
                $scope.catalFeatMapping.artIdentif = self.artIdentif;
            	catalArtFeatMappingResource.update($scope.catalFeatMapping).success(function(){
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
            catalArtFeatMappingResource.deleteById(self.selectedItem.id).success(function(){
                init();
            })
        }

    }]);

