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
            if (Auth.authz.hasResourceRole('role.' + workspace, workspace)) {
                return true;
            }
            else {
                return false;
            }
        };
    }
})();
