angular.module('AdSales').factory('SlsInvoiceTypeResource', function($resource){
    var resource = $resource('rest/slsinvoicetypes/:SlsInvoiceTypeId',{SlsInvoiceTypeId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});