

angular.module('AdSales').controller('EditSlsRoleInSalesController', function($scope, $routeParams, $location, SlsRoleInSalesResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsRoleInSales = new SlsRoleInSalesResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsRoleInSaless");
        };
        SlsRoleInSalesResource.get({SlsRoleInSalesId:$routeParams.SlsRoleInSalesId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsRoleInSales);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsRoleInSales.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsRoleInSaless");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsRoleInSaless");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsRoleInSales.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});