
angular.module('AdSales').controller('NewSlsRoleInSalesController', function ($scope, $location, locationParser, SlsRoleInSalesResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsRoleInSales = $scope.slsRoleInSales || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsRoleInSaless/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsRoleInSalesResource.save($scope.slsRoleInSales, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsRoleInSaless");
    };
});