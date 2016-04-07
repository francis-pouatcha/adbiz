(function() {
    'use strict';

    angular
        .module('app.catalArtEquivalence')
        .factory('CatalArtEquivalenceForm', factory);

    factory.$inject = ['$translate', 'Article','genericResource'];
    /* @ngInject */
    function factory($translate, Article,genericResource) {

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
                    type: 'typeaheadTwo',
                    templateOptions: {
                        label: $translate.instant('CatalArtEquivalence.equivArtIdentif'),
                        //disabled: false,
                        required: true,
                        placeholder: $translate.instant('Cip_or_name')
                    },
                    controller: /* @ngInject */
                        function($scope, Article) {
                            console.log($translate.proposedLanguage());
                            /*var coreSearchInput = {};
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
                            };*/
                            var searchInput = {entity:{},fieldNames:[],start: 0,max: 10};
                            searchInput.className = 'org.adorsys.adcatal.jpa.CatalArtLangMappingSearchInput';
                            searchInput.fieldNames.push('artName');
                            $scope.to.options = function(value) {
                                searchInput.entity.artName = value;
                                return genericResource.findByLikeWithSearchInputPromissed('/adcatal.server/rest/catalartfeatmappings', searchInput)
                                    .then(function(entitySearchResult){
                                        return entitySearchResult.resultList;
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
