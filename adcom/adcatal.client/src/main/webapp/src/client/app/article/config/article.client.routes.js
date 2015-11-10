(function() {
    'use strict';
    angular
        .module('app.article')
        .run(appRun);

    appRun.$inject = ['routerHelper', 'BASE_VIEW'];
    /* @ngInject */
    function appRun(routerHelper, BASE_VIEW) {
        var otherwise = '/article';
        routerHelper.configureStates(getStates(BASE_VIEW), otherwise);
    }

    function getStates(BASE_VIEW) {
        return [
            {
                state: 'Articles',
                config: {
                    url:  '/',
                    templateUrl: BASE_VIEW+'/app/article/views/list.html',
                    controller: 'ArticleController',
                    controllerAs: 'vm',
                    title: 'List Articles'

                }
            },,
            {
                state: 'listArticle',
                config: {
                    url:  '/article',
                    templateUrl: BASE_VIEW+'/app/article/views/list.html',
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
                    templateUrl: BASE_VIEW+'/app/article/views/create.html',
                    controller: 'ArticleController',
                    controllerAs: 'vm',
                    title: 'Create Article'
                }
            },
            {
                state: 'viewArticle',
                config: {
                    url: '/article/:articleId',
                    templateUrl: BASE_VIEW+'/app/article/views/view.html',
                    controller: 'ArticleController',
                    controllerAs: 'vm',
                    title: 'View Article'
                }
            },
            {
                state: 'editArticle',
                config: {
                    url: '/article/:articleId/edit',
                    templateUrl: BASE_VIEW+'/app/article/views/edit.html',
                    controller: 'ArticleController',
                    controllerAs: 'vm',
                    title: 'Edit Article'
                }
            }
        ];
    }
})();
