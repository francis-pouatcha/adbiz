(function() {
    'use strict';

    angular
        .module('app.catalProdFmlyLangMap')
        .factory('CatalProdFmlyLangMapForm', factory);

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
                    key: 'famPath',
                    type: 'input',
                    templateOptions: {
                        label: 'famPath:',
                        disabled: disabled
                    }
                },
                {
                    key: 'langIso2',
                    type: 'input',
                    templateOptions: {
                        label: 'langIso2:',
                        disabled: disabled
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
