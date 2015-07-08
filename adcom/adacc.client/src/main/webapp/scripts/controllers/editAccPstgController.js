

angular.module('AdAcc').controller('EditAccPstgController', function($scope, $routeParams, $location, AccPstgResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.accPstg = new AccPstgResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/AccPstgs");
        };
        AccPstgResource.get({AccPstgId:$routeParams.AccPstgId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.accPstg);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.accPstg.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/AccPstgs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/AccPstgs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.accPstg.$remove(successCallback, errorCallback);
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
    
    $scope.get();
});