
angular.module('AdSales').controller('NewSlsAcctController', function ($scope, $location, locationParser, SlsAcctResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.slsAcct = $scope.slsAcct || {};
    
    $scope.amtSideList = [
        "D",
        "C"
    ];
    
    $scope.blnceSideList = [
        "D",
        "C"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/SlsAccts/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SlsAcctResource.save($scope.slsAcct, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/SlsAccts");
    };
});