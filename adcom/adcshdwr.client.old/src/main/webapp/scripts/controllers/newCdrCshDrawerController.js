
angular.module('AdCshdwr').controller('NewCdrCshDrawerController', function ($scope, $location, locationParser, CdrCshDrawerResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrCshDrawer = $scope.cdrCshDrawer || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrCshDrawers/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrCshDrawerResource.save($scope.cdrCshDrawer, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrCshDrawers");
    };
});