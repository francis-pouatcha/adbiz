
angular.module('AdCshdwr').controller('NewCdrPymntItemController', function ($scope, $location, locationParser, CdrPymntItemResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrPymntItem = $scope.cdrPymntItem || {};
    
    $scope.pymntModeList = [
        "CASH",
        "CHECK",
        "CREDIT_CARD",
        "VOUCHER"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrPymntItems/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrPymntItemResource.save($scope.cdrPymntItem, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrPymntItems");
    };
});