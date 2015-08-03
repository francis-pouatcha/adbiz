/**
 * Created by adorsys on 8/3/15.
 */

describe('module: app.article', function () {

    beforeEach(module('app.article'));

    describe('Controller: ArticleController', function () {
    'use strict';


    var ArticleController,  log, stateParams, article , articleForm, translate , tableSettings;

    beforeEach(inject(function ($controller,logger,$stateParams,Article,ArticleForm,$translate,TableSettings) {
        log = logger.$new();
        stateParams = $stateParams.$new();
        article = Article.$new();
        articleForm = ArticleForm.$new();
        translate = $translate.$new();
        tableSettings = TableSettings.$new();

        ArticleController = $controller('ArticleController', {
            logger: log, $stateParams : stateParams, Article : article , ArticleForm: articleForm , $translate: translate, TableSettings: tableSettings
        });
    }));

    var vm = ArticleController;

    it('vm.article is defined ',function(){

        expect(vm.article).toBeDefined();

    });

    it('vm.tableParams is defined ',function(){

        expect(vm.tableParams).toBeDefined();

    });

    });

    it('test is defined ',function(){

        expect(true).toBe(true);

    });

})





