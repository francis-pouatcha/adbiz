'use strict';
    
angular.module('AdCatal')

.controller('catalArtDetailConfigCtlr',['$scope','catalArtDetailConfigResource','$modal','$routeParams',function($scope,catalArtDetailConfigResource,$modal,$routeParams){
	
    var self = this ;
    $scope.catalArtDetailConfigCtlr = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.catalArtDetailConfigs = [];
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.artPic;
    self.openEditForm = openEditForm;
    self.openCreateForm = openCreateForm;
    self.ModalInstanceEditCtrl = ModalInstanceEditCtrl ;
    self.ModalInstanceCreateCtrl = ModalInstanceCreateCtrl ;
    self.handleSelectedItem = handleSelectedItem;
    self.error = "";
    self.deleteItem = deleteItem;
    
    init();
    function init(){
        self.artPic = $routeParams.pic;
        search(self.artPic);
    }
    function search(artPic){
        catalArtDetailConfigResource.findByArtPic(artPic).success(function(result){
            self.catalArtDetailConfigs = result;
        }).error(function(error){
            self.error = error;
        });
    }

        function handleSelectedItem(index){
            index = index ? index : 0 ;
            self.selectedIndex = index ;
            angular.copy(self.catalArtDetailConfigs[self.selectedIndex],self.selectedItem ) ;
        };


        function openCreateForm(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/CatalArticle/CatalArtDetailConfigForm.html',
                controller: self.ModalInstanceCreateCtrl,
                size: size,
                resolve: {
                    artPic: function () {
                        return self.artPic;
                    }
                }
            });
        };

        function ModalInstanceCreateCtrl($scope, $modalInstance,artPic) {
            $scope.formCreate = false;
            $scope.artPic = artPic;
            $scope.currentAction="Entity_create.title";

            $scope.save = function () {
                $scope.catalArtDetailConfig.pic = $scope.artPic;
            	catalArtDetailConfigResource.create($scope.catalArtDetailConfig).success(function () {
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
                templateUrl: 'views/CatalArticle/CatalArtDetailConfigForm.html',
                controller: self.ModalInstanceEditCtrl,
                size: size,
                resolve:{
                	catalArtDetailConfig: function(){
                        return self.selectedItem;
                    },
                    artPic: function(){
                        return self.artPic;
                    }
                }

            });
        };

        function ModalInstanceEditCtrl($scope, $modalInstance,catalArtDetailConfig,artPic) {
            $scope.formCreate = false;
            $scope.catalArtDetailConfig = catalArtDetailConfig;
            $scope.currentAction="Entity_edit.title";

            $scope.save = function () {
                $scope.catalArtDetailConfig.pic = artPic;
            	catalArtDetailConfigResource.update($scope.catalArtDetailConfig).success(function(){
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
            catalArtDetailConfigResource.deleteById(self.selectedItem.id).success(function(){
                init();
            })
        }

    }]);

