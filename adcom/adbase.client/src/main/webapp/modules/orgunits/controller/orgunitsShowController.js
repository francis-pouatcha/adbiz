(function () {
    'use strict';
    angular.module('AdBase').controller('orgUnitShowController',orgUnitShowController);

    orgUnitShowController.$inject = ['$scope', 'orgUnitsService','$location','$routeParams'];

    function orgUnitShowController($scope,orgUnitsService, $location,$routeParams){
        var self = this ;
        self.orgUnit = {};
        self.show = show;
        self.error;
        self.remove = remove;
        init();

        function init(){
            show();
        }

        function show(){

            var identif = $routeParams.identif ;
            orgUnitsService.findDtoByIdentif(identif).then(function(result){
                self.orgUnit = result
            })
        };
        
        function remove(id) {
            orgUnitsService.remove(id).then(function(data){
                $location.path("/orgunits");
            },function (error){
                self.error = error;
            })
        }
    };



})();