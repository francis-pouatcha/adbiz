(function() {
    'use strict';

    angular
        .module('app.catalArtManufSupp')
        .factory('CatalArtManufSuppForm', factory);

    factory.$inject = ['$translate'];
    /* @ngInject */
    function factory($translate) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'artIdentif',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtManufSupp.artIdentif'),
                        disabled: true,
                        required: true
                    }
                },
                {
                    key: 'msIdentif',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtManufSupp.msIdentif'),
                        disabled: disabled
                    }
                },
                {
                    key: 'msType',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtManufSupp.msType'),
                        disabled: disabled
                    }
                },
                {
                    key: 'warrantyMonths',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtManufSupp.warrantyMonths'),
                        disabled: disabled
                    }
                },
                {
                    key: 'returnDays',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtManufSupp.returnDays'),
                        disabled: disabled
                    }
                },
                {
                    key: 'pppu',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtManufSupp.pppu'),
                        disabled: disabled
                    }
                },
                {
                    key: 'pppuCurrIso3',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtManufSupp.pppuCurrIso3'),
                        disabled: disabled
                    }
                },
                {
                    key: 'vatRate',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtManufSupp.vatRate'),
                        disabled: disabled
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
