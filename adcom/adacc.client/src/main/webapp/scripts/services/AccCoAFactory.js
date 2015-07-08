angular.module('AdAcc').factory('AccCoAResource', function($resource) {
	var resource = $resource('rest/acccoas/:AccCoAId', {
		AccCoAId : '@id'
	}, {
		'queryAll' : {
			method : 'GET',
			isArray : true
		},
		'query' : {
			method : 'GET',
			isArray : false
		},
		'update' : {
			method : 'PUT'
		}
	});
	return resource;
});