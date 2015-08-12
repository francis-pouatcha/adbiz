(function() {
    'use strict';

    angular
        .module('app.importExport')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listImportExport',
                config: {
                    url: '/import-export',
                    templateUrl: 'app/import-export/views/view.html',
                    controller: 'ImportExportController',
                    controllerAs: 'vm',
                    title: 'ImportExports'
                }
            }
        ];
    }
})();
