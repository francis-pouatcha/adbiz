
angular.module('AdSales').controller('NewSlsSttlmtOpController', function ($scope, $location, locationParser, SlsSttlmtOpResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsSttlmtOp = $scope.slsSttlmtOp || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsSttlmtOps/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsSttlmtOpResource.save($scope.slsSttlmtOp, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsSttlmtOps");
    };
});