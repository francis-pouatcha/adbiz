(function() {
    'use strict';

    angular
        .module('app.catalArtEquivalence')
        .factory('CatalArtEquivalenceForm', factory);

    factory.$inject = ['$translate', 'Article'];
    /* @ngInject */
    function factory($translate, Article) {

        var getCreateFields = function() {

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
                        disabled: false,
                        required: true
                    },
                    controller: /* @ngInject */
                        function($scope, Article) {
                            var coreSearchInput = {};
                            coreSearchInput.start = 0;
                            coreSearchInput.max = 10;
                            coreSearchInput.entity = {};
                            coreSearchInput.fieldNames = [];
                            coreSearchInput.className = 'org.adorsys.adcatal.' +
                                'jpa.CatalArticleSearchInput';
                            coreSearchInput.fieldNames.push('identif');

                            $scope.to.options = function(value) {
                                coreSearchInput.entity.identif = value;
                                return Article.findByLike(coreSearchInput)
                                    .$promise.then(function(response) {
                                        return response.resultList;

                                    });
                            };
                        }
                },
                {
                    key: 'usage',
                    type: 'textarea',
                    templateOptions: {
                        label: $translate.instant('CatalArtEquivalence.usage')
                    }
                }
            ];

            return fields;

        };

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'displayMainArtName',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtEquivalence.mainArtIdentif'),
                        disabled: true,
                        required: true
                    }
                },
                {
                    key: 'displayEquivArtName',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtEquivalence.equivArtIdentif'),
                        disabled: true,
                        required: true
                    }
                },
                {
                    key: 'usage',
                    type: 'textarea',
                    templateOptions: {
                        label: $translate.instant('CatalArtEquivalence.usage'),
                        disabled: disabled
                    }
                }
            ];

            return fields;

        };
        
        var service = {
            getFormFields: getFormFields,
            getCreateFields: getCreateFields
        };

        return service;

    }

})();
