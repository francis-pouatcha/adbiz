
angular.module('AdCshdwr').controller('NewCdrPymtHstryController', function ($scope, $location, locationParser, CdrPymtHstryResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrPymtHstry = $scope.cdrPymtHstry || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrPymtHstrys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrPymtHstryResource.save($scope.cdrPymtHstry, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrPymtHstrys");
    };
});