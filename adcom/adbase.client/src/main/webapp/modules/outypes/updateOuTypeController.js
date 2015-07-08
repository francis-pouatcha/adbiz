'use strict';

angular.module('AdBase')
    .controller('updateOuTypeController', ['$scope', 'ouTypeResource', '$location','$routeParams',
        function ($scope, ouTypeResource,$location,$routeParams) {
            $scope.outype = {};
            //controllers's methods
            $scope.update = function () {
                 ouTypeResource.update($scope.ouType).success(function(data){
                    $location.path('/outypes/show/'+data.id);
                }).error(function(){});
            };
            $scope.goToList = function (){
                $location.path('/outypes/list');
            }
            
            //others functions
            function loadParentsOrgUnit() {
                ouTypeResource.findActifsFromNow().success(function (data) {
                    $scope.parents = data.resultList;
                    console.log($scope.parents);
                }).error(function () {});
            }
            //load the actual outype to edit
            function loadOuType(id) {
                ouTypeResource.findById(id).success(function(data){
                   $scope.ouType = data; 
                }).error(function(error){
                    //log
                });
            }
            //init controller
            function init() {
                loadParentsOrgUnit();
                loadOuType($routeParams.ouTypeId);
            }
            init();
    }]);