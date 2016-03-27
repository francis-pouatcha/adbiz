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
                    type: 'input',
                    key: 'qualifier',
                    templateOptions: {
                        label: $translate.instant('CatalArtDetailConfig.qualifier'),
                        //pattern: '\\d{5}',
                        disabled:disabled,
                        required:true
                    }
                },
                {
                    key: 'mngInPptn',
                    type: 'checkbox',
                    templateOptions: {
                        label: $translate.instant('CatalArtDetailConfig.mngInPptn'),
                        disabled: disabled
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
                    type: 'input',
                    key: 'sppu',
                    templateOptions: {
                        type:'number',
                        label: $translate.instant('CatalArticle.sppu'),
                        disabled:disabled
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
