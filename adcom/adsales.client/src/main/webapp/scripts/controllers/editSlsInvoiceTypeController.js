

angular.module('AdSales').controller('EditSlsInvoiceTypeController', function($scope, $routeParams, $location, SlsInvoiceTypeResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsInvoiceType = new SlsInvoiceTypeResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsInvoiceTypes");
        };
        SlsInvoiceTypeResource.get({SlsInvoiceTypeId:$routeParams.SlsInvoiceTypeId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsInvoiceType);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsInvoiceType.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsInvoiceTypes");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsInvoiceTypes");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsInvoiceType.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});