'use strict';
angular.module('NavBar').directive('focus', function () {
    // Linker function
    return {
        link: function(scope, element) {
            element[0].focus();
        }
    };
});