(function() {
    'use strict';

    angular
        .module('app.widgets')
        .directive('htWidgetHeader', htWidgetHeader);

    /* @ngInject */
    function htWidgetHeader() {
        //Usage:
        //<div ht-widget-header title="vm.map.title"></div>
        // Creates:
        // <div ht-widget-header=""
        //      title="Movie"
        //      allow-collapse="true" </div>
        var directive = {
            scope: {
            },
            templateUrl: 'app/widgets/dirPagination.tpl.html',
            restrict: 'EA'
        };
        return directive;
    }
})();
