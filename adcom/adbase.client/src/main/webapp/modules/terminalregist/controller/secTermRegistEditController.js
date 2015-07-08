(function () {
    'use strict';
    angular.module('AdBase').controller('secTermRegistEditController',secTermRegistEditController);

    secTermRegistEditController.$inject = ['$scope', 'secTerminalRegistService','$location','$routeParams'];

    function secTermRegistEditController($scope,secTerminalRegistService, $location,$routeParams){
        var self = this ;
        self.secTermRegist = {};
        self.edit = edit;
        self.error = "";

        load();
        
        function edit(){
            secTerminalRegistService.update(self.secTermRegist).then(function(result){
                $location.path('/sectermregistr/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };

        function load(){
            var identif = $routeParams.identif ;
            secTerminalRegistService.loadOne(identif).then(function(result){
                self.secTermRegist = result;
            },function(error){
                self.error = error;
            })
        };
    };



})();