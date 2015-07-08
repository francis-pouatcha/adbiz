angular.module('AdSales').factory('SlsAcctResource', function($resource){
    var resource = $resource('rest/slsaccts/:SlsAcctId',{SlsAcctId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});