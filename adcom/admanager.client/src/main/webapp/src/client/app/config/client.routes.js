(function() {
    'use strict';

    angular
        .module('admanager')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }


    function getStates() {
        return [

            {
                state: 'CdrCshDrawers',
                config: {
                    url: '/',
                    templateUrl: '/admanager.client/src/client/app/views/CdrCshDrawer/CdrCshDrawers.html',
                    controller: 'cdrCshDrawersCtlr',
                    //controllerAs: 'vm',
                    title: 'Caisse',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> Caisse'
                    }
                }
            },

            {
                state: 'showCdrCshDrawers',
                config: {
                    url: '/show/:id',
                    templateUrl: '/admanager.client/src/client/app/views/CdrCshDrawer/showCdrCshDrawer.html',
                    controller: 'cdrCshDrawersEditCtlr',
                    //controllerAs: 'vm',
                    title: 'Etat de la caisse'
                }
            }
        ];
    }
})();
