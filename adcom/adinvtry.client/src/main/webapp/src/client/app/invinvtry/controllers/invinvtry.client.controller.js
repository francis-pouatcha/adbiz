
(function () {
    'use strict';

    angular
        .module('app.invinvtry')
        .controller('invInvtrysCtlr', invInvtrysCtlr);

    invInvtrysCtlr.$inject = ['$scope','invInvtryManagerResource','utils', 'invInvtryUtils'];
    /* @ngInject */
    function invInvtrysCtlr($scope, invInvtryManagerResource, utils, invInvtryUtils) {

        $scope.data = {};
        $scope.data.invInvtrys = [];
        $scope.data.total;
        $scope.invInvtryUtils=invInvtryUtils;

        // Initialize Search input and pagination
        $scope.searchInput = utils.searchInputInit().searchInput;
        $scope.searchInput.className = 'org.adorsys.adinvtry.jpa.InvInvtrySearchInput';
        $scope.pagination = utils.searchInputInit().pagination;

        findCustom($scope.searchInput);

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

            if(angular.isDefined(searchInput.entity.invtryNbr) && searchInput.entity.invtryNbr){
                searchInput.fieldNames.push('invtryNbr');
            }
            if(angular.isDefined(searchInput.entity.acsngUser) && searchInput.entity.acsngUser){
                searchInput.fieldNames.push('login');
            }
            if(angular.isDefined(searchInput.entity.invtryStatus) && searchInput.entity.invtryStatus){
                searchInput.fieldNames.push('status');
            }
            if(angular.isDefined(searchInput.entity.invtryGroup) && searchInput.entity.invtryGroup){
                searchInput.fieldNames.push('invtryGroup');
            }
            if(angular.isDefined(searchInput.entity.invInvtryType) && searchInput.entity.invInvtryType){
                searchInput.fieldNames.push('txType');
            }
            if(angular.isDefined(searchInput.entity.descptn) && searchInput.entity.descptn){
                searchInput.fieldNames.push('descptn');
            }
            if(angular.isDefined(searchInput.entity.section) && searchInput.entity.section){
                searchInput.fieldNames.push('section');
            }
            if(angular.isDefined(searchInput.entity.rangeStart) && searchInput.entity.rangeStart){
                searchInput.fieldNames.push('rangeStart');
            }
            if(angular.isDefined(searchInput.entity.rangeEnd) && searchInput.entity.rangeEnd){
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
            } else if (angular.equals($scope.display.section,$scope.searchInput.entity.section)) {
                return;
            }

            genericResource.findByLikePromissed(invInvtryUtils.stksectionsUrlBase, 'identif', $scope.searchInput.entity.section)
                .then(function(entitySearchResult){
                    var resultList = entitySearchResult.resultList;
                    if(angular.isDefined(resultList) && resultList.length>0)
                        $scope.display.section = resultList[0].identif;
                    $scope.display.sectionName = resultList[0].name;
                }, function(error){
                    $scope.display.section = '';
                    $scope.display.sectionName = '';
                });
        }

        $scope.onSectionSelected = function(item,model,label){
            $scope.searchInput.entity.section=item.identif;
            $scope.display.section=item.strgSection;
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
        'invInvtryState','genericResource','searchResultHandler','utils','fileExtractor', '$stateParams', '$http'];
    /* @ngInject */
    function invInvtryShowCtlr($scope,invInvtryManagerResource,$location,invInvtryUtils,invInvtryState
                            ,genericResource,searchResultHandler,utils,fileExtractor, $stateParams, $http) {

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
            invInvtryManagerResource.get({identif:$scope.invtryNbr}, function(invInvtry){
                $scope.invInvtry = invInvtry;
                $scope.invtryCopy = angular.copy($scope.invInvtry);
                fixDateFields();
                loadInvInvtryItems();
            })
        }


        function loadInvInvtryItems() {
            prepareSearchInput();
            if($scope.searchInput.fieldNames.length==1 && $scope.searchInput.fieldNames.indexOf('invtryNbr')!=-1){
                genericResource.customMethod(invInvtryUtils.invinvtrysUrlBase+'/findByInvtryNbrSortedBySectionAndArtName',$scope.searchInput)
                    .success(function(searchResult){
                        itemsResultHandler.searchResult(searchResult);
                    })
                    .error(function(error){
                        $scope.error=error;
                    });
            } else {
                genericResource.findByLike(invInvtryUtils.invinvtrysUrlBase,$scope.searchInput)
                    .success(function(searchResult){
                        itemsResultHandler.searchResult(searchResult);
                    })
                    .error(function(error){
                        $scope.error=error;
                    });
            }
        }
        function prepareSearchInput(){
            $scope.searchInput.entity.invtryNbr=$scope.invInvtry.invtryNbr;
            $scope.searchInput.fieldNames = [];
            if(angular.isDefined($scope.searchInput.entity.invtryNbr) && $scope.searchInput.entity.invtryNbr){
                $scope.searchInput.fieldNames.push('invtryNbr');
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
        }
        function initView(){
            getInvtry();
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
            genericResource.builfReportGet(invInvtryUtils.invinvtrysUrlBase+'/invintryreport.pdf',$scope.invInvtry.invtryNbr).success(function(data){
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
            unsetEditing(invtryItem);
            invInvtryManagerResource.addItem(invtryItem)
                .success(function(invtryItem){
                    itemsResultHandler.unshift(invtryItem);
                    $scope.searchInput.entity.artPic=undefined;
                    $scope.searchInput.entity.artName=undefined;
                    $scope.searchInput.entity.lotPic=undefined;
                    $scope.searchInput.entity.asseccedQty=undefined;
                })
                .error(function(error){
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

            invInvtryManagerResource.updateItem(invtryItem)
                .success(function(invtryItem){
                    itemsResultHandler.replace(invtryItem);
                })
                .error(function(data){
                    console.log(data);
                    //$scope.error = error;
                });
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
            $scope.searchInput.entity.section=item.strgSection;
            $scope.display.sectionName=item.name;
            $scope.display.sectionCode=item.sectionCode;
        };

        $scope.onArticleLotChangedInSearch = function(){
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
                        genericResource.findByIdentif(invInvtryUtils.catalarticlesUrlBase,$scope.searchInput.entity.artPic)
                            .success(function(catalArticle){
                                $scope.searchInput.entity.artName=catalArticle.features.artName;
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
            $scope.searchInput.entity.lotPic = item.lotPic;
            var artPic = item.artPic;
            if(	(angular.isUndefined($scope.searchInput.entity.artPic) ||
                !$scope.searchInput.entity.artPic) ||
                $scope.searchInput.entity.artPic!=artPic)
            {
                $scope.searchInput.entity.artPic=artPic;
                $scope.searchInput.entity.artName=item.artName;
            }

            var strgSection = item.strgSection;
            if((angular.isUndefined($scope.searchInput.entity.section) ||
                !$scope.searchInput.entity.section) ||
                $scope.searchInput.entity.section!=strgSection)
            {
                $scope.searchInput.entity.section=strgSection;
                $scope.display.sectionName=item.sectionName;
            }
        };

        $scope.showLotsForArtPic = function(lotPic){
            if((angular.isDefined(lotPic) && lotPic.length>=8)){
                return [];
            }
            var artPic = $scope.searchInput.entity.artPic;
            var section = $scope.searchInput.entity.section;
            if(((angular.isUndefined(artPic) || !artPic)) && ((angular.isUndefined(section) || !section))){
                return [];
            }
            return invInvtryUtils.loadStkSectionArticleLotsByIdentif(artPic,lotPic,section);
        };

        $scope.onArticleLotSelectedInSearch = function(item,model,label){
            $scope.searchInput.entity.lotPic=item.lotPic;
            $scope.searchInput.entity.artPic=item.artPic;
//    	// read the article name
            genericResource.findByIdentif(invInvtryUtils.catalarticlesUrlBase,item.artPic)
                .success(function(catalArticle){
                    $scope.searchInput.entity.artName=catalArticle.features.artName;
                })
                .error(function(error){
                    $scope.error=error;
                });
        };

        $scope.onArticleSelectedInSearch = function(item,model,label){
            $scope.searchInput.entity.artPic=item.pic;
            $scope.searchInput.entity.artName=item.features.artName;

        };

    }
})();

