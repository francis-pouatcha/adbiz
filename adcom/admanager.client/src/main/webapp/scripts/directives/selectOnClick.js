'use strict';
angular.module('Admanager').directive('selectOnClick', function () {
    // Linker function
    return function (scope, element, attrs) {
        element.bind('click', function () {
            this.select();
        });
    };
});