(function() {
    'use strict';

    angular
        .module('app.catalPicMapping')
        .factory('CatalPicMappingForm', factory);

    factory.$inject = ['$translate'];
    /* @ngInject */
    function factory($translate) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'artIdentif',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalPicMapping.artIdentif'),
                        disabled: true,
                        required: true
                    }
                },
                {
                    key: 'code',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalPicMapping.code'),
                        disabled: disabled
                    }
                },
                {
                    key: 'codeOrigin',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalPicMapping.codeOrigin'),
                        disabled: disabled
                    }
                },
                {
                    key: 'addInfo',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalPicMapping.addInfo'),
                        disabled: disabled
                    }
                },
                {
                    key: 'priority',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalPicMapping.priority'),
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
