angular.module('AdCshdwr').factory('CdrDsPymntItemResource', function($resource){
    var resource = $resource('rest/cdrdspymntitems/:CdrDsPymntItemId',{CdrDsPymntItemId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});