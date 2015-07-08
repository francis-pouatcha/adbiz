'use strict';

angular.module('AdBase')
    .controller('listOuTypeController', ['$scope', 'ouTypeResource', 'ouTypeService',
        function ($scope, ouTypeResource, ouTypeService) {
            $scope.outypes = [];
            $scope.searchInput = {
                entity: {},
                start: 0,
                max: 20,
                fieldNames: []
            };
            $scope.parents = [];
            $scope.delete = function (ouId) {
                return ouTypeResource.delete(ouId);
            };
            $scope.search = function () {
                return ouTypeResource.search(organisationUnit);
            };

            $scope.doSearch = function () {
                executeSearch($scope.searchInput);
            };

            $scope.print = function () {
                //print
            };

            function executeSearch(searchInput) {
                ouTypeResource.search(searchInput).success(function (data) {
                    $scope.outypes = data.resultList;
                    console.log($scope.outypes);
                }).error(function (data) {
                    //log
                });
            }
            //init function
            function init() {
                executeSearch($scope.searchInput);
                ouTypeResource.findActifsFromNow().success(function (data) {
                    $scope.parents = data.resultList;
                    console.log($scope.parents);
                }).error(function () {});
                console.log($scope.parents);
            }
            init();
}]);