

angular.module('AdSales').controller('EditSlsSOItemController', function($scope, $routeParams, $location, SlsSOItemResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsSOItem = new SlsSOItemResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsSOItems");
        };
        SlsSOItemResource.get({SlsSOItemId:$routeParams.SlsSOItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsSOItem);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsSOItem.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsSOItems");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsSOItems");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsSOItem.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});