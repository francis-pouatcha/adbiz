(function() {
    'use strict';

    angular
        .module('app.catalArtEquivalence')
        .factory('CatalArtEquivalenceForm', factory);

    factory.$inject = ['$translate','Article'];
    /* @ngInject */
    function factory($translate,Article) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'mainArtIdentif',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtEquivalence.mainArtIdentif'),
                        disabled: true,
                        required: true
                    }
                },
                {
                    key: 'equivArtIdentif',
                    type: 'typeahead',
                    templateOptions: {
                        label: $translate.instant('CatalArtEquivalence.equivArtIdentif'),
                        disabled: disabled,
                        required: true,
                        controller: /* @ngInject */
                            function($scope, Article) {
                                $scope.getData = function(val){
                                    console.log(val);
                                    return Article.query({},{max:10, start:0},{})
                                        .$promise.then(function (response) {
                                             console.log(response.resultList);
                                        });
                                }
                        }
                    }
                }
            ];

            return fields;

        };

        var service = {
            getFormFields: getFormFields
        };

        return service;

    }

})();
