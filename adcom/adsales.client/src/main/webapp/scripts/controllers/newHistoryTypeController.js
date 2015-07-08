
angular.module('AdSales').controller('NewHistoryTypeController', function ($scope, $location, locationParser, HistoryTypeResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.historyType = $scope.historyType || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/HistoryTypes/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        HistoryTypeResource.save($scope.historyType, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/HistoryTypes");
    };
});