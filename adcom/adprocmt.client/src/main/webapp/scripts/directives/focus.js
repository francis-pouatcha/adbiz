'use strict';
angular.module('AdProcmt').directive('focus', function () {
    // Linker function
    return {
        link: function(scope, element) {
            element[0].focus();
        }
    };
});