'use strict';

angular.module('AdProcmt').controller('prcmtDeliveryAddItemCtlr',['$scope','$routeParams','$location','$q','ProcmtUtils','genericResource','PrcmtDeliveryState','fileExtractor',function($scope,$routeParams,$location,$q,ProcmtUtils,genericResource,PrcmtDeliveryState,fileExtractor){
    var self = this ;
    $scope.prcmtDeliveryAddItemCtlr = self;
    self.prcmtDelivery = {};
    self.prcmtDeliveryItemHolder = {
        dlvryItem:{},
        recvngOus:[],
        strgSctns:[]

    };
    self.closeStatus = false;
    self.prcmtDeliveryItemHolders = [];
    self.prcmtDeliveryItemHoldersDeleted = [];
    self.error = "";
    self.findArticleByName = findArticleByName;
    self.findArticleByCip = findArticleByCip;
    self.loading = true;
    self.onSelect = onSelect;
    self.save = save;
    self.close = close;
    self.addItem = addItem;
    self.editItem = editItem;
    self.deleteItem = deleteItem;
    self.selectedIndex;
    self.selectedItem;
    self.taux;
    self.tauxMultiplicateur = tauxMultiplicateur;
    self.totalAmountEntered = 0;
    self.show = false;
    self.showMore = showMore;
    self.showLess = showLess;
    self.loadBusinessPartner = loadBusinessPartner;
    self.loadOrgUnit = loadOrgUnit;
    self.loadstkSection = loadstkSection;
    self.handlePrintRequestEvent =handlePrintRequestEvent;
    self.running="";

    load();

    function load(){
        var data = PrcmtDeliveryState.getDeliveryHolder();
        self.prcmtDelivery = data.delivery;
        self.prcmtDeliveryItemHolders = data.deliveryItems;
        if(self.prcmtDelivery.dlvryStatus=='INITIATED' || self.prcmtDelivery.dlvryStatus=='ONGOING'){
            self.closeStatus = true;
        }
    };

    function findArticleByName (value) {
        return findArticle('artName',value);
    }

    function findArticleByCip (value) {
        return findArticle('artPic',value);
    }

    function findArticle(variableName, variableValue){
        return genericResource.findByLikePromissed(ProcmtUtils.stkArtlot2strgsctnsUrlBase, variableName, variableValue)
            .then(function (entitySearchResult) {
                var resultList = entitySearchResult.resultList;
                var displayDatas = [];
                angular.forEach(resultList,function(item){
                    var artName = item.artName;
                    var displayable = {};
                    var sectionArticleLot = item.sectionArticleLot;
                    if(sectionArticleLot) {
                        var artQties = sectionArticleLot.artQties;
                        if(!artQties) artQties = [];
                        angular.forEach(artQties, function(artQty){
                            var displayableStr = "";
                            displayable.artName = artName;
                            displayableStr = artQty.artPic
                            displayableStr += " - "+artName;
                            if(artQty.lotPic) {
                                displayable.lotPic = artQty.lotPic;
                            }
                            if(artQty.section) {
                                displayable.section = artQty.section;
                                displayableStr += " - "+artQty.section;
                            }
                            if(artQty.stockQty) {
                                displayable.stockQty = artQty.stockQty;
                                displayableStr += " - Qty ("+artQty.stockQty+")";
                            }
                            displayable.artPic = artQty.artPic;
                            displayable.sppuPreTax = sectionArticleLot.sppuHT;
                            displayable.minSppuHT = sectionArticleLot.minSppuHT;
                            displayable.sppuTaxIncl = sectionArticleLot.sppuTaxIncl;
                            displayable.sppuCur = sectionArticleLot.sppuCur;
                            displayable.vatPct = sectionArticleLot.vatPct;
                            displayable.vatSalesPct = sectionArticleLot.vatSalesPct;
                            displayable.salesVatAmt = sectionArticleLot.salesVatAmt;
                            displayable.salesWrntyDys = sectionArticleLot.salesWrntyDys;
                            displayable.salesRtrnDays = sectionArticleLot.salesRtrnDays;
                            displayable.pppuPreTax = sectionArticleLot.pppuHT;
                            displayable.purchWrntyDys = sectionArticleLot.purchWrntyDys;
                            displayable.purchRtrnDays = sectionArticleLot.purchRtrnDays;

                            displayable.displayableStr = displayableStr;
                            displayDatas.push(displayable);
                        });
                    }
                });
                return displayDatas;
            });
    }

    function onSelect(item,model,label){

        self.prcmtDeliveryItemHolder.dlvryItem.artPic = item.artPic;
        self.prcmtDeliveryItemHolder.dlvryItem.artName = item.artName;
        self.prcmtDeliveryItemHolder.dlvryItem.lotPic = item.lotPic;
        self.prcmtDeliveryItemHolder.dlvryItem.sppuPreTax = item.sppuPreTax;
        self.prcmtDeliveryItemHolder.dlvryItem.sppuCur = item.sppuCur;
        self.prcmtDeliveryItemHolder.dlvryItem.vatPct = item.vatPct;
        self.prcmtDeliveryItemHolder.dlvryItem.vatSalesPct = item.vatSalesPct;
        self.prcmtDeliveryItemHolder.dlvryItem.stkQtyPreDlvry =item.stockQty;
        self.prcmtDeliveryItemHolder.dlvryItem.minSppuHT = item.minSppuHT;
        self.prcmtDeliveryItemHolder.dlvryItem.salesWrntyDys = item.salesWrntyDys;
        self.prcmtDeliveryItemHolder.dlvryItem.salesRtrnDays = item.salesRtrnDays;
        self.prcmtDeliveryItemHolder.dlvryItem.purchWrntyDys = item.purchWrntyDys;
        self.prcmtDeliveryItemHolder.dlvryItem.purchRtrnDays = item.purchRtrnDays;
        self.prcmtDeliveryItemHolder.dlvryItem.pppuPreTax = item.pppuPreTax;
    }

    function loadBusinessPartner(val){
        return genericResource.findByLikePromissed(ProcmtUtils.adbnsptnr, 'fullName', val)
            .then(function(entitySearchResult){
                if(!angular.isUndefined(entitySearchResult))
                    return entitySearchResult.resultList;
                else return "";
            });
    }

    function loadOrgUnit(val){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        if(val){
            searchInput.entity.identif='hs';
            searchInput.entity.fullName = val+'%';
            searchInput.fieldNames.push('fullName');
        }
        return genericResource.findByLikeWithSearchInputPromissed(ProcmtUtils.orgunits,searchInput)
            .then(function(entitySearchResult){
                if(!angular.isUndefined(entitySearchResult))
                    return entitySearchResult.resultList;
                else return "";
            });
    }


    function loadstkSection(val){
        return loadstkSectionPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        })
    }
    function loadstkSectionPromise(val){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        searchInput.entity.name = val;
        searchInput.fieldNames.push('name');
        var deferred = $q.defer();
        genericResource.findByLike(ProcmtUtils.stkSection,searchInput).success(function (entitySearchResult) {
            deferred.resolve(entitySearchResult);
        }).error(function(){
            deferred.reject("No section unit");
        });
        return deferred.promise;
    }

    function save(){
        self.running ="Veuillez patientez, l'enregistrement se poursuit ...";
        var prcmtDeliveryHolder = {};
        prcmtDeliveryHolder.delivery = self.prcmtDelivery;
        prcmtDeliveryHolder.deliveryItems = self.prcmtDeliveryItemHolders;
        for(var i=0;i<self.prcmtDeliveryItemHoldersDeleted.length;i++){
                prcmtDeliveryHolder.deliveryItems.push(self.prcmtDeliveryItemHoldersDeleted[i])
            }

        var start=0;
        var max=3;
        var requests = [];
        while(start<prcmtDeliveryHolder.deliveryItems.length){
            var data = {
                delivery:{},
                deliveryItems:[]
            };
            data.delivery = prcmtDeliveryHolder.delivery;
            for(var i=start;i<start+max;i++){
                if(prcmtDeliveryHolder.deliveryItems[i])
                    data.deliveryItems.push(prcmtDeliveryHolder.deliveryItems[i]);
            }
            start +=max;
            var request = genericResource.customMethod(ProcmtUtils.urlManagerDelivery+'/update',data);
            requests.push(request);
        }
        $q.all(requests).then(function(result) {
            var prcmtDeliveryHolderTmp={
                delivery:{},
                deliveryItems:[]
            };
            angular.forEach(result, function(response) {
                prcmtDeliveryHolderTmp.delivery = response.data.delivery;
                for(var i=0;i<response.data.deliveryItems.length;i++){
                    prcmtDeliveryHolderTmp.deliveryItems.push(response.data.deliveryItems[i]);
                }
            });

            return prcmtDeliveryHolderTmp;
        }).then(function(tmpResult) {
            self.running ="";
            self.prcmtDelivery = tmpResult.delivery;
            self.prcmtDeliveryItemHolders = tmpResult.deliveryItems;
        });
    }

    function close () {
        self.running ="Veuillez patientez, l'enregistrement se poursuit ...";
        var prcmtDeliveryHolder = {};
        prcmtDeliveryHolder.delivery = self.prcmtDelivery;
        prcmtDeliveryHolder.deliveryItems = self.prcmtDeliveryItemHolders;

        for(var i=0;i<self.prcmtDeliveryItemHoldersDeleted.length;i++){
            prcmtDeliveryHolder.deliveryItems.push(self.prcmtDeliveryItemHoldersDeleted[i])
        }

        var start=0;
        var max=3;
        var requests = [];
        while(start<prcmtDeliveryHolder.deliveryItems.length){
            var data = {
                delivery:{},
                deliveryItems:[]
            };
            data.delivery = prcmtDeliveryHolder.delivery;
            for(var i=start;i<start+max;i++){
                if(prcmtDeliveryHolder.deliveryItems[i])
                    data.deliveryItems.push(prcmtDeliveryHolder.deliveryItems[i]);
            }
            start +=max;
            var request = genericResource.customMethod(ProcmtUtils.urlManagerDelivery+'/update',data);
            requests.push(request);
        }
        $q.all(requests).then(function(result) {
            var prcmtDeliveryHolderTmp={
                delivery:{},
                deliveryItems:[]
            };
            angular.forEach(result, function(response) {
                prcmtDeliveryHolderTmp.delivery = response.data.delivery;
                for(var i=0;i<response.data.deliveryItems.length;i++){
                    prcmtDeliveryHolderTmp.deliveryItems.push(response.data.deliveryItems[i]);
                }
            });

            return prcmtDeliveryHolderTmp;
        }).then(function(tmpResult) {
            genericResource.customMethod(ProcmtUtils.urlManagerDelivery+'/close',tmpResult.delivery).success(function(){
                self.running ="";
                self.closeStatus = false;
                self.prcmtDelivery = tmpResult.delivery;
                self.prcmtDeliveryItemHolders = tmpResult.deliveryItems;
            })
        });
    }

    function addItem(){
        if(self.rcvngOrgUnit){
            var rcvngOrgUnitHolder = {rcvngOrgUnit:{}};
            rcvngOrgUnitHolder.rcvngOrgUnit.rcvngOrgUnit = self.rcvngOrgUnit;
            rcvngOrgUnitHolder.rcvngOrgUnit.qtyDlvrd = self.prcmtDeliveryItemHolder.dlvryItem.qtyDlvrd;
            rcvngOrgUnitHolder.rcvngOrgUnit.freeQty = self.prcmtDeliveryItemHolder.dlvryItem.freeQty;
            if(self.prcmtDeliveryItemHolder.dlvryItem.dlvryItemNbr){
                rcvngOrgUnitHolder.rcvngOrgUnit.dlvryItemNbr = self.prcmtDeliveryItemHolder.dlvryItem.dlvryItemNbr;
            }
            self.prcmtDeliveryItemHolder.recvngOus = [];
             self.prcmtDeliveryItemHolder.recvngOus.push(rcvngOrgUnitHolder);
        }
        if(self.strgSection){
            var strgSctnHolder = {strgSctn:{}};
            strgSctnHolder.strgSctn.strgSection = self.strgSection;
            strgSctnHolder.strgSctn.qtyStrd = self.prcmtDeliveryItemHolder.dlvryItem.qtyDlvrd;
            if(self.prcmtDeliveryItemHolder.dlvryItem.dlvryItemNbr){
                strgSctnHolder.strgSctn.dlvryItemNbr = self.prcmtDeliveryItemHolder.dlvryItem.dlvryItemNbr;
            }
            self.prcmtDeliveryItemHolder.strgSctns = [];
            self.prcmtDeliveryItemHolder.strgSctns.push(strgSctnHolder);
        }

        var found = false;
        for(var i=0;i<self.prcmtDeliveryItemHolders.length;i++){
            if(self.prcmtDeliveryItemHolders[i].dlvryItem.artPic==self.prcmtDeliveryItemHolder.dlvryItem.artPic){
                self.prcmtDeliveryItemHolders[i].dlvryItem.qtyDlvrd = parseInt(self.prcmtDeliveryItemHolders[i].dlvryItem.qtyDlvrd) + parseInt(self.prcmtDeliveryItemHolder.dlvryItem.qtyDlvrd);
                found = true;
                break;
            }
        }
        if(!found){
            self.prcmtDeliveryItemHolders.unshift(self.prcmtDeliveryItemHolder);
        }
        //CLEAR
        self.prcmtDeliveryItemHolder = {dlvryItem:{},recvngOus:[],strgSctns:[]};
        self.taux = "";
        self.rcvngOrgUnit = "";
        self.strgSection = "";

        calculTotalAmountEntered();
        $('#artName').focus();
    }
    function deleteItem(index){
        var prcmtDeliveryItemHolderDeleted = {};
        angular.copy(self.prcmtDeliveryItemHolders[index],prcmtDeliveryItemHolderDeleted) ;
        self.prcmtDeliveryItemHolders.splice(index,1);
        if(prcmtDeliveryItemHolderDeleted.dlvryItem && prcmtDeliveryItemHolderDeleted.dlvryItem.id){
            prcmtDeliveryItemHolderDeleted.deleted = true;
            self.prcmtDeliveryItemHoldersDeleted.push(prcmtDeliveryItemHolderDeleted);
        }
        calculTotalAmountEntered();
    }
    function editItem(index){
        self.taux = "";
        angular.copy(self.prcmtDeliveryItemHolders[index],self.prcmtDeliveryItemHolder) ;
        self.prcmtDeliveryItemHolders.splice(index,1);
        calculTotalAmountEntered();

        if(self.prcmtDeliveryItemHolder.recvngOus[0]){
            self.rcvngOrgUnit = self.prcmtDeliveryItemHolder.recvngOus[0].rcvngOrgUnit.rcvngOrgUnit;
        }
        if(self.prcmtDeliveryItemHolder.strgSctns[0]){
            self.strgSection = self.prcmtDeliveryItemHolder.strgSctns[0].strgSctn.strgSection;
        }
    }

    function tauxMultiplicateur(){
        if(self.prcmtDeliveryItemHolder.dlvryItem.pppuPreTax && self.taux){
            self.prcmtDeliveryItemHolder.dlvryItem.sppuPreTax = 1 * self.prcmtDeliveryItemHolder.dlvryItem.pppuPreTax + (self.prcmtDeliveryItemHolder.dlvryItem.pppuPreTax * (self.taux / 100)) ;
        }
    }

    function calculTotalAmountEntered(){
        self.totalAmountEntered = 0;
        for(var i=0;i<self.prcmtDeliveryItemHolders.length;i++){
            self.totalAmountEntered = self.totalAmountEntered + (self.prcmtDeliveryItemHolders[i].dlvryItem.pppuPreTax * self.prcmtDeliveryItemHolders[i].dlvryItem.qtyDlvrd);
        }

    }

    function showMore(){
        self.show = true;
    }

    function showLess(){
        self.show = false;
    }

    function handlePrintRequestEvent() {
        genericResource.builfReportGet(ProcmtUtils.urlBase+'/deliveryreport.pdf',self.prcmtDelivery.dlvryNbr).success(function(data){
            fileExtractor.extractFile(data,"application/pdf");
        }).error(function (error) {
            $scope.error = error;
        });
    }

}]);
