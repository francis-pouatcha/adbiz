
angular.module('AdAcc').controller('NewAccBlncController', function ($scope, $location, locationParser, AccBlncResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.accBlnc = $scope.accBlnc || {};
    
    $scope.pstgDirList = [
        "DEBIT",
        "CREDIT"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/AccBlncs/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        AccBlncResource.save($scope.accBlnc, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/AccBlncs");
    };
});