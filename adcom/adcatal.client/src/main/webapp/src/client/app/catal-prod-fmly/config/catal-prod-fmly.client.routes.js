(function() {
    'use strict';

    angular
        .module('app.catalProdFmly')
        .run(appRun);

    appRun.$inject = ['routerHelper','BASE_VIEW_ADCATAL','$translate'];
    /* @ngInject */
    function appRun(routerHelper,BASE_VIEW_ADCATAL,$translate) {

        $translate('CatalProductFamily.title').then(function(CatalProductFamilyTranslate){
            console.log(CatalProductFamilyTranslate);
            routerHelper.configureStates(getStates(BASE_VIEW_ADCATAL, CatalProductFamilyTranslate));
        });
    }

    function getStates(BASE_VIEW_ADCATAL,CatalProductFamilyTranslate) {


        return [
            {
                state: 'listCatalProdFmly',
                config: {
                    url: '/catal-prod-fmly',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-prod-fmly/views/list.html',
                    controller: 'CatalProdFmlyController',
                    controllerAs: 'vm',
                    title: 'List CatalProdFmlies',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i>'+ CatalProductFamilyTranslate
                    }
                }
            },
            {
                state: 'createCatalProdFmly',
                config: {
                    url: '/catal-prod-fmly/create',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-prod-fmly/views/create.html',
                    controller: 'CatalProdFmlyController',
                    controllerAs: 'vm',
                    title: 'Create CatalProdFmly'
                }
            },
            {
                state: 'viewCatalProdFmly',
                config: {
                    url: '/catal-prod-fmly/:catalProdFmlyId',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-prod-fmly/views/view.html',
                    controller: 'CatalProdFmlyController',
                    controllerAs: 'vm',
                    title: 'View CatalProdFmly'
                }
            },
            {
                state: 'editCatalProdFmly',
                config: {
                    url: '/catal-prod-fmly/:catalProdFmlyId/edit',
                    templateUrl: BASE_VIEW_ADCATAL+'/app/catal-prod-fmly/views/edit.html',
                    controller: 'CatalProdFmlyController',
                    controllerAs: 'vm',
                    title: 'Edit CatalProdFmly'
                }
            }
        ];
    }
})();
