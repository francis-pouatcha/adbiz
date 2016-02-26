(function() {
    'use strict';

    angular
        .module('app.mvnt')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'moveOut',
                config: {
                    url: '/moveOut',
                    templateUrl: '/adstock.client/src/client/app/mvnt/views/move-out.html',
                    controller: 'mvntCtlr',
                    title: 'Stock Move Out',
                    settings: {
                        nav: 4,
                        content: '<i class="fa fa-folder-open"></i> Stock Move Out'
                    }
                }
            },
            {
                state: 'moveIn',
                config: {
                    url: '/moveIn',
                    templateUrl: '/adstock.client/src/client/app/mvnt/views/move-in.html',
                    controller: 'mvntCtlr',
                    title: 'Stock Move In',
                    settings: {
                        nav: 5,
                        content: '<i class="fa fa-folder-open"></i> Stock Move In'
                    }
                }
            },{
                state: 'transfer',
                config: {
                    url: '/transfer',
                    templateUrl: '/adstock.client/src/client/app/mvnt/views/transfer.html',
                    controller: 'mvntCtlr',
                    title: 'Stock Transfer',
                    settings: {
                        nav: 6,
                        content: '<i class="fa fa-folder-open"></i> Stock transfer'
                    }
                }
            }
        ];
    }
})();
