(function () {
    'use strict';
    angular.module('AdBase').controller('localityShowController',localityShowController);

    localityShowController.$inject = ['$scope', 'localityService','$location','$routeParams'];

    function localityShowController($scope,localityService, $location,$routeParams){
        var self = this ;
        self.locality = {};
        self.remove = remove;

       show();

        function show(){
            var identif = $routeParams.identif ;
            localityService.loadOne(identif).then(function(result){
               self.locality = result;
            })
        };

        function remove(id){
            localityService.delete(id).then(function(result){
                $location.path('/locality');
            })
        }
    };
})();