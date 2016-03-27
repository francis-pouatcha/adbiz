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
            	  className: 'row',
                  fieldGroup: [
   		                {
		                	className: 'col-xs-12',
   		                    key: 'artIdentif',
   		                    type: 'input',
   		                    hide: true,
   		                    templateOptions: {
   		                        label: $translate.instant('CatalPicMapping.artIdentif'),
   		                        disabled: true,
   		                        required: true
   		                    }
   		                }
       		       ]

              },
              {
                  className: 'row',
                  fieldGroup: [
		                {
		                	className: 'col-xs-4',
		                    key: 'code',
		                    type: 'input',
		                    templateOptions: {
		                        label: $translate.instant('CatalPicMapping.code'),
		                        disabled: disabled,
		                        required: true
		                    }
		                },
		                {
		                	className: 'col-xs-8',
		                    key: 'codeOrigin',
		                    type: 'select',
		                    templateOptions: {
		                        label: $translate.instant('CatalPicMapping.codeOrigin'),
		                        disabled: disabled,
		                        required: true,
		                        options: [
		                            {name: $translate.instant('CatalCipOrigine_MAIN_description'), value: 'MAIN'},
		                            {name: $translate.instant('CatalCipOrigine_SUPPLIER_description'), value: 'SUPPLIER'},
		                            {name: $translate.instant('CatalCipOrigine_DETAIL_description'), value: 'DETAIL'},
		                            {name: $translate.instant('CatalCipOrigine_MANUFACTURER_description'), value: 'MANUFACTURER'},
		                            {name: $translate.instant('CatalCipOrigine_RESALER_description'), value: 'RESALER'},
		                            {name: $translate.instant('CatalCipOrigine_GOVERNMENT_description'), value: 'GOVERNMENT'},
		                            {name: $translate.instant('CatalCipOrigine_BROKER_description'), value: 'BROKER'},
		                            {name: $translate.instant('CatalCipOrigine_CUSTOMERSERVICE_description'), value: 'CUSTOMERSERVICE'},
		                            {name: $translate.instant('CatalCipOrigine_CUSTOMER_description'), value: 'CUSTOMER'},
		                            {name: $translate.instant('CatalCipOrigine_INSURANCE_description'), value: 'INSURANCE'}
		                        ]
		                    }
		                }
		          ]
              },
              
              {
                  className: 'row',
                  fieldGroup: [
              
		                {
		                	className: 'col-xs-4',
		                    key: 'bpIdentif',
		                    type: 'input',
		                    templateOptions: {
		                        label: $translate.instant('CatalPicMapping.bpIdentif'),
		                        disabled: disabled,
		                        required: true
		                    }
		                },
		                {
		                	className: 'col-xs-8',
		                    key: 'bpName',
		                    type: 'input',
		                    templateOptions: {
		                        label: $translate.instant('CatalPicMapping.bpName'),
		                        disabled: disabled,
		                        required: true
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
		                    key: 'unitPrice',
		                    templateOptions: {
		                        type:'number',
		                        label: $translate.instant('CatalPicMapping.unitPrice'),
		                        disabled:disabled
		                    }
		                },
		                {
		                	className: 'col-xs-4',
		                    key: 'currIso3',
		                    type: 'input',
		                    templateOptions: {
		                        label: $translate.instant('CatalPicMapping.currIso3'),
		                        disabled: disabled,
		                        required: true
		                    }
		                },
		                {
		                	className: 'col-xs-4',
		                    key: 'vatRate',
		                    type: 'input',
		                    templateOptions: {
		                        label: $translate.instant('CatalPicMapping.vatRate'),
		                        disabled: disabled,
		                        required: true
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
		                    key: 'returnDays',
		                    templateOptions: {
		                        type:'number',
		                        label: $translate.instant('CatalPicMapping.returnDays'),
		                        disabled:disabled
		                    }
		                },
		                {
		                	className: 'col-xs-4',
		                    type: 'input',
		                    key: 'warrantyMonths',
		                    templateOptions: {
		                        type:'number',
		                        label: $translate.instant('CatalPicMapping.warrantyMonths'),
		                        disabled:disabled
		                    }
		                },
		                {
		                	className: 'col-xs-4',
		                    type: 'input',
		                    key: 'maxDisctRate',
		                    templateOptions: {
		                        type:'number',
		                        label: $translate.instant('CatalPicMapping.maxDisctRate'),
		                        disabled:disabled
		                    }
		                }
		           ]
              },
              
              {
                  className: 'row',
                  fieldGroup: [
		                {
		                	className: 'col-xs-12',
		                    key: 'addInfo',
		                    type: 'textarea',
		                    templateOptions: {
		                        label: $translate.instant('CatalPicMapping.addInfo'),
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
