
angular.module('AdCshdwr').controller('NewCdrPymntController', function ($scope, $location, locationParser, CdrPymntResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrPymnt = $scope.cdrPymnt || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrPymnts/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrPymntResource.save($scope.cdrPymnt, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrPymnts");
    };
});