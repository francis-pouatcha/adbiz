

angular.module('AdAcc').controller('EditAccCoAController', function($scope, $routeParams, $location, AccCoAResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.accCoA = new AccCoAResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/AccCoAs");
        };
        AccCoAResource.get({AccCoAId:$routeParams.AccCoAId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.accCoA);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.accCoA.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/AccCoAs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/AccCoAs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.accCoA.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});