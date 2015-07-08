angular.module('AdCshdwr').factory('CdrPymntItemResource', function($resource){
    var resource = $resource('rest/cdrpymntitems/:CdrPymntItemId',{CdrPymntItemId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});