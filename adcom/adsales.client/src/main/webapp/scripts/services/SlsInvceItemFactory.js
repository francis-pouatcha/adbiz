angular.module('AdSales').factory('SlsInvceItemResource', function($resource){
    var resource = $resource('rest/slsinvceitems/:SlsInvceItemId',{SlsInvceItemId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});