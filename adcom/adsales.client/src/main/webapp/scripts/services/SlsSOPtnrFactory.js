angular.module('AdSales').factory('SlsSOPtnrResource', function($resource){
    var resource = $resource('rest/slssoptnrs/:SlsSOPtnrId',{SlsSOPtnrId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});