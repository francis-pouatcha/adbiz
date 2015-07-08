'use strict';

angular.module('AdStock')
.config(['$routeProvider',
         function($routeProvider) {
    $routeProvider
      .when('/StkMvnts',{templateUrl:'views/StkMvnt/StkMvnts.html',controller:'stkMvntsCtlr'})
      .when('/StkMvnts/show',{templateUrl:'views/StkMvnt/StkMvntShow.html',controller:'stkMvntShowCtlr'});
}])
