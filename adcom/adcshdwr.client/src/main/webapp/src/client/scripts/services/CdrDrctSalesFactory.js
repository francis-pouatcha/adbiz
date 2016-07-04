angular.module('AdCshdwr').factory('CdrDrctSalesResource', function($resource){
    var resource = $resource('rest/cdrdrctsaless/:CdrDrctSalesId',{CdrDrctSalesId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});