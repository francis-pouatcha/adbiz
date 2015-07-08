
angular.module('AdSales').controller('NewProcStepController', function ($scope, $location, locationParser, ProcStepResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.procStep = $scope.procStep || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/ProcSteps/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ProcStepResource.save($scope.procStep, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/ProcSteps");
    };
});