(function() {
    'use strict';

    angular
        .module('app.catalArtDetailConfig')
        .factory('CatalArtDetailConfigForm', factory);

    factory.$inject = ['$translate'];
    /* @ngInject */
    function factory($translate) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'cntnrIdentif',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('cntnrIdentif'),
                        disabled: true,
                        required: true
                    }
                },
                {
                    key: 'qtyOfDtldInMain',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtDetailConfig.qtyOfDtldInMain'),
                        disabled: disabled,
                        required: true
                    }
                },
                {
                    key: 'pptnOfDtldInMain',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtDetailConfig.targetPrprtn'),
                        disabled: disabled,
                        required: true
                    }
                },
                {
                    key: 'mngInPptn',
                    type: 'checkbox',
                    templateOptions: {
                        label: $translate.instant('CatalArtDetailConfig.mngInPptn'),
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
