(function() {
    'use strict';

    angular
        .module('app.stkSection')
        .factory('StkSectionForm', factory);

    factory.$inject = ['$translate'];
    function factory($translate) {

        var getFormFields = function(disabled) {
            var fields = [
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-6',
                            type: 'input',
                            key: 'identif',
                            templateOptions: {
                                label: $translate.instant('StockSection.code.title'),
                                disabled:disabled,
                                required:true
                            }
                        },
                        {
                            className: 'col-xs-6',
                            type: 'input',
                            key: 'name',
                            templateOptions: {
                                label: $translate.instant('StockSection.name.title'),
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
                            className: 'col-xs-6',
                            type: 'input',
                            key: 'geoCode',
                            templateOptions: {
                                label: $translate.instant('StockSection.geoCode.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-6',
                            type: 'input',
                            key: 'wharehouse',
                            templateOptions: {
                                label: $translate.instant('StockSection.wharehouse.title'),
                                disabled:disabled
                            }
                        }
                    ]
                },
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-6',
                            type: 'input',
                            key: 'position',
                            templateOptions: {
                                label: $translate.instant('StockSection.position.title'),
                                disabled: disabled
                            }
                        }
                    ]
                }
            ];

            return fields;

        };
        
        var getSearchFormFields = function(disabled) {
            var fields = [
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'identif',
                            templateOptions: {
                                label: $translate.instant('StockSection.code.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'name',
                            templateOptions: {
                                label: $translate.instant('StockSection.name.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'geoCode',
                            templateOptions: {
                                label: $translate.instant('StockSection.geoCode.title'),
                                disabled:disabled
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
                            key: 'wharehouse',
                            templateOptions: {
                                label: $translate.instant('StockSection.wharehouse.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'position',
                            templateOptions: {
                                label: $translate.instant('StockSection.position.title'),
                                disabled: disabled
                            }
                        }
                    ]
                }
            ];
            return fields;
        };
        
        var stkSectionId = '';
        var service = {
            getFormFields: getFormFields,
            getSearchFormFields: getSearchFormFields,
            stkSectionId: stkSectionId
        };

        return service;

    }

})();
