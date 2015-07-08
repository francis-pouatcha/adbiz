

angular.module('AdSales').controller('EditSlsInvceItemController', function($scope, $routeParams, $location, SlsInvceItemResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsInvceItem = new SlsInvceItemResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsInvceItems");
        };
        SlsInvceItemResource.get({SlsInvceItemId:$routeParams.SlsInvceItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsInvceItem);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsInvceItem.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsInvceItems");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsInvceItems");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsInvceItem.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});