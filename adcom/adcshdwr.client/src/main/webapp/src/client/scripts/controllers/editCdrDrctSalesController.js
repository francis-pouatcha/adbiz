

angular.module('AdCshdwr').controller('EditCdrDrctSalesController', function($scope, $routeParams, $location, CdrDrctSalesResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrDrctSales = new CdrDrctSalesResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrDrctSaless");
        };
        CdrDrctSalesResource.get({CdrDrctSalesId:$routeParams.CdrDrctSalesId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrDrctSales);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrDrctSales.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrDrctSaless");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrDrctSaless");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrDrctSales.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});