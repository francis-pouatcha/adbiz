'use strict';

angular.module('AdStock')
.config(['$routeProvider',
         function($routeProvider) {
    $routeProvider
//      .when('/',{templateUrl:'views/StkArticleLot/StkArticleLots.html',controller:'stkArticleLotsCtlr'})
      .when('/StkArticleLots',{templateUrl:'views/StkArticleLot/StkArticleLots.html',controller:'stkArticleLotsCtlr'})
      .when('/StkArticleLots/show',{templateUrl:'views/StkArticleLot/StkArticleLotShow.html',controller:'stkArticleLotShowCtlr'})
}])
