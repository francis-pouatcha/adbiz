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
        .constant('BASE_ROUTE_DASHBOARD', '/addashboard.client');

})();
