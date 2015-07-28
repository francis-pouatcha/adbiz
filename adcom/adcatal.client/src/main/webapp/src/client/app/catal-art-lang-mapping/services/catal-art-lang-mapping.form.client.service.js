(function() {
    'use strict';

    angular
        .module('app.catalArtLangMapping')
        .factory('CatalArtLangMappingForm', factory);

    function factory() {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'cntnrIdentif',
                    type: 'input',
                    templateOptions: {
                        label: 'cntnrIdentif:',
                        disabled: true
                    }
                },
                {
                    key: 'artName',
                    type: 'input',
                    templateOptions: {
                        label: 'artName:',
                        disabled: disabled
                    }
                },
                {
                    key: 'shortName',
                    type: 'input',
                    templateOptions: {
                        label: 'shortName:',
                        disabled: disabled
                    }
                },
                {
                    key: 'langIso2',
                    type: 'select',
                    templateOptions: {
                        label: 'langIso2',
                        disabled: disabled,
                        options: [
                            {name: 'french', value: 'fr'},
                            {name: 'english', value: 'en'}
                        ]
                    }
                },
                {
                    key: 'purpose',
                    type: 'textarea',
                    templateOptions: {
                        label: 'purpose:',
                        disabled: disabled
                    }
                },
                {
                    key: 'usage',
                    type: 'textarea',
                    templateOptions: {
                        label: 'usage:',
                        disabled: disabled
                    }
                },
                {
                    key: 'warnings',
                    type: 'textarea',
                    templateOptions: {
                        label: 'warnings:',
                        disabled: disabled
                    }
                },
                {
                    key: 'substances',
                    type: 'textarea',
                    templateOptions: {
                        label: 'substances:',
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
