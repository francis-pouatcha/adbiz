'use strict';

angular.module('AdCatal')
.config(['$routeProvider',
         function($routeProvider) {
    $routeProvider
      .when('/CatalProductFamilies/new',{templateUrl:'views/CatalProductFamily/CatalProductFamilyCreate.html',controller:'catalProductFamilyCreateCtlr'})
      .when('/CatalProductFamilies',{templateUrl:'views/CatalProductFamily/CatalProductFamilies.html',controller:'catalProductFamiliesCtlr'})
      .when('/CatalProductFamilies/show/:famCode',{templateUrl:'views/CatalProductFamily/CatalProductFamilyShow.html',controller:'catalProductFamilyShowCtlr'});
}])
