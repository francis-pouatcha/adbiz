angular.module('AdSales').factory('SlsSalesOrderResource', function($resource){
    var resource = $resource('rest/slssalesorders/:SlsSalesOrderId',{SlsSalesOrderId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});