
(function () {
    'use strict';

    angular
        .module('app.invinvtry')
        .controller('invInvtrysCtlr', invInvtrysCtlr);

    invInvtrysCtlr.$inject = ['$scope',
        'genericResource',
        'invInvtryUtils',
        'invInvtryState',
        '$location',
        '$rootScope',
        'invInvtryManagerResource',
        'adUtils'];
    /* @ngInject */
    function invInvtrysCtlr($scope,
                            genericResource,
                            invInvtryUtils,
                            invInvtryState,
                            $location,
                            $rootScope,
                            invInvtryManagerResource,
                            adUtils) {

        $scope.coreSearchInput = invInvtryState.resultHandler.coreSearchInput(null,'org.adorsys.adinvtry.rest.InvInvtrySearchInput');
        $scope.itemPerPage=invInvtryState.resultHandler.itemPerPage;
        $scope.totalItems=invInvtryState.resultHandler.totalItems;
        $scope.currentPage=invInvtryState.resultHandler.currentPage();
        $scope.maxSize =invInvtryState.resultHandler.maxResult;
        $scope.invInvtrys =invInvtryState.resultHandler.entities;
        $scope.selectedIndex=invInvtryState.resultHandler.selectedIndex;
        $scope.error = "";
        $scope.invInvtryUtils=invInvtryUtils;
        $scope.display = invInvtryState.resultHandler.displayInfo();

//        var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
//            invInvtryUtils.translate();
//        });

        $scope.$on('$destroy', function () {
           // translateChangeSuccessHdl();
        });

        init();

        function init(){
            if(invInvtryState.resultHandler.hasEntities())return;
            findByLike($scope.coreSearchInput);
        }

        function processSearchInput(coreSearchInput){
            //coreSearchInput.entity = {};
            if(angular.isDefined(coreSearchInput.entity.identif) && coreSearchInput.entity.identif){
                coreSearchInput.fieldNames.push('identif');
            }
            if(angular.isDefined(coreSearchInput.entity.acsngUser) && coreSearchInput.entity.acsngUser){
                coreSearchInput.fieldNames.push('acsngUser');
            }
            if(angular.isDefined(coreSearchInput.entity.invtryStatus) && coreSearchInput.entity.invtryStatus){
                coreSearchInput.fieldNames.push('invtryStatus');
            }
            if(angular.isDefined(coreSearchInput.entity.invtryGroup) && coreSearchInput.entity.invtryGroup){
                coreSearchInput.fieldNames.push('invtryGroup');
            }
            if(angular.isDefined(coreSearchInput.entity.invInvtryType) && coreSearchInput.entity.invInvtryType){
                coreSearchInput.fieldNames.push('invInvtryType');
            }
            if(angular.isDefined(coreSearchInput.entity.descptn) && coreSearchInput.entity.descptn){
                coreSearchInput.fieldNames.push('descptn');
            }
            if(angular.isDefined(coreSearchInput.entity.section) && coreSearchInput.entity.section){
                coreSearchInput.fieldNames.push('section');
            }
            if(angular.isDefined(coreSearchInput.entity.rangeStart) && coreSearchInput.entity.rangeStart){
                coreSearchInput.fieldNames.push('rangeStart');
            }
            if(angular.isDefined(coreSearchInput.entity.rangeEnd) && coreSearchInput.entity.rangeEnd){
                coreSearchInput.fieldNames.push('rangeEnd');
            }

        }
        function findByLike(coreSearchInput){
            genericResource.findCustom(invInvtryUtils.urlBase, coreSearchInput)
                .success(function(entitySearchResult) {
                    // store search
                    invInvtryState.resultHandler.searchResult(entitySearchResult);
                    $scope.coreSearchInput = invInvtryState.resultHandler.coreSearchInput();
                   // setAccessingUserName();
                    //setSectionName();
                })
                .error(function(error){
                    $scope.error=error;
                });
        }

        $scope.handleSearchRequestEvent = function(){
            processSearchInput($scope.coreSearchInput);
            findByLike($scope.coreSearchInput);
        };

        $scope.paginate = function paginate(){
            invInvtryState.resultHandler.currentPage($scope.currentPage);
            $scope.coreSearchInput = invInvtryState.resultHandler.paginate();
            findByLike($scope.coreSearchInput);
        };

        $scope.handlePrintRequestEvent = function(){
            // To do
        };

        $scope.show = function(invInvtry, index){
            var i = invInvtryState.resultHandler.selectedObject(invInvtry);
            if(i>-1){
                $location.path('/InvInvtrys/show/');
            }
        };

        $scope.edit = function(invInvtry, index){
            if(invInvtryState.resultHandler.selectedObject(invInvtry)){
                $location.path('/InvInvtrys/edit/');
            }
        };

        $scope.onUserChanged = function(){
            setAccessingUserName();
        };
        function setAccessingUserName(){
            if(angular.isUndefined($scope.coreSearchInput.entity.acsngUser) || !$scope.coreSearchInput.entity.acsngUser){
                $scope.display.acsngUserFullName='';
                $scope.display.acsngUser='';
                return;
            } else if (angular.equals($scope.display.acsngUser,$scope.coreSearchInput.entity.acsngUser)) {
                return;
            }
            genericResource.findByLikePromissed(invInvtryUtils.loginnamessUrlBase, 'loginName', $scope.coreSearchInput.entity.acsngUser)
                .then(function(entitySearchResult){
                    var resultList = entitySearchResult.resultList;
                    if(angular.isDefined(resultList) && resultList.length>0)
                        $scope.display.acsngUser = resultList[0].loginName;
                    $scope.display.acsngUserFullName = resultList[0].fullName;
                }, function(error){
                    $scope.coreSearchInput.entity.acsngUser='';
                    $scope.display.acsngUser = '';
                    $scope.display.acsngUserFullName = '';
                });
        }
        $scope.onSectionChanged = function(){
            setSectionName();
        };
        function setSectionName(){
            if(angular.isUndefined($scope.coreSearchInput.entity.section) || !$scope.coreSearchInput.entity.section){
                $scope.display.section='';
                $scope.display.sectionName='';
                return;
            } else if (angular.equals($scope.display.section,$scope.coreSearchInput.entity.section)) {
                return;
            }

            genericResource.findByLikePromissed(invInvtryUtils.stksectionsUrlBase, 'sectionCode', $scope.coreSearchInput.entity.section)
                .then(function(entitySearchResult){
                    var resultList = entitySearchResult.resultList;
                    if(angular.isDefined(resultList) && resultList.length>0)
                        $scope.display.section = resultList[0].sectionCode;
                    $scope.display.sectionName = resultList[0].name;
                }, function(error){
                    $scope.display.section = '';
                    $scope.display.sectionName = '';
                });
        }

        $scope.onUserSelected = function(item,model,label){
            $scope.coreSearchInput.entity.acsngUser=item.loginName;
            $scope.display.acsngUser=item.loginName;
            $scope.display.acsngUserFullName=item.fullName;
        };

        $scope.onSectionSelected = function(item,model,label){
            $scope.coreSearchInput.entity.section=item.sectionCode;
            $scope.display.section=item.strgSection;
            $scope.display.sectionName=item.name;
        }

        $scope.isSelected = function(invtry){
            return invInvtryState.selection.indexOf(invtry.identif)!=-1;
        };
        // ad or remove from the collection depending on state of the check box. Then sort.
        $scope.selChanged = function(invtry){
            var index = invInvtryState.selection.indexOf(invtry.identif);
            if(index==-1){
                invInvtryState.selection.push(invtry.identif);
            } else {
                invInvtryState.selection.splice(index,1);
            }
            invInvtryState.selection.sort();
        };

        $scope.compare = function(){
            if(invInvtryState.selection.length<0) return;
            $location.path('/InvInvtrys/compare');
        };
        $scope.selection = invInvtryState.selection;
        $scope.mainInvtry = invInvtryState.mainInvtry;
        $scope.cantMerge = function(){
            return invInvtryState.selection.length<1 ||
                angular.isUndefined(invInvtryState.mainInvtry.identif) ||
                !invInvtryState.mainInvtry.identif ||
                (invInvtryState.selection.indexOf(invInvtryState.mainInvtry.identif)!=-1 && invInvtryState.selection.length<2);
        };
        $scope.merge = function(){
            if(invInvtryState.selection.length<0) return;
            if(angular.isUndefined(invInvtryState.mainInvtry.identif) || !invInvtryState.mainInvtry.identif) return;
            var listHolder = {list:[]};
            listHolder.list.push(invInvtryState.mainInvtry.identif);
            for (var i = 0; i < invInvtryState.selection.length; i++) {
                if(!angular.equals(invInvtryState.selection[i], invInvtryState.mainInvtry.identif)){
                    listHolder.list.push(invInvtryState.selection[i]);
                }
            }
            if(listHolder.list.length<2) return;
            invInvtryManagerResource.merge(listHolder)
                .success(function(listHolder) {
                    findByLike($scope.coreSearchInput);
                })
                .error(function(error){
                    $scope.error=error;
                });
        };
    }

    angular
        .module('app.invinvtry')
        .controller('invInvtryCreateCtlr', invInvtryCreateCtlr);

    invInvtryCreateCtlr.$inject = ['$scope',
        'genericResource',
        'invInvtryUtils',
        'invInvtryState',
        '$location',
        '$rootScope',
        'invInvtryManagerResource',
        'adUtils'];
    /* @ngInject */
    function invInvtryCreateCtlr($scope,
                            genericResource,
                            invInvtryUtils,
                            invInvtryState,
                            $location,
                            $rootScope,
                            invInvtryManagerResource,
                            adUtils) {

        $scope.invInvtry = {};
        $scope.display = {};
        $scope.error = "";
        $scope.invInvtryUtils=invInvtryUtils;

        $scope.onUserSelectedInCreate = function(item,model,label){
            $scope.invInvtry.acsngUser=item.loginName;
            $scope.display.acsngUserFullName=item.fullName;
            $scope.display.acsngUser=item.loginName;
        };

        $scope.onUserNameChanged = function(){
            if(angular.isDefined($scope.invInvtry.acsngUser) &&
                $scope.invInvtry.acsngUser &&
                angular.isDefined($scope.display.acsngUser) &&
                $scope.display.acsngUser &&
                ($scope.invInvtry.acsngUser==$scope.display.acsngUser)){return;}

            if($scope.invInvtry.acsngUser && $scope.invInvtry.acsngUser.length<3) return;
            // Read user
            genericResource.findById(invInvtryUtils.loginnamessUrlBase,$scope.invInvtry.acsngUser)
                .success(function(item){
                    $scope.display.acsngUserFullName=item.fullName;
                    $scope.display.acsngUser=item.loginName;
                });
        };
        $scope.onSectionSelectedInCreate = function(item,model,label){
            $scope.invInvtry.section=item.sectionCode;
            $scope.display.sectionName=item.name;
        };

        $scope.create = function(){
            $scope.invInvtry.preparedDt=new Date();
            //$scope.invInvtry.invtryStatus='ONGOING';
            invInvtryManagerResource.save($scope.invInvtry,
                function(invInvtry){
                    var index = invInvtryState.resultHandler.unshift(invInvtry);
                    if(adUtils.greaterThan(index)){
                        $location.path('/InvInvtrys/');
                    }
                },
                function(error){
                    $scope.error = error;
                }
            );
        };
    }

    angular
        .module('app.invinvtry')
        .controller('invInvtryShowCtlr', invInvtryShowCtlr);

    invInvtryShowCtlr.$inject = ['$scope',
        'genericResource',
        'invInvtryUtils',
        'invInvtryState',
        '$location',
        '$rootScope',
        'invInvtryManagerResource',
        'adUtils'];
    /* @ngInject */
    function invInvtryShowCtlr($scope,
                                  genericResource,
                                  invInvtryUtils,
                                  invInvtryState,
                                  $location,
                                  $rootScope,
                                  invInvtryManagerResource,
                                  adUtils) {

            $scope.invInvtry = invInvtryState.resultHandler.entity();
            $scope.error = "";
            $scope.invInvtryUtils=invInvtryUtils;
            var itemsResultHandler = invInvtryState.itemsResultHandler();
            $scope.coreSearchInput = itemsResultHandler.coreSearchInput(null,'org.adorsys.adinvtry.rest.InvInvtryItemSearchInput');
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
                $scope.invInvtry = invInvtryState.resultHandler.entity();
                $scope.invtryCopy = angular.copy($scope.invInvtry);
                $scope.invtryEditable = !invInvtryUtils.isInvtryPosted();
                $scope.itemEditable = invInvtryUtils.isInvtryOpen();
            }

            $scope.onUserSelectedInShow = function(item,model,label){
                $scope.invInvtry.acsngUser=item.loginName;
                $scope.display.acsngUserFullName=item.fullName;
            }

            function consumeError(error, status) {
                var error_msg=error;
                if(400 === status && error && error.contains("org.codehaus.jackson.JsonParseException")) {
                    var key = [];
                    key.push("InvInvtry_longPdctJsonArr_exception.title")
                    error_msg = invInvtryUtils.translatePromise(key).then(function(translations){
                        if(translations && translations.length > 0) {
                            error_msg = translations;
                            $scope.error = error_msg;
                        }
                    });
                }
                return error_msg;
            }

            function loadInvInvtryItems() {
                prepareSearchInput();
                /*if($scope.coreSearchInput.fieldNames.length==1 && $scope.coreSearchInput.fieldNames.indexOf('identif')!=-1){
                    genericResource.customMethod(invInvtryUtils.invinvtrysUrlBase+'/findByInvtryNbrSortedBySectionAndArtName',$scope.coreSearchInput)
                        .success(function(searchResult){
                            itemsResultHandler.searchResult(searchResult);
                        })
                        .error(function(error){
                            $scope.error=error;
                        });
                } else {*/
                    genericResource.findByLike(invInvtryUtils.invinvtrysUrlBase,$scope.coreSearchInput)
                        .success(function(searchResult){
                            itemsResultHandler.searchResult(searchResult);
                        })
                        .error(function(error){
                            $scope.error=error;
                        });
               // }
            }
            function prepareSearchInput(){
                $scope.coreSearchInput.entity = {};
                $scope.coreSearchInput.entity.identif=$scope.invInvtry.identif;
                $scope.coreSearchInput.fieldNames = [];
                if(angular.isDefined($scope.coreSearchInput.entity.identif) && $scope.coreSearchInput.entity.identif){
                    $scope.coreSearchInput.fieldNames.push('identif');
                }

                if(angular.isDefined($scope.coreSearchInput.entity.lotPic) && $scope.coreSearchInput.entity.lotPic){
                    $scope.coreSearchInput.fieldNames.push('lotPic');
                }

                if(angular.isDefined($scope.coreSearchInput.entity.artPic) && $scope.coreSearchInput.entity.artPic){
                    $scope.coreSearchInput.fieldNames.push('artPic');
                }

                if(angular.isDefined($scope.coreSearchInput.entity.section) && $scope.coreSearchInput.entity.section){
                    $scope.coreSearchInput.fieldNames.push('section');
                }
            }
            function initView(){
                fixDateFields();
                setAccessingUserName();
                loadInvInvtryItems();
            }
            initView();
            function setAccessingUserName(){
                if(angular.isUndefined($scope.invInvtry.acsngUser) || !$scope.invInvtry.acsngUser)
                    return;
                genericResource.findByLikePromissed(invInvtryUtils.loginnamessUrlBase, 'loginName', $scope.invInvtry.acsngUser)
                    .then(function(entitySearchResult){
                        var resultList = entitySearchResult.resultList;
                        if(angular.isDefined(resultList) && resultList.length>0)
                            $scope.display.acsngUserFullName = resultList[0].fullName;
                    }, function(error){
                        $scope.invInvtry.acsngUser='';
                        $scope.display.acsngUserFullName = '';
                    });
            }
            function fixDateFields(){
                $scope.invInvtry.invtryDt = adUtils.fixDateField($scope.invInvtry.invtryDt);
                $scope.invInvtry.preparedDt = adUtils.fixDateField($scope.invInvtry.preparedDt);
                $scope.invInvtry.acsngDt = adUtils.fixDateField($scope.invInvtry.acsngDt);
            }

            $scope.handleSearchRequestEvent = function(){
                loadInvInvtryItems();
            };

            $scope.paginate = function(){
                itemsResultHandler.currentPage($scope.currentPage);
                $scope.coreSearchInput = itemsResultHandler.paginate();
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
                $scope.coreSearchInput = itemsResultHandler.newSearchInput();
                $scope.display = itemsResultHandler.displayInfo({});
                loadInvInvtryItems();
            }
            $scope.handleAlphabeticRequestEvent = function(){
                $scope.coreSearchInput = itemsResultHandler.newSearchInput();
                $scope.coreSearchInput.a2z = true;
                loadInvInvtryItems();
            }

            $scope.addItem = function(invtryItem){
                unsetEditing(invtryItem);
                invInvtryManagerResource.addItem(invtryItem,
                    function(invtryItem){
                        itemsResultHandler.unshift(invtryItem);
                        $scope.coreSearchInput.entity.artPic=undefined;
                        $scope.coreSearchInput.entity.artName=undefined;
                        $scope.coreSearchInput.entity.lotPic=undefined;
                        $scope.coreSearchInput.entity.asseccedQty=undefined;
                    },function(error){
                        $scope.error = error;
                    }
                );
            };
            $scope.check = function(){
                invInvtryManagerResource.validate($scope.invInvtry,
                    function(invInvtry){
                        if(invInvtryState.resultHandler.replace(invInvtry)){
                            $scope.invInvtry = invInvtryState.resultHandler.entity();
                            invInvtryState.selection=[$scope.invInvtry.identif];
                            $location.path('/InvInvtrys/compare');
                        }
                    },
                    function(error){
                        $scope.error = error;
                    }
                );
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
                $scope.editingExpirDt(invtryItem, $event);
                $scope.editingAsseccedQty(invtryItem, $event);
            }

            $scope.updateItem = function(invtryItem){
                var changed = isItemModified(invtryItem);

                cleanupItem(invtryItem);

                if(!changed) return;

                $scope.asseccedQtyChanged(invtryItem);

                invInvtryManagerResource.updateItem(invtryItem,
                    function(invtryItem){
                        itemsResultHandler.replace(invtryItem);
                    },
                    function(data){
                        console.log(data);
                        //$scope.error = error;
                    }
                );
            };

            $scope.updateItemOnKeyPress = function(event,invtryItem){
                if (event.which === 13 || event.which === 9) $scope.updateItem(invtryItem);
            };

            $scope.cancelEditItem = function(invtryItem){
                if(!isItemModified(invtryItem)) return;
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
                invInvtryManagerResource.disableItem(invtryItem)
                    .success(function(invtryItem){
                        itemsResultHandler.replace(invtryItem);
                    })
                    .error(function(error){
                        $scope.error = error;
                    });
            };
            $scope.enableItem = function(invtryItem){
                cleanupItem(invtryItem);
                invInvtryManagerResource.enableItem(invtryItem)
                    .success(function(invtryItem){
                        itemsResultHandler.replace(invtryItem);
                    })
                    .error(function(error){
                        $scope.error = error;
                    });
            };

            $scope.asseccedQtyChanged = function(invtryItem){
                var dt = new Date();
                invtryItem.acsngDt = dt.getTime();
            };

            function invtryChangedFctn(){
                return !angular.equals($scope.invInvtry, $scope.invtryCopy);
            };

            $scope.update = function(){
                if(!invtryChangedFctn()) return;
                invInvtryManagerResource.update($scope.invInvtry,
                    function(invInvtry){
                        if(invInvtryState.resultHandler.replace(invInvtry)){
                            refreshDisplay();
                        }
                    },function(error){
                        $scope.error = error;
                    }
                );
            };

            $scope.close = function(){
                invInvtryManagerResource.close($scope.invInvtry,
                    function(invInvtry){
                        if(invInvtryState.resultHandler.replace(invInvtry)){
                            $scope.invInvtry = invInvtryState.resultHandler.entity();
                        }
                    },
                    function(error){
                        $scope.error = error;
                    }
                );
            };
            $scope.post = function(){
                invInvtryManagerResource.post($scope.invInvtry,
                    function(invInvtry){
                        if(invInvtryState.resultHandler.replace(invInvtry)){
                            $scope.invInvtry = invInvtryState.resultHandler.entity();
                        }
                    },
                    function(error){
                        $scope.error = error;
                    }
                );
            };

            $scope.onSectionSelectedInSearch = function(item,model,label){
                $scope.coreSearchInput.entity.section=item.strgSection;
                $scope.display.sectionName=item.name;
                $scope.display.sectionCode=item.sectionCode;
            };

            $scope.onArticleLotChangedInSearch = function(){
                if($scope.coreSearchInput.entity.lotPic && $scope.coreSearchInput.entity.lotPic.length<9) return;
                if(
                    (angular.isDefined($scope.coreSearchInput.entity.artPic) && $scope.coreSearchInput.entity.artPic) &&
                    (angular.isDefined($scope.coreSearchInput.entity.section) && $scope.coreSearchInput.entity.section)
                    ){
                    return;
                }
                if(angular.isUndefined($scope.coreSearchInput.entity.artPic) || !$scope.coreSearchInput.entity.artPic){
                    genericResource.findByIdentif(invInvtryUtils.stkarticlelotsUrlBase,$scope.coreSearchInput.entity.lotPic.toUpperCase())
                        .success(function(articleLot){
                            $scope.coreSearchInput.entity.artPic=articleLot.artPic;
                            genericResource.findByIdentif(invInvtryUtils.catalarticlesUrlBase,$scope.coreSearchInput.entity.artPic)
                                .success(function(catalArticle){
                                    $scope.coreSearchInput.entity.artName=catalArticle.features.artName;
                                })
                                .error(function(error){
                                    $scope.error=error;
                                });

                        })
                        .error(function(error){
                            // Ignore
                        });
                }
            };

            $scope.onArticleLotSectionSelectedInSearch = function(item,model,label){
                $scope.coreSearchInput.entity.lotPic = item.lotPic;
                var artPic = item.artPic;
                if(	(angular.isUndefined($scope.coreSearchInput.entity.artPic) ||
                    !$scope.coreSearchInput.entity.artPic) ||
                    $scope.coreSearchInput.entity.artPic!=artPic)
                {
                    $scope.coreSearchInput.entity.artPic=artPic;
                    $scope.coreSearchInput.entity.artName=item.artName;
                }

                var strgSection = item.strgSection;
                if((angular.isUndefined($scope.coreSearchInput.entity.section) ||
                    !$scope.coreSearchInput.entity.section) ||
                    $scope.coreSearchInput.entity.section!=strgSection)
                {
                    $scope.coreSearchInput.entity.section=strgSection;
                    $scope.display.sectionName=item.sectionName;
                }
            };

            $scope.showLotsForArtPic = function(lotPic){
                if((angular.isDefined(lotPic) && lotPic.length>=8)){
                    return [];
                }
                var artPic = $scope.coreSearchInput.entity.artPic;
                var section = $scope.coreSearchInput.entity.section;
                if(((angular.isUndefined(artPic) || !artPic)) && ((angular.isUndefined(section) || !section))){
                    return [];
                }
                return invInvtryUtils.loadStkSectionArticleLotsByIdentif(artPic,lotPic,section);
            };

            $scope.onArticleLotSelectedInSearch = function(item,model,label){
                $scope.coreSearchInput.entity.lotPic=item.lotPic;
                $scope.coreSearchInput.entity.artPic=item.artPic;
//    	// read the article name
                genericResource.findByIdentif(invInvtryUtils.catalarticlesUrlBase,item.artPic)
                    .success(function(catalArticle){
                        $scope.coreSearchInput.entity.artName=catalArticle.features.artName;
                    })
                    .error(function(error){
                        $scope.error=error;
                    });
            };

            $scope.onArticleSelectedInSearch = function(item,model,label){
                $scope.coreSearchInput.entity.artPic=item.pic;
                $scope.coreSearchInput.entity.artName=item.features.artName;

            };

            $scope.previous = function (){
                var inv = invInvtryState.resultHandler.previous();
                if(inv){
                    $scope.invInvtry=inv;
                    initView();
                }
            }

            $scope.next =  function (){
                var inv = bpBnsPtnrState.resultHandler.next();
                if(inv){
                    $scope.invInvtry=inv;
                    initView();
                }
            };

            $scope.makeMain = function(invInvtry){
                // Can only be main if not yet posted.
                if(invInvtryUtils.isInvtryPosted(invInvtry)) {
                    alert(invInvtry.identif + invInvtryUtils.translations['InvInvtry_postedCanNotBeMerged_description.alert']);
                    return;
                }
                if(angular.isDefined(invInvtry.containerId) && invInvtry.containerId &&
                    (invInvtry.identif != invInvtry.containerId)){
                    alert(invInvtry.identif + invInvtryUtils.translations['InvInvtry_alreadyMergedTo_description.alert'] + invInvtry.containerId);
                    return;
                }
                invInvtryState.mainInvtry = invInvtry;
            };

            $scope.isMainInventory = function(invInvtry){
                return angular.isDefined(invInvtryState.mainInvtry) &&
                    angular.isDefined(invInvtryState.mainInvtry.identif) &&
                    invInvtryState.mainInvtry.identif == invInvtry.identif;
            };

    }



    angular
        .module('app.invinvtry')
        .controller('invInvtryCompareCtlr', invInvtryCompareCtlr);

    invInvtryCompareCtlr.$inject = ['$scope',
        'genericResource',
        'invInvtryUtils',
        'invInvtryState',
        '$location',
        '$rootScope',
        'invInvtryManagerResource',
        'adUtils'];
    /* @ngInject */
    function invInvtryCompareCtlr($scope,
                                 genericResource,
                                 invInvtryUtils,
                                 invInvtryState,
                                 $location,
                                 $rootScope,
                                 invInvtryManagerResource,
                                 adUtils) {

        $scope.invInvtry = invInvtryState.resultHandler.entity();
        $scope.coreSearchInput = invInvtryState.compareResultHandler.coreSearchInput();
        $scope.itemPerPage=invInvtryState.compareResultHandler.itemPerPage;
        $scope.totalItems=invInvtryState.compareResultHandler.totalItems;
        $scope.currentPage=invInvtryState.compareResultHandler.currentPage();
        $scope.maxSize =invInvtryState.compareResultHandler.maxResult;
        $scope.invtryItemLists =invInvtryState.compareResultHandler.entities;
        $scope.selectedIndex=invInvtryState.compareResultHandler.selectedIndex;
        $scope.error = "";
        $scope.invInvtryUtils=invInvtryUtils;
        $scope.display = invInvtryState.compareResultHandler.displayInfo();

        init();

        $scope.showAll = function(){
            findCompare($scope.coreSearchInput);
        };
        $scope.showConflict = function(){
            findConflict($scope.coreSearchInput);
        };
        $scope.reload = function(){
            findByLike($scope.coreSearchInput);
        };
        $scope.edit = function(){
            var invtryItemLists = $scope.invtryItemLists();
            for (var int = 0; int < invtryItemLists.length; int++) {
                var invtryItemList = invtryItemLists[int];
                editing(invtryItemList);
            }
        };
        function editing(invtryItemList){
            if(invtryItemList.sameQty) return;
            var invtryItems = invtryItemList.invtryItems;
            for (var int2 = 0; int2 < invtryItems.length; int2++) {
                var invtryItem = invtryItems[int2];
                invtryItem.editing = true;
            }
        }

        $scope.disable = function(invtryItemList, invtryItem){
            if(!angular.isDefined(invtryItem.editing) || !invtryItem.editing) return;
            delete invtryItem.editing;
            if(angular.isDefined(invtryItem.others)) invtryItem.others = undefined;
            if(angular.isDefined(invtryItem.disabledDt) && invtryItem.disabledDt){
                // enable
                invInvtryManagerResource.enableItem(invtryItem)
                    .success(function(invtryItemList) {
                        // store search
                        invInvtryState.compareResultHandler.replace(invtryItemList);
                        processEntity(invtryItemList);
                        editing(invtryItemList);
                    })
                    .error(function(error){
                        $scope.error=error;
                        invtryItem.editing = true;
                    });
            } else {
                // disable
                invInvtryManagerResource.disableItem(invtryItem)
                    .success(function(invtryItemList) {
                        // store search
                        invInvtryState.compareResultHandler.replace(invtryItemList);
                        processEntity(invtryItemList);
                        editing(invtryItemList);
                    })
                    .error(function(error){
                        $scope.error=error;
                        invtryItem.editing = true;
                    });
            }
        };

        function init(){
            $scope.coreSearchInput.entity.identifs = angular.copy(invInvtryState.selection);
            findByLike($scope.coreSearchInput);
        }

        function findByLike(coreSearchInput){
            if(angular.isDefined(invInvtryState.onlyConflict)){
                if(invInvtryState.onlyConflict){
                    findConflict(coreSearchInput);
                } else {
                    findCompare(coreSearchInput);
                }
            } else if($scope.coreSearchInput.entity.identifs.length==1){
                findConflict(coreSearchInput);
            } else {
                findCompare(coreSearchInput);
            }
        }
        function findCompare(coreSearchInput){
            invInvtryState.onlyConflict = false;
            genericResource.customMethod(invInvtryUtils.invinvtrysUrlBase + '/findCompare', coreSearchInput)
                .success(function(entitySearchResult) {
                    // store search
                    invInvtryState.compareResultHandler.searchResult(entitySearchResult);
                    $scope.coreSearchInput = invInvtryState.compareResultHandler.coreSearchInput();
                    processEntities();
                })
                .error(function(error){
                    $scope.error=error;
                });
        }
        function findConflict(coreSearchInput){
            invInvtryState.onlyConflict = true;
            genericResource.customMethod(invInvtryUtils.invinvtrysUrlBase + '/findConflict', coreSearchInput)
                .success(function(entitySearchResult) {
                    // store search
                    invInvtryState.compareResultHandler.searchResult(entitySearchResult);
                    $scope.coreSearchInput = invInvtryState.compareResultHandler.coreSearchInput();
                    processEntities();
                })
                .error(function(error){
                    $scope.error=error;
                });
        }

        $scope.paginate = function paginate(){
            invInvtryState.compareResultHandler.currentPage($scope.currentPage);
            $scope.coreSearchInput = invInvtryState.compareResultHandler.paginate();
            findByLike($scope.coreSearchInput);
        };

        function processEntities(){
            var entities = invInvtryState.compareResultHandler.entities();
            var identifs = $scope.coreSearchInput.entity.identifs;
            // Sort the inventryItemList in the order of InvetryNbrs.
            for (var int = 0; int < entities.length; int++) {
                var invtryItemList = entities[int];
                invtryItemList.sortedItems = [];
                for (var int2 = 0; int2 < identifs.length; int2++) {
                    var invtryItem = selectInvtryItem(identifs[int2], invtryItemList.invtryItems);
                    invtryItemList.sortedItems.push(invtryItem);
                }
            }
        }

        function processEntity(invtryItemList){
            var identifs = $scope.coreSearchInput.entity.identifs;
            invtryItemList.sortedItems = [];
            for (var int2 = 0; int2 < identifs.length; int2++) {
                var invtryItem = selectInvtryItem(identifs[int2], invtryItemList.invtryItems);
                invtryItemList.sortedItems.push(invtryItem);
            }
        }

        function selectInvtryItem(identif, invtryItems){
            var found = null;
            for (var int = 0; int < invtryItems.length; int++) {
                var invtryItem = invtryItems[int];
                if(angular.isDefined(invtryItem) && (identif==invtryItem.identif)){
                    if(found==null){
                        found = invtryItem;
                    } else {
                        if(angular.isUndefined(found.others))
                            found.others = [];
                        found.others.push(invtryItem);
                    }
                }
            }
            return found;
        }

    }

})()

