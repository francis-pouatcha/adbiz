(function () {
    'use strict';
    angular.module('AdBase').controller('secTermCreateController',secTermCreateController);

    secTermCreateController.$inject = ['$scope', 'secTerminalService','$location'];

    function secTermCreateController($scope,secTerminalService, $location){
        var self = this ;
        self.secTerm = {};
        self.create = create;
        self.error = "";

        function create(){
            secTerminalService.create(self.secTerm).then(function(result){
                $location.path('/secTerm/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };
    };



})();