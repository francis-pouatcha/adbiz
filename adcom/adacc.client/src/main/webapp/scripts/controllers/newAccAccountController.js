
angular.module('AdAcc').controller('NewAccAccountController', function ($scope, $location, locationParser, AccAccountResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.accAccount = $scope.accAccount || {};
    
    $scope.accTypeList = [
        "CAPITAL_ACC",
        "ESTATE_ASSETS_ACC",
        "GENERAL_ASSETS_ACC",
        "THIRD_PARTY_ACC",
        "TREASURY_ACC",
        "EXPENSE_ACC",
        "INCOME_ACC",
        "CLOSING_ACC"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/AccAccounts/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        AccAccountResource.save($scope.accAccount, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/AccAccounts");
    };
});