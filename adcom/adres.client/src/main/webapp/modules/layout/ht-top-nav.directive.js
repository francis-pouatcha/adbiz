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
            templateUrl: '/adres.client/modules/layout/ht-top-nav.html'
        };

        /* @ngInject */
        function TopNavController($translate, $rootScope) {
            var vm = this;
            vm.changeLang = function(lang) {
                $translate.use(lang);
            };

            vm.username = $rootScope.username;
            vm.logout = $rootScope.logout;
            vm.appTitle = $rootScope.appTitle;
        }

        return directive;
    }
})();
