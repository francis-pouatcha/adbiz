

angular.module('AdSales').controller('EditSlsInvcePtnrController', function($scope, $routeParams, $location, SlsInvcePtnrResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsInvcePtnr = new SlsInvcePtnrResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsInvcePtnrs");
        };
        SlsInvcePtnrResource.get({SlsInvcePtnrId:$routeParams.SlsInvcePtnrId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsInvcePtnr);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsInvcePtnr.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsInvcePtnrs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsInvcePtnrs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsInvcePtnr.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});