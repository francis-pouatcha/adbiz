angular.module('AdSales').factory('SlsSOItemResource', function($resource){
    var resource = $resource('rest/slssoitems/:SlsSOItemId',{SlsSOItemId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});