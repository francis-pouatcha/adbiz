'use strict';
angular.module('AdSales').directive('focus', function () {
    // Linker function
    return {
        link: function(scope, element) {
            element[0].focus();
        }
    };
});