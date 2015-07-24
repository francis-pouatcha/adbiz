(function() {
    'use strict';

    angular
        .module('app.dashboard')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                /*state: 'dashboard',
                config: {
                    url: '/',
                    templateUrl: 'app/dashboard/dashboard.html',
                    controller: 'DashboardController',
                    controllerAs: 'vm',
                    title: 'dashboard'
                }*/
                state: 'dashboard',
                config: {
                    url: '/',
                    templateUrl: 'app/article/views/list.html',
                    controller: 'ArticleController',
                    controllerAs: 'vm',
                    title: 'List Articles'
                }
            }
        ];
    }
})();
