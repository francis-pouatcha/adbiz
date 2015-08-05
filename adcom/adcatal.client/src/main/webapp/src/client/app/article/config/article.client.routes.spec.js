/* jshint -W117, -W030 */
describe('article routes', function () {

    describe('state listArticle', function () {
        var controller;
        var view = 'app/article/views/list.html';

        beforeEach(function() {
            module('app.article', bard.fakeToastr);
            bard.inject('$httpBackend', '$location', '$rootScope', '$state', '$templateCache');
        });

        beforeEach(function() {
            $templateCache.put(view, '');
        });

        bard.verifyNoOutstandingHttpRequests();

        it('should map state listArticle to url / ', function() {
            expect($state.href('listArticle', {})).to.equal('/');
        });

        it('should map /listArticle route to listArticle View template', function () {
            expect($state.get('listArticle').templateUrl).to.equal(view);
        });

        it('of listArticle should work with $state.go', function () {
            $state.go('listArticle');
            $rootScope.$apply();
            expect($state.is('listArticle'));
        });
    });

    describe('state createArticle', function () {
        var controller;
        var view = 'app/article/views/list.html';

        beforeEach(function() {
            module('app.article', bard.fakeToastr);
            bard.inject('$httpBackend', '$location', '$rootScope', '$state', '$templateCache');
        });

        beforeEach(function() {
            $templateCache.put(view, '');
        });

        bard.verifyNoOutstandingHttpRequests();

        it('should map state createArticle to url / ', function() {
            expect($state.href('createArticle', {})).to.equal('/');
        });

        it('should map /createArticle route to createArticle View template', function () {
            expect($state.get('createArticle').templateUrl).to.equal(view);
        });

        it('of createArticle should work with $state.go', function () {
            $state.go('createArticle');
            $rootScope.$apply();
            expect($state.is('createArticle'));
        });
    });
});
