(function() {
    'use strict';

    angular
        .module('app.catalArtLangMapping')
        .factory('CatalArtLangMappingForm', factory);

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
                        disabled: true
                    }
                },
                {
                    key: 'artName',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtFeatMapping.artName'),
                        disabled: disabled
                    }
                },
                {
                    key: 'shortName',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtFeatMapping.shortName'),
                        disabled: disabled
                    }
                },
                {
                    key: 'langIso2',
                    type: 'select',
                    templateOptions: {
                        label: $translate.instant('langIso2'),
                        disabled: disabled,
                        options: [
                            {name: $translate.instant('LANG_FR'), value: 'fr'},
                            {name: $translate.instant('LANG_EN'), value: 'en'}
                        ]
                    }
                },
                {
                    key: 'purpose',
                    type: 'textarea',
                    templateOptions: {
                        label: $translate.instant('CatalAbstractFeatMapping.purpose'),
                        disabled: disabled
                    }
                },
                {
                    key: 'usage',
                    type: 'textarea',
                    templateOptions: {
                        label: $translate.instant('CatalAbstractFeatMapping.usage'),
                        disabled: disabled
                    }
                },
                {
                    key: 'warnings',
                    type: 'textarea',
                    templateOptions: {
                        label: $translate.instant('CatalAbstractFeatMapping.warnings'),
                        disabled: disabled
                    }
                },
                {
                    key: 'substances',
                    type: 'textarea',
                    templateOptions: {
                        label: $translate.instant('CatalAbstractFeatMapping.substances'),
                        disabled: disabled
                    }
                }
            ];

            return fields;

        };
        
        var makeId = function(cntnrIdentif,langIso2){
        	return cntnrIdentif + "_" + langIso2;
        };

        var service = {
            getFormFields: getFormFields,
            makeId: makeId
        };

        return service;

    }

})();
