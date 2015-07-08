(function () {
    'use strict';
    angular.module('AdBase').controller('secTermShowController',secTermShowController);

    secTermShowController.$inject = ['$scope', 'secTerminalService','$location','$routeParams'];

    function secTermShowController($scope,secTerminalService, $location,$routeParams){
        var self = this ;
        self.secTerm = {};
        self.remove = remove;

       show();

        function show(){
            var identif = $routeParams.identif ;
            secTerminalService.loadOne(identif).then(function(result){
               self.secTerm = result;
            })
        };

        function remove(id){
            secTerminalService.delete(id).then(function(result){
                $location.path('/secTerm');
            })
        }
    };
})();