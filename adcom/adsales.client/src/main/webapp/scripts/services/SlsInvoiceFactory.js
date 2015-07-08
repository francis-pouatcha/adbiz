angular.module('AdSales').factory('SlsInvoiceResource', function($resource){
    var resource = $resource('rest/slsinvoices/:SlsInvoiceId',{SlsInvoiceId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});