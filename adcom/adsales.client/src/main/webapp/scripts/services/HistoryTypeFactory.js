angular.module('AdSales').factory('HistoryTypeResource', function($resource){
    var resource = $resource('rest/historytypes/:HistoryTypeId',{HistoryTypeId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});