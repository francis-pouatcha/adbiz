(function() {
    'use strict';

    angular
        .module('app.catalPicMapping')
        .factory('CatalPicMappingForm', factory);

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
                    key: 'code',
                    type: 'input',
                    templateOptions: {
                        label: 'code:',
                        disabled: disabled
                    }
                },
                {
                    key: 'codeOrigin',
                    type: 'input',
                    templateOptions: {
                        label: 'codeOrigin:',
                        disabled: disabled
                    }
                },
                {
                    key: 'addInfo',
                    type: 'input',
                    templateOptions: {
                        label: 'addInfo:',
                        disabled: disabled
                    }
                },
                {
                    key: 'priority',
                    type: 'input',
                    templateOptions: {
                        label: 'priority:',
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
