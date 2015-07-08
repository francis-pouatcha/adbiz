angular.module('AdSales').factory('SlsInvceStatusResource', function($resource){
    var resource = $resource('rest/slsinvcestatuss/:SlsInvceStatusId',{SlsInvceStatusId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});