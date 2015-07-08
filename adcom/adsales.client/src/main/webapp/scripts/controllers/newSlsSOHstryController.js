
angular.module('AdSales').controller('NewSlsSOHstryController', function ($scope, $location, locationParser, SlsSOHstryResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsSOHstry = $scope.slsSOHstry || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsSOHstrys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsSOHstryResource.save($scope.slsSOHstry, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsSOHstrys");
    };
});