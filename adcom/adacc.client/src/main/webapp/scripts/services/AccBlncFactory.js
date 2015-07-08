angular.module('AdAcc').factory('AccBlncResource', function($resource) {
	var resource = $resource('rest/accblncs/:AccBlncId', {
		AccBlncId : '@id'
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