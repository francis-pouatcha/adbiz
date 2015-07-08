'use strict';
    
angular.module('AdCatal')

.controller('catalArtManufSuppsCtlr',['$scope','catalArtManufSuppResource','bplegalptnridsResource','$modal','$routeParams','$q',function($scope,catalArtManufSuppResource,bplegalptnridsResource,$modal,$routeParams,$q){
	
    var self = this ;
    $scope.catalArtManufSuppsCtlr = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.catalArtManufSupps = [];
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
    self.cipOrigines = [];
    self.currencys = ['XAF','EUR','NGN','USD'];
    self.loadBusinessPartner = loadBusinessPartner;

    init();
    function init(){
        self.artIdentif = $routeParams.pic;
        self.searchInput = {
            entity:{},
            fieldNames:[]
        }
        findByLike(self.searchInput);
    }

    function loadBusinessPartner(businessPartnerName){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        if(businessPartnerName){
            searchInput.entity.cpnyName = businessPartnerName+'%';
            searchInput.fieldNames.push('cpnyName');
        }
        var deferred = $q.defer();
        bplegalptnridsResource.findByLike(searchInput).success(function (entitySearchResult) {
            deferred.resolve(entitySearchResult);
        }).error(function(){
            deferred.reject("No Manufacturer/Supplier");
        });
        return deferred.promise;
    }

    function findByLike(searchInput){
    	searchInput.entity.artIdentif=self.artIdentif;
    	searchInput.fieldNames.push('artIdentif');
    	catalArtManufSuppResource.findByLike(searchInput)
    	.success(function(entitySearchResult) {
            self.catalArtManufSupps = entitySearchResult.resultList;
            self.cipOrigines = entitySearchResult.cipOrigines;
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

        function handleSelectedItem(index){
            index = index ? index : 0 ;
            self.selectedIndex = index ;
            angular.copy(self.catalArtManufSupps[self.selectedIndex],self.selectedItem ) ;
        };


        function openCreateForm(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/CatalArticle/CatalArtManufSuppForm.html',
                controller: self.ModalInstanceCreateCtrl,
                size: size,
                resolve: {
                    cipOrigines: function () {
                        return self.cipOrigines;
                    },
                    currencys: function () {
                        return self.currencys;
                    }
                }
            });
        };

        function ModalInstanceCreateCtrl($scope, $modalInstance,cipOrigines,currencys) {
            $scope.formCreate = false;
            $scope.catalArtManufSupp = {};
            $scope.currentAction="Entity_create.title";
            $scope.currencys = currencys;
            $scope.cipOrigines=cipOrigines;
            $scope.selectedCipOrigine=cipOrigines.length>0?cipOrigines[0]:null;

            $scope.loadBusinessPartner = function(val){
                return self.loadBusinessPartner(val).then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                })
            }
            $scope.loading = true;

            $scope.save = function () {
                $scope.catalArtManufSupp.msType=$scope.selectedCipOrigine.enumKey;
                $scope.catalArtManufSupp.msIdentif = $scope.catalArtManufSupp.msIdentif.identif;
                $scope.catalArtManufSupp.artIdentif = self.artIdentif;
            	catalArtManufSuppResource.create($scope.catalArtManufSupp).success(function () {
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
                templateUrl: 'views/CatalArticle/CatalArtManufSuppForm.html',
                controller: self.ModalInstanceEditCtrl,
                size: size,
                resolve:{
                	catalArtManufSupp: function(){
                        return self.selectedItem;
                    },
                    cipOrigines: function(){
                        return self.cipOrigines;
                    },
                    currencys: function () {
                        return self.currencys;
                    }
                }

            });
        };

        function ModalInstanceEditCtrl($scope, $modalInstance,catalArtManufSupp,cipOrigines,currencys) {
            $scope.formCreate = false;
            $scope.catalArtManufSupp = catalArtManufSupp;
            $scope.currentAction="Entity_edit.title";
            $scope.cipOrigines=cipOrigines;
            $scope.currencys = currencys;
            $scope.selectedCipOrigine=function(){
                for (var i = 0; i < cipOrigines.length; i++) {
                    if(cipOrigines[i].enumKey==catalArtManufSupp.msType) return cipOrigines[i];
                }
                if(cipOrigines.length>0)return cipOrigines[0];
                return null;
            }();

            $scope.loadBusinessPartner = function(val){
                return self.loadBusinessPartner(val).then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                })
            }
            $scope.loading = true;

            $scope.isClean = function() {
                return !angular.equals(catalArtManufSupp, $scope.catalArtManufSupp);
            };

            $scope.save = function () {
                $scope.catalArtManufSupp.artIdentif = self.artIdentif;
                $scope.catalArtManufSupp.msIdentif = $scope.catalArtManufSupp.msIdentif.identif;
                $scope.catalArtManufSupp.msType=$scope.selectedCipOrigine.enumKey;
            	catalArtManufSuppResource.update($scope.catalArtManufSupp).success(function(){
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
            catalArtManufSuppResource.deleteById(self.selectedItem.id).success(function(){
                init();
            })
        }

    }]);

