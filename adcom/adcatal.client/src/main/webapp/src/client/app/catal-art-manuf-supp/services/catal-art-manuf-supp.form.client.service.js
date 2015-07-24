(function() {
    'use strict';

    angular
        .module('app.catalArtManufSupp')
        .factory('CatalArtManufSuppForm', factory);

    function factory() {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'artIdentif',
                    type: 'input',
                    templateOptions: {
                        label: 'artIdentif:',
                        disabled: true,
                        required: true
                    }
                },
                {
                    key: 'msIdentif',
                    type: 'input',
                    templateOptions: {
                        label: 'msIdentif:',
                        disabled: disabled
                    }
                },
                {
                    key: 'msType',
                    type: 'input',
                    templateOptions: {
                        label: 'msType:',
                        disabled: disabled
                    }
                },
                {
                    key: 'warrantyMonths',
                    type: 'input',
                    templateOptions: {
                        label: 'warrantyMonths:',
                        disabled: disabled
                    }
                },
                {
                    key: 'returnDays',
                    type: 'input',
                    templateOptions: {
                        label: 'returnDays:',
                        disabled: disabled
                    }
                },
                {
                    key: 'pppu',
                    type: 'input',
                    templateOptions: {
                        label: 'pppu:',
                        disabled: disabled
                    }
                },
                {
                    key: 'pppuCurrIso3',
                    type: 'input',
                    templateOptions: {
                        label: 'pppuCurrIso3:',
                        disabled: disabled
                    }
                },
                {
                    key: 'vatRate',
                    type: 'input',
                    templateOptions: {
                        label: 'vatRate:',
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
