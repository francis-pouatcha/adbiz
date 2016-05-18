(function() {
    'use strict';

    angular
        .module('app.stkSection')
        .factory('StkSection', StkSection);

    StkSection.$inject = ['$resource', 'API_BASE_ADSTOCK_URL'];
    /* @ngInject */
    function StkSection($resource, API_BASE_ADSTOCK_URL) {

        var params = {
            stkSectionId: '@id'
        };

        var actions = {
            update: {
                method: 'PUT'
            },
            findCustom:{
                method: 'POST',
                url: API_BASE_ADSTOCK_URL + '/stksections/findCustom'
            },
            findByLike:{
            	method: 'POST',
            	url: API_BASE_ADSTOCK_URL + '/stksections/findByLike'
            },
            listAll:{
                method: 'GET',
                url: API_BASE_ADSTOCK_URL + '/stksections',
                params:{
                    start: -1,
                    max: 20
                }
            },
            findByIdentifIn: {
                method: 'POST',
                url: API_BASE_ADSTOCK_URL + '/stksections/findByIdentifIn'
            }
        };

        var API_URL = API_BASE_ADSTOCK_URL + '/stksections/:stkSectionId';

        return $resource(API_URL, params, actions);
    }

    angular
        .module('app.stkSection')
        .factory('sectionUtil', sectionUtil);

    sectionUtil.$inject = ['genericResource'];
    /* @ngInject */
    function sectionUtil(genericResource) {

        var service = {};

       var stksectionsUrlBase='/adstock.server/rest/stksections';

        service.loadSectionsBySectionCode = function(identif){
            return genericResource.findByLikePromissed(stksectionsUrlBase, 'identif', identif, 'org.adorsys.adstock.jpa.StkSection')
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.loadSectionsBySectionName = function(sectionName){
            return genericResource.findByLikePromissed(stksectionsUrlBase, 'name', sectionName, 'org.adorsys.adstock.jpa.StkSection')
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        return service;

    }

})();
