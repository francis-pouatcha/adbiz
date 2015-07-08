
angular.module('AdSales').controller('NewSlsInvoiceController', function ($scope, $location, locationParser, SlsInvoiceResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsInvoice = $scope.slsInvoice || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsInvoices/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsInvoiceResource.save($scope.slsInvoice, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsInvoices");
    };
});