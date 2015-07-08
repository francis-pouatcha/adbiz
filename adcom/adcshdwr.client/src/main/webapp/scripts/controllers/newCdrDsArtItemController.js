
angular.module('AdCshdwr').controller('NewCdrDsArtItemController', function ($scope, $location, locationParser, CdrDsArtItemResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cdrDsArtItem = $scope.cdrDsArtItem || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CdrDsArtItems/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CdrDsArtItemResource.save($scope.cdrDsArtItem, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CdrDsArtItems");
    };
});