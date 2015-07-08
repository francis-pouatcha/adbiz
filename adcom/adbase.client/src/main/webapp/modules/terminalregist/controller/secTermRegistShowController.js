(function () {
    'use strict';
    angular.module('AdBase').controller('secTermRegistShowController',secTermRegistShowController);

    secTermRegistShowController.$inject = ['$scope', 'secTerminalRegistService','$location','$routeParams'];

    function secTermRegistShowController($scope,secTerminalRegistService, $location,$routeParams){
        var self = this ;
        self.secTermRegist = {};
        self.remove = remove;

       show();

        function show(){
            var identif = $routeParams.identif ;
            secTerminalRegistService.loadOne(identif).then(function(result){
               self.secTermRegist = result;
            })
        };

        function remove(id){
            secTerminalRegistService.delete(id).then(function(result){
                $location.path('/sectermregistr');
            })
        }
    };
})();