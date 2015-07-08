
angular.module('AdAcc').controller('NewAccCoAController', function ($scope, $location, locationParser, AccCoAResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.accCoA = $scope.accCoA || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/AccCoAs/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        AccCoAResource.save($scope.accCoA, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/AccCoAs");
    };
});