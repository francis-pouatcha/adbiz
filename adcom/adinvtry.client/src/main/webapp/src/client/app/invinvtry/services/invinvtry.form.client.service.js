(function() {
    'use strict';

    angular
        .module('app.invinvtry')
        .factory('InvinvtryForm', factory);

    factory.$inject = ['$translate'];
    /* @ngInject */

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
                                label: $translate.instant('InvInvtry_invInvtryType_description.title'),
                                disabled:disabled,
                                required:true,
                                options: [

                                ]

                            }


                        },
                        {
                            className: 'col-xs-6',
                            type: 'input',
                            key: 'ctryOfRsdnc',
                            templateOptions: {
                                label: $translate.instant('InvInvtry_acsngUser_description.title'),
                                //pattern: '\\d{5}',
                                disabled:disabled,
                                required:true
                            }
                        }


                    ]
                },
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'cpnyName',
                            templateOptions: {
                                label: $translate.instant('InvInvtry_acsngUserFullName_description.title'),
                                disabled:disabled,
                                required:true
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'gender',
                            templateOptions: {
                                label: $translate.instant('InvInvtry_invtryGroup_description.title'),
                                disabled:disabled
                            }
                        },

                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'legalForm',
                            templateOptions: {
                                label: $translate.instant('InvInvtry_descptn_description.title'),
                                disabled:disabled
                            }

                        }

                    ]
                },
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'firstName',
                            templateOptions: {
                                label: $translate.instant('InvInvtryItem_section_description.title'),
                                disabled:disabled,
                                required:true
                            }
                        },
                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'lastName',
                            templateOptions: {
                                label: $translate.instant('InvInvtryItem_sectionName_description.title'),
                                disabled:disabled
                            }
                        },

                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'gender',
                            templateOptions: {
                                label: $translate.instant('InvInvtryItem_artNameStart_description.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-3',
                            type: 'input',
                            key: 'brthDt',
                            templateOptions: {
                                label: $translate.instant('InvInvtryItem_artNameEnd_description.title'),
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
