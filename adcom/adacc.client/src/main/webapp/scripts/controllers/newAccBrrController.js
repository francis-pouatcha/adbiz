
angular.module('AdAcc').controller('NewAccBrrController', function ($scope, $location, locationParser, AccBrrResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.accBrr = $scope.accBrr || {};
    
    $scope.brrTypeList = [
        "COST_CTR",
        "COST_BRR",
        "CUSTOMER",
        "SUPPLIER",
        "BROKER",
        "BUSINESS_PTNR"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/AccBrrs/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        AccBrrResource.save($scope.accBrr, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/AccBrrs");
    };
});