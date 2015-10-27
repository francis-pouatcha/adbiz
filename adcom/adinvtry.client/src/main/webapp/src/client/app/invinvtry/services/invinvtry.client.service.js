(function() {
    'use strict';

    angular
        .module('app.invinvtry')
        .factory('invInvtryManagerResource',['$http','$resource','API_BASE_URL',

    function($http,$resource,API_BASE_URL){

        var params = {
            identif: '@identif',
            itemIdentif: '@itemIdentif'
        };

        var actions = {
            query:  {method:'GET', isArray:true},
            update: {
                method: 'PUT'
            },
            close: {
                method: 'PUT',
                url: API_BASE_URL + '/inventory/:identif/close'
            },
            validate: {
                method: 'PUT',
                url: API_BASE_URL + '/inventory/:identif/validate'
            },
            post: {
                method: 'PUT',
                url: API_BASE_URL + '/inventory/:identif/post'
            },
            addItem: {
                method: 'POST',
                url: API_BASE_URL + '/inventory/:identif/items'
            },
            updateItem: {
                method: 'PUT',
                url: API_BASE_URL + '/inventory/:identif/items/:itemIdentif'
            },
            disableItem: {
                method: 'PUT',
                url: API_BASE_URL + '/inventory/:identif/items/:itemIdentif/disable'
            },
            enableItem: {
                method: 'PUT',
                url: API_BASE_URL + '/inventory/:identif/items/:itemIdentif/enable'
            }

        };

        var API_URL = API_BASE_URL + '/inventory/:identif';

        return $resource(API_URL, params, actions);

    }])


    angular
        .module('app.invinvtry')
        .factory('invInvtryState', invInvtryState);

    invInvtryState.$inject = ['$rootScope', 'searchResultHandler'];
    /* @ngInject */
    function invInvtryState($rootScope, searchResultHandler) {

        var service = {};
        service.selection = [];
        service.mainInvtry = {};
        service.resultHandler = searchResultHandler.newResultHandler('invtryNbr');
        service.itemsResultHandler = function(){
            var itemsResultHandlerVar = service.resultHandler.dependent('items');
            if(angular.isUndefined(itemsResultHandlerVar)){
                itemsResultHandlerVar = searchResultHandler.newResultHandler('identif');
                service.resultHandler.dependent('items', itemsResultHandlerVar);
            }
            return itemsResultHandlerVar;
        };
        service.compareResultHandler = searchResultHandler.newResultHandler('salIndex');
        return service;

    }

    angular
        .module('app.invinvtry')
        .factory('invInvtryUtils', invInvtryUtils);

    invInvtryUtils.$inject = ['$translate','genericResource','invInvtryState','API_BASE_URL', 'BASE_SERVER'];
    /* @ngInject */
    function invInvtryUtils($translate,genericResource,invInvtryState,API_BASE_URL, BASE_SERVER) {

        var service = {};

        service.inventoryManagerUrlBase = API_BASE_URL + "/inventory";
        service.urlBase= API_BASE_URL + '/invinvtrys';
        service.invinvtrysUrlBase= API_BASE_URL+ '/invinvtryitems';
        service.stksectionsUrlBase=BASE_SERVER+'/adstock.server/rest/stksections';
        service.stkarticlelotsUrlBase=BASE_SERVER+'/adstock.server/rest/stkarticlelots';
        service.catalarticlesUrlBase=BASE_SERVER+'/adcatal.server/rest/catalarticles';
        service.loginnamessUrlBase=BASE_SERVER+'/adbase.server/rest/loginnamess';
        service.stkarticlelot2strgsctnsUrlBase=BASE_SERVER+'/adstock.server/rest/stkarticlelot2strgsctns';
        service.alphabet = "abcdefghijklmnopqrstuvwxyz";

        service.invInvtryTypeI18nMsgTitleKey = function(enumKey){
            return "InvInvtryType_"+enumKey+"_description.title";
        };
        service.invInvtryTypeI18nMsgTitleValue = function(enumKey){
            return service.translations[service.invInvtryTypeI18nMsgTitleKey(enumKey)];
        };

        service.invInvtryTypes = [
            {enumKey:'BY_SECTION', translKey:'InvInvtryType_BY_SECTION_description.title'},
            {enumKey:'ALPHABETICAL_ORDER_RANGE', translKey:'InvInvtryType_ALPHABETICAL_ORDER_RANGE_description.title'},
            {enumKey:'FREE_INV', translKey:'InvInvtryType_FREE_INV_description.title'}
        ];

        service.invInvntrStatusI18nMsgTitleKey = function(enumKey){
            return "InvInvntrStatus_"+enumKey+"_description.title";
        };
        service.invInvntrStatusI18nMsgTitleValue = function(enumKey){
            return service.translations[service.invInvntrStatusI18nMsgTitleKey(enumKey)];
        };

        service.invInvtryStatusI18nMsgTitleValue = function(enumKey){
            return service.translations[service.invInvntrStatusI18nMsgTitleKey(enumKey)];
        };
        service.invInvntrStati = [
            {enumKey:'INITIALIZING', translKey:'InvInvntrStatus_INITIALIZING_description.title'},
            {enumKey:'SUSPENDED', translKey:'InvInvntrStatus_SUSPENDED_description.title'},
            {enumKey:'ONGOING', translKey:'InvInvntrStatus_ONGOING_description.title'},
            {enumKey:'RESUMED', translKey:'InvInvntrStatus_RESUMED_description.title'},
            {enumKey:'CLOSED', translKey:'InvInvntrStatus_CLOSED_description.title'},
            {enumKey:'MERGED', translKey:'InvInvntrStatus_MERGED_description.title'},
            {enumKey:'POSTED', translKey:'InvInvntrStatus_POSTED_description.title'}
        ];


        service.loadSectionsBySectionCode = function(sectionCode){
            return genericResource.findByLikePromissed(service.stksectionsUrlBase, 'sectionCode', sectionCode, 'org.adorsys.adstock.jpa.StkSection')
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
            searchInput.codesAndNames = artName;
            return genericResource.findByLikeWithSearchInputPromissed(service.catalarticlesUrlBase, searchInput)
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.loadStkSectionArticleLots = function(strgSection){
            return genericResource.findByLikePromissed(service.stksectionsUrlBase, 'sectionCode', strgSection, 'org.adorsys.adstock.jpa.StkSection')
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        };

        service.loadStkSectionArticleLotsByIdentif = function(artPic, lotPic, strgSection){
            var searchInput = {entity:{},fieldNames:[],start: 0,max: 30};
            if(angular.isDefined(artPic) && artPic){
                searchInput.entity.artPic = artPic;
                searchInput.fieldNames.push('artPic');
            }
            if(angular.isDefined(strgSection) && strgSection){
                searchInput.entity.strgSection = strgSection;
                searchInput.fieldNames.push('strgSection');
            }
            if(angular.isDefined(lotPic) && lotPic){
                searchInput.entity.lotPic = lotPic;
                searchInput.fieldNames.push('lotPic');
            }
            return genericResource.findByLikeWithSearchInputPromissed(service.stkarticlelot2strgsctnsUrlBase, searchInput)
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
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

        service.isInvtryBySection = function(invInvtry){
            return invInvtry && invInvtry.invInvtryType && invInvtry.invInvtryType=='BY_SECTION';
        };

        service.isInvtryByOrderAlphabeticRange = function(invInvtry){
            return invInvtry && invInvtry.invInvtryType && invInvtry.invInvtryType=='ALPHABETICAL_ORDER_RANGE';
        };
        service.isInvtryOpen = function (invInvtry) {
            return angular.isDefined(invInvtry) && !service.isInvtryClosed(invInvtry) && !service.isInvtryPosted(invInvtry);
        }
        service.isInvtryClosed = function (invInvtry) {
            return angular.isDefined(invInvtry) && angular.isDefined(invInvtry.closedDate) && invInvtry.closedDate;
        }
        service.isInvtryPosted = function (invInvtry) {
            return angular.isDefined(invInvtry) && angular.isDefined(invInvtry.postedDate) && invInvtry.postedDate;
        }
        service.isInvtryMerged = function (invInvtry) {
            return angular.isDefined(invInvtry) && angular.isDefined(invInvtry.mergedDate) && invInvtry.mergedDate;
        }
        return service;

    }

})();
