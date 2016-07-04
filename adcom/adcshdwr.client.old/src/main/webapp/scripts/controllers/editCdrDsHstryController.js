

angular.module('AdCshdwr').controller('EditCdrDsHstryController', function($scope, $routeParams, $location, CdrDsHstryResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrDsHstry = new CdrDsHstryResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrDsHstrys");
        };
        CdrDsHstryResource.get({CdrDsHstryId:$routeParams.CdrDsHstryId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrDsHstry);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrDsHstry.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrDsHstrys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrDsHstrys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrDsHstry.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});