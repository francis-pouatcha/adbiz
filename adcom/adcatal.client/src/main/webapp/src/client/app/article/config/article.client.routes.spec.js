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
            expect($state.href('listArticle', {})).toEqual('/');
        });
        it('should map /listArticle route to listArticle View template', function () {
            expect($state.get('listArticle').templateUrl).toEqual(view);
        });
        it('of listArticle should work with $state.go', function () {
            $state.go('listArticle');
            $rootScope.$apply();
            expect($state.is('listArticle'));
        });
    });

});
