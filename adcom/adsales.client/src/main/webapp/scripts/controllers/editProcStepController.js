

angular.module('AdSales').controller('EditProcStepController', function($scope, $routeParams, $location, ProcStepResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.procStep = new ProcStepResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/ProcSteps");
        };
        ProcStepResource.get({ProcStepId:$routeParams.ProcStepId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.procStep);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.procStep.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/ProcSteps");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/ProcSteps");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.procStep.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});