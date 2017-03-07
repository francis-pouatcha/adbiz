'use strict';

angular.module('adcshdwr')

    .factory('cdrDrctSaleManagerResource', ['$http', function ($http) {
        var service = {};
        var urlBase = '/adcshdwr.server/rest/cdrcshdrawers';
        return service;
    }])
    .factory('cdrDrctSalesUtils', ['$translate', 'genericResource', '$q', 'cdrDrctSaleManagerResource','dateFilter',
        function ($translate, genericResource, $q, cdrDrctSaleManagerResource,dateFilter) {
            var service = {};

            service.urlBase = '/adcshdwr.server/rest/cdrdrctsaless';
            service.cdrdrctsalesmanager = '/adcshdwr.server/rest/directsales';
            service.cdrdrctsales = '/adcshdwr.server/rest/cdrdrctsaless';
            service.cdrdrctsalesItem = '/adcshdwr.server/rest/cdrdsartitems';
            service.catalarticlesUrlBase = '/adcatal.server/rest/catalarticles';
            service.catalarticlesfeatmappingsUrlBase = '/adcatal.server/rest/catalartfeatmappings';
            service.stkArtLotUrlBase = '/adstock.server/rest/stkarticlelots';
            service.stkArtlot2strgsctnsUrlBase = '/adstock.server/rest/stkarticlelot2strgsctns';
            service.stkLotStockQtyUrl = '/adstock.server/rest/stkartstockqtys';
            service.pymtItemUrl='/adcshdwr.server/rest/cdrpymntitems';
            service.cdrPymtUrl='/adcshdwr.server/rest/cdrpymnts';

            service.translate = function () {
                $translate([

                    'CdrDsArtItem_artName_description.text',
                    'CdrDsArtItem_artName_description.title',
                    'CdrDsArtItem_artPic_description.text',
                    'CdrDsArtItem_artPic_description.title',
                    'CdrDsArtItem_description.text',
                    'CdrDsArtItem_description.title',
                    'CdrDsArtItem_dsNbr_description.text',
                    'CdrDsArtItem_dsNbr_description.title',
                    'CdrDsArtItem_grossSPPreTax_description.text',
                    'CdrDsArtItem_grossSPPreTax_description.title',
                    'CdrDsArtItem_lotPic_description.text',
                    'CdrDsArtItem_lotPic_description.title',
                    'CdrDsArtItem_netSPPreTax_description.text',
                    'CdrDsArtItem_netSPPreTax_description.title',
                    'CdrDsArtItem_netSPTaxIncl_description.text',
                    'CdrDsArtItem_netSPTaxIncl_description.title',
                    'CdrDsArtItem_objctOrgUnit_description.text',
                    'CdrDsArtItem_objctOrgUnit_description.title',
                    'CdrDsArtItem_rebate_description.text',
                    'CdrDsArtItem_rebate_description.title',
                    'CdrDsArtItem_restockgFees_description.text',
                    'CdrDsArtItem_restockgFees_description.title',
                    'CdrDsArtItem_returnedQty_description.text',
                    'CdrDsArtItem_returnedQty_description.title',
                    'CdrDsArtItem_soldQty_description.text',
                    'CdrDsArtItem_soldQty_description.title',
                    'CdrDsArtItem_sppuCur_description.text',
                    'CdrDsArtItem_sppuCur_description.title',
                    'CdrDsArtItem_sppuPreTax_description.text',
                    'CdrDsArtItem_sppuPreTax_description.title',
                    'CdrDsArtItem_vatAmount_description.text',
                    'CdrDsArtItem_vatAmount_description.title',
                    'CdrDsArtItem_vatPct_description.text',
                    'CdrDsArtItem_vatPct_description.title',

                    'CdrDsHstry_cdrNbr_description.text',
                    'CdrDsHstry_cdrNbr_description.title',
                    'CdrDsHstry_description.text',
                    'CdrDsHstry_description.title',
                    'CdrDsHstry_dsNbr_description.text',
                    'CdrDsHstry_dsNbr_description.title',
                    'CdrDrctSales_cashier_description.text',
                    'CdrDrctSales_cashier_description.title',
                    'CdrDrctSales_cdrNbr_description.text',
                    'CdrDrctSales_cdrNbr_description.title',
                    'CdrDrctSales_description.text',
                    'CdrDrctSales_description.title',
                    'CdrDrctSales_dsCur_description.text',
                    'CdrDrctSales_dsCur_description.title',
                    'CdrDrctSales_dsNbr_description.text',
                    'CdrDrctSales_dsNbr_description.title',
                    'CdrDrctSales_grossSPPreTax_description.text',
                    'CdrDrctSales_grossSPPreTax_description.title',
                    'CdrDrctSales_netAmtToPay_description.text',
                    'CdrDrctSales_netAmtToPay_description.title',
                    'CdrDrctSales_netSPPreTax_description.text',
                    'CdrDrctSales_netSPPreTax_description.title',
                    'CdrDrctSales_netSPTaxIncl_description.text',
                    'CdrDrctSales_netSPTaxIncl_description.title',
                    'CdrDrctSales_netSalesAmt_description.text',
                    'CdrDrctSales_netSalesAmt_description.title',
                    'CdrDrctSales_pymtDscntAmt_description.text',
                    'CdrDrctSales_pymtDscntAmt_description.title',
                    'CdrDrctSales_pymtDscntPct_description.text',
                    'CdrDrctSales_pymtDscntPct_description.title',
                    'CdrDrctSales_rcptNbr_description.text',
                    'CdrDrctSales_rcptNbr_description.title',
                    'CdrDrctSales_rcptPrntDt_description.text',
                    'CdrDrctSales_rcptPrntDt_description.title',
                    'CdrDrctSales_rdngDscntAmt_description.text',
                    'CdrDrctSales_rdngDscntAmt_description.title',
                    'CdrDrctSales_rebate_description.text',
                    'CdrDrctSales_rebate_description.title',
                    'CdrDrctSales_vatAmount_description.text',
                    'CdrDrctSales_vatAmount_description.title',
                    'CdrDrctSales_paidAmt_description.title',
                    'CdrDrctSales_changeAmt_description.title',
                    'CdrDrctSales_from_description.title',
                    'CdrDrctSales_to_description.title',

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


                    'CatalArticle_pic_description.title',
                    'CatalArticle_articleName_description.title',
                    'CatalArticle_stockQty_description.title',
                    'CatalArticle_sppu_description.title',
                    'CatalArticle_vatRate_description.title',
                    'CatalArticle_familiesIdentif_description.title'
                ])
                    .then(function (translations) {
                        service.translations = translations;
                    });
            };

            service.loadArticlesByPic = function (artPic) {
                return genericResource.findByLikePromissed(service.catalarticlesUrlBase, 'pic', artPic)
                    .then(function (entitySearchResult) {
                        return entitySearchResult.resultList;
                    });
            };

            service.loadAStkLotStockQtyByPic = function (lotPic) {
                return genericResource.findByLike(service.stkLotStockQtyUrl, 'lotPic', lotPic)
                    .then(function (entitySearchResult) {
                        return entitySearchResult.resultList;
                    });
            };

            service.loadArticleLotByArtPic = function (artPic) {
                return genericResource.findByLikePromissed(service.stkArtLotUrlBase, 'artPic', artPic)
                    .then(function (entitySearchResult) {
                        var resultList = entitySearchResult.resultList;
                        return parseArticleSearchResult(resultList);
                    });
            }

            service.loadArticleLotByPic = function (lotPic) {
                return genericResource.findByLikePromissed(service.stkArtLotUrlBase, 'lotPic', lotPic)
                    .then(function (entitySearchResult) {
                        var resultList = entitySearchResult.resultList;
                        return parseArticleSearchResult(resultList);
                    });
            }

            service.loadArticlesLotByName = function (artName) {

                return  genericResource.getWithPromise(service.stkArtLotUrlBase+"/findbyartname/"+artName)
                    .then(function (resultList) {
                        return parseArticleSearchResult(resultList);
                    });
            };

            service.loadArticlesLotQtyByLotPic = function (lotPic) {
                lotPic=lotPic.toUpperCase();
                return  genericResource.getWithPromise(service.stkArtLotUrlBase+"/findbylotpic/"+lotPic)
                    .then(function (resultList) {
                        return parseArticleSearchResult(resultList);
                    });
            };


            function parseArticleSearchResult(resultList){
                var displayDatas = [];
                for	(var index = 0; index < resultList.length; index++) {
                    var item = resultList[index];

                    if(angular.isUndefined(item)) continue;
                    var displayable = {};
                    var displayableStr = "";
                    displayableStr = item.artPic;
                    displayableStr += " - "+item.artName;
                    displayableStr += " - lot (" + item.lotPic + ")";
                    displayableStr += " - section (" + item.section + ")";
                    displayableStr += "- Qte (" + item.trgtQty + ")";
                    displayableStr += " - puv (" + item.slsNetPrcTaxIncl +" "+item.slsUnitPrcCur+")";

                    if(angular.isDefined(item.expirDt) && item.expirDt){
                        displayableStr += " - dtExpir (" + dateFilter(item.expirDt, 'dd.MM.yyyy') + ")";
                    }

                    if(angular.isDefined(item.stkgDt) && item.stkgDt){
                        displayableStr += " - dtStkg (" + dateFilter(item.stkgDt, 'dd.MM.yyyy') + ")";
                    }


                    displayable=item;
                    displayable.displayableStr = displayableStr;

                    displayDatas.push(displayable);
                }
                return displayDatas;
            }

            service.translate();
            return service;
        }])
    .factory('cdrDrctSalesState', ['$rootScope', '$q', function ($rootScope, $q) {

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
            service.cdrDrctSales(entitySearchResult.resultList);
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
        service.cdrDrctSale = function (index) {
            if (index && index >= 0 && index < cdrDrctSalesVar.length) selectedIndexVar = index;
            if (selectedIndexVar < 0 || selectedIndexVar >= cdrDrctSalesVar.length) return;
            return cdrDrctSalesVar[selectedIndexVar];
        };

        // replace the current partner after a change.
        service.replace = function (cdrDrctSale) {
            if (!cdrDrctSalesVar || !cdrDrctSale) return;
            var currentInvt = service.cdrDrctSale();
            if (currentInvt && currentInvt.invtryNbr == cdrDrctSale.invtryNbr) {
                cdrDrctSalesVar[selectedIndexVar] = cdrDrctSale;
            } else {
                for (var index in cdrDrctSalesVar) {
                    if (cdrDrctSalesVar[index].invtryNbr == cdrDrctSale.invtryNbr) {
                        cdrDrctSalesVar[index] = cdrDrctSale;
                        selectedIndexVar = index;
                        break;
                    }
                }
            }
        };
        service.set = function (cdrDrctSale) {
            if (!cdrDrctSalesVar || !cdrDrctSale) return false;
            cdrDrctSalesVar[selectedIndexVar] = cdrDrctSale;
            return true;
        };

        // CHeck if the business partner to be displayed is at the correct index.
        service.peek = function (cdrDrctSale, index) {
            if (!cdrDrctSalesVar || !cdrDrctSale) return false;
            if (index >= 0 && index < cdrDrctSalesVar.length) {
                selectedIndexVar = index;
                return true;
            }
            return false;
        };

        service.push = function (cdrDrctSale) {
            if (!cdrDrctSalesVar || !cdrDrctSale) return false;
            var length = cdrDrctSalesVar.push(cdrDrctSale);
            selectedIndexVar = length - 1;
            return true;
        };

        service.previous = function () {
            if (cdrDrctSalesVar.length <= 0) return;

            if (selectedIndexVar <= 0) {
                selectedIndexVar = cdrDrctSalesVar.length - 1;
            } else {
                selectedIndexVar -= 1;
            }
            return service.cdrDrctSale();
        };

        service.next = function () {
            if (cdrDrctSalesVar.length <= 0) return;

            if (selectedIndexVar >= cdrDrctSalesVar.length - 1 || selectedIndexVar < 0) {
                selectedIndexVar = 0;
            } else {
                selectedIndexVar += 1;
            }

            return service.cdrDrctSale();
        };
        service.searchResult = function (srchRslt) {
            if (srchRslt) {
                searchResult = srchRslt;
            }
            return searchResult;
        };
        service.hasEntities = function() {
            return searchResult.resultList && searchResult.resultList.length > 0;
        }
        return service;

    }])
    .controller('cdrDrctSalesCtlr', ['$scope', 'genericResource', 'cdrDrctSalesUtils', 'cdrDrctSalesState', '$location', '$rootScope','$translate',
        function ($scope, genericResource, cdrDrctSalesUtils, cdrDrctSalesState, $location, $rootScope,$translate) {

            $scope.searchInput = cdrDrctSalesState.searchInput();
            $scope.itemPerPage = cdrDrctSalesState.itemPerPage;
            $scope.totalItems = cdrDrctSalesState.totalItems;
            $scope.currentPage = cdrDrctSalesState.currentPage();
            $scope.maxSize = cdrDrctSalesState.maxSize;
            $scope.cdrCshDrawers = cdrDrctSalesState.cdrCshDrawers;
            $scope.selectedIndex = cdrDrctSalesState.selectedIndex;
            $scope.processSearchInput = processSearchInput;
            $scope.handlePrintRequestEvent = handlePrintRequestEvent;
            $scope.paginate = paginate;
            $scope.error = "";
            $scope.cdrDrctSalesUtils = cdrDrctSalesUtils;
            $scope.show = show;
            $scope.edit = edit;
            $scope.openCreateForm = openCreateForm;

            $scope.openDateComponent = function(componentId,$event) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.dateConfig[componentId].opened = true;
            };

            $translate.use('fr');

            $scope.dateConfig = {
                format: 'dd-MM-yyyy',
                drctSalesDtFrom: {
                    opened : false
                },
                drctSalesDtTo: {
                    opened : false
                }
            };

            var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
                cdrDrctSalesUtils.translate();
            });

            $scope.$on('$destroy', function () {
                translateChangeSuccessHdl();
            });

            init();

            function processSearchInput() {
                var searchInput = {
                    entity: {},
                    fieldNames: [],
                    start: 0,
                    max: 25
                };
                if (angular.isDefined($scope.searchInput.entity.identif ) && $scope.searchInput.entity.identif ) {
                    searchInput.entity.identif = $scope.searchInput.entity.identif;
                    searchInput.fieldNames.push('identif');
                }
                if($scope.searchInput.valueDtFrom){
                    searchInput.valueDtFrom = $scope.searchInput.valueDtFrom;
                    searchInput.fieldNames.push('valueDtFrom');
                }

                if($scope.searchInput.valueDtTo){
                    searchInput.valueDtTo = $scope.searchInput.valueDtTo;
                    searchInput.fieldNames.push('valueDtTo');
                }

                findCustom(searchInput);
            }

            function findCustom(searchInput) {
                searchInput.entity.username = $rootScope.username;
                searchInput.fieldNames.push('username');
                genericResource.findCustom(cdrDrctSalesUtils.urlBase, searchInput)
                    .success(function (entitySearchResult) {
                        // store search
                        cdrDrctSalesState.searchResult(entitySearchResult);
                        //$scope.searchInput = cdrDrctSalesState.searchResult().searchInput;
                        $scope.cdrDrctSales = cdrDrctSalesState.searchResult().resultList;
                    })
                    .error(function (error) {
                        $scope.error = error;
                    });
            }

            function handlePrintRequestEvent() {}

            function paginate() {
                $scope.searchInput = cdrDrctSalesState.paginate();
                findCustom($scope.searchInput);
            };


            function init() {
                findCustom($scope.searchInput);
            }

            function show(cdrCshDrawer, index) {

            }

            function edit(cdrCshDrawer, index) {

            }

            function openCreateForm() {}
        }])
    .controller('cdrDrctSalesCreateCtlr', ['$scope', 'cdrDrctSalesUtils', 'fileExtractor', '$translate', 'genericResource', '$location', 'cdrDrctSalesState', '$modal', '$http','$stateParams','cdrCshDrawerUtils','$q','$filter','logger','$rootScope',
        function ($scope, cdrDrctSalesUtils, fileExtractor, $translate, genericResource, $location, cdrDrctSalesState, $modal, $http,$stateParams,cdrCshDrawerUtils,$q,$filter,logger, $rootScope) {
            $scope.cdrCshDrawer = {
                cdrDrctSales: {}
            };
            $scope.error = "";
            $scope.cur = "XAF";
            $scope.cdrDrctSalesUtils = cdrDrctSalesUtils;
            $scope.cdrDsArtItemHolder = {
                item: {}
            };
            $scope.cdrDsArtHolder = {
                cdrDrctSales: {},
                items: []
            };
            $scope.showprint = false;
            var cdrNber;

            init();

            function init(){
                cdrCshDrawerUtils.getActive().then(function (result) {
                    if (result) {
                        cdrNber = result.identif;
                    } else {
                        $location.path("/");
                        logger.warning("Ouvrez une caisse, avant de procéder a la vente.", {}, "Pas de caisse active!")
                    }
                });

                var slsIdentif = $stateParams.id;
                if(slsIdentif){
                    $scope.showprint = true;
                    genericResource.findById(cdrDrctSalesUtils.cdrdrctsales, slsIdentif)
                        .success(function (entitySearchResult) {
                            $scope.cdrDsArtHolder.cdrDrctSales=entitySearchResult;
                            var searchInput = {
                                entity:{},
                                fieldNames:[]
                            };
                            searchInput.entity.cntnrIdentif = slsIdentif;
                            searchInput.fieldNames.push('cntnrIdentif');
                            genericResource.findByLike(cdrDrctSalesUtils.cdrdrctsalesItem, searchInput)
                                .success(function (data) {
                                    var cdrDsArtItemHolder ={};
                                    angular.forEach(data.resultList, function(item){
                                        cdrDsArtItemHolder.artName=item.artName;
                                        cdrDsArtItemHolder.item=item;
                                        $scope.cdrDsArtHolder.items.push(cdrDsArtItemHolder);
                                    });
                                });
                        })
                        .error(function (error) {
                            $scope.error = error;
                        });

                }

            }

            $scope.onArticleSelectedInSearch = function (item, model, label) {
                $scope.cdrDsArtItemHolder.item.artPic = item.artPic;
                $scope.cdrDsArtItemHolder.item.lotPic = item.lotPic;
                $scope.cdrDsArtItemHolder.item.section = item.section;
                $scope.cdrDsArtItemHolder.artName = item.artName;
                $scope.cdrDsArtItemHolder.item.artName = item.artName;
                $scope.cdrDsArtItemHolder.item.slsUnitPrcPreTax = item.slsUnitPrcPreTax;
                if (!item.slsUnitPrcPreTax) item.slsUnitPrcPreTax = 0.0;
                if (!item.slsVatAmt) item.slsVatAmt = 0.0;
                $scope.cdrDsArtItemHolder.maxStockQty = item.trgtQty;
                $scope.cdrDsArtItemHolder.item.slsUnitPrcCur = item.slsUnitPrcCur;
                $scope.cdrDsArtItemHolder.item.slsVatPct = item.slsVatPct; //the slsVatPct
            };

            $scope.evltSlsNetPrcPreTax = function(){
                if($scope.cdrDsArtItemHolder.item.trgtQty){
                    parseInt($scope.cdrDsArtItemHolder.item.slsUnitPrcPreTax * $scope.cdrDsArtItemHolder.item.trgtQty)==NaN?
                        $scope.cdrDsArtItemHolder.item.slsNetPrcPreTax=0:$scope.cdrDsArtItemHolder.item.slsNetPrcPreTax=parseInt($scope.cdrDsArtItemHolder.item.slsUnitPrcPreTax * $scope.cdrDsArtItemHolder.item.trgtQty);
                }else{
                    $scope.cdrDsArtItemHolder.item.slsNetPrcPreTax=0;
                }
            }

            $scope.addItem = function () {
                addItem($scope.cdrDsArtItemHolder);
            };

            $scope.clear = function () {
                $scope.cdrDsArtItemHolder = {
                    item: {}
                };
            };

            $scope.cdrDsArtItems = function () {
                return $scope.cdrDsArtItemHolder.items;
            };

            $scope.editItem = function (index) {
                if(!$scope.showprint)
                    $scope.cdrDsArtItemHolder = angular.copy($scope.cdrDsArtHolder.items[index]);
            };

            $scope.deleteItem = function (index) {
                $scope.cdrDsArtHolder.items.splice(index,1);
            };

            $scope.saveAndLeave = function () {
                if($scope.cdrDsArtHolder && $scope.cdrDsArtHolder.items.length > 0){
                    computeCdrDsArtHolder();
                    genericResource.create(cdrDrctSalesUtils.cdrdrctsalesmanager, $scope.cdrDsArtHolder).success(function (result) {
                        $scope.cdrDsArtHolder = result;
                        $location.path("/CdrDrctSales/show" + result.id);
                    }).error(function (error) {
                        $scope.error = error;
                    });
                }
            };


            function clear(){
                $scope.cdrDsArtHolder=null;
                $scope.cdrDsArtHolder = {};
                $scope.cdrDsArtHolder = {
                    cdrDrctSales: {},
                    items: []
                };
                $scope.cdrDsArtItemHolder = {
                    item: {}
                };
                $scope.showprint = false;
                $scope.error="";
            }

            function pay(invNbr){

                var cdrPaymnt = {};
                cdrPaymnt.invNbr=invNbr;
                cdrPaymnt.cdrNbr=cdrNber;
                cdrPaymnt.amt=$scope.cdrDsArtHolder.cdrDrctSales.slsNetAmtToPay;
                cdrPaymnt.cashier="caissier"; //to be changed by connected user
                cdrPaymnt.paidBy="CLIENT DIVERS";

                var deferred = $q.defer();
                genericResource.create(cdrDrctSalesUtils.cdrPymtUrl, cdrPaymnt)
                    .success(function(cdrPaymntResult){
                        deferred.resolve(cdrPaymntResult);
                        var pymtItem = {};
                        pymtItem.cntnrIdentif=cdrPaymntResult.identif;
                        pymtItem.amt=$scope.cdrDsArtHolder.cdrDrctSales.slsNetAmtToPay;
                        pymtItem.rcvdAmt=$scope.cdrDsArtHolder.paidAmt;
                        pymtItem.diffAmt=$scope.cdrDsArtHolder.changeAmt;
                        pymtItem.pymntMode='CASH';
                        pymtItem.orgUnit='CLIENT DIVERS';

                        genericResource.create(cdrDrctSalesUtils.pymtItemUrl, pymtItem)
                            .success(function(pymtItemResult){
                            });
                    });
                return deferred.promise;
            }

            $scope.save = function () {
                //computeCdrDsArtHolder();
                if(verifCdrDsArtHolder() == false){
                    return;
                }
                $scope.cdrDsArtHolder.cdrDrctSales.cdrNbr=cdrNber;
                $scope.cdrDsArtHolder.cdrDrctSales.bsnsPtnrName="CLIENT DIVERS"
                $scope.cdrDsArtHolder.cdrDrctSales.username=$rootScope.username;

                genericResource.create(cdrDrctSalesUtils.cdrdrctsalesmanager, $scope.cdrDsArtHolder.cdrDrctSales).success(function (result) {
                    $scope.showprint = true;
                    $scope.cdrDsArtHolder.cdrDrctSales = result;

                    $scope.error = "";
                    var i=0;
                    angular.forEach($scope.cdrDsArtHolder.items, function(elt){
                        elt.item.cntnrIdentif = $scope.cdrDsArtHolder.cdrDrctSales.identif;
                        i+=1;
                        genericResource.create(cdrDrctSalesUtils.cdrdrctsalesmanager+"/"+elt.item.cntnrIdentif+"/items", elt.item)
                            .success(function (itemResult) {
                            });
                    });

                    pay(result.identif).then(function(cdrPaymnt){
                        $scope.cdrDsArtHolder.cdrDrctSales.pymntNbr = cdrPaymnt.identif;
                        genericResource.update(cdrDrctSalesUtils.cdrdrctsalesmanager, $scope.cdrDsArtHolder.cdrDrctSales)
                            .success(function(saleResult){
                                genericResource.postObject(cdrDrctSalesUtils.cdrdrctsalesmanager+"/"+saleResult.identif+"/post")
                                    .success(function(){

                                        if($scope.cdrDsArtHolder.cdrDrctSales.slsNetAmtToPay > 0){
                                            printReceipt(saleResult.identif);
                                        }else{
                                            printVoucher($scope.cdrDsArtHolder.cdrDrctSales.cdrNbr);
                                        }
                                        return;
                                    });
                            });
                    });

                }).error(function (error) {
                    $scope.error = error;
                });

            };

            // Print pdf receipt
            function printReceipt(id){
                var printerData = {};
                printerData.drctSalesIdentif = id;
                genericResource.builfReport(cdrDrctSalesUtils.cdrdrctsalesmanager+"/"+id+"/receipt.pdf", printerData).success(function (result) {
                    fileExtractor.extractFile(result,"application/pdf");
                }).error(function (error) {
                    $scope.error = error;
                });
            };
            // Print pdf voucher
            function printVoucher(id){
                var printerData = {};
                printerData.displaySlsNetPymtAmt=true;
                printerData.displayTicketMessage=true;
                printerData.ticketMessage="Les produits vendus ne sont ni échangés, ni retournés. Merci de votre confiance!";
                printerData.drctSalesIdentif = saleResult.identif;
                genericResource.builfReport(cdrDrctSalesUtils.cdrdrctsalesmanager+"/"+id+"/voucher.pdf", printerData).success(function (result) {
                    fileExtractor.extractFile(result,"application/pdf");
                }).error(function (error) {
                    $scope.error = error;
                });
            };


            function verifCdrDsArtHolder(){
                if($scope.cdrDsArtHolder.cdrDrctSales.slsNetAmtToPay > $scope.cdrDsArtHolder.paidAmt || !$scope.cdrDsArtHolder.paidAmt){
                    $scope.error = "Montant paye est inferieur au montant de vente";
                    return false;
                }
                if($scope.cdrDsArtHolder.cdrDrctSales.slsPymtDscntAmt > $scope.cdrDsArtHolder.cdrDrctSales.slsNetPrcPreTax){
                    $scope.error = "Montant de l'escompte est superieur au montant de vente hors taxe";
                    return false;
                }
                if(!($scope.cdrDsArtHolder.items.length > 0)){
                    $scope.error = "Aucune ligne de vente existante";
                    return false;
                }
            }

            $scope.cancel = function () {
                clear();
            };

            $scope.recompute = function () {
                computeCdrDsArtHolder();
            };

            $scope.printRequest = function(){
                if($scope.cdrDsArtHolder.cdrDrctSales.slsNetAmtToPay > 0){
                    printReceipt($scope.cdrDsArtHolder.cdrDrctSales.id);
                }else{
                    printVoucher($scope.cdrDsArtHolder.cdrDrctSales.cdrNbr);
                }
            };

            $scope.hasItem = function () {
                return $scope.cdrDsArtHolder.items.length > 0;
            };

            $scope.pymtDscntPctChanged = function () {
                if (!$scope.cdrDsArtHolder.cdrDrctSales.slsPymtDscntPct) return;
                $scope.cdrDsArtHolder.cdrDrctSales.slsPymtDscntAmt = ($scope.cdrDsArtHolder.cdrDrctSales.slsPymtDscntPct * $scope.cdrDsArtHolder.cdrDrctSales.slsNetPrcPreTax) / 100;
                $scope.cdrDsArtHolder.cdrDrctSales.slsNetPymtAmt = $scope.cdrDsArtHolder.cdrDrctSales.slsNetPrcTaxIncl - $scope.cdrDsArtHolder.cdrDrctSales.slsPymtDscntAmt;
                var slsNetPymtAmt = $scope.cdrDsArtHolder.cdrDrctSales.slsNetPymtAmt;
                roundAmountObject.roundAmount(slsNetPymtAmt);
                $scope.cdrDsArtHolder.cdrDrctSales.slsNetAmtToPay = roundAmountObject.amount;
                $scope.cdrDsArtHolder.cdrDrctSales.slsRdngDscntAmt =roundAmountObject.roundDiscount;
                $scope.paidAmtChanged();
                $filter('number')($scope.cdrDsArtHolder.cdrDrctSales.slsRdngDscntAmt, 0)
            };

            $scope.pymtDscntAmtChanged = function () {
                if (!$scope.cdrDsArtHolder.cdrDrctSales.slsPymtDscntAmt) return;
                $scope.cdrDsArtHolder.cdrDrctSales.slsPymtDscntPct = ($scope.cdrDsArtHolder.cdrDrctSales.slsPymtDscntAmt * 100) / $scope.cdrDsArtHolder.cdrDrctSales.slsNetPrcPreTax;
                $scope.cdrDsArtHolder.cdrDrctSales.slsNetPymtAmt = $scope.cdrDsArtHolder.cdrDrctSales.slsNetPrcTaxIncl - $scope.cdrDsArtHolder.cdrDrctSales.slsPymtDscntAmt; //update the slsNetPrcTaxIncl
                var slsNetPymtAmt = $scope.cdrDsArtHolder.cdrDrctSales.slsNetPymtAmt;
                roundAmountObject.roundAmount(slsNetPymtAmt);
                $scope.cdrDsArtHolder.cdrDrctSales.slsNetAmtToPay = roundAmountObject.amount;
                $scope.cdrDsArtHolder.cdrDrctSales.slsRdngDscntAmt =roundAmountObject.roundDiscount;
                $scope.paidAmtChanged();

                $filter('number')($scope.cdrDsArtHolder.cdrDrctSales.slsPymtDscntPct, 0)
            };

            $scope.paidAmtChanged = function () {
                if (!$scope.cdrDsArtHolder.paidAmt) return;
                $scope.cdrDsArtHolder.changeAmt = $scope.cdrDsArtHolder.paidAmt - $scope.cdrDsArtHolder.cdrDrctSales.slsNetAmtToPay;
            }

            function clearObject(anObject) {
                anObject = {
                    item: {}
                };
                return anObject;
            }

            function isNotCorrect(cdrDsArtItemHolder) {
                return isCorrect(cdrDsArtItemHolder) == false;
            }

            function isCorrect(cdrDsArtItemHolder) {
                if (!cdrDsArtItemHolder || angular.isUndefined(cdrDsArtItemHolder) || !(cdrDsArtItemHolder.item.artPic)) {
                    return false;
                }
                return true;
            }

            function addItem(cdrDsArtItemHolder) {
                if (!cdrDsArtItemHolder.item.trgtQty) cdrDsArtItemHolder.item.trgtQty = 0.0;
                var billedQty=Math.abs(parseInt(cdrDsArtItemHolder.item.trgtQty));
                console.log(billedQty);
                if (isNotCorrect($scope.cdrDsArtItemHolder)) return;
                if (cdrDsArtItemHolder.item.trgtQty > cdrDsArtItemHolder.maxStockQty){
                    $scope.error ="Quantite Vendus superieur a la quantite en stock";
                    return;
                }
                if (0 > cdrDsArtItemHolder.item.slsVatPct){
                    $scope.error ="La TVA ne peut etre negatif";
                    return;
                }
                if (cdrDsArtItemHolder.item.slsRstckgUnitFeesPreTax > cdrDsArtItemHolder.item.slsUnitPrcPreTax
                    || cdrDsArtItemHolder.item.slsUnitPrcPreTax  == cdrDsArtItemHolder.item.slsRstckgUnitFeesPreTax){
                    $scope.error ="La frais de stockage ne peut etre superieur au montant de vente";
                    return;
                }
                if (cdrDsArtItemHolder.item.slsRebateAmt > cdrDsArtItemHolder.item.slsUnitPrcPreTax * billedQty
                    || cdrDsArtItemHolder.item.slsUnitPrcPreTax * billedQty == cdrDsArtItemHolder.item.slsRebateAmt){
                    $scope.error ="La remise ne peut etre superieur au montant de vente";
                    return;
                }
                var copy = angular.copy(cdrDsArtItemHolder)
                var i = 0;
                var items = $scope.cdrDsArtHolder.items;
                if (items.length == 0) {
                    $scope.cdrDsArtHolder.items.push(copy);
                } else {
                    var found = false;
                    angular.forEach(items, function (artItemHolder) {
                        if ((artItemHolder.item.artPic == copy.item.artPic) && (artItemHolder.artName == copy.artName)) {
                            items[i] = copy;
                            found = true;
                        }
                        i += 1;
                    });
                    if (!found) {
                        $scope.cdrDsArtHolder.items.push(copy);
                    }
                }
                computeCdrDsArtHolder();
                $scope.pymtDscntPctChanged();
                $scope.error = "";
                $scope.cdrDsArtItemHolder = clearObject($scope.cdrDsArtItemHolder);
            }

            function computeCdrDsArtHolder() {
                var items = $scope.cdrDsArtHolder.items;

                var totalNetSalesAmt = 0.0;
                var totalVatAmount = 0.0;
                var totalslsNetPrcPreTax = 0.0;
                var totalNetSPTaxIncl = 0.0;
                var totalGrossSPPreTax = 0.0;
                var currency = $scope.cur;

                angular.forEach(items, function (artItemHolder) {

                    if (!artItemHolder.item.slsRebateAmt) artItemHolder.item.slsRebateAmt = 0.0;
                    if (!artItemHolder.item.trgtQty) artItemHolder.item.trgtQty = 0.0;
                    var grossSPPTax = artItemHolder.item.slsUnitPrcPreTax * (parseInt(artItemHolder.item.trgtQty));
                    var slsNetPrcPreTax = grossSPPTax - parseInt(artItemHolder.item.slsRebateAmt);
                    if(!artItemHolder.item.slsRstckgUnitFeesPreTax)
                        artItemHolder.item.slsRstckgUnitFeesPreTax = 0.0;
                    artItemHolder.item.slsRstckgFeesPreTax=artItemHolder.item.slsRstckgUnitFeesPreTax*artItemHolder.item.trgtQty;
                    slsNetPrcPreTax = slsNetPrcPreTax + parseInt(artItemHolder.item.slsRstckgFeesPreTax);
                    var vatAmount = slsNetPrcPreTax * (artItemHolder.item.slsVatPct / 100);
                    var netSPTaxIncl = slsNetPrcPreTax + vatAmount;
                    artItemHolder.item.slsNetPrcPreTax = slsNetPrcPreTax;
                    artItemHolder.item.slsNetPrcTaxIncl = netSPTaxIncl;
                    artItemHolder.item.slsVatAmt = vatAmount;
                    artItemHolder.item.slsUnitPrcCur = $scope.cur;
                    //update the global sale object
                    totalGrossSPPreTax += grossSPPTax;
                    totalslsNetPrcPreTax += slsNetPrcPreTax;
                    totalNetSPTaxIncl += netSPTaxIncl;
                    totalVatAmount += vatAmount;
                });
                totalNetSalesAmt = totalNetSPTaxIncl;

                if ($scope.cdrDsArtHolder.cdrDrctSales.slsRebateAmt) {
                    totalslsNetPrcPreTax = totalslsNetPrcPreTax - $scope.cdrDsArtHolder.cdrDrctSales.slsRebateAmt;
                }
                $scope.cdrDsArtHolder.cdrDrctSales.slsNetPymtAmt = totalslsNetPrcPreTax;
                roundAmountObject.roundAmount(totalNetSalesAmt);
                $scope.cdrDsArtHolder.cdrDrctSales.slsNetAmtToPay = roundAmountObject.amount;
                $scope.cdrDsArtHolder.cdrDrctSales.slsRdngDscntAmt =roundAmountObject.roundDiscount;
                $scope.cdrDsArtHolder.cdrDrctSales.slsNetPrcPreTax = totalslsNetPrcPreTax;
                $scope.cdrDsArtHolder.cdrDrctSales.slsNetPrcTaxIncl = totalNetSPTaxIncl;
                $scope.cdrDsArtHolder.cdrDrctSales.slsVatAmt = totalVatAmount;
                $scope.cdrDsArtHolder.cdrDrctSales.slsPrcCur = currency;
            }



            var roundAmountObject = {};
            roundAmountObject.amount;
            roundAmountObject.roundDiscount;

            roundAmountObject.roundAmount = function(amount){
                this.roundDiscount = 0;
                roundAmountObject.amount = Math.round(amount);
                var isMultiple = false;
                while (!isMultiple) {
                    if (Math.abs(this.amount) % 5 == 0) {
                        isMultiple = true;
                    }
                    else {
                        this.roundDiscount++;
                        this.amount++;
                    }
                }

            }

            /* $scope.fetchCatalArticles = function () {
             var modalInstance = $modal.open({
             templateUrl: '/adcshdwr.client/src/client/app/views/CdrDrctSale/CatalArticlesModule.html',
             controller: catalArticlesCtlrTwo,
             windowClass: 'fetchArticles',
             scope: $scope,
             resolve: {
             userForm: function () {
             return $scope.userForm;
             }
             }
             });

             modalInstance.result.then(function (selectedItem) {
             // console.log("select element in form : ---> " + selectedItem);
             }, function () {
             // console.log('Modal dismissed at: ' + new Date());
             });

             };

             function catalArticlesCtlrTwo() {

             var self = this ;
             $scope.catalArticlesCtlr = self;

             self.searchInput = {
             entity:{
             },
             fieldNames:[],
             start:0,
             max:self.itemPerPage
             };
             self.totalItems ;
             self.itemPerPage=25;
             self.currentPage = 1;
             self.smallnumPages= 10;
             self.maxSize = 10 ;
             self.catalArticles = [];
             self.catalArticle = {};
             self.searchEntity = {};
             self.selectedItem = {} ;
             self.selectedIndex  ;
             self.handleSearchRequestEvent = handleSearchRequestEvent;
             self.handlePrintRequestEvent = handlePrintRequestEvent;
             self.paginate = paginate;
             self.error = "";
             self.showList = true;
             self.showArticle = showArticle;

             init();

             function init(){
             self.searchInput = {
             entity:{
             },
             fieldNames:[],
             start:0,
             max:self.itemPerPage
             }
             findCustom(self.searchInput);
             }

             function findCustom(searchInput){
             genericResource.findCustom('/adcatal.server/rest/catalarticles', searchInput)
             .success(function(entitySearchResult) {
             self.catalArticles = entitySearchResult.resultList;
             self.totalItems = entitySearchResult.count ;
             });
             }

             function processSearchInput(){
             var fieldNames = [];
             if(self.searchInput.entity.features.artName){
             fieldNames.push('features.artName') ;
             self.searchInput.entity.features.langIso2='fr';
             }
             if(self.searchInput.entity.pic){
             fieldNames.push('pic') ;
             }
             if(self.searchInput.entity.familyFeatures.familyName){
             fieldNames.push('familyFeatures.familyName') ;
             self.searchInput.entity.familyFeatures.langIso2='fr';
             }
             self.searchInput.fieldNames = fieldNames ;
             return self.searchInput ;
             };

             function  handleSearchRequestEvent(){
             processSearchInput();
             findCustom(self.searchInput);
             };

             function paginate(){
             self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
             self.searchInput.max = self.itemPerPage ;
             findCustom(self.searchInput);
             };

             function handlePrintRequestEvent(){
             }

             function showList(){
             if(self.showList == true){
             self.showList = false;
             }

             else{
             self.showList = true;
             }
             };

             function showArticle(artPic){
             $http.get('/adcatal.server/rest/catalarticles/' + artPic)
             .success(function(data) {
             self.catalArticle = data;
             });
             showList();
             };

             };*/

        }])
    .controller('cdrDrctSalesShowCtlr', ['$scope', 'genericResource', 'fileExtractor', '$location', 'cdrDrctSalesUtils', 'cdrDrctSalesState','$stateParams',
        function ($scope, genericResource, fileExtractor, $location, cdrDrctSalesUtils, cdrDrctSalesState, $stateParams) {
            $scope.cdrDsArtHolder = {
                cdrDrctSales: {},
                items: []
            };
            $scope.error = "";
            $scope.cdrDrctSalesUtils = cdrDrctSalesUtils;

            $scope.validateReturn = " validé d'abord le retour de produit(s) !";
            $scope.validateReturnQty = false;
            $scope.cdrDsArtItemHolder = {
                item: {}
            };


            init();

            function init() {
                var id = $stateParams.id;
                genericResource.findById(cdrDrctSalesUtils.cdrdrctsalesmanager, id)
                    .success(function (entitySearchResult) {
                        $scope.cdrDsArtHolder.cdrDrctSales=entitySearchResult;
                    })
                    .error(function (error) {
                        $scope.error = error;
                    });
            }

            $scope.addItem = function () {
                $scope.validateReturnQty = false;
                addItem($scope.cdrDsArtItemHolder);
            };

            $scope.cdrDsArtItems = function () {
                return $scope.cdrDsArtItemHolder.items;
            };

            $scope.editItem = function (index) {
                $scope.cdrDsArtItemHolder = angular.copy($scope.cdrDsArtHolder.items[index]);
            };

            function clear(){
                $scope.cdrDsArtHolder = {
                    cdrDrctSales: {},
                    items: []
                };
                $scope.cdrDsArtItemHolder = {
                    item: {}
                };
                $scope.showprint = false;
                $scope.error="";
            }

            // Print pdf receipt
            function printReceipt(id){
                genericResource.builfReportGet(cdrDrctSalesUtils.cdrdrctsalesmanager+"/receiptreport.pdf", id).success(function (result) {
                    fileExtractor.extractFile(result,"application/pdf");
                }).error(function (error) {
                    $scope.error = error;
                });
            };
            // Print pdf voucher
            function printVoucher(cdrNbr){
                genericResource.builfReportGet(cdrDrctSalesUtils.cdrdrctsalesmanager+"/voucherreport.pdf", cdrNbr).success(function (result) {
                    fileExtractor.extractFile(result,"application/pdf");
                }).error(function (error) {
                    $scope.error = error;
                });
            };



            $scope.printRequest = function(){
                if($scope.cdrDsArtHolder.cdrDrctSales.slsNetAmtToPay > 0){
                    printReceipt($scope.cdrDsArtHolder.cdrDrctSales.id);
                }else{
                    printVoucher($scope.cdrDsArtHolder.cdrDrctSales.cdrNbr);
                }
            };
            function clearObject(anObject) {
                anObject = {
                    item: {}
                };
                return anObject;
            }

            function addItem(cdrDsArtItemHolder) {

                var copy = angular.copy(cdrDsArtItemHolder)
                var i = 0;
                var items = $scope.cdrDsArtHolder.items;
                if (items.length == 0) {
                    $scope.cdrDsArtHolder.items.push(copy);
                } else {
                    var found = false;
                    angular.forEach(items, function (artItemHolder) {
                        if ((artItemHolder.item.artPic == copy.item.artPic) && (artItemHolder.artName == copy.artName)) {
                            items[i] = copy;
                            found = true;
                        }
                        i += 1;
                    });
                    if (!found) {
                        $scope.cdrDsArtHolder.items.push(copy);
                    }
                }
                $scope.error = "";
                $scope.cdrDsArtItemHolder = clearObject($scope.cdrDsArtItemHolder);
            }

        }]);