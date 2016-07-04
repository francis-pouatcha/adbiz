

angular.module('AdCshdwr').controller('EditCdrPymntObjectController', function($scope, $routeParams, $location, CdrPymntObjectResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrPymntObject = new CdrPymntObjectResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrPymntObjects");
        };
        CdrPymntObjectResource.get({CdrPymntObjectId:$routeParams.CdrPymntObjectId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrPymntObject);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrPymntObject.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrPymntObjects");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrPymntObjects");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrPymntObject.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});