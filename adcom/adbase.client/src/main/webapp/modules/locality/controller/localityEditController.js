(function () {
    'use strict';
    angular.module('AdBase').controller('localityEditController',localityEditController);

    localityEditController.$inject = ['$scope', 'localityService','$location','$routeParams','countryService'];

    function localityEditController($scope,localityService, $location,$routeParams,countryService){
        var self = this ;
        self.locality = {};
        self.edit = edit;
        self.countrys = [];
        self.error = "";

        init();

        function init(){

            load();
            loadCountry();
        }
        
        function edit(){
            localityService.update(self.locality).then(function(result){
                $location.path('/locality/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };

        function load(){
            var identif = $routeParams.identif ;
            localityService.loadOne(identif).then(function(result){
                self.locality = result;
            },function(error){
                self.error = error;
            })
        };

        function loadCountry(){
            countryService.findActifsFromNow().then(function(data){
                self.countrys=data;
            });
        }
    };



})();