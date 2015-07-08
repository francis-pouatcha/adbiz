

angular.module('AdCshdwr').controller('EditCdrCstmrVchrController', function($scope, $routeParams, $location, CdrCstmrVchrResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrCstmrVchr = new CdrCstmrVchrResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrCstmrVchrs");
        };
        CdrCstmrVchrResource.get({CdrCstmrVchrId:$routeParams.CdrCstmrVchrId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrCstmrVchr);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrCstmrVchr.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrCstmrVchrs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrCstmrVchrs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrCstmrVchr.$remove(successCallback, errorCallback);
    };
    
    $scope.canceledList = [
        "true",  
        " false"  
    ];
    $scope.settledList = [
        "true",  
        " false"  
    ];
    
    $scope.get();
});