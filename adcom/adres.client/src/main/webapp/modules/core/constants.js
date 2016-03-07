/* global toastr:false, moment:false */
(function() {
    'use strict';

    var BaseUrl = 'http://localhost:8080';

    angular
        .module('app.core')
        .constant('toastr', toastr)
        .constant('moment', moment)
        .constant('BASE_ROUTE', '/addashboard.client')

        .constant('BASE_SERVER', BaseUrl)

        .constant('API_BASE_URL', BaseUrl+'/adcatal.server/rest')
        .constant('BASE_VIEW', '/adcatal.client/src/client')

        .constant('BASE_ROUTE_DASHBOARD', '/addashboard.client')


        .constant('API_BASE_ADINVTRY_URL', BaseUrl+'/adinvtry.server/rest')

        .constant('API_BASE_ADPROCMT_URL', BaseUrl+'/adprocmt.server/rest')
        .constant('API_BASE_SALES_URL', BaseUrl+'/adsales.server/rest')


        .constant('BASE_VIEW_STOCK', '/adstock.client/src/client')
        .constant('API_BASE_ADSTOCK_URL', BaseUrl+'/adstock.server/rest');

})();
