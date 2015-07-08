

angular.module('AdAcc').controller('EditAccBrrController', function($scope, $routeParams, $location, AccBrrResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.accBrr = new AccBrrResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/AccBrrs");
        };
        AccBrrResource.get({AccBrrId:$routeParams.AccBrrId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.accBrr);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.accBrr.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/AccBrrs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/AccBrrs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.accBrr.$remove(successCallback, errorCallback);
    };
    
    $scope.brrTypeList = [
        "COST_CTR",  
        "COST_BRR",  
        "CUSTOMER",  
        "SUPPLIER",  
        "BROKER",  
        "BUSINESS_PTNR"  
    ];
    
    $scope.get();
});