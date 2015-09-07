/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .constant('toastr', toastr)
        .constant('moment', moment)
        .constant('BASE_ROUTE', '/adstock.client')
        .constant('BASE_SERVER', 'http://localhost:8080')
        .constant('API_BASE_URL', 'http://localhost:8080/adstock.server/rest');
})();
