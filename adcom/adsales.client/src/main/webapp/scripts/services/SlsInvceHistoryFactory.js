angular.module('AdSales').factory('SlsInvceHistoryResource', function($resource){
    var resource = $resource('rest/slsinvcehistorys/:SlsInvceHistoryId',{SlsInvceHistoryId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});