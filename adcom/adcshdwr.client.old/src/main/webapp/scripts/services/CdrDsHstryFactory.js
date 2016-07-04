angular.module('AdCshdwr').factory('CdrDsHstryResource', function($resource){
    var resource = $resource('rest/cdrdshstrys/:CdrDsHstryId',{CdrDsHstryId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});