
angular.module('AdSales').controller('NewSlsInvoiceTypeController', function ($scope, $location, locationParser, SlsInvoiceTypeResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsInvoiceType = $scope.slsInvoiceType || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsInvoiceTypes/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsInvoiceTypeResource.save($scope.slsInvoiceType, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsInvoiceTypes");
    };
});