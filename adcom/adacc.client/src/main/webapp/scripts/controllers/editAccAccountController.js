

angular.module('AdAcc').controller('EditAccAccountController', function($scope, $routeParams, $location, AccAccountResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.accAccount = new AccAccountResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/AccAccounts");
        };
        AccAccountResource.get({AccAccountId:$routeParams.AccAccountId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.accAccount);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.accAccount.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/AccAccounts");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/AccAccounts");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.accAccount.$remove(successCallback, errorCallback);
    };
    
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
    
    $scope.get();
});