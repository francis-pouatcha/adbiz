(function() {
    'use strict';

    angular
        .module('app.stockArticlelot')
        .factory('StockArticlelotForm', factory);

    factory.$inject = ['$translate','sectionUtil'];
    function factory($translate,sectionUtil) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'lotPic',
                            templateOptions: {
                                label: $translate.instant('StockArticlelot.lotPic.title'),
                                disabled:true
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'artPic',
                            templateOptions: {
                                label: $translate.instant('StockArticlelot.artPic.title'),
                                disabled:true
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'section',
                            templateOptions: {
                                label: $translate.instant('StockArticlelot.section.title'),
                                disabled:true
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
                            key: 'acsngUser',
                            templateOptions: {
                                label: $translate.instant('StockArticlelot.acsngUser.title'),
                                disabled:true
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'datepicker',
                            key: 'expirDt',
                            templateOptions: {
                                label: $translate.instant('StockArticlelot.expirDt.title'),
                                type: 'text',
                                datepickerPopup: 'dd-MM-yyyy',
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'supplier',
                            templateOptions: {
                                label: $translate.instant('StockArticlelot.supplier.title'),
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
                            key: 'stkUnitValCur',
                            templateOptions: {
                                label: $translate.instant('StockArticlelot.stkUnitValCur.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'trgtQty',
                            templateOptions: {
                                label: $translate.instant('StockArticlelot.trgtQty.title'),
                                disabled:disabled
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'datepicker',
                            key: 'stkgDt',
                            templateOptions: {
                                label: $translate.instant('StockArticlelot.stkgDt.title'),
                                type: 'text',
                                datepickerPopup: 'dd-MM-yyyy',
                                disabled:disabled
                            }
                        }
                    ]
                  }
                
            ];

            return fields;

        };
        
        
        
        var getTabsFormFields= function(disabled, model){
             var fields = [
                {
                   title: 'Purchase Prices',
                   active: true,
                   form: {
                        options: {},
                        model: model,
                        fields: [
                            {
                                className: 'row col-xs-3',
                                type: 'input',
                                key: 'prchUnitPrcPreTax',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.prchUnitPrcPreTax.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'prchUnitPrcTaxIncl',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.prchUnitPrcTaxIncl.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'prchGrossPrcPreTax',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.prchGrossPrcPreTax.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'prchNetPrcPreTax',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.prchNetPrcPreTax.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'row col-xs-3',
                                type: 'input',
                                key: 'prchVatPct',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.prchVatPct.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'prchVatAmt',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.prchVatAmt.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'prchUnitPrcCur',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.prchUnitPrcCur.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'prchVatUnitAmt',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.prchVatUnitAmt.title'),
                                    disabled:disabled
                                }
                            },
                        ]
                     }
                   },
                 {
                   title: 'Sales Prices',
                   form: {
                        options: {},
                        model: model,
                        fields: [
                            {
                                className: 'row col-xs-3',
                                type: 'input',
                                key: 'slsUnitPrcPreTax',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.slsUnitPrcPreTax.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'slsUnitPrcTaxIncl',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.slsUnitPrcTaxIncl.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'slsGrossPrcPreTax',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.slsGrossPrcPreTax.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'slsNetPrcPreTax',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.slsNetPrcPreTax.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'row col-xs-3',
                                type: 'input',
                                key: 'slsVatPct',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.slsVatPct.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'slsVatAmt',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.slsVatAmt.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'slsVatUnitAmt',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.slsVatUnitAmt.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'col-xs-3',
                                type: 'input',
                                key: 'slsUnitPrcCur',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.slsUnitPrcCur.title'),
                                    disabled:disabled
                                }
                            },
                            {
                                className: 'row col-xs-3',
                                type: 'input',
                                key: 'slsNetPrcTaxIncl',
                                templateOptions: {
                                    label: $translate.instant('StockArticlelot.slsNetPrcTaxIncl.title'),
                                    disabled:disabled
                                }
                            }
                        ]
                     }
                   },
                 ]
             return fields;
          };
        

        var service = {
            getFormFields: getFormFields,
            getTabsFormFields: getTabsFormFields
        };

        return service;

    }

})();
