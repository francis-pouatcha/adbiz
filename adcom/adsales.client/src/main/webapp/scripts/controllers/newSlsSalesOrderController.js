
angular.module('AdSales').controller('NewSlsSalesOrderController', function ($scope, $location, locationParser, SlsSalesOrderResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsSalesOrder = $scope.slsSalesOrder || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsSalesOrders/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsSalesOrderResource.save($scope.slsSalesOrder, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsSalesOrders");
    };
});