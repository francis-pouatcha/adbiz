(function() {
    'use strict';

    angular
        .module('adcshdwr')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

   /* $routeProvider
        .when('/CdrPymnts',{templateUrl:'/adcshdwr.client/src/client/app/views/CdrPymnt/CdrPymnts.html',controller:'cdrPaymntsCtlr'})
        .when('/CdrPymnts/new',{templateUrl:'/adcshdwr.client/src/client/app/views/CdrPymnt/createCdrPymnt.html',controller:'cdrPaymntCreateCtlr'})
        .when('/CdrPymnts/edit/:identif',{templateUrl:'views/CdrPymnt/editCdrPymnt.html',controller:'cdrPaymntEditCtlr'})
        .when('/SlsInvoices',{templateUrl:'/adcshdwr.client/src/client/app/views/SlsInvoice/SlsInvoices.html',controller:'cdrSlsInvoicesCtlr'})
        .when('/SlsInvoices/show',{templateUrl:'/adcshdwr.client/src/client/app/views/SlsInvoice/SlsInvoiceShow.html',controller:'cdrSlsInvoicesShowCtlr'})
        .when('/CdrCshDrawers',{templateUrl:'/adcshdwr.client/src/client/app/views/CdrCshDrawer/CdrCshDrawers.html',controller:'cdrCshDrawersCtlr'})
        .when('/CdrCshDrawers/new',{templateUrl:'/adcshdwr.client/src/client/app/views/CdrCshDrawer/createCdrCshDrawer.html',controller:'cdrCshDrawersCreateCtlr'})

        .when('/CdrCshDrawers/show/:id',{templateUrl:'/adcshdwr.client/src/client/app/views/CdrCshDrawer/showCdrCshDrawer.html',controller:'cdrCshDrawersEditCtlr'})
        .when('/CdrDrctSales',{templateUrl:'/adcshdwr.client/src/client/app/views/CdrDrctSale/CdrDrctSales.html',controller:'cdrDrctSalesCtlr'})
        .when('/CdrDrctSales/new/:origDsNbr',{templateUrl:'views/CdrDrctSale/createDrctSales.html',controller:'cdrDrctSalesCreateCtlr'})
        .when('/CdrDrctSales/new',{templateUrl:'/adcshdwr.client/src/client/app/views/CdrDrctSale/createDrctSales.html',controller:'cdrDrctSalesCreateCtlr'})
        .when('/CdrDrctSales',{templateUrl:'/adcshdwr.client/src/client/app/views/CdrDrctSale/CdrDrctSales.html',controller:'cdrDrctSalesCtlr'})
        .when('/CdrDrctSales/show/:id',{templateUrl:'/adcshdwr.client/src/client/app/views/CdrDrctSale/showDrctSales.html',controller:'cdrDrctSalesShowCtlr'})
        .otherwise({redirectTo: '/CdrDrctSales'});*/

    function getStates() {
        return [
            {
                state: 'Caisse',
                config: {
                    url: '/',
                    templateUrl: '/adcshdwr.client/src/client/app/views/CdrDrctSale/CdrDrctSales.html',
                    controller: 'cdrDrctSalesCtlr',
                    //controllerAs: 'vm',
                    title: 'Ventes',
                    settings: {
                        nav: 1,
                        content: '<i class="fa fa-folder-open"></i> Vente'
                    }
                }
            },
            /*{
                state: 'CdrPymnts',
                config: {
                    url: '/CdrPymnts',
                    templateUrl: '/adcshdwr.client/src/client/app/views/CdrPymnt/CdrPymnts.html',
                    controller: 'cdrPaymntsCtlr',
                    //controllerAs: 'vm',
                    title: 'paiements',
                    settings: {
                        nav: 2,
                        content: '<i class="fa fa-folder-open"></i> Paiement'
                    }
                }
            },*/
            {
                state: 'newCdrPymnts',
                config: {
                    url: '/CdrPymnts/new',
                    templateUrl: '/adcshdwr.client/src/client/app/views/CdrPymnt/createCdrPymnt.html',
                    controller: 'cdrPaymntCreateCtlr',
                    //controllerAs: 'vm',
                    title: 'Enregistrer un paiement'

                }
            },
            {
                state: 'CdrCshDrawers',
                config: {
                    url: '/CdrCshDrawers',
                    templateUrl: '/adcshdwr.client/src/client/app/views/CdrCshDrawer/CdrCshDrawers.html',
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
                state: 'newCdrCshDrawers',
                config: {
                    url: '/CdrCshDrawers/new',
                    templateUrl: '/adcshdwr.client/src/client/app/views/CdrCshDrawer/createCdrCshDrawer.html',
                    controller: 'cdrCshDrawersCreateCtlr',
                    //controllerAs: 'vm',
                    title: 'Nouvelle Caisse'
                }
            },
            {
                state: 'showCdrCshDrawers',
                config: {
                    url: '/CdrCshDrawers/show/:id',
                    templateUrl: '/adcshdwr.client/src/client/app/views/CdrCshDrawer/showCdrCshDrawer.html',
                    controller: 'cdrCshDrawersEditCtlr',
                    //controllerAs: 'vm',
                    title: 'Etat de la caisse'
                }
            },
            {
                state: 'newCdrDrctSales',
                config: {
                    url: '/CdrDrctSales/new/:id',
                    templateUrl: '/adcshdwr.client/src/client/app/views/CdrDrctSale/createDrctSales.html',
                    controller: 'cdrDrctSalesCreateCtlr',
                    //controllerAs: 'vm',
                    title: 'Nouvelle vente'
                }
            },
            {
                state: 'showCdrDrctSales',
                config: {
                    url: '/CdrDrctSales/show',
                    templateUrl: '/adcshdwr.client/src/client/app/views/CdrDrctSale/showDrctSales.html',
                    controller: 'cdrDrctSalesShowCtlr',
                    //controllerAs: 'vm',
                    title: 'Vente'
                }
            }
            /*{
                state: 'SlsInvoices',
                config: {
                    url: '/SlsInvoices',
                    templateUrl: '/adcshdwr.client/src/client/app/views/SlsInvoice/SlsInvoices.html',
                    controller: 'cdrSlsInvoicesCtlr',
                    //controllerAs: 'vm',
                    title: 'Factures',
                    settings: {
                        nav: 5,
                        content: '<i class="fa fa-folder-open"></i> Facture'
                    }
                }
            },
            {
                state: 'showSlsInvoices',
                config: {
                    url: '/SlsInvoices/show',
                    templateUrl: '/adcshdwr.client/src/client/app/views/SlsInvoice/SlsInvoiceShow.html',
                    controller: 'cdrSlsInvoicesShowCtlr',
                    //controllerAs: 'vm',
                    title: 'Facture'
                }
            }*/

        ];
    }
})();
