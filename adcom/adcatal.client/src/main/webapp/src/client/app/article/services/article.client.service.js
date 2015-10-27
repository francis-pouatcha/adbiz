(function() {
    'use strict';

    angular
        .module('app.article')
        .factory('Article', Article);

    Article.$inject = ['$resource', 'API_BASE_URL'];
    /* @ngInject */
    function Article($resource, API_BASE_URL) {

        var params = {
            articleId: '@id'
        };

        var actions = {
            'query':  {method:'GET', isArray:false},
            update: {
                method: 'PUT'
            },
            findByLike: {
                method: 'POST',
                url: API_BASE_URL + '/catalarticles/findByLike'
            }
        };

        var API_URL = API_BASE_URL + '/catalarticles/:articleId';

        return $resource(API_URL, params, actions);

    }

})();
