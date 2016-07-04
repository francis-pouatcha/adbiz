

angular.module('AdCshdwr').controller('EditCdrPymtHstryController', function($scope, $routeParams, $location, CdrPymtHstryResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrPymtHstry = new CdrPymtHstryResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrPymtHstrys");
        };
        CdrPymtHstryResource.get({CdrPymtHstryId:$routeParams.CdrPymtHstryId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrPymtHstry);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrPymtHstry.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrPymtHstrys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrPymtHstrys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrPymtHstry.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});