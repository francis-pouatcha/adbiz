angular.module('AdSales').factory('SlsRoleInSalesResource', function($resource){
    var resource = $resource('rest/slsroleinsaless/:SlsRoleInSalesId',{SlsRoleInSalesId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});