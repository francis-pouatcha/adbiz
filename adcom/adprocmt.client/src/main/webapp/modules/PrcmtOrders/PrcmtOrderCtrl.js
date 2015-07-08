'use strict';
    
angular.module('AdProcmt')
.factory('PrcmtOrderState',[function(){
        var service = {};
        var prcmtOrderHolder = {};

        service.setOrderHolder = function(OrderHolder){
            prcmtOrderHolder = OrderHolder;
        }
        service.getOrderHolder = function(){
            return prcmtOrderHolder;
        }

        return service;
}])
.controller('prcmtOrderCtrl',['$scope','$location','ProcmtUtils','genericResource','PrcmtOrderState',function($scope,$location,ProcmtUtils,genericResource,PrcmtOrderState){
	
    var self = this ;
    $scope.prcmtOrderCtrl = self;

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
    self.maxSize = 5 ;
    self.prcmtOrders = [];
    self.searchEntity = {};
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.handleSearchRequestEvent = handleSearchRequestEvent;
    self.handlePrintRequestEvent = handlePrintRequestEvent;
    self.paginate = paginate;
    self.error = "";
    self.showPage = show;
    self.showEdit = showEdit;
    
    init();

    function init(){
        self.searchInput = {
            entity:{
            },
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
        findByLike(self.searchInput);
    }

    function findByLike(searchInput){
        genericResource.findCustom(ProcmtUtils.urlPrcmtOrder,searchInput)
    		.success(function(entitySearchResult) {
	            self.prcmtOrders = entitySearchResult.resultList;
	            self.totalItems = entitySearchResult.count ;
    		});
    }


    function  handleSearchRequestEvent(){
        findByLike(self.searchInput);
    };

    function paginate(){
    	self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
    	self.searchInput.max = self.itemPerPage ;
        findByLike(self.searchInput);
    };

        function show(prcmtOrder) {
            genericResource.findById(ProcmtUtils.urlManageOrder,prcmtOrder.id)
                .success(function(data){
                    PrcmtOrderState.setOrderHolder(data);
                    $location.path('/PrcmtOrders/show');
                })
        }

        function showEdit(val){
            if(val == 'ONGOING'){
                return true;
            }else{
                return false;
            }
        }
	function handlePrintRequestEvent(){		
	}
    
}])
.controller('prcmtOrderCreateCtlr',['$scope','$location','$q','ProcmtUtils','genericResource','PrcmtOrderState',function($scope,$location,$q,ProcmtUtils,genericResource,PrcmtOrderState){
	var self = this ;
    $scope.prcmtOrderCreateCtlr = self;
    self.prcmtOrder = {};
    self.create = create;
    self.error = "";
    self.loadBusinessPartner = loadBusinessPartner;
    self.loadOrgUnit = loadOrgUnit;
    self.currencys = ['XAF','EUR','NGN','USD'];
    self.triggerModes = [];
    self.orderTypes = [];

        loadTriggerMode();
        loadOrderType();

        function loadTriggerMode(){
            genericResource.listAll(ProcmtUtils.urlPrcmtOrder+'/listAllTriggerMode').success(function(data){
                self.triggerModes = data;
            })
        }
        function loadOrderType(){
            genericResource.listAll(ProcmtUtils.urlPrcmtOrder+'/listAllPOType').success(function(data){
                self.orderTypes = data;
            })
        }

        function loadBusinessPartner(val){
            return loadBusinessPartnerPromise(val).then(function(entitySearchResult){
                return entitySearchResult.resultList;
            })
        }

        function loadBusinessPartnerPromise(businessPartnerName){
            var searchInput = {
                entity:{},
                fieldNames:[],
                start: 0,
                max: 10
            };
            if(businessPartnerName){
                searchInput.entity.cpnyName = businessPartnerName+'%';
                searchInput.fieldNames.push('cpnyName');
            }
            var deferred = $q.defer();
            genericResource.findByLike(ProcmtUtils.adbnsptnr,searchInput).success(function (entitySearchResult) {
                deferred.resolve(entitySearchResult);
            }).error(function(){
                deferred.reject("No Manufacturer/Supplier");
            });
            return deferred.promise;
        }

        function loadOrgUnit(val){
            return loadOrgUnitPromise(val).then(function(entitySearchResult){
                return entitySearchResult.resultList;
            })
        }

        function loadOrgUnitPromise(val){
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
            var deferred = $q.defer();
            genericResource.findByLike(ProcmtUtils.orgunits,searchInput).success(function (entitySearchResult) {
                deferred.resolve(entitySearchResult);
            }).error(function(){
                deferred.reject("No organisation unit");
            });
            return deferred.promise;
        }

    function create(){
        if(self.prcmtOrder.supplier)
            self.prcmtOrder.supplier = self.prcmtOrder.supplier.ptnrNbr;
        if(self.prcmtOrder.ordrngOrgUnit)
            self.prcmtOrder.ordrngOrgUnit = self.prcmtOrder.ordrngOrgUnit.identif;
        genericResource.customMethod(ProcmtUtils.urlManageOrder+'/create',self.prcmtOrder)
    	.success(function(data){
            PrcmtOrderState.setOrderHolder(data);
    		$location.path('/PrcmtOrders/show');
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
	
}])
.controller('prcmtOrderEditCtlr',['$scope','$routeParams','$location','ProcmtUtils','genericResource',function($scope,$routeParams,$location,ProcmtUtils,genericResource){
    var self = this ;
    $scope.prcmtOrderEditCtlr = self;
    self.prcmtOrder = {};
    self.update = update;
    self.error = "";
        self.loadBusinessPartner = loadBusinessPartner;
        self.loadOrgUnit = loadOrgUnit;
        self.currencys = ['XAF','EUR','NGN','USD'];
        self.triggerModes = [];
        self.orderTypes = [];

    function update(){
        genericResource.update(ProcmtUtils.urlPrcmtOrder,self.prcmtOrder)
    	.success(function(data){
            $location.path('/PrcmtOrders/show/'+data.id);
        })
    	.error(function(error){
            self.error = error;
        });
    };

    init();

    function init(){
        load();
    }
    function load(){
        var identif = $routeParams.identif;
        genericResource.findById(ProcmtUtils.urlPrcmtOrder,identif)
        .success(function(data){
            self.prcmtOrder = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

        loadTriggerMode();
        loadOrderType();

        function loadTriggerMode(){
            genericResource.listAll(ProcmtUtils.urlPrcmtOrder+'/listAllTriggerMode').success(function(data){
                self.triggerModes = data;
            })
        }
        function loadOrderType(){
            genericResource.listAll(ProcmtUtils.urlPrcmtOrder+'/listAllPOType').success(function(data){
                self.orderTypes = data;
            })
        }

        function loadBusinessPartner(val){
            return loadBusinessPartnerPromise(val).then(function(entitySearchResult){
                return entitySearchResult.resultList;
            })
        }

        function loadBusinessPartnerPromise(businessPartnerName){
            var searchInput = {
                entity:{},
                fieldNames:[],
                start: 0,
                max: 10
            };
            if(businessPartnerName){
                searchInput.entity.cpnyName = businessPartnerName+'%';
                searchInput.fieldNames.push('cpnyName');
            }
            var deferred = $q.defer();
            genericResource.findByLike(ProcmtUtils.adbnsptnr,searchInput).success(function (entitySearchResult) {
                deferred.resolve(entitySearchResult);
            }).error(function(){
                deferred.reject("No Manufacturer/Supplier");
            });
            return deferred.promise;
        }

        function loadOrgUnit(val){
            return loadOrgUnitPromise(val).then(function(entitySearchResult){
                return entitySearchResult.resultList;
            })
        }

        function loadOrgUnitPromise(val){
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
            var deferred = $q.defer();
            genericResource.findByLike(ProcmtUtils.orgunits,searchInput).success(function (entitySearchResult) {
                deferred.resolve(entitySearchResult);
            }).error(function(){
                deferred.reject("No organisation unit");
            });
            return deferred.promise;
        }

    }]);
