(function () {
    'use strict';
    angular.module('AdBase').controller('secTermEditController',secTermEditController);

    secTermEditController.$inject = ['$scope', 'secTerminalService','$location','$routeParams'];

    function secTermEditController($scope,secTerminalService, $location,$routeParams){
        var self = this ;
        self.secTerm = {};
        self.edit = edit;
        self.error = "";

        load();
        
        function edit(){
            secTerminalService.update(self.secTerm).then(function(result){
                $location.path('/secTerm/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };

        function load(){
            var identif = $routeParams.identif ;
            secTerminalService.loadOne(identif).then(function(result){
                self.secTerm = result;
            },function(error){
                self.error = error;
            })
        };
    };



})();