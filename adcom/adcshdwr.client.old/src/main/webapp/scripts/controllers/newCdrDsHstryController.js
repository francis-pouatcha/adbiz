
angular.module('AdCshdwr').controller('NewCdrDsHstryController', function ($scope, $location, locationParser, CdrDsHstryResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrDsHstry = $scope.cdrDsHstry || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrDsHstrys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrDsHstryResource.save($scope.cdrDsHstry, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrDsHstrys");
    };
});