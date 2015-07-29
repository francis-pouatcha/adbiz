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
                        required: true
                    },
                    controller: /* @ngInject */
                        function($scope, Article) {
                            var coreSearchInput = {};
                            coreSearchInput.start = 0;
                            coreSearchInput.max = 10;
                            coreSearchInput.entity = {};
                            coreSearchInput.fieldNames = [];
                            coreSearchInput.className = 'org.adorsys.adcatal.jpa.CatalArticleSearchInput';
                            coreSearchInput.fieldNames.push('identif');

                            $scope.to.options = function(value){
                                coreSearchInput.entity.identif = value;
                                return Article.findByLike(coreSearchInput).$promise.then(function(response){
                                    return response.resultList;

                                });
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
