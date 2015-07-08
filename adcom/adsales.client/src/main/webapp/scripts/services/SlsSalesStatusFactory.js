angular.module('AdSales').factory('SlsSalesStatusResource', function($resource){
    var resource = $resource('rest/slssalesstatuss/:SlsSalesStatusId',{SlsSalesStatusId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});