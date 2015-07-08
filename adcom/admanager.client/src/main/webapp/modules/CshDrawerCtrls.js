'use strict';

angular.module('Admanager')
    .factory('CshDrawerUtils', ['sessionManager', '$translate', 'genericResource', '$q', function (sessionManager, $translate, genericResource, $q) {
        var service = {};

        service.cshdwr = '/adcshdwr.server/rest/cdrcshdrawers';
        service.login = '/adbase.server/rest/logins';
        service.language = sessionManager.language;

        service.currentWsUser = sessionManager.userWsData();

        service.loadLogin = function(value){
            return genericResource.findByLikePromissed(service.login, "loginName", value)
                .then(function (entitySearchResult) {
                    return entitySearchResult.resultList;
                })

        }

        service.translate = function () {
            $translate([
                    'CdrCshDrawer_cashier_description.text',
                    'CdrCshDrawer_cashier_description.title',
                    'CdrCshDrawer_cdrNbr_description.text',
                    'CdrCshDrawer_cdrNbr_description.title',
                    'CdrCshDrawer_closedBy_description.text',
                    'CdrCshDrawer_closedBy_description.title',
                    'CdrCshDrawer_clsngDt_description.text',
                    'CdrCshDrawer_clsngDt_description.title',
                    'CdrCshDrawer_description.text',
                    'CdrCshDrawer_description.title',
                    'CdrCshDrawer_initialAmt_description.text',
                    'CdrCshDrawer_initialAmt_description.title',
                    'CdrCshDrawer_opngDt_description.text',
                    'CdrCshDrawer_opngDt_description.title',
                    'CdrCshDrawer_ttlCashIn_description.text',
                    'CdrCshDrawer_ttlCashIn_description.title',
                    'CdrCshDrawer_ttlCashOut_description.text',
                    'CdrCshDrawer_ttlCashOut_description.title',
                    'CdrCshDrawer_ttlCash_description.text',
                    'CdrCshDrawer_ttlCash_description.title',
                    'CdrCshDrawer_ttlCheck_description.text',
                    'CdrCshDrawer_ttlCheck_description.title',
                    'CdrCshDrawer_ttlCredCard_description.text',
                    'CdrCshDrawer_ttlCredCard_description.title',
                    'CdrCshDrawer_ttlVchrIn_description.text',
                    'CdrCshDrawer_ttlVchrIn_description.title',
                    'CdrCshDrawer_ttlVchrOut_description.text',
                    'CdrCshDrawer_ttlVchrOut_description.title',
                    'CdrCshDrawer_close_description.title'
                 ])
                .then(function (translations) {
                    service.translations = translations;
                });
        };
        service.translate();
        return service;
}])
    .factory('CshDrawerState', ['$rootScope', '$q', function ($rootScope, $q) {

        var service = {};
        var selectedIndexVar = -1;
        var searchResult = {};
        service.selectedIndex = function (selectedIndexIn) {
            if (selectedIndexIn) selectedIndexVar = selectedIndexIn;
            return selectedIndexVar;
        };

        var totalItemsVar = 0;
        service.totalItems = function (totalItemsIn) {
            if (totalItemsIn) totalItemsVar = totalItemsIn;
            return totalItemsVar;
        };

        var currentPageVar = 0;
        service.currentPage = function (currentPageIn) {
            if (currentPageIn) currentPageVar = currentPageIn;
            return currentPageVar;
        };

        var maxSizeVar = 5;
        service.maxSize = function (maxSizeIn) {
            if (maxSizeIn) maxSizeVar = maxSizeIn;
            return maxSizeVar;
        };

        var itemPerPageVar = 25;
        var searchInputVar = {
            entity: {},
            fieldNames: [],
            start: 0,
            max: itemPerPageVar
        };
        service.searchInput = function (searchInputIn) {
            if (!searchInputIn)
                return angular.copy(searchInputVar);

            searchInputVar = angular.copy(searchInputIn);
            return searchInputIn;
        };



        service.searchInputChanged = function (searchInputIn) {
            return angular.equals(searchInputVar, searchInputIn);
        };
        service.itemPerPage = function (itemPerPageIn) {
            if (itemPerPageIn) itemPerPageVar = itemPerPageIn;
            return itemPerPageVar;
        };

        //
        service.consumeSearchResult = function (searchInput, entitySearchResult) {
            // store search
            service.searchInput(searchInput);
            // Store result
            service.invInvtrys(entitySearchResult.resultList);
            service.totalItems(entitySearchResult.count);
            service.selectedIndex(-1);
        };


        service.paginate = function () {
            searchInputVar.start = ((currentPageVar - 1) * itemPerPageVar);
            searchInputVar.max = itemPerPageVar;
            return service.searchInput();
        };

        // returns sets and returns the business partner at the passed index or
        // if not set the business partner at the currently selected index.
        service.invInvtry = function (index) {
            if (index && index >= 0 && index < invInvtrysVar.length) selectedIndexVar = index;
            if (selectedIndexVar < 0 || selectedIndexVar >= invInvtrysVar.length) return;
            return invInvtrysVar[selectedIndexVar];
        };

        // replace the current partner after a change.
        service.replace = function (invInvtry) {
            if (!invInvtrysVar || !invInvtry) return;
            var currentInvt = service.invInvtry();
            if (currentInvt && currentInvt.invtryNbr == invInvtry.invtryNbr) {
                invInvtrysVar[selectedIndexVar] = invInvtry;
            } else {
                for (var index in invInvtrysVar) {
                    if (invInvtrysVar[index].invtryNbr == invInvtry.invtryNbr) {
                        invInvtrysVar[index] = invInvtry;
                        selectedIndexVar = index;
                        break;
                    }
                }
            }
        };
        service.set = function (invInvtry) {
            if (!invInvtrysVar || !invInvtry) return false;
            invInvtrysVar[selectedIndexVar] = invInvtry;
            return true;
        };

        // CHeck if the business partner to be displayed is at the correct index.
        service.peek = function (invInvtry, index) {
            if (!invInvtrysVar || !invInvtry) return false;
            if (index >= 0 && index < invInvtrysVar.length) {
                selectedIndexVar = index;
                return true;
            }
            return false;
        };

        service.push = function (invInvtry) {
            if (!invInvtrysVar || !invInvtry) return false;
            var length = invInvtrysVar.push(invInvtry);
            selectedIndexVar = length - 1;
            return true;
        };

        service.previous = function () {
            if (invInvtrysVar.length <= 0) return;

            if (selectedIndexVar <= 0) {
                selectedIndexVar = invInvtrysVar.length - 1;
            } else {
                selectedIndexVar -= 1;
            }
            return service.invInvtry();
        };

        service.next = function () {
            if (invInvtrysVar.length <= 0) return;

            if (selectedIndexVar >= invInvtrysVar.length - 1 || selectedIndexVar < 0) {
                selectedIndexVar = 0;
            } else {
                selectedIndexVar += 1;
            }

            return service.invInvtry();
        };

        service.searchResult = function (srchRslt) {
            if (srchRslt) {
                searchResult = srchRslt;
            }
            return searchResult;
        };

        return service;

}])
    .controller('CshDrawerCtrls', ['$scope', 'genericResource', 'CshDrawerUtils', 'CshDrawerState', '$location', '$rootScope', 'commonTranslations','fileExtractor',
function ($scope, genericResource, CshDrawerUtils, CshDrawerState, $location, $rootScope, commonTranslations,fileExtractor) {

            $scope.searchInput = CshDrawerState.searchInput();
            $scope.itemPerPage = CshDrawerState.itemPerPage;
            $scope.totalItems = CshDrawerState.totalItems;
            $scope.currentPage = CshDrawerState.currentPage();
            $scope.maxSize = CshDrawerState.maxSize;
            $scope.cdrCshDrawers = CshDrawerState.cdrCshDrawers;
            $scope.selectedIndex = CshDrawerState.selectedIndex;
            $scope.handleSearchRequestEvent = handleSearchRequestEvent;
            $scope.handlePrintRequestEvent = handlePrintRequestEvent;
            $scope.paginate = paginate;
            $scope.error = "";
            $scope.CshDrawerUtils = CshDrawerUtils;
            $scope.commonTranslations = commonTranslations;
            var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
                CshDrawerUtils.translate();
            });

            $scope.$on('$destroy', function () {
                translateChangeSuccessHdl();
            });

            init();

    function findCustom(searchInput) {
        genericResource.findCustom(CshDrawerUtils.cshdwr, searchInput)
            .success(function (entitySearchResult) {
                // store search
                CshDrawerState.searchResult(entitySearchResult);
                //$scope.searchInput = cdrDrctSalesState.searchResult().searchInput;
                $scope.cdrCshDrawers = CshDrawerState.searchResult().resultList;
            })
            .error(function (error) {
                $scope.error = error;
            });
    }

            function handleSearchRequestEvent() {
                var searchInput = {
                    entity: {},
                    fieldNames: [],
                    start: 0,
                    max: 25
                };
                if (angular.isDefined($scope.searchInput.entity.cashier ) && $scope.searchInput.entity.cashier ) {
                    searchInput.entity.cashier = $scope.searchInput.entity.cashier;
                }
                if($scope.searchInput.from)
                    searchInput.from = $scope.searchInput.from;

                if($scope.searchInput.to)
                    searchInput.to = $scope.searchInput.to;

                findCustom(searchInput);
            }

            function handlePrintRequestEvent() {
                genericResource.builfReport(CshDrawerUtils.cshdwr+'/cshdwrreport.pdf', $scope.searchInput).success(function(data){
                    fileExtractor.extractFile(data,"application/pdf");
                }).error(function (error) {
                    $scope.error = error;
                });
            }

            function paginate() {
                $scope.searchInput = CshDrawerState.paginate();
                findCustom($scope.searchInput);
            };

            function paginate() {
                $scope.searchInput = CshDrawerState.paginate();
                findCustom($scope.searchInput);
            }

            function init() {
                findCustom($scope.searchInput);
            }


}]);
  