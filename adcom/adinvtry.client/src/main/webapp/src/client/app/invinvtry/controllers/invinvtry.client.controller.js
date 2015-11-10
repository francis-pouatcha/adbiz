
(function () {
    'use strict';

    angular
        .module('app.invinvtry')
        .controller('invInvtrysCtlr', invInvtrysCtlr);

    invInvtrysCtlr.$inject = ['$scope','invInvtryManagerResource' ];
    /* @ngInject */
    function invInvtrysCtlr($scope, invInvtryManagerResource) {

        $scope.invInvtrys = [];

        init();

        function init(){
            invInvtryManagerResource.query({start:0, max:20}, function(response){
                $scope.invInvtrys = response;
            });
        }
    }


    angular
        .module('app.invinvtry')
        .controller('invInvtryCreateCtlr', invInvtryCreateCtlr);

    invInvtryCreateCtlr.$inject = ['$scope'];
    /* @ngInject */
    function invInvtryCreateCtlr($scope) {

        $scope.invInvtry = {};

    }


    angular
        .module('app.invinvtry')
        .controller('invInvtryShowCtlr', invInvtryShowCtlr);

    invInvtryShowCtlr.$inject = ['$scope'];
    /* @ngInject */
    function invInvtryShowCtlr($scope) {

    }

})();

