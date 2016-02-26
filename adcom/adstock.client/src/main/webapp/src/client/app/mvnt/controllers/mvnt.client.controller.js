
(function () {
    'use strict';

    angular
        .module('app.mvnt')
        .controller('mvntCtlr', mvntCtlr);

    mvntCtlr.$inject = ['$scope','mvntManagerResource','mvntUtils','genericResource','$rootScope'];
    /* @ngInject */
    function mvntCtlr($scope,mvntManagerResource,mvntUtils,genericResource,$rootScope) {


        $scope.mvntUtils=mvntUtils;
        $scope.mvnt = {};
        $scope.listMvnts = [];
        $scope.listTransfers = [];

        $scope.handleResetRequestEvent = function(){
            $scope.mvnt = {};
            $scope.display={};
            $scope.todisplay={};
        };

        $scope.moveOut = function(mvnt){
            mvnt.acsngDt=new Date().getTime();
            mvnt.mvntType='OUT';
            //mvnt.acsngUser=$rootScope.username;
            mvnt.artPic=undefined;
            mvnt.artName=undefined;
            $scope.display={};
            mvntManagerResource.moveOut({}, mvnt ,function(result){
                $scope.listMvnts.push(result);
                $scope.mvnt = {};
                }, function(error){
                    $scope.error = error;
            });
        };

        $scope.moveIn = function(mvnt){
            mvnt.acsngDt=new Date().getTime();
            mvnt.mvntType='IN';
            //mvnt.acsngUser=$rootScope.username;
            mvnt.artPic=undefined;
            mvnt.artName=undefined;
            $scope.display={};
            mvntManagerResource.moveIn({}, mvnt ,function(result){
                $scope.listMvnts.push(result);
                $scope.mvnt = {};
            }, function(error){
                $scope.error = error;
            });
        };

        $scope.transfer = function(mvnt){
            var mvntOut={};
            mvntOut.acsngDt=new Date().getTime();
            mvntOut.mvntType='OUT';
            mvntOut.lotPic=mvnt.lotPic;
            mvntOut.section=mvnt.section;
            mvntOut.mvntTerminal=mvnt.mvntTerminal;
            mvntOut.trgtQty=mvnt.trgtQty;
            mvntOut.origProcs=mvnt.origProcs;
            mvntOut.origProcsNbr=mvnt.origProcsNbr;

            var mvntIn={};
            mvntIn.acsngDt=new Date().getTime();
            mvntIn.mvntType='OUT';
            mvntIn.lotPic=mvnt.lotPic;
            mvntIn.section=mvnt.tosection;
            mvntIn.mvntTerminal=mvnt.mvntTerminal;
            mvntIn.trgtQty=mvnt.trgtQty;
            mvntIn.origProcs=mvnt.origProcs;
            mvntIn.origProcsNbr=mvnt.origProcsNbr;

            $scope.display={};
            $scope.todisplay={};
            mvntManagerResource.moveOut({}, mvntOut ,function(result){
                mvntManagerResource.moveIn({}, mvntIn ,function(result){
                    $scope.listTransfers.push(mvnt);
                    $scope.mvnt = {};
                }, function(error){
                    $scope.error = error;
                });
            }, function(error){
                $scope.error = error;
            });
        }

        $scope.onSectionSelectedInSearch = function(item,model,label){
            $scope.mvnt.section=item.identif;
            $scope.display.sectionName=item.name;
            $scope.display.identif=item.identif;
        };

        $scope.onToSectionSelectedInSearch = function(item,model,label){
            $scope.mvnt.tosection=item.identif;
            $scope.todisplay.sectionName=item.name;
            $scope.todisplay.identif=item.identif;
        };


        /*$scope.onArticleLotChangedInSearch = function(){
            if(mvnt.lotPic && mvnt.lotPic.length<9) return;
            if(
                (angular.isDefined(mvnt.artPic) && mvnt.artPic) &&
                (angular.isDefined(mvnt.section) && mvnt.section)
                ){
                return;
            }
            if(angular.isUndefined(mvnt.artPic) || !mvnt.artPic){
                genericResource.findByIdentif(mvntUtils.stkarticlelotsUrlBase,mvnt.lotPic.toUpperCase())
                    .success(function(articleLot){
                        mvnt.artPic=articleLot.artPic;
                        genericResource.findBy(mvntUtils.catalartfeatmappingsUrlBase,searchInput)
                            .success(function(response){
                                mvnt.artName=response.resultList[0].artName;
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
            $scope.mvnt.lotPic=item.lotPic;
            $scope.mvnt.artPic=item.artPic;
            $scope.mvnt.section = item.section;

            var searchInput = coreSearchInputInit(item.artPic);
            genericResource.findBy(mvntUtils.catalartfeatmappingsUrlBase,searchInput)
                .success(function(response){
                    $scope.mvnt.artName=response.resultList[0].artName;
                })
                .error(function(error){
                    $scope.error=error;
                });

            genericResource.findByLikePromissed(mvntUtils.stksectionsUrlBase, 'identif', $scope.mvnt.section)
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
        	var artPic = $scope.mvnt.artPic;
        	var section = $scope.mvnt.section;
        	if(((angular.isUndefined(artPic) || !artPic)) && ((angular.isUndefined(section) || !section))){
        		if(angular.isUndefined(lotPic) || lotPic.length<7) return [];
        	}
        	return mvntUtils.loadStkSectionArticleLotsByIdentif(artPic,lotPic,section);
        };


        $scope.onArticleSelectedInSearch = function(item,model,label){
            $scope.mvnt.artPic=item.identif;
            var searchInput = coreSearchInputInit(item.identif);
            genericResource.findBy(mvntUtils.catalartfeatmappingsUrlBase,searchInput)
                .success(function(response){
                    $scope.mvnt.artName=response.resultList[0].artName;
                })
                .error(function(error){
                    $scope.error=error;
                });
            
            updateOnArticleSelcted(item);
        };

        $scope.onArticleSelectedInSearchArtName = function(item,model,label){
            $scope.mvnt.artPic=item.cntnrIdentif;
            $scope.mvnt.artName=item.artName;
            
            updateOnArticleSelcted(item);

        };
        
        function updateOnArticleSelcted(item){

            // We have the article code. Tentative section and Lot
            var searchInput = mvntUtils.prepareStkSectionArticleLotSearchInput($scope.mvnt.artPic,
                $scope.mvnt.lotPic,$scope.mvnt.section);

            genericResource.findByLikeWithSearchInputPromissed(mvntUtils.stkarticlelot2strgsctnsUrlBase, searchInput)
            .then(function(entitySearchResult){
            	if(entitySearchResult.resultList && entitySearchResult.resultList.length===1){
            		var lot2Section = entitySearchResult.resultList[0];
                    $scope.mvnt.lotPic = lot2Section.lotPic;
                    $scope.mvnt.section = lot2Section.section;


                    genericResource.findByLikePromissed(mvntUtils.stksectionsUrlBase, 'identif', $scope.mvnt.section)
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

