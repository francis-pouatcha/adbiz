

angular.module('AdSales').controller('EditSlsSalesStatusController', function($scope, $routeParams, $location, SlsSalesStatusResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsSalesStatus = new SlsSalesStatusResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsSalesStatuss");
        };
        SlsSalesStatusResource.get({SlsSalesStatusId:$routeParams.SlsSalesStatusId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsSalesStatus);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsSalesStatus.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsSalesStatuss");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsSalesStatuss");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsSalesStatus.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});