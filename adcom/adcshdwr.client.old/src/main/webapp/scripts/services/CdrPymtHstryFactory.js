angular.module('AdCshdwr').factory('CdrPymtHstryResource', function($resource){
    var resource = $resource('rest/cdrpymthstrys/:CdrPymtHstryId',{CdrPymtHstryId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});