(function () {
    'use strict';
    angular.module('AdBase').controller('localityCreateController',localityCreateController);

    localityCreateController.$inject = ['$scope', 'localityService','$location','countryService'];

    function localityCreateController($scope,localityService, $location,countryService){
        var self = this ;
        self.locality = {};
        self.countrys = [];
        self.create = create;
        self.error = "";

        loadCountry();

        function create(){
            localityService.create(self.locality).then(function(result){
                $location.path('/locality/show/'+result.id);
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