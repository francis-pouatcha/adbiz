(function() {
    'use strict';

    angular
        .module('app.bpbnsptnr')
        .factory('BpbnsptnrForm', factory);

    factory.$inject = ['$translate'];
    /* @ngInject */

    var isLegal = true;
    var isIndividual = false;


    function typeChange(model){
        if(model.value == 'LEGAL'){
            isLegal = true;
            isIndividual = false;
        }
        else{
            isIndividual = true;
            isLegal = false;
        }
    }

    function factory($translate) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-6',
                            type: 'select',
                            key: 'ptnrType',
                            templateOptions: {
                                label: $translate.instant('BpBnsPtnr_ptnrType_description.title'),
                                disabled:disabled,
                                required:true,
                                options: [
                                    {name: $translate.instant('BpPtnrType_LEGAL_description.title'), value: 'LEGAL'},
                                    {name: $translate.instant('BpPtnrType_INDIVIDUAL_description.title'), value: 'INDIVIDUAL'}
                                ]

                            },


                        },
                        {
                            className: 'col-xs-6',
                            type: 'input',
                            key: 'ctryOfRsdnc',
                            templateOptions: {
                                label: $translate.instant('BpBnsPtnr_ctryOfRsdnc_description.title'),
                                //pattern: '\\d{5}',
                                disabled:disabled,
                                required:true
                            }
                        }


                    ]
                },
                {
                    className: 'row',
                    hide:isLegal,
                    fieldGroup: [
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'cpnyName',
                            templateOptions: {
                                label: $translate.instant('BpLegalPtnrId_cpnyName_description.title'),
                                disabled:disabled,
                                required:true
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'gender',
                            templateOptions: {
                                label: $translate.instant('BpLegalPtnrId_equity_description.title'),
                                disabled:disabled
                            }
                        },

                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'legalForm',
                            templateOptions: {
                                label: $translate.instant('BpLegalPtnrId_legalForm_description.title'),
                                disabled:disabled
                            }

                        }

                    ]
                },
                {
                    className: 'row',
                    hide:isIndividual,
                    fieldGroup: [
                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'firstName',
                            templateOptions: {
                                label: $translate.instant('BpIndivPtnrName_firstName_description.title'),
                                disabled:disabled,
                                required:true
                            }
                        },
                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'lastName',
                            templateOptions: {
                                label: $translate.instant('BpIndivPtnrName_lastName_description.title'),
                                disabled:disabled
                            }
                        },

                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'gender',
                            templateOptions: {
                                label: $translate.instant('BpIndivPtnrName_gender_description.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'brthDt',
                            templateOptions: {
                                label: $translate.instant('BpIndivPtnrName_brthDt_description.title'),
                                disabled:disabled
                            }
                        }

                    ]
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
