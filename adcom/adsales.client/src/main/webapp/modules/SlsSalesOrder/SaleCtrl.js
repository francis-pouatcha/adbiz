'use strict';

angular.module('AdSales')
  .factory('saleUtils',['genericResource','$q',function(genericResource,$q){
        var service = {};

        service.urlBase='';
        service.adbnsptnr='/adbnsptnr.server/rest/bplegalptnrids';
        service.catalarticles='/adcatal.server/rest/catalarticles';
        service.orgunits='/adbase.server/rest/orgunits';
        service.stkSection = '/adstock.server/rest/stksections';
        service.sale = '/adsales.server/rest/sale';
        service.stkArtlot2strgsctnsUrlBase = '/adstock.server/rest/stkarticlelot2strgsctns';

        return service;
  }])
.controller('saleCtlr',['$scope','$modal','saleUtils','slsSalesOrderState','genericResource','$routeParams','$location','$q', 'conversionPrice','dateFilter',
                        function($scope,$modal,saleUtils,slsSalesOrderState,genericResource,$routeParams,$location,$q,conversionPrice,dateFilter){
    var self = this ;
    $scope.saleCtlr = self;
    $scope.cur = "XAF";
    $scope.conversionPrice = conversionPrice;
    self.slsSalesOrder = slsSalesOrderState.resultHandler.entity();
    self.slsSalesOrderHolder = {
        slsSalesOrder:{},
        slsSOItemsholder:[],
        slsSOPtnrsHolder:[]
    };
    if(slsSalesOrderState.slsSalesOrderHolder){
        self.slsSalesOrderHolder = slsSalesOrderState.slsSalesOrderHolder;
    }
    if(!self.slsSalesOrderHolder){
        self.slsSalesOrderHolder.slsSalesOrder.soDt = new Date();
        self.slsSalesOrderHolder.slsSalesOrder.grossSPPreTax = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax = 0;
        self.slsSalesOrderHolder.slsSalesOrder.vatAmount = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl = 0;
        self.slsSalesOrderHolder.slsSalesOrder.rebate = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt = 0;
        self.slsSalesOrderHolder.slsSalesOrder.soStatus = 'INITIATED';
        self.slsSalesOrderHolder.slsSalesOrder.soCur = $scope.cur;
     }
        
    self.slsSalesOrderHolderTab = [];
    self.slsSalesOrderItemHolder = {};
    self.error = "";
    self.onSelect = onSelect;
    self.addItem = addItem;
    self.editItem = editItem;
    self.deleteItem = deleteItem;
    self.index=0;
    self.selectedItem;
    self.totalAmountEntered = 0;
    self.loadBusinessPartner = loadBusinessPartner;
    self.slsSOItemsholderDeleted = [];
    self.maxDisctRate;
    self.cloturerCmd = cloturerCmd;
    self.annulerCmd = annulerCmd;
    self.newCmd = newCmd;
    self.addBptnr = addBptnr;
    self.previous = previous;
    self.next = next;
    self.calculAmount = calculAmount;
    self.tabLength = tabLength;
    self.ModalInstanceAddBptrnCtrl = ModalInstanceAddBptrnCtrl;
    self.totalAmount = totalAmount;
    self.showBtnClose = true;
    self.ptnrRole;
    self.findArticleByName = findArticleByName;
    self.findArticleByCip = findArticleByCip;
    loadPtnrRole();
    loadSO();
    
    
    function loadSO(){
       //loading
    }

    function loadBusinessPartner(val){
        return genericResource.findByLikePromissed(saleUtils.adbnsptnr, 'cpnyName', val)
            .then(function(entitySearchResult){
                if(!angular.isUndefined(entitySearchResult))
                    return entitySearchResult.resultList;
                else return "";
            });
    }

        function findArticleByName (value) {
            return findArticle('artName',value);
        }

        function findArticleByCip (value) {
            return findArticle('artPic',value);
        }

    function findArticle(variableName, variableValue){
        return genericResource.findByLikePromissed(saleUtils.stkArtlot2strgsctnsUrlBase, variableName, variableValue)
            .then(function (entitySearchResult) {
                var resultList = entitySearchResult.resultList;
                return parseArticleSearchResult(resultList);
            });
    }

    function parseArticleSearchResult(resultList){
        var displayDatas = [];
        for	(var index = 0; index < resultList.length; index++) {
        	var item = resultList[index];
            var artName = item.artName;
            var lotPic = item.lotPic;
            var displayable = {};
            var sectionArticleLot = item.sectionArticleLot;
            if(angular.isUndefined(sectionArticleLot)) continue;
            var artQties = sectionArticleLot.artQties;
            if (angular.isUndefined(artQties) || artQties.length==0) continue;
            var artQty;
            for	(var index1 = 0; index1 < artQties.length; index1++) {
            	if(!angular.equals(lotPic, artQties[index1].lotPic)) continue;
            	artQty = artQties[index1]; break;
            }
            // continue if no qty for this lot.
            if (angular.isUndefined(artQty) || angular.isUndefined(artQty.stockQty) || artQty.stockQty <= 0) continue;
            
            var displayableStr = "";
            displayable.artName = artName;
            displayableStr = artQty.artPic
            displayableStr += " - "+artName;
            if (artQty.lotPic) {
                displayable.lotPic = artQty.lotPic;
                displayableStr += "- lot (" + artQty.lotPic + ")";
            }
            if (artQty.section) {
                displayable.section = artQty.section;
                displayableStr += "- section (" + artQty.section + ")";
            }
            if (artQty.stockQty) {
                displayable.stockQty = artQty.stockQty;
                displayableStr += "- Qty (" + artQty.stockQty + ")";
            }
            displayableStr += " - ppuHT (" + sectionArticleLot.sppuHT + ")";
            
            if(angular.isDefined(sectionArticleLot.expirDt) && sectionArticleLot.expirDt)
                displayableStr += " - expirDt (" + dateFilter(sectionArticleLot.expirDt, 'dd.MM.yyyy') + ")";

            if(angular.isDefined(sectionArticleLot.stkgDt) && sectionArticleLot.stkgDt)
                displayableStr += " - stkgDt (" + dateFilter(sectionArticleLot.stkgDt, 'dd.MM.yyyy') + ")";
            	
            displayable.artPic = artQty.artPic;
            displayable.sppuPreTax = sectionArticleLot.sppuHT;
            displayable.minSppuHT = sectionArticleLot.minSppuHT;
            displayable.sppuTaxIncl = sectionArticleLot.sppuTaxIncl;
            displayable.sppuCur = sectionArticleLot.sppuCur;
            displayable.vatPct = sectionArticleLot.vatSalesPct;
            displayable.salesVatAmt = sectionArticleLot.salesVatAmt;
            displayable.salesWrntyDys = sectionArticleLot.salesWrntyDys;
            displayable.salesRtrnDays = sectionArticleLot.salesRtrnDays;

            displayable.displayableStr = displayableStr;

            displayDatas.push(displayable);
        }
        return displayDatas;
    }
    
        function loadPtnrRole(){
            genericResource.listAll(saleUtils.sale+'/listAllPtnrRole').success(function(data){
                self.ptnrRole = data;
            });
        }

    function onSelect(item,model,label){
        self.slsSalesOrderItemHolder.slsSOItem.artPic = item.artPic;
        self.slsSalesOrderItemHolder.slsSOItem.artName = item.artName;
        self.slsSalesOrderItemHolder.slsSOItem.section = item.section;
        self.slsSalesOrderItemHolder.slsSOItem.lotPic = item.lotPic;
        self.slsSalesOrderItemHolder.slsSOItem.sppuPreTax = item.sppuPreTax;
        self.slsSalesOrderItemHolder.slsSOItem.sppuCur = item.sppuCur;
        self.slsSalesOrderItemHolder.slsSOItem.vatPct = item.vatPct;
        self.slsSalesOrderItemHolder.slsSOItem.stkQty =item.stockQty;
        self.slsSalesOrderItemHolder.slsSOItem.section =item.section;
        calculAmount();
    }

        function calculAmount() {
            if(self.slsSalesOrderItemHolder.slsSOItem.orderedQty){
                if(!self.slsSalesOrderItemHolder.slsSOItem.returnedQty)
                    self.slsSalesOrderItemHolder.slsSOItem.returnedQty = 0;
                self.slsSalesOrderItemHolder.slsSOItem.deliveredQty = parseInt(self.slsSalesOrderItemHolder.slsSOItem.orderedQty) - parseInt(self.slsSalesOrderItemHolder.slsSOItem.returnedQty);
                self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax = self.slsSalesOrderItemHolder.slsSOItem.sppuPreTax*self.slsSalesOrderItemHolder.slsSOItem.deliveredQty;
            }
            if(self.slsSalesOrderItemHolder.slsSOItem.rebate)
                self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax=self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax-parseInt(self.slsSalesOrderItemHolder.slsSOItem.rebate);

            if(self.slsSalesOrderItemHolder.slsSOItem.rebatePct)
                self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax=self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax-((parseInt(self.slsSalesOrderItemHolder.slsSOItem.rebatePct)*self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax)/100);

            if(!self.slsSalesOrderItemHolder.slsSOItem.rebate && !self.slsSalesOrderItemHolder.slsSOItem.rebatePct)
                self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax = self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax;

            if(self.slsSalesOrderItemHolder.slsSOItem.orderedQty){
                self.slsSalesOrderItemHolder.slsSOItem.vatAmount = self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax*self.slsSalesOrderItemHolder.slsSOItem.vatPct/100;
                self.slsSalesOrderItemHolder.slsSOItem.netSPTaxIncl = self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax+self.slsSalesOrderItemHolder.slsSOItem.vatAmount;
            }
        }
    function totalAmount(){
        self.slsSalesOrderHolder.slsSalesOrder.grossSPPreTax = 0;
        self.slsSalesOrderHolder.slsSalesOrder.rebate = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax = 0;
        self.slsSalesOrderHolder.slsSalesOrder.vatAmount = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt = 0;

        angular.forEach(self.slsSalesOrderHolder.slsSOItemsholder, function (value, key) {
            self.slsSalesOrderHolder.slsSalesOrder.grossSPPreTax = self.slsSalesOrderHolder.slsSalesOrder.grossSPPreTax + value.slsSOItem.grossSPPreTax;
            self.slsSalesOrderHolder.slsSalesOrder.rebate = self.slsSalesOrderHolder.slsSalesOrder.rebate + parseInt(value.slsSOItem.rebate);
            self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax = self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax + value.slsSOItem.netSPPreTax;
            self.slsSalesOrderHolder.slsSalesOrder.vatAmount = self.slsSalesOrderHolder.slsSalesOrder.vatAmount + value.slsSOItem.vatAmount;
            self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl = self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl + value.slsSOItem.netSPTaxIncl;
        })

        if(self.slsSalesOrderHolder.slsSalesOrder.pymtDscntPct)
            self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt =self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl -(self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax*self.slsSalesOrderHolder.slsSalesOrder.pymtDscntPct/100);

        if(self.slsSalesOrderHolder.slsSalesOrder.pymtDscntAmt){
            self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt=self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl - parseInt(self.slsSalesOrderHolder.slsSalesOrder.pymtDscntAmt);
        }

        if(!self.slsSalesOrderHolder.slsSalesOrder.pymtDscntAmt && !self.slsSalesOrderHolder.slsSalesOrder.pymtDscntPct){
            self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt = self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl;
        }

    }
        
    function addItem(){
        var found = false;
        for(var i=0;i<self.slsSalesOrderHolder.slsSOItemsholder.length;i++){
            if(self.slsSalesOrderHolder.slsSOItemsholder[i].slsSOItem.artPic==self.slsSalesOrderItemHolder.slsSOItem.artPic){
                self.slsSalesOrderHolder.slsSOItemsholder[i].slsSOItem.orderedQty = parseInt(self.slsSalesOrderHolder.slsSOItemsholder[i].slsSOItem.orderedQty) + parseInt(self.slsSalesOrderItemHolder.slsSOItem.orderedQty);
                found = true;
                break;
            }
        }
        if(!found){
            self.slsSalesOrderHolder.slsSOItemsholder.unshift(self.slsSalesOrderItemHolder);
        }
        self.slsSalesOrderItemHolder = {};
        totalAmount();
        $('#artName').focus();
        enableCloseCmd();
    }
    function deleteItem(index){
        var slsSalesOrderItemHolderDeleted = {};
        angular.copy(self.slsSalesOrderHolder.slsSOItemsholder[index],slsSalesOrderItemHolderDeleted) ;
        self.slsSalesOrderHolder.slsSOItemsholder.splice(index,1);
        if(slsSalesOrderItemHolderDeleted.slsSOItem && slsSalesOrderItemHolderDeleted.slsSOItem.id) {
            slsSalesOrderItemHolderDeleted.deleted = true;
            self.slsSOItemsholderDeleted.push(slsSalesOrderItemHolderDeleted);
        }
        totalAmount();
        enableCloseCmd();
    }
    function editItem(index){
        angular.copy(self.slsSalesOrderHolder.slsSOItemsholder[index],self.slsSalesOrderItemHolder) ;
        self.slsSalesOrderHolder.slsSOItemsholder.splice(index,1);
        totalAmount();
        enableCloseCmd();
    }
        function cloturerCmd(){
            for(var i=0;i<self.slsSOItemsholderDeleted.length;i++){
                self.prcmtOrderHolder.poItems.push(self.slsSOItemsholderDeleted[i])
            }
            genericResource.customMethod(saleUtils.sale+'/doSale',self.slsSalesOrderHolder).success(function(data){
                clearSaleOrder();
            });
        }
        function annulerCmd(){
            cancelSaleOrder();
        }
        function newCmd(){

            if(self.slsSalesOrderHolder){
                if(self.slsSalesOrderHolder.slsSOItemsholder.length > 0){
                    self.slsSalesOrderHolderTab.push(self.slsSalesOrderHolder);
                    self.index = self.slsSalesOrderHolderTab.length-1;
                }
            }
            clearSaleOrder();
        }
        function addBptnr(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/SlsSalesOrder/SlsSOPtnr.html',
                controller: self.ModalInstanceAddBptrnCtrl,
                size: size,
                resolve:{
                    slsSOPtnrsHolder: function(){
                        return self.slsSalesOrderHolder.slsSOPtnrsHolder;
                    }
                }
            });
        }
    
        function cancelSaleOrder(){
            $location.path('#/SlsSalesOrders');
	    }
     
        function clearSaleOrder(){
            self.slsSalesOrderHolder = {
                slsSalesOrder:{},
                slsSOItemsholder:[],
                slsSOPtnrsHolder:[]
            };
            self.slsSalesOrderHolder.slsSalesOrder.soDt = new Date();
            self.slsSalesOrderHolder.slsSalesOrder.grossSPPreTax = 0;
            self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax = 0;
            self.slsSalesOrderHolder.slsSalesOrder.vatAmount = 0;
            self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl = 0;
            self.slsSalesOrderHolder.slsSalesOrder.rebate = 0;
            self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt = 0;
            self.slsSalesOrderHolder.slsSalesOrder.soStatus = 'INITIATED';
            self.slsSalesOrderItemHolder = {};
            self.showBtnClose = true;
        }
        function ModalInstanceAddBptrnCtrl($scope, $modalInstance, slsSOPtnrsHolder) {
             $scope.slsSOPtnrsHolder = slsSOPtnrsHolder;
            $scope.loadBusinessPartner = function(val){
                return self.loadBusinessPartner(val);
            }
            $scope.roleInSOs = self.ptnrRole;
            $scope.addBptrn = function(){
                var slsSOPtnrHolder = {};
                slsSOPtnrHolder.slsSOPtnr = $scope.slsSOPtnr;
                slsSOPtnrHolder.slsSOPtnr.soNbr = self.slsSalesOrderHolder.slsSalesOrder.soNbr;
                $scope.slsSOPtnrsHolder.push(slsSOPtnrHolder);
                $scope.slsSOPtnr = {};

            }
            $scope.deleteItem = function (index) {
                $scope.slsSOPtnrsHolder.splice(index,1);
            }

            $scope.cancel = function () {
                self.slsSalesOrderHolder.slsSOPtnrsHolder = $scope.slsSOPtnrsHolder;
                $modalInstance.dismiss('cancel');
            };
            $scope.$on('modal.hide',function(){
                self.slsSalesOrderHolder.slsSOPtnrsHolder = $scope.slsSOPtnrsHolder;
            });
        };

        function previous(){
            if(self.index > 0 ){
                if(self.slsSalesOrderHolder){
                    if(self.slsSalesOrderHolder.slsSOItemsholder && self.slsSalesOrderHolder.slsSOItemsholder.length > 0){
                        self.slsSalesOrderHolderTab.push(self.slsSalesOrderHolder);
                    }
                }
                self.index-=1;
                angular.copy(self.slsSalesOrderHolderTab[self.index],self.slsSalesOrderHolder);
                self.slsSalesOrderHolderTab.splice(self.index,1);
                self.slsSalesOrderItemHolder = {};
                enableCloseCmd ();
                console.log(self.index);
                console.log('lenght tab '+self.slsSalesOrderHolderTab.length);
            }

        }
        function next(){
            if(self.slsSalesOrderHolderTab.length > self.index){
                if(self.slsSalesOrderHolder){
                    if(self.slsSalesOrderHolder.slsSOItemsholder && self.slsSalesOrderHolder.slsSOItemsholder.length > 0){
                        self.slsSalesOrderHolderTab.push(self.slsSalesOrderHolder);
                    }
                }
                self.index+=1;
                angular.copy(self.slsSalesOrderHolderTab[self.index],self.slsSalesOrderHolder);
                self.slsSalesOrderHolderTab.splice(self.index,1);
                self.slsSalesOrderItemHolder = {};
                enableCloseCmd ();
                console.log(self.index);
                console.log('lenght tab '+self.slsSalesOrderHolderTab.length);
            }

        }
        function tabLength (){
            return self.slsSalesOrderHolderTab.length;
        }

        function enableCloseCmd (){
            if(self.slsSalesOrderHolder.slsSOItemsholder && self.slsSalesOrderHolder.slsSOItemsholder.length > 0)
                self.showBtnClose = false;
            else
                self.showBtnClose = true;
        }

}]);
