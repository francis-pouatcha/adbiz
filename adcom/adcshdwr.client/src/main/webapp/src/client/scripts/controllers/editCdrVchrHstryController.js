

angular.module('AdCshdwr').controller('EditCdrVchrHstryController', function($scope, $routeParams, $location, CdrVchrHstryResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrVchrHstry = new CdrVchrHstryResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrVchrHstrys");
        };
        CdrVchrHstryResource.get({CdrVchrHstryId:$routeParams.CdrVchrHstryId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrVchrHstry);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrVchrHstry.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrVchrHstrys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrVchrHstrys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrVchrHstry.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});