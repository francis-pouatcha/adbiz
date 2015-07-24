(function() {
    'use strict';

    angular
        .module('app.catalArtDetailConfig')
        .factory('CatalArtDetailConfigForm', factory);

    function factory() {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'cntnrIdentif',
                    type: 'input',
                    templateOptions: {
                        label: 'cntnrIdentif:',
                        disabled: true,
                        required: true
                    }
                },
                {
                    key: 'qtyOfDtldInMain',
                    type: 'input',
                    templateOptions: {
                        label: 'qtyOfDtldInMain:',
                        disabled: disabled,
                        required: true
                    }
                },
                {
                    key: 'pptnOfDtldInMain',
                    type: 'input',
                    templateOptions: {
                        label: 'pptnOfDtldInMain:',
                        disabled: disabled,
                        required: true
                    }
                },
                {
                    key: 'mngInPptn',
                    type: 'checkbox',
                    templateOptions: {
                        label: 'mngInPptn:',
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
