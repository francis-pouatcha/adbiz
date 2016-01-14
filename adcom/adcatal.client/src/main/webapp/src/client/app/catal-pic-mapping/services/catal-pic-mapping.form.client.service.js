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
                    type: 'select',
                    templateOptions: {
                        label: $translate.instant('CatalPicMapping.codeOrigin'),
                        disabled: disabled,
                        options: [
                            {name: $translate.instant('CatalCipOrigine_MAIN_description'), value: 'MAIN'},
                            {name: $translate.instant('CatalCipOrigine_SUPPLIER_description'), value: 'SUPPLIER'},
                            {name: $translate.instant('CatalCipOrigine_DETAIL_description'), value: 'DETAIL'},
                            {name: $translate.instant('CatalCipOrigine_MANUFACTURER_description'), value: 'MANUFACTURER'},
                            {name: $translate.instant('CatalCipOrigine_RESALER_description'), value: 'RESALER'},
                            {name: $translate.instant('CatalCipOrigine_GOVERNMENT_description'), value: 'GOVERNMENT'},
                            {name: $translate.instant('CatalCipOrigine_BROKER_description'), value: 'BROKER'},
                            {name: $translate.instant('CatalCipOrigine_CUSTOMERSERVICE_description'), value: 'CUSTOMERSERVICE'},
                            {name: $translate.instant('CatalCipOrigine_INSURANCE_description'), value: 'INSURANCE'}
                        ]
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
