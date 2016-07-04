

angular.module('AdCshdwr').controller('EditCdrCshDrawerController', function($scope, $routeParams, $location, CdrCshDrawerResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrCshDrawer = new CdrCshDrawerResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrCshDrawers");
        };
        CdrCshDrawerResource.get({CdrCshDrawerId:$routeParams.CdrCshDrawerId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrCshDrawer);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrCshDrawer.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrCshDrawers");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrCshDrawers");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrCshDrawer.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});