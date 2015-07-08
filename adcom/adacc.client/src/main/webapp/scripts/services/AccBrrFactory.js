angular.module('AdAcc').factory('AccBrrResource', function($resource){
    var resource = $resource('rest/accbrrs/:AccBrrId',{AccBrrId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});