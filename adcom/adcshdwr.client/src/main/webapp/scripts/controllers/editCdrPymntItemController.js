

angular.module('AdCshdwr').controller('EditCdrPymntItemController', function($scope, $routeParams, $location, CdrPymntItemResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.cdrPymntItem = new CdrPymntItemResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CdrPymntItems");
        };
        CdrPymntItemResource.get({CdrPymntItemId:$routeParams.CdrPymntItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.cdrPymntItem);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.cdrPymntItem.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CdrPymntItems");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CdrPymntItems");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.cdrPymntItem.$remove(successCallback, errorCallback);
    };
    
    $scope.pymntModeList = [
        "CASH",  
        "CHECK",  
        "CREDIT_CARD",  
        "VOUCHER"  
    ];
    
    $scope.get();
});