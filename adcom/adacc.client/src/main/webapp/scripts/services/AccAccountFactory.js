angular.module('AdAcc').factory('AccAccountResource', function($resource){
    var resource = $resource('rest/accaccounts/:AccAccountId',{AccAccountId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});