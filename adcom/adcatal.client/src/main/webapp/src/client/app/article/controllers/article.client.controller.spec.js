/* jshint -W117, -W030 */
describe('ArticleController', function() {
    beforeEach(function() {
        bard.appModule('app.article');
        bard.$inject = ['$controller', '$log', '$q', '$rootScope', 'Article'];
    });
    beforeEach(function () {
    });
    bard.verifyNoOutstandingHttpRequests();

    it('Controller First test ', function() {
        expect(true).toBe(true);
    });

});
