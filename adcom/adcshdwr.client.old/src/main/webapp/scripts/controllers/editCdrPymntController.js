

angular.module('AdCshdwr').controller('EditCdrPymntController', function($scope, $routeParams, $location, CdrPymntResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrPymnt = new CdrPymntResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrPymnts");
        };
        CdrPymntResource.get({CdrPymntId:$routeParams.CdrPymntId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrPymnt);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrPymnt.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrPymnts");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrPymnts");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrPymnt.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});