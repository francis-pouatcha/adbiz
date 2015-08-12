/* jshint -W117, -W030 */
describe('ArticleController', function() {
    var controller;
    var people = mockData.getMockPeople();
    beforeEach(function() {
        bard.appModule('app.article');
        bard.inject('$controller', '$log', '$q', '$rootScope', 'Article');
    });
    beforeEach(function () {
        //sinon.stub(Article, 'getPeople').returns($q.when(people));
        controller = $controller('ArticleController');
        $rootScope.$apply();
    });
    bard.verifyNoOutstandingHttpRequests();

});
