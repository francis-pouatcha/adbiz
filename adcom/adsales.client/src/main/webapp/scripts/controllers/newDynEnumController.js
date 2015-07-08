
angular.module('AdSales').controller('NewDynEnumController', function ($scope, $location, locationParser, DynEnumResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.dynEnum = $scope.dynEnum || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/DynEnums/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        DynEnumResource.save($scope.dynEnum, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/DynEnums");
    };
});