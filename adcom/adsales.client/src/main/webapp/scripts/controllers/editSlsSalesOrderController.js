

angular.module('AdSales').controller('EditSlsSalesOrderController', function($scope, $routeParams, $location, SlsSalesOrderResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsSalesOrder = new SlsSalesOrderResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsSalesOrders");
        };
        SlsSalesOrderResource.get({SlsSalesOrderId:$routeParams.SlsSalesOrderId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsSalesOrder);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsSalesOrder.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsSalesOrders");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsSalesOrders");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsSalesOrder.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});