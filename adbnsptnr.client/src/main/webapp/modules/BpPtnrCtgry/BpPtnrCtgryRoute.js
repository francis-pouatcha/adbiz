'use strict';

angular.module('AdBnsptnr')
.config(['$routeProvider',
         function($routeProvider) {
    $routeProvider
      .when('/BpPtnrCtgrys/new',{templateUrl:'views/BpPtnrCtgry/BpPtnrCtgryCreate.html',controller:'bpPtnrCtgryCreateCtlr'})
      .when('/BpPtnrCtgrys',{templateUrl:'views/BpPtnrCtgry/BpPtnrCtgrys.html',controller:'bpPtnrCtgrysCtlr'})
      .when('/BpPtnrCtgrys/show',{templateUrl:'views/BpPtnrCtgry/BpPtnrCtgryShow.html',controller:'bpPtnrCtgryShowCtlr'})
      .when('/BpPtnrCtgrys/edit',{templateUrl:'views/BpPtnrCtgry/BpPtnrCtgryEdit.html',controller:'bpPtnrCtgryEditCtlr'});
}])
