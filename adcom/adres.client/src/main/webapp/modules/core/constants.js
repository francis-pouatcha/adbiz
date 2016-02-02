/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .constant('toastr', toastr)
        .constant('moment', moment)
        .constant('BASE_ROUTE', '/addashboard.client')
        .constant('BASE_SERVER', 'http://localhost:8080')

        .constant('API_BASE_URL', 'http://localhost:8080/adcatal.server/rest')
        .constant('BASE_VIEW', '/adcatal.client/src/client')

        .constant('BASE_ROUTE_DASHBOARD', '/addashboard.client')


        .constant('API_BASE_ADINVTRY_URL', 'http://localhost:8080/adinvtry.server/rest')

        .constant('API_BASE_ADPROCMT_URL', 'http://localhost:8080/adprocmt.server/rest')


        .constant('BASE_VIEW_STOCK', '/adstock.client/src/client')
        .constant('API_BASE_ADSTOCK_URL', 'http://localhost:8080/adstock.server/rest');

})();
