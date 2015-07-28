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
            update: {
                method: 'PUT'
            }
        };

        var API_URL = API_BASE_URL + '/catalarticles/:articleId';

        return $resource(API_URL, params, actions);

    }

})();
