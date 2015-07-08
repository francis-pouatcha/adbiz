(function () {
    'use strict';
    angular.module('AdBase').controller('secTermRegistCreateController',secTermRegistCreateController);

    secTermRegistCreateController.$inject = ['$scope', 'secTerminalRegistService','$location'];

    function secTermRegistCreateController($scope,secTerminalRegistService, $location){
        var self = this ;
        self.secTermRegist = {};
        self.create = create;
        self.error = "";

        function create(){
            secTerminalRegistService.create(self.secTermRegist).then(function(result){
                $location.path('/sectermregistr/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };
    };



})();