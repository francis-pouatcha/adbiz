
(function () {
    'use strict';

    angular
        .module('app.invinvtry')
        .controller('invInvtrysCtlr', invInvtrysCtlr);

    invInvtrysCtlr.$inject = ['$scope','invInvtryManagerResource','utils'];
    /* @ngInject */
    function invInvtrysCtlr($scope, invInvtryManagerResource, utils) {

        $scope.data = {};
        $scope.data.invInvtrys = [];
        $scope.data.total;

        // Initialize Search input and pagination
        $scope.searchInput = utils.searchInputInit().searchInput;
        $scope.searchInput.className = 'org.adorsys.adinvtry.jpa.InvInvtrySearchInput';
        $scope.pagination = utils.searchInputInit().pagination;

        findCustom();

        function findCustom() {
            invInvtryManagerResource.findCustom($scope.searchInput, function(response){
                    $scope.data.invInvtrys = response.resultList;
                    $scope.data.total = response.total;
                },
                function(errorResponse) {
                    $scope.error = errorResponse.data.summary;
                });

        }
        // Paginate over the list
        $scope.paginate = function(newPage){
            utils.pagination($scope.searchInput, $scope.pagination, newPage);
            findCustom();
        };
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

