'use strict';

angular.module('AdBnsptnr')
.config(['$routeProvider',
         function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/BpBnsPtnr/BpBnsPtnrs.html',controller:'bpBnsPtnrsCtlr'})
      .when('/BpBnsPtnrs/new',{templateUrl:'views/BpBnsPtnr/BpBnsPtnrCreate.html',controller:'bpBnsPtnrCreateCtlr'})
      .when('/BpBnsPtnrs',{templateUrl:'views/BpBnsPtnr/BpBnsPtnrs.html',controller:'bpBnsPtnrsCtlr'})
      .when('/BpBnsPtnrs/show',{templateUrl:'views/BpBnsPtnr/BpBnsPtnrShow.html',controller:'bpBnsPtnrShowCtlr'})
      .when('/BpBnsPtnrs/edit',{templateUrl:'views/BpBnsPtnr/BpBnsPtnrEdit.html',controller:'bpBnsPtnrEditCtlr'});
}])
