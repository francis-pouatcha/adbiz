angular.module('AdSales').factory('ProcStepResource', function($resource){
    var resource = $resource('rest/procsteps/:ProcStepId',{ProcStepId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});