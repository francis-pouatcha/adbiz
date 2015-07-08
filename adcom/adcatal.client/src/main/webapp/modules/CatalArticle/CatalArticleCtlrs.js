'use strict';
    
angular.module('AdCatal')

.controller('catalArticlesCtlr',['$scope','catalArticleResource',function($scope,catalArticleResource){
	
    var self = this ;
    $scope.catalArticlesCtlr = self;

    self.searchInput = {
        entity:{
        	features:{},
    		familyFeatures:{}
        },
        fieldNames:[],
        start:0,
        max:self.itemPerPage
    };
    self.totalItems ;
    self.itemPerPage=25;
    self.currentPage = 1;
    self.smallnumPages= 10;
    self.maxSize = 10 ;
    self.catalArticles = [];
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
            	features:{},
        		familyFeatures:{}
            },
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
        findCustom(self.searchInput);
    }

    function findCustom(searchInput){
    	catalArticleResource.findCustom(searchInput)
    		.success(function(entitySearchResult) {
	            self.catalArticles = entitySearchResult.resultList;
	            self.totalItems = entitySearchResult.count ;
    		});
    }

    function processSearchInput(){
        var fieldNames = [];
        if(self.searchInput.entity.features.artName){
        	fieldNames.push('features.artName') ;
        	self.searchInput.entity.features.langIso2='fr';
        }
        if(self.searchInput.entity.pic){
        	fieldNames.push('pic') ;
        }
        if(self.searchInput.entity.familyFeatures.familyName){
        	fieldNames.push('familyFeatures.familyName') ;
        	self.searchInput.entity.familyFeatures.langIso2='fr';
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
.controller('catalArticleCreateCtlr',['$scope','catalArticleResource', '$location' ,function($scope,catalArticleResource, $location){
	var self = this ;
    $scope.catalArticleCreateCtlr = self;
    self.catalArticle = {};
    self.create = create;
    self.error = "";

    function create(){
    	catalArticleResource.create(self.catalArticle)
    	.success(function(data){
    		$location.path('/CatalArticles/show/' + data.identif);
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
	
}])
.controller('catalArticleEditCtlr',['$scope','catalArticleResource','$routeParams','$location',function($scope,catalArticleResource,$routeParams,$location){
    var self = this ;
    $scope.catalArticleEditCtlr = self;
    self.catalArticle = {};
    self.update = update;
    self.error = "";

    function update(){
    	catalArticleResource.update(self.catalArticle)
    	.success(function(data){
            $location.path('/CatalArticles/show/'+data.identif);
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
        var pic = $routeParams.pic;
        catalArticleResource.findByIdentif(pic)
        .success(function(data){
            self.catalArticle = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

}])
.controller('catalArticleShowCtlr',['$scope','catalArticleResource','$routeParams','$location',function($scope,catalArticleResource,$routeParams,$location){
    var self = this ;
    $scope.catalArticleShowCtlr = self;
    self.catalArticle = {};
    self.error = "";
    self.previousArticle = previousArticle;
    self.nextArticle = nextArticle;

    load();

    function load(){
        var pic = $routeParams.pic;
        catalArticleResource.findByIdentif(pic)
        .success(function(data){
            self.catalArticle = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

    function previousArticle(pic){
        catalArticleResource.previous(pic).success(function(data){
            $location.path('/CatalArticles/show/'+data.identif);
        });
    }

    function nextArticle(pic){
        catalArticleResource.next(pic).success(function(data){
            $location.path('/CatalArticles/show/'+data.identif);
        });
    }

}]);
