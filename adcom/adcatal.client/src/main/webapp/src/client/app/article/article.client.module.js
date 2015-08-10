(function () {
    'use strict';

    angular.module('app.article', ['app.core','app.widgets']);
    angular.module('app').requires.push('app.article');

})();
