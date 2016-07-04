
angular.module('AdCshdwr').controller('NewCdrDsPymntItemController', function ($scope, $location, locationParser, CdrDsPymntItemResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrDsPymntItem = $scope.cdrDsPymntItem || {};
    
    $scope.pymntModeList = [
        "CASH",
        "CHECK",
        "CREDIT_CARD",
        "VOUCHER"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrDsPymntItems/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrDsPymntItemResource.save($scope.cdrDsPymntItem, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrDsPymntItems");
    };
});