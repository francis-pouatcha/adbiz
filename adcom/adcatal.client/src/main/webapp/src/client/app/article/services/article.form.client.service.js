(function() {
    'use strict';

    angular
        .module('app.article')
        .factory('ArticleForm', factory);

    factory.$inject = ['$translate'];
    /* @ngInject */
    function factory($translate) {

        var getFormFields = function(disabled,hideName) {

            var fields = [
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'identif',
                            templateOptions: {
                                label: $translate.instant('CIP'),
                                disabled:disabled,
                                required:true
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'artName',
                            templateOptions: {
                                label: $translate.instant('CatalArtFeatMapping.artName'),
                                //pattern: '\\d{5}',
                                disabled:disabled,
                                required:true
                            },
                            hideExpression:''+hideName
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'shortName',
                            templateOptions: {
                                label: $translate.instant('Article ShortName'),
                                //pattern: '\\d{5}',
                                disabled:disabled
                            },
                            hideExpression:''+hideName
                        }

                    ]
                },

                {
                    className: 'row',
                    fieldGroup: [

                        {
                            className: 'col-xs-2',
                            type: 'input',
                            key: 'sppu',
                            templateOptions: {
                                type:'number',
                                label: $translate.instant('CatalArticle.sppu'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-2',
                            type: 'input',
                            key: 'maxDisctRate',
                            templateOptions: {
                                label: $translate.instant('CatalArticle.maxDisctRate'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-2',
                            type: 'input',
                            key: 'minStockQty',
                            templateOptions: {
                                type: 'number',
                                label: $translate.instant('CatalArticle.minStockQty'),
                                max: 99999,
                                min: 0,
                                //pattern: '\\d{5}',
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-2',
                            type: 'input',
                            key: 'maxStockQty',
                            templateOptions: {
                                type: 'number',
                                label: $translate.instant('CatalArticle.maxStockQty'),
                                max: 99999,
                                min: 0,
                                //pattern: '\\d{5}',
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-2',
                            type: 'input',
                            key: 'vatRate',
                            templateOptions: {
                                label: $translate.instant('CatalArticle.vatRate'),
                                disabled:disabled
                            }
                        },
                        {
                            //className: 'col-xs-4',
                            type: 'checkbox',
                            key: 'active',
                            templateOptions: {
                                label: $translate.instant('CatalArticle.active'),
                                disabled:disabled
                            }
                        },
                        {
                            //className: 'col-xs-4',
                            type: 'checkbox',
                            key: 'authorizedSale',
                            templateOptions: {
                                label: $translate.instant('CatalArticle.authorizedSale'),
                                disabled:disabled
                            }
                        }
                    ]
                }
            ];

            return fields;

        };

        var catalArticleId = '';

        var service = {
            getFormFields: getFormFields,
            catalArticleId: catalArticleId
        };

        return service;

    }

})();
