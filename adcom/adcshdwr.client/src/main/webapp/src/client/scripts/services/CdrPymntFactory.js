angular.module('AdCshdwr').factory('CdrPymntResource', function($resource){
    var resource = $resource('rest/cdrpymnts/:CdrPymntId',{CdrPymntId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});