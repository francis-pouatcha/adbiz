'use strict';

angular.module('AdCshdwr')

.factory('cdrCshDrawerManagerResource', ['$http', function ($http) {
        var service = {};
        var urlBase = '/adcshdwr.server/rest/cdrcshdrawers';
        return service;
}])
    .factory('cdrCshDrawerUtils', ['sessionManager', '$translate', 'genericResource', '$q', 'cdrCshDrawerManagerResource', function (sessionManager, $translate, genericResource, $q, cdrCshDrawerManagerResource) {
        var service = {};

        service.urlBase = '/adcshdwr.server/rest/cdrcshdrawers';
        service.language = sessionManager.language;

        service.currentWsUser = sessionManager.userWsData();

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
                    'CdrCshDrawer_close_description.title',

                    'Entity_show.title',
                    'Entity_previous.title',
                    'Entity_list.title',
                    'Entity_next.title',
                    'Entity_edit.title',
                    'Entity_create.title',
                    'Entity_update.title',
                    'Entity_Result.title',
                    'Entity_search.title',
                    'Entity_cancel.title',
                    'Entity_save.title',
                    'Entity_By.title',
                    'Entity_saveleave.title',
                    'Entity_add.title',
                    'Entity_back.title'
                 ])
                .then(function (translations) {
                    service.translations = translations;
                });
        };
        service.getActive = function () {
            var deferred = $q.defer();
            genericResource.get(service.urlBase + "/getActive").success(function (result) {
                deferred.resolve(result);
            }).error(function (error) {
                deferred.reject(error);
            });
            return deferred.promise;
        }

        service.translate();
        return service;
}])
    .factory('cdrCshDrawerState', ['$rootScope', '$q', function ($rootScope, $q) {

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
    .controller('cdrCshDrawersCtlr', ['$scope', 'genericResource', 'cdrCshDrawerUtils', 'cdrCshDrawerState', '$location', '$rootScope', 'commonTranslations',
function ($scope, genericResource, cdrCshDrawerUtils, cdrCshDrawerState, $location, $rootScope, commonTranslations) {

            $scope.searchInput = cdrCshDrawerState.searchInput();
            $scope.itemPerPage = cdrCshDrawerState.itemPerPage;
            $scope.totalItems = cdrCshDrawerState.totalItems;
            $scope.currentPage = cdrCshDrawerState.currentPage();
            $scope.maxSize = cdrCshDrawerState.maxSize;
            $scope.cdrCshDrawers = cdrCshDrawerState.cdrCshDrawers;
            $scope.selectedIndex = cdrCshDrawerState.selectedIndex;
            $scope.handleSearchRequestEvent = handleSearchRequestEvent;
            $scope.handlePrintRequestEvent = handlePrintRequestEvent;
            $scope.paginate = paginate;
            $scope.error = "";
            $scope.cdrCshDrawerUtils = cdrCshDrawerUtils;
            $scope.show = show;
            $scope.edit = edit;
            $scope.commonTranslations = commonTranslations;
            $scope.openCreateForm = openCreateForm;
            var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
                cdrCshDrawerUtils.translate();
            });

            $scope.$on('$destroy', function () {
                translateChangeSuccessHdl();
            });

            init();

    function findCustom(searchInput) {
        genericResource.findCustom(cdrCshDrawerUtils.urlBase, searchInput)
            .success(function (entitySearchResult) {
                // store search
                cdrCshDrawerState.searchResult(entitySearchResult);
                //$scope.searchInput = cdrDrctSalesState.searchResult().searchInput;
                 $scope.cdrDrctSales = cdrCshDrawerState.searchResult().resultList;
            })
            .error(function (error) {
                $scope.error = error;
            });
    }


    function handleSearchRequestEvent() {
                if ($scope.searchInput.acsngUser) {
                    $scope.searchInput.entity.acsngUser = $scope.searchInput.acsngUser.loginName;
                } else {
                    $scope.searchInput.entity.acsngUser = '';
                }
                findCustom($scope.searchInput);
            }

            function handlePrintRequestEvent() {
                // To do
            }

            function paginate() {
                $scope.searchInput = cdrCshDrawerState.paginate();
                findCustom($scope.searchInput);
            };

            function paginate() {
                $scope.searchInput = cdrCshDrawerState.paginate();
                findCustom($scope.searchInput);
            }

            function init() {
                //find previous cdr cshdrawers
                loadPreviousCshDrws();
            }

            function loadPreviousCshDrws() {
                genericResource.get(cdrCshDrawerUtils.urlBase + "/findPreviousCdrCshDrawer").success(function (result) {
                    $scope.cdrCshDrawers = result;
                }).error(function (error) {
                    $scope.error = error;
                });
            }

            function show(cdrCshDrawer, index) {

            }

            function edit(cdrCshDrawer, index) {

            }

            function checkOpenedCdrCshDrawer() {

                cdrCshDrawerUtils.getActive().then(function (result) {
                    if (result) {
                        $location.path("/CdrCshDrawers/show/" + result.id); //go to the show if a cash drawer exist
                    } else {
                        $location.path("/CdrCshDrawers/new")
                    }
                }, function (error) {
                    $scope.error = error;
                });
            }

            function openCreateForm() {
                checkOpenedCdrCshDrawer();
            }
}])
    .controller('cdrCshDrawersCreateCtlr', ['$scope', 'cdrCshDrawerUtils', '$translate', 'genericResource', '$location', 'cdrCshDrawerState', 'commonTranslations',
        function ($scope, cdrCshDrawerUtils, $translate, genericResource, $location, cdrCshDrawerState, commonTranslations) {
            $scope.cdrCshDrawer = {};$scope.cdrCshDrawer.initialAmt = 0;
            $scope.create = create;
            $scope.error = "";
            $scope.cdrCshDrawerUtils = cdrCshDrawerUtils;
            $scope.commonTranslations = commonTranslations;

            function create() {
                genericResource.create(cdrCshDrawerUtils.urlBase + "/openCshDrawer", $scope.cdrCshDrawer).success(function (result) {
                    $scope.cdrCshDrawer = result;
                    $location.path("/CdrCshDrawers/show/" + result.id);
                }).error(function (error) {
                    $scope.error = error;
                });
            };

}])
    .controller('cdrCshDrawersEditCtlr', ['$scope', 'genericResource', '$location', 'cdrCshDrawerUtils', 'cdrCshDrawerState', 'commonTranslations',
                                 function ($scope, genericResource, $location, cdrCshDrawerUtils, cdrCshDrawerState, commonTranslations) {
            $scope.cdrCshDrawer = cdrCshDrawerState.cdrCshDrawer;
            $scope.error = "";
            $scope.cdrCshDrawerUtils = cdrCshDrawerUtils;
            $scope.commonTranslations = commonTranslations;
            $scope.decaissUIisOpen = false;

            init();

            function init() {
                cdrCshDrawerUtils.getActive().then(function (result) {
                    $scope.cdrCshDrawer = result;
                }, function (error) {
                    $scope.error = error;
                });
            }
            $scope.close = function () {
                genericResource.create(cdrCshDrawerUtils.urlBase + "/closeCshDrawer", $scope.cdrCshDrawer).success(function (result) {
                    $scope.cdrCshDrawer = result;
                    $location.path("/CdrCshDrawers/show/" + result.id);
                }).error(function (error) {
                    $scope.error = error;
                });
            };
            $scope.decaissUI = function(){
                if($scope.decaissUIisOpen == false)
                    $scope.decaissUIisOpen =true;
                else
                    $scope.decaissUIisOpen = false;
            }
}]);