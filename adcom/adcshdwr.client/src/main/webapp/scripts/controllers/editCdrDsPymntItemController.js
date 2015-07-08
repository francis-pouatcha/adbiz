

angular.module('AdCshdwr').controller('EditCdrDsPymntItemController', function($scope, $routeParams, $location, CdrDsPymntItemResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrDsPymntItem = new CdrDsPymntItemResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrDsPymntItems");
        };
        CdrDsPymntItemResource.get({CdrDsPymntItemId:$routeParams.CdrDsPymntItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrDsPymntItem);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrDsPymntItem.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrDsPymntItems");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrDsPymntItems");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrDsPymntItem.$remove(successCallback, errorCallback);
    };
    
    $scope.pymntModeList = [
        "CASH",  
        "CHECK",  
        "CREDIT_CARD",  
        "VOUCHER"  
    ];
    
    $scope.get();
});