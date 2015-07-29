'use strict';

angular.module("adaptmt")

.controller('aptAptmtRepportShowController',['$scope','genericResource', '$translate', 'aptAptmtRepportsService','aptAptmtsService','$location','$routeParams',
                                             function($scope,genericResource, $translate, aptAptmtRepportsService,aptAptmtsService,$location,$routeParams){

	var self = this;
	self.aptAptmtRepport = {};
	self.aptAptmts = {};
	self.show = show;
	self.previous = previous;
	self.next = next;

	function show(){

		var identif = $routeParams.id;

		aptAptmtRepportsService.loadAptAptmtRepport(identif).then(function(result){

			self.aptAptmtRepport = result;


		})

	};

	function init(){
		show();
		

	};

	init();

	function previous(){
		self.error = "";
		aptAptmtRepportsService.previousAptAptmtRepport(self.aptAptmtRepport.id).then(function(result){
			self.aptAptmtRepport = result;
		},function(error){
			self.error = error;
		})

	}

	function next(){
		self.error = "";
		aptAptmtRepportsService.nextAptAptmtRepport(self.aptAptmtRepport.id).then(function(result){
			self.aptAptmtRepport = result;
		},function(error){
			self.error = error;
		})

	}


}]);