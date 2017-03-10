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
                        nav: 1,
                        content: '<i class="fa fa-folder-open"></i> Caisse'
                    }
                }
            },
            {
                state: 'Vente',
                config: {
                    url: '/vente',
                    templateUrl: '/admanager.client/src/client/app/views/CdrDrctSale/CdrDrctSales.html',
                    controller: 'cdrDrctSalesCtlr',
                    //controllerAs: 'vm',
                    title: 'Ventes',
                    settings: {
                        nav: 2,
                        content: '<i class="fa fa-folder-open"></i> Vente'
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
            },
            {
                state: 'newCdrDrctSales',
                config: {
                    url: '/CdrDrctSales/new/:id',
                    templateUrl: '/admanager.client/src/client/app/views/CdrDrctSale/createDrctSales.html',
                    controller: 'cdrDrctSalesCreateCtlr',
                    //controllerAs: 'vm',
                    title: 'Nouvelle vente'
                }
            },
            {
                state: 'showCdrDrctSales',
                config: {
                    url: '/CdrDrctSales/show-vente/:id',
                    templateUrl: '/admanager.client/src/client/app/views/CdrDrctSale/showDrctSales.html',
                    controller: 'cdrDrctSalesShowCtlr',
                    //controllerAs: 'vm',
                    title: 'Vente'
                }
            }
        ];
    }
})();
