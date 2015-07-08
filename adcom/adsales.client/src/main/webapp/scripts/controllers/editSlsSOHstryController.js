

angular.module('AdSales').controller('EditSlsSOHstryController', function($scope, $routeParams, $location, SlsSOHstryResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsSOHstry = new SlsSOHstryResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsSOHstrys");
        };
        SlsSOHstryResource.get({SlsSOHstryId:$routeParams.SlsSOHstryId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsSOHstry);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsSOHstry.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsSOHstrys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsSOHstrys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsSOHstry.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});