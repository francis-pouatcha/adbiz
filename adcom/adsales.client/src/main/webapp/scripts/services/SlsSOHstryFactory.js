angular.module('AdSales').factory('SlsSOHstryResource', function($resource){
    var resource = $resource('rest/slssohstrys/:SlsSOHstryId',{SlsSOHstryId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});