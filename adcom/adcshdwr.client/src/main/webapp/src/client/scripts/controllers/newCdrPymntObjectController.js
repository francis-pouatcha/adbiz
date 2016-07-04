
angular.module('AdCshdwr').controller('NewCdrPymntObjectController', function ($scope, $location, locationParser, CdrPymntObjectResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrPymntObject = $scope.cdrPymntObject || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrPymntObjects/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrPymntObjectResource.save($scope.cdrPymntObject, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrPymntObjects");
    };
});