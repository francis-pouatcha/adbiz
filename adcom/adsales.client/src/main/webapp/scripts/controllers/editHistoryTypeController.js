

angular.module('AdSales').controller('EditHistoryTypeController', function($scope, $routeParams, $location, HistoryTypeResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.historyType = new HistoryTypeResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/HistoryTypes");
        };
        HistoryTypeResource.get({HistoryTypeId:$routeParams.HistoryTypeId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.historyType);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.historyType.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/HistoryTypes");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/HistoryTypes");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.historyType.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});