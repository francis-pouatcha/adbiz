
angular.module('AdSales').controller('NewSlsSOItemController', function ($scope, $location, locationParser, SlsSOItemResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsSOItem = $scope.slsSOItem || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsSOItems/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsSOItemResource.save($scope.slsSOItem, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsSOItems");
    };
});