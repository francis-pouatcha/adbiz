'use strict';
    
angular.module('AdCatal').controller('catalArtEquivalenceCtlr',['$scope','catalArtEquivalenceResource', 'catalArticleResource', '$modal', '$routeParams', function($scope,catalArtEquivalenceResource, catalArticleResource, $modal, $routeParams){
	
    var self = this ;
    $scope.catalArtEquivalenceCtlr = self;

    self.searchInput = {
    	entity:{},
    	fieldNames:[]
    };
    self.searchInputArticles ={
    		start:0,
            max:50
    }
    self.articles= [];
    self.equivalences= [];
    self.totalItems ;
    self.artIdentif;
    self.itemPerPage=25;
    self.currentPage = 1;
    self.maxSize = 5 ;
    self.searchEntity = {};
    self.selectedItem = {};
    self.openEditForm = openEditForm;
    self.openCreateForm = openCreateForm;
    self.deleteItem = deleteItem;
    self.ModalInstanceCreateCtrl = ModalInstanceCreateCtrl;
    self.ModalInstanceEditCtrl = ModalInstanceEditCtrl;
    self.selectedIndex ;
    self.paginate = paginate;
    self.error = "";
    
    init();

    function init(){
       self.artIdentif= $routeParams.pic;
       self.searchInput = {
    	   entity:{},
    	   fieldNames:[]
       };
       self.searchInputArticles ={
       		start:0,
            max:50
       }
        findBy(self.searchInput);
        findAll(self.searchInputArticles);
    };
    
    
    function findBy(searchInput){
    	searchInput.entity.mainArtIdentif= self.artIdentif;
    	searchInput.fieldNames.push('mainArtIdentif');
    	catalArtEquivalenceResource.findBy(searchInput)
    	.success(function(entitySearchResult){
    		self.equivalences= entitySearchResult.resultList;
    	})
    	.error(function(error){
	        self.error = error;
        });
    };
    
    
    function findAll(searchInput){
    	catalArticleResource.listAll(searchInput.start, searchInput.max)
    	.success(function(entitySearchResult){
    		self.articles = entitySearchResult.resultList;
    	})
    	.error(function(error){
    		self.error = error;
        });
    };
    
    
    function handleSelectedItem(index){
        index = index ? index : 0 ;
        self.selectedIndex = index ;
        angular.copy(self.equivalences[self.selectedIndex],self.selectedItem);
    };
    
    
    function deleteItem(index){
        handleSelectedItem(index);
        var deleteConfirm= window.confirm("Voulez-vous vraiment supprimer cette ligne?");
        if (deleteConfirm==true) {
        	catalArtEquivalenceResource.deleteById(self.selectedItem.id).success(function(){
        		init();
        	});
		}
    };
   
    
    function openCreateForm(size){
        var modalInstance= $modal.open({            
           templateUrl:
            'views/CatalArtEquivalence/CatalArtEquivalenceForm.html',
           controller: self.ModalInstanceCreateCtrl,
           size: size,
           resolve: {
        	   articles: function () {
                   return self.articles;
               }
           }
        });
    }
    
    function ModalInstanceCreateCtrl($scope, $modalInstance, articles) {
        $scope.formCreate = false;
        $scope.catalArtEquivalence={};
        $scope.catalArtEquivalence.mainArtIdentif = self.artIdentif;
        $scope.articles=articles;
        $scope.selectedCipEquiv=articles.length>0?articles[0]:null;
        $scope.currentAction="Entity_create.title";

        $scope.save = function () {
            $scope.catalArtEquivalence.mainArtIdentif = self.artIdentif;
            $scope.catalArtEquivalence.equivArtIdentif = $scope.selectedCipEquiv.features.artIdentif;
            catalArtEquivalenceResource.create($scope.catalArtEquivalence).success(function () {
                init();
            });
            $modalInstance.dismiss('cancel');
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

    };
    
    function openEditForm(size,index){
    	 handleSelectedItem(index);
        var modalInstance = $modal.open({
            templateUrl: 'views/CatalArtEquivalence/CatalArtEquivalenceForm.html',
            controller: self.ModalInstanceEditCtrl,
            size: size,
            resolve:{
            	catalArtEquivalence: function(){
                    return self.selectedItem;
                },
                articles: function(){
                    return self.articles;
                }
            }

        });
    };
    
    function ModalInstanceEditCtrl($scope, $modalInstance,catalArtEquivalence,articles) {
    	$scope.formCreate = false;
        $scope.catalArtEquivalence=catalArtEquivalence;
        $scope.currentAction="Entity_edit.title";
        $scope.articles=articles;
        $scope.selectedCipEquiv= function(){
            for (var i = 0; i < articles.length; i++) {
                if(articles[i].features.artIdentif==catalArtEquivalence.equivArtIdentif) return articles[i];
            }
            if(articles.length>0)return articles[0];
            return null;
        }();
        
        $scope.isClean = function() {
            return !angular.equals(catalArtEquivalence, $scope.catalArtEquivalence);
        };

        $scope.save = function () {
        	$scope.catalArtEquivalence.mainArtIdentif = self.artIdentif;
            $scope.catalArtEquivalence.equivArtIdentif = $scope.selectedCipEquiv.features.artIdentif;
            catalArtEquivalenceResource.update($scope.catalArtEquivalence).success(function () {
                init();
            });
            $modalInstance.dismiss('cancel');
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

    };
    
    function paginate(){
    	self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
    	self.searchInput.max = self.itemPerPage ;
    };

}]);
