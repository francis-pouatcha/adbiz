angular.module('AdSales').factory('SlsInvcePtnrResource', function($resource){
    var resource = $resource('rest/slsinvceptnrs/:SlsInvcePtnrId',{SlsInvcePtnrId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});