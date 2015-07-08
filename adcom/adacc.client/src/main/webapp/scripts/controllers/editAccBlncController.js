

angular.module('AdAcc').controller('EditAccBlncController', function($scope, $routeParams, $location, AccBlncResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.accBlnc = new AccBlncResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/AccBlncs");
        };
        AccBlncResource.get({AccBlncId:$routeParams.AccBlncId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.accBlnc);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.accBlnc.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/AccBlncs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/AccBlncs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.accBlnc.$remove(successCallback, errorCallback);
    };
    
    $scope.pstgDirList = [
        "DEBIT",  
        "CREDIT"  
    ];
    
    $scope.get();
});