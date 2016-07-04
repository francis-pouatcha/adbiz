
angular.module('AdCshdwr').controller('NewCdrVchrHstryController', function ($scope, $location, locationParser, CdrVchrHstryResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrVchrHstry = $scope.cdrVchrHstry || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrVchrHstrys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrVchrHstryResource.save($scope.cdrVchrHstry, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrVchrHstrys");
    };
});