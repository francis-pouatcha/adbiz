'use strict';

angular
.module("adaptmt")

.controller(
		'aptAptmtsController',
		[
		 '$scope',
		 'genericResource',
		 '$translate',
		 'aptAptmtsService',
		 '$location',
		 '$rootScope',
		 '$filter',
		 function($scope, genericResource, $translate,
				 aptAptmtsService, $location, $rootScope,
				 $filter) {

			 var self = this;

			 self.error = [];
			 self.searchInput = {};
			 self.aptAptmts = [];
			 self.totalItems = 0;
			 self.aptAptmtsearchResults = {};
			 self.itemPerPage = 25;
			 self.currentPage = 1;
			 self.maxSize = 5;
			 self.handleSearchRequestEvent = handleSearchRequestEvent;
			 self.handlePrintRequestEvent = handlePrintRequestEvent;
			 self.paginate = paginate;
			 self.logins = [];

			 function init() {

				 self.searchInput = {
						 entity : {},
						 fieldNames : [],
						 start : 0,
						 max : $scope.itemPerPage
				 }

				 aptAptmtsService
				 .loadAptAptmts(self.searchInput)
				 .then(
						 function(entitySearchResult) {
							 self.aptAptmts = entitySearchResult.resultList;
							 self.totalItems = entitySearchResult.count;
							 // console.log("
							 // appointmentDate : " +
							 // $filter('date')(self.aptAptmts[2].appointmentDate,
							 // 'medium', 'GMT') + "
							 // creationDate : " +
							 // self.aptAptmts[2].creationDate);
						 });

				 console.log(self.totalItems);

			 }
			 ;

			 init();

			 function findCustom(searchInput) {
				 aptAptmtsService
				 .loadAptAptmts(searchInput)
				 .then(
						 function(entitySearchResult) {
							 self.aptAptmts = entitySearchResult.resultList;
							 self.totalItems = entitySearchResult.count;
						 });
			 }

			 function processSearchInput() {
				 var fieldNames = [];
				 if (self.searchInput.entity.title) {
					 fieldNames.push('title');
				 }
				 if (self.searchInput.entity.description) {
					 fieldNames.push('description');
				 }
				 if (self.searchInput.entity.createdUserId) {
					 fieldNames.push('createdUserId');
				 }
				 if (self.searchInput.entity.closedUserId) {
					 fieldNames.push('closedUserId');
				 }
				 if (self.searchInput.entity.appointmentDate) {
					 fieldNames.push('appointmentDate');
				 }
				 self.searchInput.fieldNames = fieldNames;
				 return self.searchInput;
			 }
			 ;

			 function handleSearchRequestEvent() {
				 processSearchInput();
				 findCustom(self.searchInput);
			 }
			 ;

			 function paginate() {
				 self.searchInput.start = ((self.currentPage - 1) * self.itemPerPage);
				 self.searchInput.max = self.itemPerPage;
				 findByLike(self.searchInput);
			 }
			 ;

			 function handlePrintRequestEvent() {

			 }

		 } ])

		 .controller(
				 'aptAptmtCreateCtrl',
				 [
				  '$scope',
				  '$translate',
				  'genericResource',
				  '$location',
				  'aptAptmtBsPtnrService',
				  '$q',
				  'aptAptmtsService',
				  'aptAptmtContactsService',
				  '$filter',
				  'bpBnsPtnrUtils',
				  'bpBnsPtnrState',
				  '$rootScope',
				  function($scope, $translate, genericResource,
						  $location, aptAptmtBsPtnrService, $q,
						  aptAptmtsService, aptAptmtContactsService,
						  $filter, bpBnsPtnrUtils, bpBnsPtnrState,
						  $rootScope) {

					  var self = this;
					  $scope.aptAptmts = {};
					  self.aptAptmts = {};
					  self.searchInput = {};
					  self.error = {};
					  self.create= create;
					  $scope.aptAptmtsService = aptAptmtsService;
					  $scope.aptAptmtBsPtnrService = aptAptmtBsPtnrService;
					  $scope.aptAptmtContactsService = aptAptmtContactsService;
					  self.OrgUnit = {};
					  var currentDate;


					  function init() {

						  self.searchInput = {
								  entity : {},
								  fieldNames : [],
								  start : 0,
								  max : $scope.itemPerPage
						  }

						  aptAptmtsService
						  .loadAptAptmts(self.searchInput)
						  .then(
								  function(entitySearchResult) {
									  self.aptAptmts = entitySearchResult.resultList;
								  });

						  aptAptmtsService.getCurrentOrgUnit().then(
								  function(result) {

									  console.log(result);
									  self.OrgUnit = result;
									  

								  });

					  }
					  console.log(self.OrgUnit.fullName);

					  init();

					  function create() {
						  console.log(" appointmentDate : "
								  + $scope.aptAptmts.appointmentDate);

						  aptAptmtsService.create($scope.aptAptmts).then(
								  function(result) {
									  self.aptAptmts.id = result.id;
									  console.log(self.aptAptmts.id);
									  $location.path('/aptaptmt/show/'+result.id);
								  }, function(error) {
									  $scope.error = error;
								  });

					  }
					  ;

					  $scope
					  .$watch(
							  function() {
								  return $scope.aptAptmts.appointmentDate;
							  },
							  function(newValue, oldValue) {
								  currentDate = new Date();

								  if ($scope.aptAptmts.appointmentDate <= currentDate) {
									  $scope.aptAptmts.appointmentDate = '';
									  console
									  .log("the appointment Date begin cannot be before the current date");
								  }

								  if ($scope.aptAptmts.appointmentDate >= $scope.aptAptmts.appointmentDateEnd) {
									  $scope.aptAptmts.appointmentDate = '';
									  console
									  .log("the appointment Date begin cannot be after the appointment Date end ");
								  }



							  });

					  $scope
					  .$watch(
							  function() {
								  return $scope.aptAptmts.appointmentDateEnd;
							  },
							  function(newValue, oldValue) {
								  currentDate = new Date();

								  if ($scope.aptAptmts.appointmentDateEnd <= currentDate) {
									  $scope.aptAptmts.appointmentDateEnd = '';
									  console
									  .log("the appointment Date end cannot be before the current date");
								  }

								  if ($scope.aptAptmts.appointmentDate >= $scope.aptAptmts.appointmentDateEnd) {
									  $scope.aptAptmts.appointmentDateEnd = '';
									  console
									  .log("the appointment Date begin cannot be after the appointment Date end ");
								  }



							  });



				  } ]);




