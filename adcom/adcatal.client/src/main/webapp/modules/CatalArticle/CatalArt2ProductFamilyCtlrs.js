'use strict';
    
angular.module('AdCatal')

.controller('catalArt2ProductFamilyCtrl',['$scope','catalArt2ProductFamilyResource','$modal','$routeParams','catalProductFamilyResource',function($scope,catalArt2ProductFamilyResource,$modal,$routeParams,catalProductFamilyResource){
	
    var self = this ;
    $scope.catalArt2ProductFamilyCtrl = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.catalArt2ProductFamilies = [];
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
        catalArt2ProductFamilyResource.findByArtPic(artPic).success(function(result){
            self.catalArt2ProductFamilies = result;
        }).error(function(error){
            self.error = error;
        });
    }

        function handleSelectedItem(index){
            index = index ? index : 0 ;
            self.selectedIndex = index ;
            angular.copy(self.catalArt2ProductFamilies[self.selectedIndex],self.selectedItem ) ;
        };


        function openCreateForm(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/CatalArticle/CatalArt2ProductFamilyForm.html',
                controller: self.ModalInstanceCreateCtrl,
                size: size,
                resolve: {
                    artPic: function () {
                        return self.artPic;
                    }
                }
            });
        };

        function ModalInstanceCreateCtrl($scope, $modalInstance,catalProductFamilyResource,artPic) {
            $scope.formCreate = false;
            $scope.catalArt2ProductFamily = {};
            $scope.catalArt2ProductFamily.artPic = artPic;
            $scope.currentAction="Entity_create.title";
            $scope.catalProductFamilies = [];
            loadDependencies();
            
            function loadDependencies() {
                var catalSearchInput = catalProductFamilyResource.getSearchInput();
                catalSearchInput.max = -1;
                catalProductFamilyResource.findCustom(catalSearchInput).success(function (result){
                    $scope.catalProductFamilies = result.resultList;
                }).error (function (error) {
                   //log error. 
                });
            }
            $scope.save = function () {
            	catalArt2ProductFamilyResource.createCustom(artPic,$scope.catalArt2ProductFamily).success(function () {
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
                templateUrl: 'views/CatalArticle/CatalArt2ProductFamilyForm.html',
                controller: self.ModalInstanceEditCtrl,
                size: size,
                resolve:{
                	catalArt2ProductFamilies: function(){
                        return self.selectedItem;
                    },
                    artPic: function(){
                        return self.artPic;
                    }
                }

            });
        };

        function ModalInstanceEditCtrl($scope, $modalInstance,catalProductFamilyResource,catalArt2ProductFamily,artPic) {
            $scope.formCreate = false;
            $scope.catalArt2ProductFamily = catalArt2ProductFamily;
            $scope.catalArt2ProductFamily.artPic = artPic;
            $scope.currentAction="Entity_edit.title";
            
            loadDependencies();
            function loadDependencies() {
                var catalSearchInput = catalProductFamilyResource.getSearchInput();
                catalSearchInput.max = -1;
                catalProductFamilyResource.findCustom(catalSearchInput).success(function (result){
                    $scope.catalProductFamilies = result.resultList;
                }).error (function (error) {
                   //log error. 
                });
            }
            $scope.save = function () {
            	catalArt2ProductFamilyResource.update($scope.catalArt2ProductFamily).success(function(){
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
            catalArt2ProductFamilyResource.deleteById(self.selectedItem.id).success(function(){
                init();
            })
        }

    }]);