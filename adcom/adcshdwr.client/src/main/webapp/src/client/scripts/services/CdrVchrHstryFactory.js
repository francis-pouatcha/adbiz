angular.module('AdCshdwr').factory('CdrVchrHstryResource', function($resource){
    var resource = $resource('rest/cdrvchrhstrys/:CdrVchrHstryId',{CdrVchrHstryId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});