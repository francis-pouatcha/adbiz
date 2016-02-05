
(function () {
    'use strict';

    angular
        .module('app.invinvtry')
        .controller('invInvtrysCtlr', invInvtrysCtlr);

    invInvtrysCtlr.$inject = ['$scope','invInvtryManagerResource','utils', 'invInvtryUtils', 'genericResource'];
    /* @ngInject */
    function invInvtrysCtlr($scope, invInvtryManagerResource, utils, invInvtryUtils, genericResource) {

        $scope.data = {};
        $scope.data.invInvtrys = [];
        $scope.data.total;
        $scope.dateConfig = {
        	format: 'dd.MM.yyyy',
        	invtryDtFrom: {
				opened : false
			},
			invtryDtTo: {
				opened : false
			},
			acsngDtFrom: {
				opened : false
			},
			acsngDtTo: {
				opened : false
			}
        };
        $scope.invInvtryUtils=invInvtryUtils;

        // Initialize Search input and pagination
        $scope.searchInput = utils.searchInputInit().searchInput;
        $scope.searchInput.className = 'org.adorsys.adinvtry.rest.InvInvtrySearchInput';
        $scope.pagination = utils.searchInputInit().pagination;

        findCustom($scope.searchInput);

		$scope.openDateComponent = function(componentId,$event) {
			$event.preventDefault();
			$event.stopPropagation();
			$scope.dateConfig[componentId].opened = true;
		};

        function findCustom(searchInput) {
            invInvtryManagerResource.findCustom(searchInput, function(response){
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
            findCustom($scope.searchInput);
        };

        function processSearchInput(searchInput){

            if(angular.isDefined(searchInput.entity.identif) && searchInput.entity.identif){
                if(searchInput.fieldNames.indexOf('identif') == -1)
                    searchInput.fieldNames.push('identif');
            }
            if(angular.isDefined(searchInput.entity.login) && searchInput.entity.login){
                if(searchInput.fieldNames.indexOf('login') == -1)
                    searchInput.fieldNames.push('login');
            }
            if(angular.isDefined(searchInput.entity.status) && searchInput.entity.status){
                if(searchInput.fieldNames.indexOf('status') == -1)
                    searchInput.fieldNames.push('status');
            }
            if(angular.isDefined(searchInput.entity.invtryGroup) && searchInput.entity.invtryGroup){
                if(searchInput.fieldNames.indexOf('invtryGroup') == -1)
                    searchInput.fieldNames.push('invtryGroup');
            }
            if(angular.isDefined(searchInput.entity.txType) && searchInput.entity.txType){
                if(searchInput.fieldNames.indexOf('txType') == -1)
                    searchInput.fieldNames.push('txType');
            }
            if(angular.isDefined(searchInput.entity.descptn) && searchInput.entity.descptn){
                if(searchInput.fieldNames.indexOf('descptn') == -1)
                    searchInput.fieldNames.push('descptn');
            }
            if(angular.isDefined(searchInput.entity.section) && searchInput.entity.section){
                if(searchInput.fieldNames.indexOf('section') == -1)
                    searchInput.fieldNames.push('section');
            }
            if(angular.isDefined(searchInput.entity.rangeStart) && searchInput.entity.rangeStart){
                if(searchInput.fieldNames.indexOf('rangeStart') == -1)
                    searchInput.fieldNames.push('rangeStart');
            }
            if(angular.isDefined(searchInput.entity.rangeEnd) && searchInput.entity.rangeEnd){
                if(searchInput.fieldNames.indexOf('rangeEnd') == -1)
                    searchInput.fieldNames.push('rangeEnd');
            }
        }

        $scope.handleSearchRequestEvent = function(){
            processSearchInput($scope.searchInput);
            findCustom($scope.searchInput);
        };

        $scope.onSectionChanged = function(){
            setSectionName();
        };
        function setSectionName(){
            if(angular.isUndefined($scope.searchInput.entity.section) || !$scope.searchInput.entity.section){
                $scope.display.section='';
                $scope.display.sectionName='';
                return;
            } else if (($scope.display) && angular.equals($scope.display.section,$scope.searchInput.entity.section)) {
                return;
            }

            genericResource.findByLikePromissed(invInvtryUtils.stksectionsUrlBase, 'identif', $scope.searchInput.entity.section)
                .then(function(entitySearchResult){
                    var resultList = entitySearchResult.resultList;
                    if(angular.isDefined(resultList) && resultList.length>0){
                        $scope.display = {};
                        $scope.display.section = resultList[0].identif;
                        $scope.display.sectionName = resultList[0].name;
                    }

                }, function(error){
                    $scope.display.section = '';
                    $scope.display.sectionName = '';
                });
        }

        $scope.onSectionSelected = function(item,model,label){
            $scope.searchInput.entity.section=item.identif;
            $scope.display.section=item.identif;
            $scope.display.sectionName=item.name;
        }
    }

    angular
        .module('app.invinvtry')
        .controller('invInvtryCreateCtlr', invInvtryCreateCtlr);

    invInvtryCreateCtlr.$inject = ['$scope', 'invInvtryUtils', 'invInvtryManagerResource', '$rootScope', '$location'];
    /* @ngInject */
    function invInvtryCreateCtlr($scope, invInvtryUtils, invInvtryManagerResource, $rootScope, $location) {

        $scope.invInvtry = {};
        $scope.display = {};
        $scope.error = "";
        $scope.invInvtryUtils=invInvtryUtils;


        $scope.onSectionSelectedInCreate = function(item,model,label){
            $scope.invInvtry.section=item.identif;
            $scope.display.sectionName=item.name;
        };

        $scope.create = function(){
            $scope.invInvtry.status='ONGOING';
            $scope.invInvtry.login = $rootScope.username;
            invInvtryManagerResource.save($scope.invInvtry, function(response){
                    $scope.invInvtry = response;
                    invInvtryManagerResource.prepare({identif:$scope.invInvtry.identif}, function(data){});
                    $location.path('/invinvtry/'+$scope.invInvtry.identif+'/show/');
            },
            function(errorResponse) {
                $scope.error = errorResponse
            });
        };
    }

    angular
        .module('app.invinvtry')
        .controller('invInvtryShowCtlr', invInvtryShowCtlr);

    invInvtryShowCtlr.$inject = ['$scope','invInvtryManagerResource','$location','invInvtryUtils',
        'invInvtryState','genericResource','searchResultHandler','utils','fileExtractor', '$stateParams', '$rootScope'];
    /* @ngInject */
    function invInvtryShowCtlr($scope,invInvtryManagerResource,$location,invInvtryUtils,invInvtryState
                            ,genericResource,searchResultHandler,utils,fileExtractor, $stateParams, $rootScope) {

        $scope.invtryNbr = $stateParams.invtryNbr;
        $scope.error = "";
        $scope.invInvtryUtils=invInvtryUtils;
        var itemsResultHandler = invInvtryState.itemsResultHandler();
        $scope.searchInput = itemsResultHandler.searchInput();
        $scope.itemPerPage=itemsResultHandler.itemPerPage;
        $scope.totalItems=itemsResultHandler.totalItems;
        $scope.currentPage=itemsResultHandler.currentPage();
        $scope.maxSize =itemsResultHandler.maxResult;
        $scope.invInvtryItems =itemsResultHandler.entities;
        $scope.selectedIndex=itemsResultHandler.selectedIndex;
        $scope.display = itemsResultHandler.displayInfo();

        $scope.invtryEditable = !invInvtryUtils.isInvtryPosted($scope.invInvtry);
        $scope.itemEditable = invInvtryUtils.isInvtryOpen($scope.invInvtry);
        $scope.invtryCopy = angular.copy($scope.invInvtry);

        $scope.invtryChanged = invtryChangedFctn;

        function refreshDisplay(){
            $scope.invtryCopy = angular.copy($scope.invInvtry);
            $scope.invtryEditable = !invInvtryUtils.isInvtryPosted();
            $scope.itemEditable = invInvtryUtils.isInvtryOpen();
        }

        function getInvtry(){
            invInvtryManagerResource.getInvtry({identif:$scope.invtryNbr}, function(invInvtry){
                $scope.invInvtry = invInvtry;
                $scope.invtryCopy = angular.copy($scope.invInvtry);
                fixDateFields();
                loadInvInvtryItems();
            })
        }


        $scope.reload = function(){
            $scope.searchInput = itemsResultHandler.searchInput();
            loadInvInvtryItems(x);
        };

        function findConflict(searchInput) {
            invInvtryManagerResource.findConflict(searchInput, function(response){
                    itemsResultHandler.searchResult(response);
                },
                function(errorResponse) {
                    $scope.error = errorResponse.data.summary;
                });

        }

        $scope.showConflict = function(){
            $scope.showConfilct=true;
            $scope.searchInput = itemsResultHandler.searchInput();
            $scope.searchInput.fieldNames.push('cntnrIdentif');
            $scope.searchInput.entity.cntnrIdentif = $scope.invInvtry.identif;
            findConflict($scope.searchInput);
        };



        function loadInvInvtryItems() {
            $scope.showConfilct=false;
            prepareSearchInput();
            if($scope.searchInput.fieldNames.length==1 && $scope.searchInput.fieldNames.indexOf('cntnrIdentif')!=-1){
                $scope.searchInput.sortFieldNames=[];
                $scope.searchInput.sortFieldNames.push({fieldName:'artPic'});
                genericResource.customMethod(invInvtryUtils.invinvtrysUrlBase+'/findByBsnsObjNbrSorted',$scope.searchInput)
                    .success(function(searchResult){
                        itemsResultHandler.searchResult(searchResult);
                    })
                    .error(function(error){
                       // $scope.error=error;
                    });
            } else {
                genericResource.findByLike(invInvtryUtils.invinvtrysUrlBase,$scope.searchInput)
                    .success(function(searchResult){
                        itemsResultHandler.searchResult(searchResult);
                    })
                    .error(function(error){
                        //$scope.error=error;
                    });
            }
        }
        function prepareSearchInput(){
            $scope.searchInput.entity.cntnrIdentif=$scope.invInvtry.identif;
            $scope.searchInput.fieldNames = [];
            if(angular.isDefined($scope.searchInput.entity.cntnrIdentif) && $scope.searchInput.entity.cntnrIdentif){
                $scope.searchInput.fieldNames.push('cntnrIdentif');
            }

            if(angular.isDefined($scope.searchInput.entity.lotPic) && $scope.searchInput.entity.lotPic){
                $scope.searchInput.fieldNames.push('lotPic');
            }

            if(angular.isDefined($scope.searchInput.entity.artPic) && $scope.searchInput.entity.artPic){
                $scope.searchInput.fieldNames.push('artPic');
            }

            if(angular.isDefined($scope.searchInput.entity.section) && $scope.searchInput.entity.section){
                $scope.searchInput.fieldNames.push('section');
            }
            if(angular.isDefined($scope.searchInput.entity.asseccedQty) && $scope.searchInput.entity.asseccedQty){
                $scope.searchInput.fieldNames.push('asseccedQty');
            }
        }
        function initView(){
            getInvtry();
            var schedule = setTimeout(function(){
                                if($scope.invInvtryItems().length > 0){
                                    clearTimeout(schedule);
                                }else{
                                    loadInvInvtryItems();
                                }

                            },120000);

        }
        initView();

        function fixDateFields(){
            $scope.invInvtry.invtryDt = utils.fixDateField($scope.invInvtry.invtryDt);
            $scope.invInvtry.preparedDt = utils.fixDateField($scope.invInvtry.preparedDt);
            $scope.invInvtry.acsngDt = utils.fixDateField($scope.invInvtry.acsngDt);
        }

        $scope.handleSearchRequestEvent = function(){
            loadInvInvtryItems();
        };

        $scope.paginate = function(){
            itemsResultHandler.currentPage($scope.currentPage);
            $scope.searchInput = itemsResultHandler.paginate();
            loadInvInvtryItems();
        };

        $scope.handlePrintRequestEvent = function(){
            genericResource.builfReportGet(invInvtryUtils.invinvtrysUrlBase+'/invintryreport.pdf',$scope.invInvtry.identif).success(function(data){
                fileExtractor.extractFile(data,"application/pdf");
            }).error(function (error) {
                $scope.error = error;
            });
        }
        $scope.handleResetRequestEvent = function(){
            $scope.searchInput = itemsResultHandler.newSearchInput();
            $scope.display = itemsResultHandler.displayInfo({});
            loadInvInvtryItems();
        }
        $scope.handleAlphabeticRequestEvent = function(){
            $scope.searchInput = itemsResultHandler.newSearchInput();
            $scope.searchInput.a2z = true;
            loadInvInvtryItems();
        }

        $scope.addItem = function(invtryItem){
            invtryItem.acsngDt=new Date().getTime();
            invtryItem.acsngUser=$rootScope.username;
            unsetEditing(invtryItem);
            invInvtryManagerResource.addItem({'identif':$scope.invInvtry.identif}, invtryItem ,function(invtryItem){
                    itemsResultHandler.unshift(invtryItem);
                    $scope.searchInput.entity.artPic=undefined;
                    $scope.searchInput.entity.artName=undefined;
                    $scope.searchInput.entity.lotPic=undefined;
                    $scope.searchInput.entity.asseccedQty=undefined;
                }, function(error){
                    $scope.error = error;
            });
        };
        $scope.check = function(){
            invInvtryManagerResource.validate({identif:$scope.invInvtry.identif}, function(invInvtry){
                $scope.invInvtry = invInvtry;
            }, function(error){
                $scope.error = error;
            })
        };

        $scope.archive = function(){
            invInvtryManagerResource.archive({identif:$scope.invInvtry.identif}, function(invInvtry){
                $scope.invInvtry = invInvtry;
                $location.path('/invinvtry');
            }, function(error){
                $scope.error = error;
            })
        };

        function unsetEditing(invtryItem){
            if(angular.isDefined(invtryItem.editingExpirDt))
                delete invtryItem.editingExpirDt;
            if(angular.isDefined(invtryItem.editingAsseccedQty))
                delete invtryItem.editingAsseccedQty;
        }

        function cleanupItem(invtryItem){
            unsetEditing(invtryItem);
            if(angular.isDefined(invtryItem.oldAccessedQty))
                delete invtryItem.oldAccessedQty;
            if(angular.isDefined(invtryItem.oldAccessedDt))
                delete invtryItem.oldAccessedDt;
            if(angular.isDefined(invtryItem.oldExpirDt))
                delete invtryItem.oldExpirDt;
        }
        function isItemModified(invtryItem){
            return (angular.isDefined(invtryItem.oldAccessedQty)&& (!angular.equals(invtryItem.oldAccessedQty,invtryItem.asseccedQty))) ||
                (angular.isDefined(invtryItem.oldExpirDt) && (!angular.equals(invtryItem.oldExpirDt,invtryItem.expirDt)));
        }

        $scope.editingExpirDt = function(invtryItem, $event){
            // First set editing flag.
            invtryItem.editingExpirDt = true;
            if(angular.isUndefined(invtryItem.oldExpirDt) && angular.isDefined(invtryItem.expirDt)){
                invtryItem.oldExpirDt = angular.copy(invtryItem.expirDt);
            }
        };
        $scope.editingAsseccedQty = function(invtryItem, $event){
            // First set editing flag.
            invtryItem.editingAsseccedQty = true;
            // Then clone and hold clone.
            if(angular.isUndefined(invtryItem.oldAccessedQty) && angular.isDefined(invtryItem.asseccedQty)){
                invtryItem.oldAccessedQty = angular.copy(invtryItem.asseccedQty);
            }
            if(angular.isUndefined(invtryItem.oldAccessedDt) && angular.isDefined(invtryItem.acsngDt)){
                invtryItem.oldAccessedDt = angular.copy(invtryItem.acsngDt);
            }
        };
        $scope.editingItem = function(invtryItem, $event){
            invtryItem.editing=true;
            $scope.editingExpirDt(invtryItem, $event);
            $scope.editingAsseccedQty(invtryItem, $event);
        }

        $scope.updateItem = function(invtryItem){
            var changed = isItemModified(invtryItem);

            cleanupItem(invtryItem);

            if(!changed) return;

            $scope.asseccedQtyChanged(invtryItem);

            invInvtryManagerResource.updateasseccedQty({'identif':invtryItem.cntnrIdentif, 'itemIdentif':invtryItem.identif},
                invtryItem, function(invtryItem){
                    itemsResultHandler.replace(invtryItem);
                },function(data){
                    console.log(data);
                    //$scope.error = error;
                });
        };

        $scope.updateItemOnKeyPress = function(event,invtryItem){
            if (event.which === 13 || event.which === 9) $scope.updateItem(invtryItem);
        };

        $scope.cancelEditItem = function(invtryItem){
            if(isItemModified(invtryItem)) return;
            invtryItem.editing=false;
            if(angular.isDefined(invtryItem.oldAccessedQty) && angular.isDefined(invtryItem.asseccedQty) && !angular.equals(invtryItem.oldAccessedQty,invtryItem.asseccedQty)){
                invtryItem.asseccedQty = angular.copy(invtryItem.oldAccessedQty);
            }
            if(angular.isDefined(invtryItem.oldAccessedDt) && angular.isDefined(invtryItem.acsngDt) && !angular.equals(invtryItem.oldAccessedDt,invtryItem.acsngDt)){
                invtryItem.acsngDt = angular.copy(invtryItem.oldAccessedDt);
            }
            if(angular.isDefined(invtryItem.oldExpirDt) && angular.isDefined(invtryItem.expirDt) && !angular.equals(invtryItem.oldExpirDt,invtryItem.expirDt)){
                invtryItem.expirDt = angular.copy(invtryItem.oldExpirDt);
            }
            cleanupItem(invtryItem);
        }

        $scope.disableItem = function(invtryItem){
            cleanupItem(invtryItem);
            invInvtryManagerResource.disableItem({'identif':invtryItem.cntnrIdentif, 'itemIdentif':invtryItem.identif}
                ,invtryItem,function(result){
                    var index = itemsResultHandler.replace(result);
                },function(error){
                    $scope.error = error;
                });
        };
        $scope.enableItem = function(invtryItem){
            cleanupItem(invtryItem);
            invInvtryManagerResource.enableItem({'identif':invtryItem.cntnrIdentif, 'itemIdentif':invtryItem.identif},
                invtryItem, function(result){
                    itemsResultHandler.replace(result);
                },function(error){
                    $scope.error = error;
                });
        };

        $scope.asseccedQtyChanged = function(invtryItem){
            var dt = new Date();
            invtryItem.acsngDt = dt.getTime();
            invtryItem.acsngUser=$rootScope.username;
        };

        function invtryChangedFctn(){
            return !angular.equals($scope.invInvtry, $scope.invtryCopy);
        };

        $scope.update = function(){
            if(!invtryChangedFctn()) return;
            invInvtryManagerResource.update($scope.invInvtry, function(invInvtry){
                $scope.invInvtry = invInvtry;
                refreshDisplay();
            }, function(error){
                $scope.error = error;
            })
        };

        $scope.close = function(){
            invInvtryManagerResource.close({identif:$scope.invInvtry.identif}, function(invInvtry){
                $scope.invInvtry = invInvtry;
            }, function(error){
                $scope.error = error;
            })
        };
        $scope.post = function(){
            invInvtryManagerResource.post({identif:$scope.invInvtry.identif}, function(invInvtry){
                $scope.invInvtry = invInvtry;
            }, function(error){
                $scope.error = error;
            })
        };

        $scope.onSectionSelectedInSearch = function(item,model,label){
            $scope.searchInput.entity.section=item.identif;
            $scope.display.sectionName=item.name;
            $scope.display.identif=item.identif;
        };

        /*$scope.onArticleLotChangedInSearch = function(){
            if($scope.searchInput.entity.lotPic && $scope.searchInput.entity.lotPic.length<9) return;
            if(
                (angular.isDefined($scope.searchInput.entity.artPic) && $scope.searchInput.entity.artPic) &&
                (angular.isDefined($scope.searchInput.entity.section) && $scope.searchInput.entity.section)
                ){
                return;
            }
            if(angular.isUndefined($scope.searchInput.entity.artPic) || !$scope.searchInput.entity.artPic){
                genericResource.findByIdentif(invInvtryUtils.stkarticlelotsUrlBase,$scope.searchInput.entity.lotPic.toUpperCase())
                    .success(function(articleLot){
                        $scope.searchInput.entity.artPic=articleLot.artPic;
                        genericResource.findBy(invInvtryUtils.catalartfeatmappingsUrlBase,searchInput)
                            .success(function(response){
                                $scope.searchInput.entity.artName=response.resultList[0].artName;
                            })
                            .error(function(error){
                                $scope.error=error;
                            });
                    })
                    .error(function(error){
                        // Ignore
                    });
            }
        };*/

        $scope.onArticleLotSectionSelectedInSearch = function(item,model,label){
            $scope.searchInput.entity.lotPic=item.lotPic;
            $scope.searchInput.entity.artPic=item.artPic;
            $scope.searchInput.entity.section = item.section;

            var searchInput = coreSearchInputInit(item.artPic);
            genericResource.findBy(invInvtryUtils.catalartfeatmappingsUrlBase,searchInput)
                .success(function(response){
                    $scope.searchInput.entity.artName=response.resultList[0].artName;
                })
                .error(function(error){
                    $scope.error=error;
                });

            genericResource.findByLikePromissed(invInvtryUtils.stksectionsUrlBase, 'identif', $scope.searchInput.entity.section)
                .then(function(entitySearchResult){
                    var resultList = entitySearchResult.resultList;
                    if(angular.isDefined(resultList) && resultList.length>0){
                        $scope.display = {};
                        $scope.display.identif = resultList[0].identif;
                        $scope.display.section = resultList[0].identif;
                        $scope.display.sectionName = resultList[0].name;
                    }

                }, function(error){
                    $scope.display.identif = '';
                    $scope.display.section = '';
                    $scope.display.sectionName = '';
                });
        };

        $scope.showLotsForArtPic = function(lotPic){
        	var artPic = $scope.searchInput.entity.artPic;
        	var section = $scope.searchInput.entity.section;
        	if(((angular.isUndefined(artPic) || !artPic)) && ((angular.isUndefined(section) || !section))){
        		if(angular.isUndefined(lotPic) || lotPic.length<7) return [];
        	}
        	return invInvtryUtils.loadStkSectionArticleLotsByIdentif(artPic,lotPic,section);
        };


        $scope.onArticleSelectedInSearch = function(item,model,label){
            $scope.searchInput.entity.artPic=item.identif;
            var searchInput = coreSearchInputInit(item.identif);
            genericResource.findBy(invInvtryUtils.catalartfeatmappingsUrlBase,searchInput)
                .success(function(response){
                    $scope.searchInput.entity.artName=response.resultList[0].artName;
                })
                .error(function(error){
                    $scope.error=error;
                });
            
            updateOnArticleSelcted(item);
        };

        $scope.onArticleSelectedInSearchArtName = function(item,model,label){
            $scope.searchInput.entity.artPic=item.cntnrIdentif;
            $scope.searchInput.entity.artName=item.artName;
            
            updateOnArticleSelcted(item);

        };
        
        function updateOnArticleSelcted(item){

            // We have the article code. Tentative section and Lot
            var searchInput = invInvtryUtils.prepareStkSectionArticleLotSearchInput($scope.searchInput.entity.artPic,
            		$scope.searchInput.entity.lotPic,$scope.searchInput.entity.section);

            genericResource.findByLikeWithSearchInputPromissed(invInvtryUtils.stkarticlelot2strgsctnsUrlBase, searchInput)
            .then(function(entitySearchResult){
            	if(entitySearchResult.resultList && entitySearchResult.resultList.length===1){
            		var lot2Section = entitySearchResult.resultList[0];
            		$scope.searchInput.entity.lotPic = lot2Section.lotPic;
            		$scope.searchInput.entity.section = lot2Section.section;


                    genericResource.findByLikePromissed(invInvtryUtils.stksectionsUrlBase, 'identif', $scope.searchInput.entity.section)
                    .then(function(entitySearchResult){
                        var resultList = entitySearchResult.resultList;
                        if(angular.isDefined(resultList) && resultList.length>0){
                            $scope.display = {};
                            $scope.display.identif = resultList[0].identif;
                            $scope.display.section = resultList[0].identif;
                            $scope.display.sectionName = resultList[0].name;
                        }

                    }, function(error){
                        $scope.display.identif = '';
                        $scope.display.section = '';
                        $scope.display.sectionName = '';
                    });
            	
            	}
            });
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

