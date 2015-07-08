'use strict';

angular.module('AdSales')
  .factory('SlsInvoiceUtils',['genericResource','$q',function(genericResource,$q){
        var service = {};

        service.urlBase='';
        service.adbnsptnr='/adbnsptnr.server/rest/bplegalptnrids';
        service.catalarticles='/adcatal.server/rest/catalarticles';
        service.orgunits='/adbase.server/rest/orgunits';
        service.stkSection = '/adstock.server/rest/stksections';
        service.invoice = '/adsales.server/rest/invoice';
        service.sale = '/adsales.server/rest/sale';
        service.stkArtlot2strgsctnsUrlBase = '/adstock.server/rest/stkarticlelot2strgsctns';

        return service;
  }])
.controller('slsInvoiceNewCtlr',['$scope','$modal','SlsInvoiceUtils','slsSalesOrderState','genericResource','$routeParams','$location','$q', 'conversionPrice','sessionManager',function($scope,$modal,SlsInvoiceUtils,slsSalesOrderState,genericResource,$routeParams,$location,$q,conversionPrice,sessionManager){
    var self = this ;
    $scope.slsInvoiceNewCtlr = self;
    self.showBtnClose = true;
    $scope.cur = "XAF";
    $scope.conversionPrice = conversionPrice;
    self.slsInvoiceHolder = {
        slsInvoice:{},
        slsInvceItemsholder:[],
        slsInvcePtnrsHolder:[]
    };
    $scope.maxRebate = sessionManager.userWsData().maxRebate;
      init();
    function init(){
        var invceNbr = $routeParams.invceNbr;
        if(invceNbr){
            genericResource.findById(SlsInvoiceUtils.invoice,invceNbr).success(function(slsInvoiceHolder){
                self.slsInvoiceHolder = slsInvoiceHolder;
            }).error(function(error){
                $scope.error = error;
            });
        }
        else {
            if(slsSalesOrderState.slsInvoiceHolder){
                angular.copy(slsSalesOrderState.slsInvoiceHolder.slsInvoice, self.slsInvoiceHolder.slsInvoice);
                angular.copy(slsSalesOrderState.slsInvoiceHolder.slsInvceItemsholder, self.slsInvoiceHolder.slsInvceItemsholder);
                angular.copy(slsSalesOrderState.slsInvoiceHolder.slsInvcePtnrsHolder, self.slsInvoiceHolder.slsInvcePtnrsHolder);
                clearSlsSOState();
                if(self.slsInvoiceHolder.slsInvceItemsholder) self.showBtnClose = false;
             }

            if(!self.slsInvoiceHolder){
                console.log('Null InvoiceHolder');
                self.slsInvoiceHolder.slsInvoice.invceDt = new Date();
                self.slsInvoiceHolder.slsInvoice.grossSPPreTax = 0;
                self.slsInvoiceHolder.slsInvoice.netSPPreTax = 0;
                self.slsInvoiceHolder.slsInvoice.vatAmount = 0;
                self.slsInvoiceHolder.slsInvoice.netSPTaxIncl = 0;
                self.slsInvoiceHolder.slsInvoice.rebate = 0;
                self.slsInvoiceHolder.slsInvoice.netSalesAmt = 0;
                self.slsInvoiceHolder.slsInvoice.invceStatus = 'INITIATED';
            }
        }
  }
        function clearSlsSOState(){
            slsSalesOrderState.slsInvoiceHolder.slsInvoice = {};
            slsSalesOrderState.slsInvoiceHolder.slsInvceItemsholder = [];
            slsSalesOrderState.slsInvoiceHolder.slsInvcePtnrsHolder = [];
        }
    /*$scope.itemPerPage=slsSalesOrderState.resultHandler.itemPerPage;
    $scope.currentPage=slsSalesOrderState.resultHandler.currentPage();
    $scope.maxSize =slsSalesOrderState.resultHandler.maxResult;*/
    
    self.slsInvoiceHolderTab = [];
    self.slsInvoiceItemHolder = {};
    self.error = "";
    self.onSelect = onSelect;
    self.addItem = addItem;
    self.editItem = editItem;
    self.deleteItem = deleteItem;
    self.index=0;
    self.selectedItem;
    self.totalAmountEntered = 0;
    self.loadBusinessPartner = loadBusinessPartner;
    self.slsInvceItemsholderDeleted = [];
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
    self.ptnrRole;
    self.findArticleByName = findArticleByName;
    self.findArticleByCip = findArticleByCip;
    self.remise = remise;
    self.saveCmd = saveCmd;
    self.testSaveCmd = testSaveCmd;
    loadPtnrRole();

     $scope.pageChangeHandler = function(num) {
      //Simple Pagination
    };
    
    
    function testSaveCmd() {
        return self.slsInvoiceHolder.slsInvoice.invceStatus=='CLOSED'|| 
               self.slsInvoiceHolder.slsInvceItemsholder.length==0 ||
               self.slsInvoiceHolder.slsInvoice.invceStatus=='SUSPENDED';
    }
    
    
    function loadBusinessPartner(val){
        return genericResource.findByLikePromissed(SlsInvoiceUtils.adbnsptnr, 'cpnyName', val)
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
        return genericResource.findByLikePromissed(SlsInvoiceUtils.stkArtlot2strgsctnsUrlBase, variableName, variableValue)
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
                            if (artQty.stockQty && artQty.stockQty > 0){
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
                                displayable.vatPct = sectionArticleLot.vatSalesPct;
                                displayable.salesVatAmt = sectionArticleLot.salesVatAmt;
                                displayable.salesWrntyDys = sectionArticleLot.salesWrntyDys;
                                displayable.salesRtrnDays = sectionArticleLot.salesRtrnDays;

                                displayable.displayableStr = displayableStr;
                                displayDatas.push(displayable);
                            }
                        });
                    }
                });
                 return displayDatas;
            });
    }


        function loadPtnrRole(){
            genericResource.listAll(SlsInvoiceUtils.sale+'/listAllPtnrRole').success(function(data){
                self.ptnrRole = data;
            })
        }

    function onSelect(item,model,label){
        self.slsInvoiceItemHolder.slsInvceItem.artPic = item.artPic;
        self.slsInvoiceItemHolder.slsInvceItem.artName = item.artName;
        self.slsInvoiceItemHolder.slsInvceItem.lotPic = item.lotPic;
        self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax = item.sppuPreTax;
        self.slsInvoiceItemHolder.slsInvceItem.sppuCur = item.sppuCur;
        self.slsInvoiceItemHolder.slsInvceItem.vatPct = item.vatPct;
        self.slsInvoiceItemHolder.slsInvceItem.stkQty =item.stockQty;
        self.slsInvoiceItemHolder.slsInvceItem.section =item.section;
        calculAmount();
    }


        function remise(){
            if(!self.slsInvoiceItemHolder.slsInvceItem.qty || self.slsInvoiceItemHolder.slsInvceItem.qty==0){
                $scope.error ="Entrer la quantite a commander";
                return;
            }
            if(self.slsInvoiceItemHolder.slsInvceItem.rebate){
                if (self.slsInvoiceItemHolder.slsInvceItem.rebate > self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax * parseInt(self.slsInvoiceItemHolder.slsInvceItem.qty)
                    || self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax * parseInt(self.slsInvoiceItemHolder.slsInvceItem.qty) == self.slsInvoiceItemHolder.slsInvceItem.rebate){
                    $scope.error ="La remise ne peut etre superieur au montant de vente";
                    return;
                }
            }else{
                if(self.slsInvoiceItemHolder.slsInvceItem.rebatePct){
                    if (self.slsInvoiceItemHolder.slsInvceItem.rebate > self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax * parseInt(self.slsInvoiceItemHolder.slsInvceItem.qty)
                        || self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax * parseInt(self.slsInvoiceItemHolder.slsInvceItem.qty) == self.slsInvoiceItemHolder.slsInvceItem.rebate){
                        $scope.error ="La remise ne peut etre superieur au montant de vente";
                        return;
                    }
                }
            }
            calculAmount();
            $scope.error="";
        }
        
        function calculAmount() {
            if(self.slsInvoiceItemHolder.slsInvceItem.qty){
                self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax = self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax*self.slsInvoiceItemHolder.slsInvceItem.qty;
            }
            if(self.slsInvoiceItemHolder.slsInvceItem.rebate)
                self.slsInvoiceItemHolder.slsInvceItem.netSPPreTax=self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax-parseInt(self.slsInvoiceItemHolder.slsInvceItem.rebate);

            if(self.slsInvoiceItemHolder.slsInvceItem.rebatePct)
                self.slsInvoiceItemHolder.slsInvceItem.netSPPreTax=self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax-((parseInt(self.slsInvoiceItemHolder.slsInvceItem.rebatePct)*self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax)/100);

            if(!self.slsInvoiceItemHolder.slsInvceItem.rebate && !self.slsInvoiceItemHolder.slsInvceItem.rebatePct)
                self.slsInvoiceItemHolder.slsInvceItem.netSPPreTax = self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax;

            if(self.slsInvoiceItemHolder.slsInvceItem.qty){
                self.slsInvoiceItemHolder.slsInvceItem.vatAmount = self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax*self.slsInvoiceItemHolder.slsInvceItem.vatPct/100;
                self.slsInvoiceItemHolder.slsInvceItem.netSPTaxIncl = self.slsInvoiceItemHolder.slsInvceItem.netSPPreTax+self.slsInvoiceItemHolder.slsInvceItem.vatAmount;
            }
        }
    
    
    function totalAmount(){
        self.slsInvoiceHolder.slsInvoice.grossSPPreTax = 0.0;
        self.slsInvoiceHolder.slsInvoice.rebate = 0.0;
        self.slsInvoiceHolder.slsInvoice.netSPPreTax = 0.0;
        self.slsInvoiceHolder.slsInvoice.vatAmount = 0.0;
        self.slsInvoiceHolder.slsInvoice.netSPTaxIncl = 0.0;
        self.slsInvoiceHolder.slsInvoice.netSalesAmt = 0.0;
        self.slsInvoiceHolder.slsInvoice.holdingAmount = 0.0;

        angular.forEach(self.slsInvoiceHolder.slsInvceItemsholder, function (value, key) {
            self.slsInvoiceHolder.slsInvoice.grossSPPreTax = self.slsInvoiceHolder.slsInvoice.grossSPPreTax + value.slsInvceItem.grossSPPreTax;
            self.slsInvoiceHolder.slsInvoice.rebate = self.slsInvoiceHolder.slsInvoice.rebate + parseInt(value.slsInvceItem.rebate);
            self.slsInvoiceHolder.slsInvoice.netSPPreTax = self.slsInvoiceHolder.slsInvoice.netSPPreTax + value.slsInvceItem.netSPPreTax;
            self.slsInvoiceHolder.slsInvoice.vatAmount = self.slsInvoiceHolder.slsInvoice.vatAmount + value.slsInvceItem.vatAmount;
            self.slsInvoiceHolder.slsInvoice.netSPTaxIncl = self.slsInvoiceHolder.slsInvoice.netSPTaxIncl + value.slsInvceItem.netSPTaxIncl;
        })

        if(self.slsInvoiceHolder.slsInvoice.pymtDscntPct){
            self.slsInvoiceHolder.slsInvoice.pymtDscntAmt = self.slsInvoiceHolder.slsInvoice.netSPPreTax*self.slsInvoiceHolder.slsInvoice.pymtDscntPct/100;
            if(self.slsInvoiceHolder.slsInvoice.pymtDscntAmt > self.slsInvoiceHolder.slsInvoice.netSPTaxIncl){
                $scope.error="L'escompte ne doit pas etre superieur au montant de vente";
                return;
            }
            if(self.slsInvoiceHolder.slsInvoice.pymtDscntPct > $scope.maxRebate){
                $scope.error="L'escompte ne doit pas etre superieur a votre taux de remise";
                return;
            }
            self.slsInvoiceHolder.slsInvoice.netSalesAmt =self.slsInvoiceHolder.slsInvoice.netSPTaxIncl -(self.slsInvoiceHolder.slsInvoice.netSPPreTax*self.slsInvoiceHolder.slsInvoice.pymtDscntPct/100);
        }else{
            if(self.slsInvoiceHolder.slsInvoice.pymtDscntAmt){
                if(self.slsInvoiceHolder.slsInvoice.pymtDscntAmt > self.slsInvoiceHolder.slsInvoice.netSPTaxIncl){
                    $scope.error="L'escompte ne doit pas etre superieur au montant de vente";
                    return;
                }
                var pymtDscntPct = self.slsInvoiceHolder.slsInvoice.pymtDscntAmt*100/self.slsInvoiceHolder.slsInvoice.netSPPreTax;
                if(pymtDscntPct > $scope.maxRebate){
                    $scope.error="L'escompte ne doit pas etre superieur a votre taux de remise";
                    return;
                }
                self.slsInvoiceHolder.slsInvoice.netSalesAmt=self.slsInvoiceHolder.slsInvoice.netSPTaxIncl - parseInt(self.slsInvoiceHolder.slsInvoice.pymtDscntAmt);
                //self.slsInvoiceHolder.slsInvoice.pymtDscntPct = self.slsInvoiceHolder.slsInvoice.pymtDscntPct*100/self.slsInvoiceHolder.slsInvoice.netSPPreTax;
            }
        }
        if(!self.slsInvoiceHolder.slsInvoice.pymtDscntAmt && !self.slsInvoiceHolder.slsInvoice.pymtDscntPct){
            self.slsInvoiceHolder.slsInvoice.netSalesAmt = self.slsInvoiceHolder.slsInvoice.netSPTaxIncl;
        }
        self.slsInvoiceHolder.slsInvoice.holdingAmount = (self.slsInvoiceHolder.slsInvoice.grossSPPreTax*self.slsInvoiceHolder.slsInvoice.holdingPct)/100;
        self.slsInvoiceHolder.slsInvoice.netSalesAmt = self.slsInvoiceHolder.slsInvoice.netSalesAmt + self.slsInvoiceHolder.slsInvoice.holdingAmount;
        
        $scope.error="";
    }
    
    
    
        
    function addItem(){
        if (!self.slsInvoiceItemHolder || angular.isUndefined(self.slsInvoiceItemHolder) || (!self.slsInvoiceItemHolder.slsInvceItem.artPic && 1 > self.slsInvoiceItemHolder.slsInvceItem.qty)) {
            $scope.error ="Entrer correctement les donnees";
            return;
        }
        if (self.slsInvoiceItemHolder.slsInvceItem.qty > self.slsInvoiceItemHolder.slsInvceItem.stkQty){
            $scope.error ="Quantite Vendus superieur a la quantite en stock";
            return;
        }
        if (0 > self.slsInvoiceItemHolder.slsInvceItem.vatPct){
            $scope.error ="La TVA ne peut etre negatif";
            return;
        }
        if (self.slsInvoiceItemHolder.slsInvceItem.rebate > self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax * parseInt(self.slsInvoiceItemHolder.slsInvceItem.qty)
            || self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax * parseInt(self.slsInvoiceItemHolder.slsInvceItem.qty) == self.slsInvoiceItemHolder.slsInvceItem.rebate){
            $scope.error ="La remise ne peut etre superieur au montant de vente";
            return;
        }
        var rebatePct = (parseInt(self.slsInvoiceItemHolder.slsInvceItem.rebate)*100)/(self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax * parseInt(self.slsInvoiceItemHolder.slsInvceItem.qty));

        if (rebatePct > $scope.maxRebate || self.slsInvoiceItemHolder.slsInvceItem.rebatePct > $scope.maxRebate){
            $scope.error ="La remise ne peut etre superieur a votre taux de remise";
            return;
        }

        var found = false;
        for(var i=0;i<self.slsInvoiceHolder.slsInvceItemsholder.length;i++){
            if(self.slsInvoiceHolder.slsInvceItemsholder[i].slsInvceItem.artPic==self.slsInvoiceItemHolder.slsInvceItem.artPic){
                self.slsInvoiceHolder.slsInvceItemsholder[i].slsInvceItem.qty = parseInt(self.slsInvoiceHolder.slsInvceItemsholder[i].slsInvceItem.qty) + parseInt(self.slsInvoiceItemHolder.slsInvceItem.qty);
                found = true;
                break;
            }
        }
        if(!found){
            self.slsInvoiceHolder.slsInvceItemsholder.unshift(self.slsInvoiceItemHolder);
        }
        self.slsInvoiceItemHolder = {};
        totalAmount();
        $('#artName').focus();
        enableCloseCmd();
        $scope.error="";
    }
    
    
    function deleteItem(index){
        var slsInvoiceItemHolderDeleted = {};
        angular.copy(self.slsInvoiceHolder.slsInvceItemsholder[index],slsInvoiceItemHolderDeleted) ;
        self.slsInvoiceHolder.slsInvceItemsholder.splice(index,1);
        if(slsInvoiceItemHolderDeleted.slsInvceItem && slsInvoiceItemHolderDeleted.slsInvceItem.id) {
            slsInvoiceItemHolderDeleted.deleted = true;
            self.slsInvceItemsholderDeleted.push(slsInvoiceItemHolderDeleted);
        }
        totalAmount();
        enableCloseCmd();
    }
    
    
    function editItem(index){
        angular.copy(self.slsInvoiceHolder.slsInvceItemsholder[index],self.slsInvoiceItemHolder) ;
        self.slsInvoiceHolder.slsInvceItemsholder.splice(index,1);
        totalAmount();
        enableCloseCmd();
    }
        function cloturerCmd(){
            for(var i=0;i<self.slsInvceItemsholderDeleted.length;i++){
                self.prcmtOrderHolder.poItems.push(self.slsInvceItemsholderDeleted[i])
            }
            // Set the soNbr when it is null
            if(!self.slsInvoiceHolder.slsInvoice.soNbr){
                self.slsInvoiceHolder.slsInvoice.soNbr = self.slsInvoiceHolder.slsInvoice.invceNbr;
            }
            genericResource.customMethod(SlsInvoiceUtils.invoice+'/clotureInvoice',self.slsInvoiceHolder).success(function(data){
                self.slsInvoiceHolder=data;
            }).error(function(error){
                $scope.error=error;
            });

        }

        function saveCmd(){
            for(var i=0;i<self.slsInvceItemsholderDeleted.length;i++){
                self.prcmtOrderHolder.poItems.push(self.slsInvceItemsholderDeleted[i])
            }

            genericResource.customMethod(SlsInvoiceUtils.invoice+'/saveInvoice',self.slsInvoiceHolder).success(function(data){
                self.slsInvoiceHolder=data;
            }).error(function(error){
                $scope.error=error;
            });
        }
        function annulerCmd(){
            genericResource.get(SlsInvoiceUtils.invoice+'/cancelInvoice/'+self.slsInvoiceHolder.slsInvoice.invceNbr)
                .success(function(slsInvoice){
                    self.slsInvoiceHolder.slsInvoice = slsInvoice;
                })
                .error(function(error){
                    $scope.error = error;
                });
        }
        function newCmd(){

            if(self.slsInvoiceHolder){
                if(self.slsInvoiceHolder.slsInvceItemsholder.length > 0){
                    self.slsInvoiceHolderTab.push(self.slsInvoiceHolder);
                    self.index = self.slsInvoiceHolderTab.length-1;
                }
            }
            clearSaleOrder();
        }
        function addBptnr(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/SlsInvoice/SlsInvcePtnr.html',
                controller: self.ModalInstanceAddBptrnCtrl,
                size: size,
                resolve:{
                    slsInvcePtnrsHolder: function(){
                        return self.slsInvoiceHolder.slsInvcePtnrsHolder;
                    }
                }
            });
        }
        function clearSaleOrder(){
            self.slsInvoiceHolder = {
                slsInvoice:{},
                slsInvceItemsholder:[],
                slsInvcePtnrsHolder:[]
            };
            self.slsInvoiceHolder.slsInvoice.invceDt = new Date();
            self.slsInvoiceHolder.slsInvoice.grossSPPreTax = 0;
            self.slsInvoiceHolder.slsInvoice.netSPPreTax = 0;
            self.slsInvoiceHolder.slsInvoice.vatAmount = 0;
            self.slsInvoiceHolder.slsInvoice.netSPTaxIncl = 0;
            self.slsInvoiceHolder.slsInvoice.rebate = 0;
            self.slsInvoiceHolder.slsInvoice.netSalesAmt = 0;
            self.slsInvoiceHolder.slsInvoice.invceStatus = 'INITIATED';
            self.slsInvoiceItemHolder = {};
            self.showBtnClose = true;
        }
        function ModalInstanceAddBptrnCtrl($scope, $modalInstance, slsInvcePtnrsHolder) {
             $scope.slsInvcePtnrsHolder = slsInvcePtnrsHolder;
            $scope.loadBusinessPartner = function(val){
                return self.loadBusinessPartner(val);
            }
            $scope.roleInInvces = self.ptnrRole;
            $scope.addBptrn = function(){
                var slsInvcePtnrHolder = {};
                slsInvcePtnrHolder.slsInvcePtnr = $scope.slsInvcePtnr;
                console.log(self.slsInvoiceHolder.slsInvoice.invceNbr);
                slsInvcePtnrHolder.slsInvcePtnr.invceNbr = self.slsInvoiceHolder.slsInvoice.invceNbr;
                $scope.slsInvcePtnrsHolder.push(slsInvcePtnrHolder);
                $scope.slsInvcePtnr = {};

            }
            $scope.deleteItem = function (index) {
                $scope.slsInvcePtnrsHolder.splice(index,1);
            }

            $scope.cancel = function () {
                self.slsInvoiceHolder.slsInvcePtnrsHolder = $scope.slsInvcePtnrsHolder;
                $modalInstance.dismiss('cancel');
            };
            $scope.$on('modal.hide',function(){
                self.slsInvoiceHolder.slsInvcePtnrsHolder = $scope.slsInvcePtnrsHolder;
            });
        };

        function previous(){
            if(self.index > 0 ){
                if(self.slsInvoiceHolder){
                    if(self.slsInvoiceHolder.slsInvceItemsholder && self.slsInvoiceHolder.slsInvceItemsholder.length > 0){
                        self.slsInvoiceHolderTab.push(self.slsInvoiceHolder);
                    }
                }
                self.index-=1;
                angular.copy(self.slsInvoiceHolderTab[self.index],self.slsInvoiceHolder);
                self.slsInvoiceHolderTab.splice(self.index,1);
                self.slsInvoiceItemHolder = {};
                enableCloseCmd ();
                console.log(self.index);
                console.log('lenght tab '+self.slsInvoiceHolderTab.length);
            }

        }
        function next(){
            if(self.slsInvoiceHolderTab.length > self.index){
                if(self.slsInvoiceHolder){
                    if(self.slsInvoiceHolder.slsInvceItemsholder && self.slsInvoiceHolder.slsInvceItemsholder.length > 0){
                        self.slsInvoiceHolderTab.push(self.slsInvoiceHolder);
                    }
                }
                self.index+=1;
                angular.copy(self.slsInvoiceHolderTab[self.index],self.slsInvoiceHolder);
                self.slsInvoiceHolderTab.splice(self.index,1);
                self.slsInvoiceItemHolder = {};
                enableCloseCmd ();
                console.log(self.index);
                console.log('lenght tab '+self.slsInvoiceHolderTab.length);
            }

        }
        function tabLength (){
            return self.slsInvoiceHolderTab.length;
        }

        function enableCloseCmd (){
            if(self.slsInvoiceHolder.slsInvceItemsholder && self.slsInvoiceHolder.slsInvceItemsholder.length > 0)
                self.showBtnClose = false;
            else
                self.showBtnClose = true;
        }

}]);
