
angular.module('AdAcc').controller('NewAccPstgController', function ($scope, $location, locationParser, AccPstgResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.accPstg = $scope.accPstg || {};
    
    $scope.pstgDirList = [
        "DEBIT",
        "CREDIT"
    ];
    
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
    
    $scope.opTypeList = [
        "CAPITAL_PYMT",
        "CAPITAL_WDWL",
        "RENTAL_DPST",
        "TAX_ON_RENT",
        "RENT_PYMT",
        "UNMAT_EXPENSE",
        "OP_EXPENSE",
        "NOOP_EXPENSE",
        "MAT_EXPENSE",
        "UNREAL_INCOME",
        "OP_INCOME",
        "NOOP_INCOME",
        "REAL_INCOME",
        "TREASURY_MVMT",
        "VAT_PYMT",
        "INCOME_TAX_WH",
        "SALARY",
        "SALARY_PYMT",
        "AUX_TAX_PYMT",
        "AMORTISATION",
        "RISK_PROVISION",
        "WARANTY",
        "GRATUITY",
        "DONNATION",
        "STOCK_CORRECT",
        "SALES_COMM",
        "PURCHASE_COMM",
        "SALES",
        "PURCHASE",
        "ASSET_VAL_CORRECT",
        "OTHER_SALES_EXP",
        "OTHER_PURCHASE_EXP",
        "OTHER_SALES_INC",
        "OTHER_PURCHASE_INC",
        "GENERAL_PYMT",
        "GENERAL_ENCASHMT"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/AccPstgs/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        AccPstgResource.save($scope.accPstg, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/AccPstgs");
    };
});