

angular.module('AdSales').controller('EditSlsInvoiceController', function($scope, $routeParams, $location, SlsInvoiceResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsInvoice = new SlsInvoiceResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsInvoices");
        };
        SlsInvoiceResource.get({SlsInvoiceId:$routeParams.SlsInvoiceId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsInvoice);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsInvoice.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsInvoices");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsInvoices");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsInvoice.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});