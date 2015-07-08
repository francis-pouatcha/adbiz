angular.module('AdSales').factory('DynEnumResource', function($resource){
    var resource = $resource('rest/dynenums/:DynEnumId',{DynEnumId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});