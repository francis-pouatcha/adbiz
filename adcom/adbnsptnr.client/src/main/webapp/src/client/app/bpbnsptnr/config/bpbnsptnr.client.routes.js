(function() {
    'use strict';

    angular
        .module('app.bpbnsptnr')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'listBpbnsptnr',
                config: {
                    url: '/bpbnsptnr',
                    templateUrl: 'src/client/app/bpbnsptnr/views/list.html',
                    controller: 'BpbnsptnrController',
                    controllerAs: 'vm',
                    title: 'List Bpbnsptnrs',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> Partner'
                    }
                }
            },
            {
                state: 'createBpbnsptnr',
                config: {
                    url: '/bpbnsptnr/create',
                    templateUrl: 'src/client/app/bpbnsptnr/views/create.html',
                    controller: 'BpbnsptnrController',
                    controllerAs: 'vm',
                    title: 'Create Bpbnsptnr'
                }
            },
            {
                state: 'viewBpbnsptnr',
                config: {
                    url: '/bpbnsptnr/:bpbnsptnrId',
                    templateUrl: 'src/client/app/bpbnsptnr/views/view.html',
                    controller: 'BpbnsptnrController',
                    controllerAs: 'vm',
                    title: 'View Bpbnsptnr'
                }
            },
            {
                state: 'editBpbnsptnr',
                config: {
                    url: '/bpbnsptnr/:bpbnsptnrId/edit',
                    templateUrl: 'src/client/app/bpbnsptnr/views/edit.html',
                    controller: 'BpbnsptnrController',
                    controllerAs: 'vm',
                    title: 'Edit Bpbnsptnr'
                }
            }
        ];
    }
})();
