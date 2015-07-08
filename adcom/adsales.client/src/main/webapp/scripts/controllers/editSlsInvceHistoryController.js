

angular.module('AdSales').controller('EditSlsInvceHistoryController', function($scope, $routeParams, $location, SlsInvceHistoryResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.slsInvceHistory = new SlsInvceHistoryResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/SlsInvceHistorys");
        };
        SlsInvceHistoryResource.get({SlsInvceHistoryId:$routeParams.SlsInvceHistoryId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.slsInvceHistory);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.slsInvceHistory.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/SlsInvceHistorys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/SlsInvceHistorys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.slsInvceHistory.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});