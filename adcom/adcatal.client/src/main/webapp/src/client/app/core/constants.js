/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .constant('toastr', toastr)
        .constant('moment', moment)
        .constant('BASE_ROUTE', '/adcatal.client')
        .constant('API_BASE_URL', 'http://localhost:8080/adcatal.server/rest');
})();
