(function () {
    'use strict';

    angular
        .module('app.dashboard')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['Auth'];
    /* @ngInject */
    function DashboardController(Auth) {
        var vm = this;
        vm.hasWorkspace = function (workspace) {
            if (Auth.authz.hasResourceRole(workspace + '_role', workspace)) {
                return true;
            }
            else {
                return false;
            }
        };
    }
})();
