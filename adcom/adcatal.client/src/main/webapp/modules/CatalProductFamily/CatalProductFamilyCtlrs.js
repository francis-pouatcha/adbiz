'use strict';
    
angular.module('AdCatal')

.controller('catalProductFamiliesCtlr',['$scope','catalProductFamilyResource',function($scope,catalProductFamilyResource){
	
    var self = this ;
    $scope.catalProductFamiliesCtlr = self;

    self.searchInput = {
        entity:{
        	features:{}
        },
        fieldNames:[],
        start:0,
        max:self.itemPerPage
    };
    self.totalItems ;
    self.itemPerPage=25;
    self.currentPage = 1;
    self.maxSize = 5 ;
    self.catalProductFamilies = [];
    self.searchEntity = {};
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.handleSearchRequestEvent = handleSearchRequestEvent;
    self.handlePrintRequestEvent = handlePrintRequestEvent;
    self.paginate = paginate;
    self.error = "";
    
    init();

    function init(){
        self.searchInput = {
            entity:{
            	features:{}
            },
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
        findCustom(self.searchInput);
    }

    function findCustom(searchInput){
    	catalProductFamilyResource.findCustom(searchInput)
    		.success(function(entitySearchResult) {
	            self.catalProductFamilies = entitySearchResult.resultList;
	            self.totalItems = entitySearchResult.count ;
    		});
    }

    function processSearchInput(){
        var fieldNames = [];
        if(self.searchInput.entity.features.familyName){
        	fieldNames.push('features.familyName') ;
        }
        if(self.searchInput.entity.famCode){
        	fieldNames.push('famCode') ;
        }
        if(self.searchInput.entity.features.purpose){
        	fieldNames.push('features.purpose') ;
        }
        self.searchInput.fieldNames = fieldNames ;
        return self.searchInput ;
    };

    function  handleSearchRequestEvent(){
    	processSearchInput();
    	findCustom(self.searchInput);
    };

    function paginate(){
    	self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
    	self.searchInput.max = self.itemPerPage ;
    	findCustom(self.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
}])
.controller('catalProductFamilyCreateCtlr',['$scope','catalProductFamilyResource','$location','$modal',
                                            function($scope,catalProductFamilyResource,$location,$modal){
	var self = this ;
    $scope.catalProductFamilyCreateCtlr = self;
    self.catalProductFamily = {};
    self.create = create;
    self.cancel = cancel;
    self.error = "";
    self.ModalParentSelectCtlr=ModalParentSelectCtlr;
    self.openSelectParentForm=openSelectParentForm;
        
    function create(){
    	catalProductFamilyResource.create(self.catalProductFamily)
    	.success(function(data){
    		$location.path('/CatalProductFamilies/show/'+data.identif);
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };

    function cancel() {
    	$location.path('/CatalProductFamilies');
    };

    function openSelectParentForm(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/CatalProductFamily/CatalParentFamilySearchModal.html',
            controller: self.ModalParentSelectCtlr,
            size: size,
            resolve:{
            	catalProductFamily: function(){
                    return self.catalProductFamily;
                }
            }
        });
    };

    function ModalParentSelectCtlr($scope, $modalInstance,catalProductFamily) {
        $scope.catalParentFamilies = [];
        $scope.searchEntity = {};
        $scope.error = "";
        $scope.searchInput = {
            entity:{
            	features:{}
            },
            fieldNames:[],
            start:0,
            max:10
        };

        $scope.processSearchInput = function(){
            var fieldNames = [];
            if($scope.searchInput.entity.features.familyName){
            	fieldNames.push('features.familyName') ;
            }
            if($scope.searchInput.entity.famCode){
            	fieldNames.push('famCode') ;
            }
            if($scope.searchInput.entity.features.purpose){
            	fieldNames.push('features.purpose') ;
            }
            $scope.searchInput.fieldNames = fieldNames ;
            return $scope.searchInput ;
        };

        $scope.handleSearchRequestEvent = function(){
        	catalProductFamilyResource.findCustom($scope.processSearchInput($scope.searchInput))
        		.success(function(entitySearchResult) {
        			$scope.catalParentFamilies = entitySearchResult.resultList;
        		})
        		.error(function(error){
        			$scope.error=error;
        		});
        };
        $scope.select = function (parentFamily) {
        	catalProductFamily.parent=parentFamily;
        	catalProductFamily.parentIdentif=catalProductFamily.parent.famCode;
            $modalInstance.close('ok');
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }
}])
.controller('catalProductFamilyShowCtlr',['$scope','catalProductFamilyResource','$routeParams',
                                          function($scope,catalProductFamilyResource,$routeParams){
    var self = this ;
    $scope.catalProductFamilyShowCtlr = self;
    self.catalProductFamily = {};
    self.error = "";
    self.previousProductFamily = previousProductFamily;
    self.nextProductFamily = nextProductFamily;
	self.showParent;

    load();

    function load(){
        var famCode = $routeParams.famCode;
        catalProductFamilyResource.findByIdentif(famCode)
        .success(function(data){
            self.catalProductFamily = data;
            self.showParent = self.catalProductFamily.parent && self.catalProductFamily.parent.features;
        })
        .error(function(error){
            self.error = error;
        });
    };

    function previousProductFamily(){

    }
    function nextProductFamily(){
    }
    
}]);
