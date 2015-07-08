

angular.module('AdSales').controller('EditSlsAcctController', function($scope, $routeParams, $location, SlsAcctResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsAcct = new SlsAcctResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsAccts");
        };
        SlsAcctResource.get({SlsAcctId:$routeParams.SlsAcctId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsAcct);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsAcct.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsAccts");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsAccts");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsAcct.$remove(successCallback, errorCallback);
    };
    
    $scope.amtSideList = [
        "D",  
        "C"  
    ];
    $scope.blnceSideList = [
        "D",  
        "C"  
    ];
    
    $scope.get();
});