(function() {
    'use strict';

    angular
        .module('app.stkMvnt')
        .factory('StkMvntForm', factory);

    factory.$inject = ['$translate'];
    function factory($translate) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'identif',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.code.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'artPic',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.artPic.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'lotPic',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.lotPic.title'),
                                disabled:disabled
                            }
                        }
                    ]
                },
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'section',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.section.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'orgUnit',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.orgUnit.title'),
                                disabled: disabled
                            }
                        },
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'acsngUser',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.acsngUser.title'),
                                disabled: disabled
                            }
                        }
                    ]
                },
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'acsngDt',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.acsngDt.title'),
                                disabled: disabled
                            }
                        },
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'mvntType',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.mvntType.title'),
                                disabled: disabled
                            }
                        },
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'mvntOrigin',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.mvntOrigin.title'),
                                disabled: disabled
                            }
                        }
                    ]
                },
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'mvntDest',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.mvntDest.title'),
                                disabled: disabled
                            }
                        },
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'origProcs',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.origProcs.title'),
                                disabled: disabled
                            }
                        },
                        {
                            className: 'col-xs-12 col-md-4',
                            type: 'input',
                            key: 'origProcsNbr',
                            templateOptions: {
                                label: $translate.instant('StkMvnt.origProcsNbr.title'),
                                disabled: disabled
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
