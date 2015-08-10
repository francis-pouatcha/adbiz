/* jshint -W117, -W030 */
describe('article routes', function () {
    describe('state listArticle', function () {
        var controller;
        var view = 'app/article/views/list.html';

<<<<<<< HEAD
        beforeEach(module('app.article'));

=======
>>>>>>> d30e0dbce322424b80a0dfc8b1b160793d002e9e
        beforeEach(function() {
          //  module('app.article', bard.fakeToastr);
            bard.inject('$httpBackend', '$location', '$rootScope', '$state', '$templateCache');
        });

        bard.verifyNoOutstandingHttpRequests();
        it('should map state listArticle to url / ', function() {
            expect($state.href('listArticle', {})).toEqual('/');
        });

    });

});
