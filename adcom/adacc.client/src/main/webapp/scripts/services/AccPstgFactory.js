angular.module('AdAcc').factory('AccPstgResource', function($resource){
    var resource = $resource('rest/accpstgs/:AccPstgId',{AccPstgId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});