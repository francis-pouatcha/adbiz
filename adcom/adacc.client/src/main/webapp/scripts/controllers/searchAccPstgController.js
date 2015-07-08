

angular.module('AdAcc').controller('SearchAccPstgController', function($scope, $http, AccPstgResource ) {

    $scope.search={};
    $scope.currentPage = 0;
    $scope.pageSize= 10;
    $scope.searchResults = [];
    $scope.filteredResults = [];
    $scope.pageRange = [];
    $scope.numberOfPages = function() {
        var result = Math.ceil($scope.filteredResults.length/$scope.pageSize);
        var max = (result == 0) ? 1 : result;
        $scope.pageRange = [];
        for(var ctr=0;ctr<max;ctr++) {
            $scope.pageRange.push(ctr);
        }
        return max;
    };
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

    $scope.performSearch = function() {
        $scope.searchResults = AccPstgResource.queryAll(function(){
            $scope.numberOfPages();
        });
    };
    
    $scope.previous = function() {
       if($scope.currentPage > 0) {
           $scope.currentPage--;
       }
    };
    
    $scope.next = function() {
       if($scope.currentPage < ($scope.numberOfPages() - 1) ) {
           $scope.currentPage++;
       }
    };
    
    $scope.setPage = function(n) {
       $scope.currentPage = n;
    };

    $scope.performSearch();
});