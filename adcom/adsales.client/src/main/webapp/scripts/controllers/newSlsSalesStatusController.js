
angular.module('AdSales').controller('NewSlsSalesStatusController', function ($scope, $location, locationParser, SlsSalesStatusResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsSalesStatus = $scope.slsSalesStatus || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsSalesStatuss/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsSalesStatusResource.save($scope.slsSalesStatus, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsSalesStatuss");
    };
});