/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .constant('toastr', toastr)
        .constant('moment', moment)
        .constant('BASE_ROUTE', '/addashboard.client')

//        .constant('BASE_SERVER', BaseUrl)

        .constant('API_BASE_ADCATAL_URL', '/adcatal.server/rest')
        .constant('BASE_VIEW_ADCATAL', '/adcatal.client/src/client')

        .constant('BASE_ROUTE_DASHBOARD', '/addashboard.client')


        .constant('API_BASE_ADBNSPTNR_URL', '/adbnsptnr.server/rest')

        .constant('API_BASE_ADINVTRY_URL', '/adinvtry.server/rest')

        .constant('API_BASE_ADPROCMT_URL', '/adprocmt.server/rest')
        .constant('API_BASE_SALES_URL', '/adsales.server/rest')


        .constant('BASE_VIEW_STOCK', '/adstock.client/src/client')
        .constant('API_BASE_ADSTOCK_URL', '/adstock.server/rest');

})();
