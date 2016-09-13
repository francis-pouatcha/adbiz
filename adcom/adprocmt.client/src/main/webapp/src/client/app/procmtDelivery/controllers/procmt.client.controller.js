
(function () {
    'use strict';

    angular
        .module('app.procmtDelivery')
        .controller('ProcmtDeliveryCtlr', ProcmtCtlr);

    ProcmtCtlr.$inject = ['$scope','procmtManagerResource','utils', 'prcmtUtils', 'genericResource'];
    /* @ngInject */
    function ProcmtCtlr($scope, procmtManagerResource, utils, prcmtUtils, genericResource) {

        $scope.data = {};
        $scope.data.prcmts = [];
        $scope.data.total;
        $scope.prcmtUtils=prcmtUtils;
        
        function initSearchInput(){
            // Initialize Search input and pagination
        	$scope.searchInput = utils.searchInputInit().searchInput;
        	$scope.searchInput.className = 'org.adorsys.adprocmt.jpa.PrcmtDeliverySearchInput';
            //Number of entries showed per page.
            $scope.itemsByPage = utils.searchInputInit().stPagination.number;
        }

        initSearchInput();

        $scope.callServer = function(tableState) {
    	    var pagination = tableState.pagination;
    	    var start = pagination.start || 0, number = pagination.number || utils.searchInputInit().stPagination.number;
    	    processSearch(start, tableState.search);
        	
    	    procmtManagerResource.findByLike($scope.searchInput, function(response){
    	    	$scope.data.prcmts = response.resultList;
                tableState.pagination.numberOfPages = Math.ceil(response.count / number)
            },
            function(errorResponse) {
            	$scope.error = errorResponse.data.summary;
            });
        };
        
        function processSearch(start, searchObject) {
        	// First initialize SearchInput-Object and then set Search-Params
        	$scope.searchInput = utils.processSearch($scope.searchInput, searchObject.predicateObject);
        	$scope.searchInput.start = start;
        }
    }

    /*****************************
     * CREATE PROCUMENT DELIVERY *
     ****************************/
    angular
        .module('app.procmtDelivery')
        .controller('ProcmtDeliveryCreateCtlr', ProcmtCreateCtlr);

    ProcmtCreateCtlr.$inject = ['$scope', 'prcmtUtils', 'procmtManagerResource', '$rootScope', '$location'];
    /* @ngInject */
    function ProcmtCreateCtlr($scope, prcmtUtils, procmtManagerResource, $rootScope, $location) {

        $scope.procmt = {};
        $scope.display = {};
        $scope.error = "";
        $scope.prcmtUtils=prcmtUtils;
        $scope.dateConfig = {
        	dtOnDlvrySlip: {
				opened : false
			},
			orderDt: {
				opened : false
			}
        };

        $scope.create = function(){
            $scope.procmt.status='ONGOING';
            //$scope.procmt.login = $rootScope.username;
            procmtManagerResource.save($scope.procmt, function(response){
                    $scope.procmt = response;
                    procmtManagerResource.prepare({identif:$scope.procmt.identif}, function(data){});
                    $location.path('/procmtDelivery/'+$scope.procmt.identif+'/show/');
            },
            function(errorResponse) {
                $scope.error = errorResponse
            });
        };
        
		$scope.openDateComponent = function(componentId,$event) {
			$event.preventDefault();
			$event.stopPropagation();
			$scope.dateConfig[componentId].opened = true;
		};
    }

    /*****************************
     * SHOW PROCUMENT DELIVERY *
     ****************************/
    angular
        .module('app.procmtDelivery')
        .controller('ProcmtDeliveryShowCtlr', procmtShowCtlr);

    procmtShowCtlr.$inject = ['$scope','procmtManagerResource','$location','prcmtUtils',
        'procmtState','genericResource','searchResultHandler','utils','fileExtractor', '$stateParams', '$rootScope','logger'];
    /* @ngInject */
    function procmtShowCtlr($scope,procmtManagerResource,$location,prcmtUtils,procmtState
                            ,genericResource,searchResultHandler,utils,fileExtractor, $stateParams, $rootScope,logger) {

        $scope.procmtNbr = $stateParams.procmtNbr;
        $scope.error = "";
        $scope.prcmtUtils=prcmtUtils;
        var itemsResultHandler = procmtState.itemsResultHandler();
        $scope.searchInput = itemsResultHandler.searchInput();
        $scope.itemPerPage=itemsResultHandler.itemPerPage;
        $scope.totalItems=itemsResultHandler.totalItems;
        $scope.currentPage=itemsResultHandler.currentPage();
        $scope.maxSize =itemsResultHandler.maxResult;
        $scope.procmtItems =itemsResultHandler.entities;
        $scope.selectedIndex=itemsResultHandler.selectedIndex;
        $scope.display = itemsResultHandler.displayInfo();

        $scope.procmtEditable = !prcmtUtils.isInvtryPosted($scope.procmt);
        $scope.itemEditable = prcmtUtils.isInvtryOpen($scope.procmt);
        $scope.procmtCopy = angular.copy($scope.procmt);
        
        $scope.dateConfig = {
        	dtOnDlvrySlip: {
				opened : false
			},
			orderDt: {
				opened : false
			},
            expirDt: {
                opened : false
            }
        };
		$scope.openDateComponent = function(componentId,$event) {
			$event.preventDefault();
			$event.stopPropagation();
			$scope.dateConfig[componentId].opened = true;
		};

        $scope.procmtChanged = procmtChangedFctn;

        function refreshDisplay(){
            $scope.procmtCopy = angular.copy($scope.procmt);
            $scope.procmtEditable = !prcmtUtils.isInvtryPosted();
            $scope.itemEditable = prcmtUtils.isInvtryOpen();
        }

        function getInvtry(){
            procmtManagerResource.get({identif:$scope.procmtNbr}, function(procmt){
                $scope.procmt = procmt;
                $scope.procmtCopy = angular.copy($scope.procmt);
                fixDateFields();
                loadProcmtItems();
            })
        }


        $scope.reload = function(){
            $scope.searchInput = itemsResultHandler.searchInput();
            loadProcmtItems();
        };

        function findConflict(searchInput) {
            procmtManagerResource.findConflict(searchInput, function(response){
                    itemsResultHandler.searchResult(response);
                },
                function(errorResponse) {
                    $scope.error = errorResponse.data.summary;
                });

        }

        $scope.showConflict = function(){
            $scope.searchInput = itemsResultHandler.searchInput();
            $scope.searchInput.fieldNames.push('cntnrIdentif');
            $scope.searchInput.entity.cntnrIdentif = $scope.procmt.identif;
            findConflict($scope.searchInput);
        };



        function loadProcmtItems() {
            prepareSearchInput();
            if($scope.searchInput.fieldNames.length==1 && $scope.searchInput.fieldNames.indexOf('cntnrIdentif')!=-1){
                $scope.searchInput.sortFieldNames=[];
                $scope.searchInput.sortFieldNames.push({fieldName:'artPic'});
                genericResource.customMethod(prcmtUtils.procmtsUrlBase+'/findByBsnsObjNbrSorted',$scope.searchInput)
                    .success(function(searchResult){
                        itemsResultHandler.searchResult(searchResult);
                    })
                    .error(function(error){
                       // $scope.error=error;
                    });
            } else {
                genericResource.findByLike(prcmtUtils.procmtsUrlBase,$scope.searchInput)
                    .success(function(searchResult){
                        itemsResultHandler.searchResult(searchResult);
                    })
                    .error(function(error){
                        //$scope.error=error;
                    });
            }
        }
        function prepareSearchInput(){
            $scope.searchInput.entity.cntnrIdentif=$scope.procmt.identif;
            $scope.searchInput.fieldNames = [];
            if(angular.isDefined($scope.searchInput.entity.cntnrIdentif) && $scope.searchInput.entity.cntnrIdentif){
                $scope.searchInput.fieldNames.push('cntnrIdentif');
            }

            if(angular.isDefined($scope.searchInput.entity.expirDt) && $scope.searchInput.entity.expirDt){
                $scope.searchInput.fieldNames.push('expirDt');
            }

            if(angular.isDefined($scope.searchInput.entity.artPic) && $scope.searchInput.entity.artPic){
                $scope.searchInput.fieldNames.push('artPic');
            }

            if(angular.isDefined($scope.searchInput.entity.supplier) && $scope.searchInput.entity.supplier){
                $scope.searchInput.fieldNames.push('supplier');
            }
            if(angular.isDefined($scope.searchInput.entity.trgtQty) && $scope.searchInput.entity.trgtQty){
                $scope.searchInput.fieldNames.push('trgtQty');
            }
        }
        function initView(){
            getInvtry();
        }
        initView();

        function fixDateFields(){
            $scope.procmt.procmtDt = utils.fixDateField($scope.procmt.procmtDt);
            $scope.procmt.preparedDt = utils.fixDateField($scope.procmt.preparedDt);
            $scope.procmt.acsngDt = utils.fixDateField($scope.procmt.acsngDt);
        }

        $scope.handleSearchRequestEvent = function(){
            loadProcmtItems();
        };

        $scope.paginate = function(){
            itemsResultHandler.currentPage($scope.currentPage);
            $scope.searchInput = itemsResultHandler.paginate();
            loadProcmtItems();
        };

        $scope.handlePrintRequestEvent = function(){
            genericResource.builfReportGet(prcmtUtils.procmtsUrlBase+'/procmtreport.pdf',$scope.procmt.identif).success(function(data){
                fileExtractor.extractFile(data,"application/pdf");
            }).error(function (error) {
                $scope.error = error;
            });
        }
        $scope.handleResetRequestEvent = function(){
            $scope.searchInput = itemsResultHandler.newSearchInput();
            $scope.display = itemsResultHandler.displayInfo({});
            loadProcmtItems();
        }
        $scope.handleAlphabeticRequestEvent = function(){
            $scope.searchInput = itemsResultHandler.newSearchInput();
            $scope.searchInput.a2z = true;
            loadProcmtItems();
        }

        $scope.addItem = function(procmtItem){
            if(procmtItem.prchUnitPrcPreTax > procmtItem.slsUnitPrcPreTax){
                $scope.error = "Le prix d' achat est superieur au prix de vente!";
                return;
            }
            procmtItem.acsngDt=new Date().getTime();
            procmtItem.acsngUser=$rootScope.username;
            $scope.editing=undefined;
            console.log(procmtItem);
            unsetEditing(procmtItem);
            procmtManagerResource.addItem({'identif':$scope.procmt.identif}, procmtItem ,function(procmtItem){
                    itemsResultHandler.unshift(procmtItem);
                    $scope.searchInput.entity.artPic=undefined;
                    $scope.searchInput.entity.artName=undefined;
                    $scope.searchInput.entity.expirDt=undefined;
                    $scope.searchInput.entity.trgtQty=undefined;
                    $scope.tm=undefined;
                $scope.searchInput.entity.freeQty=undefined;
                $scope.searchInput.entity.qtyBilled=undefined;
                $scope.searchInput.entity.prchUnitPrcPreTax=undefined;
                $scope.searchInput.entity.slsUnitPrcPreTax=undefined;
                $scope.searchInput.entity.section=undefined;
                }, function(error){
                    if(error.data){
                        $scope.error = error.data;
                        logger.warning(error.data, error, "Warning");
                    }
                    else
                        $scope.error = error;

            });
        };

        function checkTotalPrice(saleItemPrice){

        }

        $scope.check = function(){
            procmtManagerResource.validate({identif:$scope.procmt.identif}, function(procmt){
                $scope.procmt = procmt;
            }, function(error){
                $scope.error = error;
            })
        };

        $scope.archive = function(){
            procmtManagerResource.archive({identif:$scope.procmt.identif}, function(procmt){
                $scope.procmt = procmt;
                $location.path('/procmtDelivery');
            }, function(error){
                $scope.error = error;
            })
        };

        //editingSupplier
        function unsetEditing(procmtItem){
            if(angular.isDefined(procmtItem.editingExpirDt))
                delete procmtItem.editingExpirDt;
            if(angular.isDefined(procmtItem.editingTrgtQty))
                delete procmtItem.editingTrgtQty;
            if(angular.isDefined(procmtItem.editingSupplier))
                delete procmtItem.editingSupplier;
        }

        function cleanupItem(procmtItem){
            unsetEditing(procmtItem);
            if(angular.isDefined(procmtItem.oldTrgtQty))
                delete procmtItem.oldTrgtQty;
            if(angular.isDefined(procmtItem.oldAccessedDt))
                delete procmtItem.oldAccessedDt;
            if(angular.isDefined(procmtItem.oldExpirDt))
                delete procmtItem.oldExpirDt;
            if(angular.isDefined(procmtItem.oldSupplier))
                delete procmtItem.oldSupplier;
            $scope.editing=undefined;
        }
        function isItemModified(procmtItem){
            return (angular.isDefined(procmtItem.oldTrgtQty)&& (!angular.equals(procmtItem.oldTrgtQty,procmtItem.trgtQty))) ||
                (angular.isDefined(procmtItem.oldExpirDt) && (!angular.equals(procmtItem.oldExpirDt,procmtItem.expirDt))) ||
                    (angular.isDefined(procmtItem.oldSupplier) && (!angular.equals(procmtItem.oldSupplier,procmtItem.supplier)));
        }

        $scope.editingExpirDt = function(procmtItem, $event){
            // First set editing flag.
            procmtItem.editingExpirDt = true;
            if(angular.isUndefined(procmtItem.oldExpirDt) && angular.isDefined(procmtItem.expirDt)){
                procmtItem.oldExpirDt = angular.copy(procmtItem.expirDt);
            }
        };
        $scope.editingSupplier = function(procmtItem, $event){
            // First set editing flag.
            procmtItem.editingSupplier = true;
            if(angular.isUndefined(procmtItem.oldSupplier) && angular.isDefined(procmtItem.supplier)){
                procmtItem.oldSupplier = angular.copy(procmtItem.supplier);
            }
        };
        $scope.editingTrgtQty = function(procmtItem, $event){
            // First set editing flag.
            procmtItem.editingTrgtQty = true;
            // Then clone and hold clone.
            if(angular.isUndefined(procmtItem.oldTrgtQty) && angular.isDefined(procmtItem.trgtQty)){
                procmtItem.oldTrgtQty = angular.copy(procmtItem.trgtQty);
            }
            /*if(angular.isUndefined(procmtItem.oldAccessedDt) && angular.isDefined(procmtItem.acsngDt)){
                procmtItem.oldAccessedDt = angular.copy(procmtItem.acsngDt);
            }*/
        };
        $scope.editingItem = function(procmtItem, $event){
            procmtItem.editing=true;

        }

        $scope.updateItem = function(procmtItem){

            //var changed = isItemModified(procmtItem); will check if is modified before proceed
            //if(!changed) return;
            cleanupItem(procmtItem);

            procmtManagerResource.updateItem({'identif':procmtItem.cntnrIdentif, 'itemIdentif':procmtItem.identif},
                procmtItem, function(procmtItem){
                    itemsResultHandler.replace(procmtItem);
                    procmtItem.editing=false;
                },function(data){
                    console.log(data);
                    //$scope.error = error;
                });
        };

        $scope.updatetrgtQty = function(procmtItem){
            var changed = isItemModified(procmtItem);
            cleanupItem(procmtItem);
            if(!changed) return;

            $scope.trgtQtyChanged(procmtItem);

            procmtManagerResource.updatetrgtQty({'identif':procmtItem.cntnrIdentif, 'itemIdentif':procmtItem.identif},
                procmtItem, function(procmtItem){
                    itemsResultHandler.replace(procmtItem);
                },function(data){
                    console.log(data);
                    //$scope.error = error;
                });
        };

        $scope.updateItemOnKeyPress = function(event,procmtItem){
            if (event.which === 13 || event.which === 9) $scope.updateItem(procmtItem);
        };

        $scope.cancelEditItem = function(procmtItem){
            if(isItemModified(procmtItem)) return;
            procmtItem.editing=false;
            if(angular.isDefined(procmtItem.oldTrgtQty) && angular.isDefined(procmtItem.trgtQty) && !angular.equals(procmtItem.oldTrgtQty,procmtItem.trgtQty)){
                procmtItem.trgtQty = angular.copy(procmtItem.oldTrgtQty);
            }
            if(angular.isDefined(procmtItem.oldAccessedDt) && angular.isDefined(procmtItem.acsngDt) && !angular.equals(procmtItem.oldAccessedDt,procmtItem.acsngDt)){
                procmtItem.acsngDt = angular.copy(procmtItem.oldAccessedDt);
            }
            if(angular.isDefined(procmtItem.oldExpirDt) && angular.isDefined(procmtItem.expirDt) && !angular.equals(procmtItem.oldExpirDt,procmtItem.expirDt)){
                procmtItem.expirDt = angular.copy(procmtItem.oldExpirDt);
            }
            if(angular.isDefined(procmtItem.oldSupplier) && angular.isDefined(procmtItem.supplier) && !angular.equals(procmtItem.oldSupplier,procmtItem.supplier)){
                procmtItem.supplier = angular.copy(procmtItem.oldSupplier);
            }
            cleanupItem(procmtItem);
        }

        $scope.disableItem = function(procmtItem){
            cleanupItem(procmtItem);
            procmtManagerResource.disableItem({'identif':procmtItem.cntnrIdentif, 'itemIdentif':procmtItem.identif}
                ,procmtItem,function(result){
                    var index = itemsResultHandler.replace(result);
                },function(error){
                    $scope.error = error;
                });
        };
        $scope.enableItem = function(procmtItem){
            cleanupItem(procmtItem);
            procmtManagerResource.enableItem({'identif':procmtItem.cntnrIdentif, 'itemIdentif':procmtItem.identif},
                procmtItem, function(result){
                    itemsResultHandler.replace(result);
                },function(error){
                    $scope.error = error;
                });
        };

        $scope.trgtQtyChanged = function(procmtItem){
            var dt = new Date();
            procmtItem.acsngDt = dt.getTime();
            procmtItem.acsngUser=$rootScope.username;
        };

        function procmtChangedFctn(){
            return !angular.equals($scope.procmt, $scope.procmtCopy);
        };

        $scope.update = function(){
            if(!procmtChangedFctn()) return;
            procmtManagerResource.update($scope.procmt, function(procmt){
                $scope.procmt = procmt;
                refreshDisplay();
            }, function(error){
                $scope.error = error;
            })
        };

        $scope.close = function(){
            procmtManagerResource.close({identif:$scope.procmt.identif}, function(procmt){
                $scope.procmt = procmt;
            }, function(error){
                $scope.error = error;
            })
        };
        $scope.post = function(){
            procmtManagerResource.post({identif:$scope.procmt.identif}, function(procmt){
                $scope.procmt = procmt;
            }, function(error){
                $scope.error = error;
            })
        };

        $scope.onSectionSelectedInSearch = function(item,model,label){
            $scope.searchInput.entity.section=item.identif;
            $scope.display.sectionName=item.name;
            $scope.display.identif=item.identif;
        };


        $scope.onArticleSelectedInSearch = function(item,model,label){
            $scope.searchInput.entity.artPic=item.identif;
            var searchInput = coreSearchInputInit(item.identif);
            genericResource.findBy(prcmtUtils.catalartfeatmappingsUrlBase,searchInput)
                .success(function(response){
                    $scope.searchInput.entity.artName=response.resultList[0].artName;
                    searchArticleLot(item.identif);
                })
                .error(function(error){
                    $scope.error=error;
                });
        };

        $scope.onArticleSelectedInSearchArtName = function(item,model,label){
            $scope.searchInput.entity.artPic=item.cntnrIdentif;
            $scope.searchInput.entity.artName=item.artName;
            searchArticleLot(item.cntnrIdentif);

        };

        $scope.changeTm = function(){
            $scope.searchInput.entity.slsUnitPrcPreTax = $scope.searchInput.entity.prchUnitPrcPreTax*1 + ($scope.searchInput.entity.prchUnitPrcPreTax*$scope.tm/100);
        }

        function searchArticleLot(artPic) {
            var coreSearchInput = {};
            coreSearchInput.entity = {};
            coreSearchInput.entity.artPic = artPic;
            coreSearchInput.fieldNames = [];
            coreSearchInput.fieldNames.push('artPic');
            coreSearchInput.sortFieldNames = [];
            coreSearchInput.sortFieldNames.push({fieldName:'closedDt'});
            coreSearchInput.className = 'org.adorsys.adstock.jpa.StkArticleLotSearchInput';
            genericResource.findBy(prcmtUtils.stkarticlelotsUrlBase,coreSearchInput)
                .success(function(response){
                    if(response.resultList.length>0){
                        $scope.searchInput.entity.prchUnitPrcPreTax=response.resultList[0].prchUnitPrcPreTax;
                        $scope.searchInput.entity.slsUnitPrcPreTax=response.resultList[0].slsUnitPrcPreTax;
                        $scope.searchInput.entity.section=response.resultList[0].section;

                    }else{
                        $scope.searchInput.entity.prchUnitPrcPreTax=response.resultList[0].pppu;
                        $scope.searchInput.entity.slsUnitPrcPreTax=response.resultList[0].sppu;
                    }
                })
        }

        function coreSearchInputInit(identif) {
            var coreSearchInput = {};
            coreSearchInput.entity = {};
            coreSearchInput.entity.cntnrIdentif = identif;
            //coreSearchInput.entity.langIso2 = $translate.use();
            coreSearchInput.fieldNames = [];
            coreSearchInput.fieldNames.push('cntnrIdentif');
            //coreSearchInput.fieldNames.push('langIso2');
            coreSearchInput.className = 'org.adorsys.adcatal.jpa.CatalArtLangMappingSearchInput';
            return coreSearchInput;
        }

    }
})();

