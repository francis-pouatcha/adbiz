(function() {
    'use strict';

    angular
        .module('app.layout')
        .directive('htTopNav', htTopNav);

    /* @ngInject */
    function htTopNav () {
        var directive = {
            bindToController: true,
            controller: TopNavController,
            controllerAs: 'vm',
            restrict: 'EA',
            scope: {
                'navline': '='
            },
            templateUrl: 'app/layout/ht-top-nav.html'
        };

        /* @ngInject */
        function TopNavController($translate, $rootScope) {
            var vm = this;
            vm.changeLang = function(lang) {
                $translate.use(lang);
            };

            vm.username = $rootScope.username;
            vm.logout = $rootScope.logout;
        }

        return directive;
    }
})();
