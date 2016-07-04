
angular.module('AdCshdwr').controller('NewCdrDrctSalesController', function ($scope, $location, locationParser, CdrDrctSalesResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrDrctSales = $scope.cdrDrctSales || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrDrctSaless/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrDrctSalesResource.save($scope.cdrDrctSales, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrDrctSaless");
    };
});