

angular.module('AdSales').controller('EditSlsSttlmtOpController', function($scope, $routeParams, $location, SlsSttlmtOpResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsSttlmtOp = new SlsSttlmtOpResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsSttlmtOps");
        };
        SlsSttlmtOpResource.get({SlsSttlmtOpId:$routeParams.SlsSttlmtOpId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsSttlmtOp);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsSttlmtOp.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsSttlmtOps");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsSttlmtOps");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsSttlmtOp.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});