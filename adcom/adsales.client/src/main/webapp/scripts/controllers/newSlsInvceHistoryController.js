
angular.module('AdSales').controller('NewSlsInvceHistoryController', function ($scope, $location, locationParser, SlsInvceHistoryResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsInvceHistory = $scope.slsInvceHistory || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsInvceHistorys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsInvceHistoryResource.save($scope.slsInvceHistory, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsInvceHistorys");
    };
});