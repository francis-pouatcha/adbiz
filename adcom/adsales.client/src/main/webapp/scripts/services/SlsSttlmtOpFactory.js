angular.module('AdSales').factory('SlsSttlmtOpResource', function($resource){
    var resource = $resource('rest/slssttlmtops/:SlsSttlmtOpId',{SlsSttlmtOpId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});