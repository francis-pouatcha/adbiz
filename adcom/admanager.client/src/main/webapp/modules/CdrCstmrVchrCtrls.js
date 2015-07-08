'use strict';

angular.module('Admanager')
    .factory('CdrCstmrVchrUtils', ['sessionManager', '$translate', 'genericResource', '$q', '$http', function (sessionManager, $translate, genericResource, $q, $http) {
        var service = {};

        service.cdrCstmr = '/adcshdwr.server/rest/cdrcstmrvchrs';
        service.login = '/adbase.server/rest/logins';
        service.language = sessionManager.language;
        service.currentWsUser = sessionManager.userWsData();
        	
        service.loadCdrCstmrVchr = function(value){
            return genericResource.findByLikePromissed(service.cdrCstmr, "vchrNbr", value)
                .then(function (entitySearchResult) {
                    return entitySearchResult.resultList;
                })
        }
        service.loadLogin = function(value){
            return genericResource.findByLikePromissed(service.login, "loginName", value)
                .then(function (entitySearchResult) {
                    return entitySearchResult.resultList;
                })
        }
        
        service.cancelCdrCstmrVchr = function(entity){
            var deferred = $q.defer();

            $http.put(service.cdrCstmr + '/cancel/' + entity.id, entity)
                .success(function(data){
                    deferred.resolve(data);
                }).error(function(data){
                    deferred.reject("Can not cancel CdrCstmrVchr !");
                });

            return deferred.promise;
        }

        service.translate = function () {
            $translate([
                    'CdrCstmrVchr_amtUsed_description.text',
                    'CdrCstmrVchr_amtUsed_description.title',
                    'CdrCstmrVchr_amt_description.text',
                    'CdrCstmrVchr_amt_description.title',
                    'CdrCstmrVchr_canceled_description.text',
                    'CdrCstmrVchr_canceled_description.title',
                    'CdrCstmrVchr_cashier_description.text',
                    'CdrCstmrVchr_cashier_description.title',
                    'CdrCstmrVchr_cdrNbr_description.text',
                    'CdrCstmrVchr_cdrNbr_description.title',
                    'CdrCstmrVchr_cstmrName_description.text',
                    'CdrCstmrVchr_cstmrName_description.title',
                    'CdrCstmrVchr_cstmrNbr_description.text',
                    'CdrCstmrVchr_cstmrNbr_description.title',
                    'CdrCstmrVchr_description.text',
                    'CdrCstmrVchr_description.title',
                    'CdrCstmrVchr_dsNbr_description.text',
                    'CdrCstmrVchr_dsNbr_description.title',
                    'CdrCstmrVchr_prntDt_description.text',
                    'CdrCstmrVchr_prntDt_description.title',
                    'CdrCstmrVchr_restAmt_description.text',
                    'CdrCstmrVchr_restAmt_description.title',
                    'CdrCstmrVchr_settled_description.text',
                    'CdrCstmrVchr_settled_description.title',
                    'CdrCstmrVchr_vchrNbr_description.text',
                    'CdrCstmrVchr_vchrNbr_description.title'
                 ])
                .then(function (translations) {
                    service.translations = translations;
                });
        };
        service.translate();
        return service;
}])
.controller('CdrCstmrVchrCtrls', ['$scope', 'genericResource', 'CdrCstmrVchrUtils', '$location', '$rootScope', 'commonTranslations','fileExtractor',
function ($scope, genericResource, CdrCstmrVchrUtils, $location, $rootScope, commonTranslations, fileExtractor) {

            $scope.searchInput = {};
            $scope.itemPerPage = 25;
            $scope.totalItems;
            $scope.currentPage = 1;
            $scope.maxSize = 5;
            $scope.cdrCstmrVchrs = [];
            $scope.selectedIndex  ;
            $scope.handleSearchRequestEvent = handleSearchRequestEvent;
            $scope.handlePrintRequestEvent = handlePrintRequestEvent;
            $scope.paginate = paginate;
            $scope.cancelCdrCstmrVchr = cancelCdrCstmrVchr;
            $scope.error = "";
            $scope.CdrCstmrVchrUtils = CdrCstmrVchrUtils;
            
            $scope.commonTranslations = commonTranslations;
            
            var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
            	CdrCstmrVchrUtils.translate();
            });

            $scope.$on('$destroy', function () {
                translateChangeSuccessHdl();
            });

            init();

    function findCustom(searchInput) {
        genericResource.findCustom(CdrCstmrVchrUtils.cdrCstmr, searchInput)
            .success(function (entitySearchResult) {
				$scope.cdrCstmrVchrs = entitySearchResult.resultList;
				console.log("that is the list of cdrCstmrVchrs : " + entitySearchResult + " or can be : " + $scope.cdrCstmrVchrs );
            })
            .error(function (error) {
                $scope.error = error;
            });
    }

	function handleSearchRequestEvent() {
	    var searchInput = {
	        entity: {},
	        fieldNames: [],
	        start: 0,
	        max: 25
	    };
	    if (angular.isDefined($scope.searchInput.entity.cashier ) && $scope.searchInput.entity.cashier ) {
	        searchInput.entity.cashier = $scope.searchInput.entity.cashier;
	    }
	    if($scope.searchInput.from)
	        searchInput.from = $scope.searchInput.from;
	
	    if($scope.searchInput.to)
	        searchInput.to = $scope.searchInput.to;
	
	    findCustom(searchInput);
	}

    function handlePrintRequestEvent() {
        genericResource.builfReport(CdrCstmrVchrUtils.cdrCstmr+'/voucherreport.pdf', $scope.searchInput).success(function(data){
            console.log(data);
            fileExtractor.extractFile(data,"application/pdf");
        }).error(function (error) {
            $scope.error = error;
        });
    }

    function paginate() {
//                $scope.searchInput = CshDrawerState.paginate();
//                findCustom($scope.searchInput);
    };

    function paginate() {
//                $scope.searchInput = CshDrawerState.paginate();
//                findCustom($scope.searchInput);
    }

    function init() {
    	
    	$scope.searchInput = {
                 entity:{},
                 fieldNames:[],
                 start:0,
                 max:self.itemPerPage
             }
    	 
        findCustom($scope.searchInput);
    }
    
    function cancelCdrCstmrVchr(item){
    	if(item.canceled === true){
    		console.log(' cannot reactivated CdrCstmrVchr ');
    	}

        if(item.canceled === false) {
            // item.canceled = true;
            CdrCstmrVchrUtils.cancelCdrCstmrVchr(item).then(function (result) {
                console.log(" canceled of -------------------> : " + item);
            })
        }
        init();
    }

}]);
  