
angular.module('AdSales').controller('NewSlsSOPtnrController', function ($scope, $location, locationParser, SlsSOPtnrResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsSOPtnr = $scope.slsSOPtnr || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsSOPtnrs/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsSOPtnrResource.save($scope.slsSOPtnr, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsSOPtnrs");
    };
});