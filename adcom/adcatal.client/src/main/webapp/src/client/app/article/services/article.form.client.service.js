(function() {
    'use strict';

    angular
        .module('app.article')
        .factory('ArticleForm', factory);

    factory.$inject = ['$translate'];
    /* @ngInject */
    function factory($translate) {

        var getFormFields = function(disabled) {

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
                            key: 'sppu',
                            templateOptions: {
                                label: $translate.instant('CatalArticle_sppu_description.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'maxDisctRate',
                            templateOptions: {
                                label: 'Max Remise',
                                disabled:disabled
                            }
                        }
                    ]
                },

                {
                    className: 'row',
                    fieldGroup: [

                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'minStockQty',
                            templateOptions: {
                                type: 'number',
                                label: 'Min Stock',
                                max: 99999,
                                min: 0,
                                //pattern: '\\d{5}',
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'maxStockQty',
                            templateOptions: {
                                type: 'number',
                                label: 'Max stock',
                                max: 99999,
                                min: 0,
                                //pattern: '\\d{5}',
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'vatRate',
                            templateOptions: {
                                label: 'Taux TVA',
                                disabled:disabled
                            }
                        },
                        {
                            //className: 'col-xs-4',
                            type: 'checkbox',
                            key: 'active',
                            templateOptions: {
                                label: 'Actif',
                                disabled:disabled
                            }
                        },
                        {
                            //className: 'col-xs-4',
                            type: 'checkbox',
                            key: 'authorizedSale',
                            templateOptions: {
                                label: 'Vendable',
                                disabled:disabled
                            }
                        }
                    ]
                }
            ];

            return fields;

        };

        var catalArticleId = "";

        var service = {
            getFormFields: getFormFields,
            catalArticleId: catalArticleId
        };

        return service;

    }

})();
