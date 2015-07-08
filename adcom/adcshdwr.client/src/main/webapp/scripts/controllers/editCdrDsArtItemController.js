

angular.module('AdCshdwr').controller('EditCdrDsArtItemController', function($scope, $routeParams, $location, CdrDsArtItemResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrDsArtItem = new CdrDsArtItemResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrDsArtItems");
        };
        CdrDsArtItemResource.get({CdrDsArtItemId:$routeParams.CdrDsArtItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrDsArtItem);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrDsArtItem.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrDsArtItems");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrDsArtItems");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrDsArtItem.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});