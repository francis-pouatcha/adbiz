(function() {
    'use strict';

    angular
        .module('app.procmtDelivery')
        .factory('procmtManagerResource',['$http','$resource','API_BASE_ADPROCMT_URL',

    function($http,$resource,API_BASE_ADPROCMT_URL){

        var params = {
            identif: '@identif',
            itemIdentif: '@itemIdentif'
        };

        var actions = {
            query:  {method:'GET', isArray:true},
            update: {
                method: 'PUT'
            },
            findCustom:{
                method: 'POST',
                url: API_BASE_ADPROCMT_URL + '/prcmtdeliverys/findCustom'
            },
            findByLike:{
            	method: 'POST',
            	url: API_BASE_ADPROCMT_URL + '/prcmtdeliverys/findByLike'
            },
            findConflict:{
                method: 'POST',
                url: API_BASE_ADPROCMT_URL + '/prcmtdlvryitems/findConflict'
            },
            listAll:{
                method: 'GET',
                url: API_BASE_ADPROCMT_URL + '/prcmtdeliverys',
                params:{
                    start: -1,
                    max: 20
                }
            },
            get:{
                method: 'GET',
                url: API_BASE_ADPROCMT_URL + '/prcmtdeliverys/:identif'
            },
            prepare: {
                method: 'PUT',
                url: API_BASE_ADPROCMT_URL + '/delivery/prepare/:identif'
            },
            close: {
                method: 'PUT',
                url: API_BASE_ADPROCMT_URL + '/delivery/:identif/close'
            },
            validate: {
                method: 'PUT',
                url: API_BASE_ADPROCMT_URL + '/delivery/:identif/validate'
            },
            archive: {
                method: 'PUT',
                url: API_BASE_ADPROCMT_URL + '/delivery/:identif/archive'
            },
            post: {
                method: 'PUT',
                url: API_BASE_ADPROCMT_URL + '/delivery/:identif/post'
            },
            addItem: {
                method: 'POST',
                url: API_BASE_ADPROCMT_URL + '/delivery/:identif/items'
            },
            updateItem: {
                method: 'PUT',
                url: API_BASE_ADPROCMT_URL + '/delivery/:identif/items/:itemIdentif'
            },
            disableItem: {
                method: 'PUT',
                url: API_BASE_ADPROCMT_URL + '/delivery/:identif/items/:itemIdentif/disable'
            },
            enableItem: {
                method: 'PUT',
                url: API_BASE_ADPROCMT_URL + '/delivery/:identif/items/:itemIdentif/enable'
            },
            updatetrgtQty: {
                method: 'PUT',
                url: API_BASE_ADPROCMT_URL + '/delivery/:identif/items/:itemIdentif/targetQty'
            }

        };

        var API_URL = API_BASE_ADPROCMT_URL + '/delivery/:identif';

        return $resource(API_URL, params, actions);

    }])


    angular
        .module('app.procmtDelivery')
        .factory('prcmtUtils', prcmtUtils);

    prcmtUtils.$inject = ['$translate','genericResource','API_BASE_URL', 'BASE_SERVER'];
    /* @ngInject */
    function prcmtUtils($translate, genericResource, API_BASE_URL, BASE_SERVER) {

        var service = {};

        service.orderManagerUrlBase = API_BASE_URL + "/delivery";
        service.urlBase= API_BASE_URL + '/prcmtdeliverys';
        service.procmtsUrlBase= BASE_SERVER+ '/adprocmt.server/rest/prcmtdlvryitems';
        service.stksectionsUrlBase=BASE_SERVER+'/adstock.server/rest/stksections';
        service.stkarticlelotsUrlBase=BASE_SERVER+'/adstock.server/rest/stkarticlelots';
        service.catalarticlesUrlBase=BASE_SERVER+'/adcatal.server/rest/catalarticles'
        service.catalartfeatmappingsUrlBase=BASE_SERVER+'/adcatal.server/rest/catalartfeatmappings';
        service.loginnamessUrlBase=BASE_SERVER+'/adbase.server/rest/loginnamess';
        service.stkarticlelot2strgsctnsUrlBase=BASE_SERVER+'/adstock.server/rest/stkarticlelot2strgsctns';
        service.alphabet = "abcdefghijklmnopqrstuvwxyz";

        service.invInvtryTypeI18nMsgTitleKey = function(enumKey){
            return "InvInvtryType_"+enumKey+"_description.title";
        };
        service.invInvtryTypeI18nMsgTitleValue = function(enumKey){
            return service.translations[service.invInvtryTypeI18nMsgTitleKey(enumKey)];
        };

        service.poTriggerModes = [
            {enumKey:'MANUAL', translKey:'ProcmtPOTriggerModeEnum_MANUAL_description.title'},
            {enumKey:'STOCK_SHORTAGE', translKey:'ProcmtPOTriggerModeEnum_STOCK_SHORTAGE_description.title'},
            {enumKey:'MOST_SOLD', translKey:'ProcmtPOTriggerModeEnum_MOST_SOLD_description.title'}
        ];

        service.POTypes = [
            {enumKey:'ORDINARY', translKey:'ProcmtPOTypeEnum_ORDINARY_description.title'},
            {enumKey:'PACKAGED', translKey:'ProcmtPOTypeEnum_PACKAGED_description.title'},
            {enumKey:'SPECIAL', translKey:'ProcmtPOTypeEnum_SPECIAL_description.title'}
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
        service.status = [
            {enumKey:'All', translKey:'all.title'},
            {enumKey:'INITIALIZING', translKey:'INITIALIZING_description.title'},
            {enumKey:'SUSPENDED', translKey:'SUSPENDED_description.title'},
            {enumKey:'ONGOING', translKey:'ONGOING_description.title'},
            {enumKey:'RESUMED', translKey:'RESUMED_description.title'},
            {enumKey:'CLOSED', translKey:'CLOSED_description.title'},
            {enumKey:'MERGED', translKey:'MERGED_description.title'},
            {enumKey:'POSTED', translKey:'POSTED_description.title'}
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
            var searchInput = {entity:{},fieldNames:[],start: 0,max: 30};
            searchInput.className = 'org.adorsys.adstock.jpa.StkArticleLot2StrgSctnSearchInput';
            if(angular.isDefined(artPic) && artPic){
                searchInput.entity.artPic = artPic;
                searchInput.fieldNames.push('artPic');
            }
            if(angular.isDefined(strgSection) && strgSection){
                searchInput.entity.section = strgSection;
                searchInput.fieldNames.push('section');
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

    angular
     .module('app.procmtDelivery')
    .factory('procmtState',['$rootScope','searchResultHandler',function($rootScope,searchResultHandler){
        var service = {};
        service.selection = [];
        service.mainProcmt = {};
        service.resultHandler = searchResultHandler.newResultHandler('cntnrIdentif');
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
    }])

})();
