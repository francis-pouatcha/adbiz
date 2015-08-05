/**
 * Created by adorsys on 8/4/15.
 */

describe("Midway: Testing Article Module", function() {
    describe("Article Module:", function() {

        var module;

        beforeEach(function() {
            module = angular.module("app.article");
        });

        it("should be registered", function() {
            expect(module).toBeDefined();
        });

    });
});
