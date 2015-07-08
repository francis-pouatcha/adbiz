angular.module('AdCshdwr').factory('CdrPymntObjectResource', function($resource){
    var resource = $resource('rest/cdrpymntobjects/:CdrPymntObjectId',{CdrPymntObjectId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});