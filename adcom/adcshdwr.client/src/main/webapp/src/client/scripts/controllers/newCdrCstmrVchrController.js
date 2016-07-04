
angular.module('AdCshdwr').controller('NewCdrCstmrVchrController', function ($scope, $location, locationParser, CdrCstmrVchrResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrCstmrVchr = $scope.cdrCstmrVchr || {};
    
    $scope.canceledList = [
        "true",
        " false"
    ];
    
    $scope.settledList = [
        "true",
        " false"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrCstmrVchrs/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrCstmrVchrResource.save($scope.cdrCstmrVchr, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrCstmrVchrs");
    };
});