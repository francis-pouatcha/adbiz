angular.module('AdCshdwr').factory('CdrDsArtItemResource', function($resource){
    var resource = $resource('rest/cdrdsartitems/:CdrDsArtItemId',{CdrDsArtItemId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});