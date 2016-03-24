(function() {
    'use strict';

    angular
        .module('app.mvnt')
        .factory('mvntManagerResource',['$http','$resource','API_BASE_ADSTOCK_URL',

    function($http,$resource,API_BASE_ADSTOCK_URL){

        var params = {
            identif: '@identif',
            itemIdentif: '@itemIdentif'
        };

        var actions = {

            moveIn:{
                method: 'POST',
                url: API_BASE_ADSTOCK_URL + '/stock/moveIn'
            },
            moveOut:{
            	method: 'POST',
            	url: API_BASE_ADSTOCK_URL + '/stock/moveOut'
            },
            transfer:{
                method: 'POST',
                url: API_BASE_ADSTOCK_URL + '/stock/transfer'
            }
        };

        var API_URL = API_BASE_ADSTOCK_URL + '/stock/:identif';

        return $resource(API_URL, params, actions);

    }])


    angular
        .module('app.mvnt')
        .factory('mvntUtils', mvntUtils);

    mvntUtils.$inject = ['$translate','genericResource','API_BASE_ADSTOCK_URL'];
    /* @ngInject */
    function mvntUtils($translate, genericResource, API_BASE_ADSTOCK_URL) {

        var service = {};

        service.inventoryManagerUrlBase = API_BASE_ADSTOCK_URL + "/inventory";
        service.urlBase= API_BASE_ADSTOCK_URL + '/invinvtrys';
        service.invinvtrysUrlBase=  '/adinvtry.server/rest/invinvtryitems';
        service.stksectionsUrlBase='/adstock.server/rest/stksections';
        service.stkarticlelotsUrlBase='/adstock.server/rest/stkarticlelots';
        service.catalarticlesUrlBase='/adcatal.server/rest/catalarticles'
        service.catalartfeatmappingsUrlBase='/adcatal.server/rest/catalartfeatmappings';
        service.loginnamessUrlBase='/adbase.server/rest/loginnamess';
        service.stkarticlelot2strgsctnsUrlBase='/adstock.server/rest/stkarticlelot2strgsctns';
        service.alphabet = "abcdefghijklmnopqrstuvwxyz";



        service.invInvntrStatusI18nMsgTitleKey = function(enumKey){
            return "InvInvntrStatus_"+enumKey+"_description.title";
        };
        service.invInvntrStatusI18nMsgTitleValue = function(enumKey){
            return service.translations[service.invInvntrStatusI18nMsgTitleKey(enumKey)];
        };
        service.invInvntrStati = [
            {enumKey:'WAREHOUSE', translKey:'StkMvtTerminal_WAREHOUSE_description.title'},
            {enumKey:'POS', translKey:'StkMvtTerminal_POS_description.title'},
            {enumKey:'SUPPLIER', translKey:'StkMvtTerminal_SUPPLIER_description.title'},
            {enumKey:'CUSTOMER', translKey:'StkMvtTerminal_CUSTOMER_description.title'},
            {enumKey:'DONNATION', translKey:'StkMvtTerminal_DONNATION_descriptionn.title'},
            {enumKey:'SAMPLE', translKey:'StkMvtTerminal_SAMPLE_description.title'},
            {enumKey:'EXPIRING', translKey:'StkMvtTerminal_EXPIRING_description.title'},
            {enumKey:'TRASH', translKey:'StkMvtTerminal_TRASH_description.title'},
            {enumKey:'NONE', translKey:'StkMvtTerminal_NONE_description.title'}
        ];


        service.loadSectionsBySectionCode = function(identif){
            return genericResource.findByLikePromissed(service.stksectionsUrlBase, 'identif', identif, 'org.adorsys.adstock.jpa.StkSection')
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.loadSectionsBySectionName = function(sectionName){
            return genericResource.findByLikePromissed(service.stksectionsUrlBase, 'name', sectionName, 'org.adorsys.adstock.jpa.StkSection')
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.loadArticlesByPic = function(artPic){
            return genericResource.findByLikePromissed(service.catalarticlesUrlBase, 'identif', artPic, 'org.adorsys.adcatal.jpa.CatalArticleSearchInput')
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.loadArticlesByName = function(artName){
            var searchInput = {entity:{},fieldNames:[],start: 0,max: 10};
            searchInput.className = 'org.adorsys.adcatal.jpa.CatalArtLangMappingSearchInput';
            searchInput.entity.artName = artName;
            searchInput.fieldNames.push('artName');
            return genericResource.findByLikeWithSearchInputPromissed(service.catalartfeatmappingsUrlBase, searchInput)
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.loadStkSectionArticleLots = function(strgSection){
            return genericResource.findByLikePromissed(service.stksectionsUrlBase, 'identif', strgSection, 'org.adorsys.adstock.jpa.StkSection')
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.loadStkSectionArticleLotsByIdentif = function(artPic, lotPic, strgSection){
            var searchInput = service.prepareStkSectionArticleLotSearchInput(artPic, lotPic, strgSection);

            return genericResource.findByLikeWithSearchInputPromissed(service.stkarticlelot2strgsctnsUrlBase, searchInput)
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };
        
        service.prepareStkSectionArticleLotSearchInput = function(artPic, lotPic, section){
            var searchInput = {entity:{},fieldNames:[],start: 0,max: 30};
            searchInput.className = 'org.adorsys.adstock.jpa.StkArticleLot2StrgSctnSearchInput';
            if(angular.isDefined(artPic) && artPic){
                searchInput.entity.artPic = artPic;
                searchInput.fieldNames.push('artPic');
            }
            if(angular.isDefined(section) && section){
                searchInput.entity.section = section;
                searchInput.fieldNames.push('section');
            }
            if(angular.isDefined(lotPic) && lotPic){
                searchInput.entity.lotPic = lotPic;
                searchInput.fieldNames.push('lotPic');
            }
        	return searchInput;
        };

        service.loadStkSectionArticleLotsByName = function(sectionName){
            return genericResource.findByLikePromissed(service.stksectionsUrlBase, 'name', sectionName, 'org.adorsys.adstock.jpa.StkSection')
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        // Load ArticlesLots from StkSection

        service.loadArticleLots = function(lotPic){
            var searchInput = {entity:{},fieldNames:[],start: 0,max: 30};
            searchInput.entity.lotPic = lotPic;
            searchInput.fieldNames.push('lotPic');
            // also load storage section
            searchInput.withStrgSection=true;
            return genericResource.findByLikeWithSearchInputPromissed(service.stkarticlelotsUrlBase, searchInput)
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.loadUsers = function(loginName){
            return genericResource.findByLikePromissed(service.loginnamessUrlBase, 'loginName', loginName)
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.loadUsersByName = function(fullName){
            return genericResource.findByLikePromissed(service.loginnamessUrlBase, 'fullName', fullName)
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.translatePromise = function(array) {
            return $translate(array);
        };

        return service;
    }
})();
