(function() {
    'use strict';

    angular
        .module('app.invinvtry')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listInvinvtry',
                config: {
                    url: '/invinvtry',
                    templateUrl: 'app/invinvtry/views/list.html',
                    controller: 'invInvtrysCtlr',
                    //controllerAs: 'vm',
                    title: 'List Invinvtries',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> Invinvtries'
                    }
                }
            },
            {
                state: 'createInvinvtry',
                config: {
                    url: '/invinvtry/create',
                    templateUrl: 'app/invinvtry/views/create.html',
                    controller: 'invInvtryCreateCtlr',
                    //controllerAs: 'vm',
                    title: 'Create Invinvtry'
                }
            },
            {
                state: 'viewInvinvtry',
                config: {
                    url: '/InvInvtrys/show/',
                    templateUrl: 'app/invinvtry/views/view.html',
                    controller: 'invInvtryShowCtlr',
                    //controllerAs: 'vm',
                    title: 'View Invinvtry'
                }
            },
            {
                state: 'editInvinvtry',
                config: {
                    url: '/invinvtry/:invinvtryId/edit',
                    templateUrl: 'app/invinvtry/views/edit.html',
                    controller: 'InvinvtryController',
                    //controllerAs: 'vm',
                    title: 'Edit Invinvtry'
                }
            }
        ];
    }
})();
