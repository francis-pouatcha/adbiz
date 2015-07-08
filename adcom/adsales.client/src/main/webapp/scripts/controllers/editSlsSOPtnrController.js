

angular.module('AdSales').controller('EditSlsSOPtnrController', function($scope, $routeParams, $location, SlsSOPtnrResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsSOPtnr = new SlsSOPtnrResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsSOPtnrs");
        };
        SlsSOPtnrResource.get({SlsSOPtnrId:$routeParams.SlsSOPtnrId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsSOPtnr);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsSOPtnr.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsSOPtnrs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsSOPtnrs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsSOPtnr.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});