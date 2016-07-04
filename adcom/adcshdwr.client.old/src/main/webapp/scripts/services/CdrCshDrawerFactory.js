angular.module('AdCshdwr').factory('CdrCshDrawerResource', function($resource){
    var resource = $resource('rest/cdrcshdrawers/:CdrCshDrawerId',{CdrCshDrawerId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});