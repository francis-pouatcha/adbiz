(function() {
    'use strict';
    angular
        .module('app.article')
        .run(appRun);
    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }
    function getStates(BASE_URL) {
        return [
            {
                state: 'listArticle',
                config: {
                    url:  '/article',
                    templateUrl: 'app/article/views/list.html',
                    controller: 'ArticleController',
                    controllerAs: 'vm',
                    title: 'List Articles',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-folder-open"></i> Articles'
                    }
                }
            },
            {
                state: 'createArticle',
                config: {
                    url: '/article/create',
                    templateUrl: 'app/article/views/create.html',
                    controller: 'ArticleController',
                    controllerAs: 'vm',
                    title: 'Create Article'
                }
            },
            {
                state: 'viewArticle',
                config: {
                    url: '/article/:articleId',
                    templateUrl: 'app/article/views/view.html',
                    controller: 'ArticleController',
                    controllerAs: 'vm',
                    title: 'View Article'
                }
            },
            {
                state: 'editArticle',
                config: {
                    url: '/article/:articleId/edit',
                    templateUrl: 'app/article/views/edit.html',
                    controller: 'ArticleController',
                    controllerAs: 'vm',
                    title: 'Edit Article'
                }
            }
        ];
    }
})();