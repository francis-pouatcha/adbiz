
angular.module('AdSales').controller('NewSlsInvceItemController', function ($scope, $location, locationParser, SlsInvceItemResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsInvceItem = $scope.slsInvceItem || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsInvceItems/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsInvceItemResource.save($scope.slsInvceItem, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsInvceItems");
    };
});