

angular.module('AdSales').controller('EditSlsInvceStatusController', function($scope, $routeParams, $location, SlsInvceStatusResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsInvceStatus = new SlsInvceStatusResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsInvceStatuss");
        };
        SlsInvceStatusResource.get({SlsInvceStatusId:$routeParams.SlsInvceStatusId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsInvceStatus);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsInvceStatus.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsInvceStatuss");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsInvceStatuss");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsInvceStatus.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});