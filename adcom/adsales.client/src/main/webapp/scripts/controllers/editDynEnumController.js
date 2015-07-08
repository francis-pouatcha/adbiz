

angular.module('AdSales').controller('EditDynEnumController', function($scope, $routeParams, $location, DynEnumResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.dynEnum = new DynEnumResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/DynEnums");
        };
        DynEnumResource.get({DynEnumId:$routeParams.DynEnumId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.dynEnum);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.dynEnum.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/DynEnums");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/DynEnums");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.dynEnum.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});