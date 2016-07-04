angular.module('AdCshdwr').factory('CdrCstmrVchrResource', function($resource){
    var resource = $resource('rest/cdrcstmrvchrs/:CdrCstmrVchrId',{CdrCstmrVchrId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});