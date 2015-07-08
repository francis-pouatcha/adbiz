
angular.module('AdSales').controller('NewSlsInvceStatusController', function ($scope, $location, locationParser, SlsInvceStatusResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsInvceStatus = $scope.slsInvceStatus || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsInvceStatuss/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsInvceStatusResource.save($scope.slsInvceStatus, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsInvceStatuss");
    };
});