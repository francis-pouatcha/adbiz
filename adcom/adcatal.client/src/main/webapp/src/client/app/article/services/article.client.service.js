(function() {
    'use strict';

    angular
        .module('app.article')
        .factory('Article', Article);

    Article.$inject = ['$resource', 'API_BASE_ADCATAL_URL'];
    /* @ngInject */
    function Article($resource, API_BASE_ADCATAL_URL) {

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
                url: API_BASE_ADCATAL_URL + '/catalarticles/findByLike'
            },
            findCustom:{
                method: 'POST',
                url: API_BASE_ADCATAL_URL + '/catalarticles/findCustom'
            },
            listAll:{
                method: 'GET',
                url: API_BASE_ADCATAL_URL + '/catalarticles',
                params:{
                    start: -1,
                    max: 20
                }
            }
        };

        var API_URL = API_BASE_ADCATAL_URL + '/catalarticles/:articleId';

        return $resource(API_URL, params, actions);

    }

})();
