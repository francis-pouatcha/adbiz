
angular.module('AdSales').controller('NewSlsInvcePtnrController', function ($scope, $location, locationParser, SlsInvcePtnrResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsInvcePtnr = $scope.slsInvcePtnr || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsInvcePtnrs/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsInvcePtnrResource.save($scope.slsInvcePtnr, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsInvcePtnrs");
    };
});