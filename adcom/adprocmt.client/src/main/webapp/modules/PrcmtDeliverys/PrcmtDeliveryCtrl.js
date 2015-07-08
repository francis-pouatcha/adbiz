'use strict';
    
angular.module('AdProcmt')
.factory('ProcmtUtils',['genericResource','$q',function(genericResource,$q){
        var service = {};

        service.urlBase='/adprocmt.server/rest/prcmtdeliverys';
        service.urlManagerDelivery='/adprocmt.server/rest/delivery';
        service.adbnsptnr='/adbnsptnr.server/rest/bplegalptnrids';
        service.catalarticles='/adcatal.server/rest/catalarticles';
        service.orgunits='/adbase.server/rest/orgunits';
        service.stkSection = '/adstock.server/rest/stksections';
        service.stkArtlot2strgsctnsUrlBase = '/adstock.server/rest/stkarticlelot2strgsctns';
        service.urlPrcmtOrder='/adprocmt.server/rest/prcmtprocorders';
        service.urlManageOrder='/adprocmt.server/rest/order';
        service.urlpoitems = '/adprocmt.server/rest/prcmtpoitems';

        return service;
}])
.factory('PrcmtDeliveryState',[function(){
        var service = {};
        var prcmtDeliveryHolder = {};

        service.setDeliveryHolder = function(DeliveryHolder){
            prcmtDeliveryHolder = DeliveryHolder;
        }
        service.getDeliveryHolder = function(){
            return prcmtDeliveryHolder;
        }

        return service;
}])
.controller('prcmtDeliveryCtrl',['$scope','$location','ProcmtUtils','genericResource','PrcmtDeliveryState',function($scope,$location,ProcmtUtils,genericResource,PrcmtDeliveryState){
	
    var self = this ;
    $scope.prcmtDeliveryCtrl = self;

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
    self.prcmtDeliverys = [];
    self.searchEntity = {};
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.handleSearchRequestEvent = handleSearchRequestEvent;
    self.handlePrintRequestEvent = handlePrintRequestEvent;
    self.paginate = paginate;
    self.error = "";
    self.showEdit = showEdit;
    self.showDelivery = showDelivery;
    
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
        genericResource.findCustom(ProcmtUtils.urlBase,searchInput)
    		.success(function(entitySearchResult) {
	            self.prcmtDeliverys = entitySearchResult.resultList;
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

	function handlePrintRequestEvent(){		
	}

        function showEdit(val){
            if(val == 'ONGOING'){
                return true;
            }else{
                return false;
            }
        }
        function showDelivery(prcmtDelivery) {
            genericResource.findById(ProcmtUtils.urlManagerDelivery,prcmtDelivery.identif)
                .success(function(data){
                    PrcmtDeliveryState.setDeliveryHolder(data);
                    $location.path('/PrcmtDeliverys/addItem');
                })
        }

}])
.controller('prcmtDeliveryCreateCtlr',['$scope','$location','$q','ProcmtUtils','genericResource',function($scope,$location,$q,ProcmtUtils,genericResource){
	var self = this ;
    $scope.prcmtDeliveryCreateCtlr = self;
    self.prcmtDelivery = {};
    self.create = create;
    self.error = "";
    self.loadBusinessPartner = loadBusinessPartner;
    self.loadOrgUnit = loadOrgUnit;
    self.currencys = ['XAF','EUR','NGN','USD'];

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
        self.prcmtDelivery.dlvryStatus = 'ONGOING';
        genericResource.create(ProcmtUtils.urlBase ,self.prcmtDelivery)
    	.success(function(data){
    		$location.path('/PrcmtDeliverys/show/'+data.id);
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
	
}])
.controller('prcmtDeliveryEditCtlr',['$scope','ProcmtUtils','$q','genericResource','$routeParams','$location',function($scope ,ProcmtUtils,$q,genericResource,$routeParams,$location){
    var self = this ;
    $scope.prcmtDeliveryEditCtlr = self;
    self.prcmtDelivery = {};
    self.update = update;
    self.error = "";
    self.loadBusinessPartner = loadBusinessPartner;

    function update(){
        genericResource.update(ProcmtUtils.urlBase,self.prcmtDelivery)
    	.success(function(data){
            $location.path('/PrcmtDeliverys/show/'+data.id);
        })
    	.error(function(error){
            self.error = "Delivery no fount";
        });
    };

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

    init();

    function init(){
        load();
    }

    function load(){
        var identif = $routeParams.identif;
        genericResource.findById(ProcmtUtils.urlBase,identif)
        .success(function(data){
            self.prcmtDelivery = data;
        })
        .error(function(error){
            self.error = "Delivery no fount";
        });
    };

}]);
