'use strict';

angular.module('AdStock')
.config(['$routeProvider',
         function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/StkSection/StkSections.html',controller:'stkSectionsCtlr'})
      .when('/StkSections/new',{templateUrl:'views/StkSection/StkSectionCreate.html',controller:'stkSectionCreateCtlr'})
      .when('/StkSections',{templateUrl:'views/StkSection/StkSections.html',controller:'stkSectionsCtlr'})
      .when('/StkSections/show',{templateUrl:'views/StkSection/StkSectionShow.html',controller:'stkSectionShowCtlr'})
      .when('/StkSections/edit',{templateUrl:'views/StkSection/StkSectionEdit.html',controller:'stkSectionEditCtlr'});
}])
