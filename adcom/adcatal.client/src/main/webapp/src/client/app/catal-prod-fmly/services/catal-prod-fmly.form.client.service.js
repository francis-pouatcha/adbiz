(function() {
    'use strict';

    angular
        .module('app.catalProdFmly')
        .factory('CatalProdFmlyForm', factory);

    factory.$inject = ['$translate','API_BASE_ADCATAL_URL', 'genericResource'];
    /* @ngInject */
    function factory($translate,API_BASE_ADCATAL_URL,genericResource) {

        function getCatlProdFmlys(viewValue){
            return genericResource.findByLikePromissed(API_BASE_ADCATAL_URL+'/catalproductfamilys',
                'identif', viewValue, 'org.adorsys.adcatal.jpa.CatalProdFmlySearchInput')
                .then(function(entitySearchResult){
                    return entitySearchResult.resultList;
                });
        }

        var getFormFields = function(disabled) {

            var fields = [
                {
                	className: 'row',
                    fieldGroup: [
		                {
		                	className: 'col-xs-6',
		                    key: 'identif',
		                    type: 'input',
		                    templateOptions: {
		                        label: $translate.instant('CatalProductFamily_identif_description.title'),
		                        disabled: disabled,
		                        required: true
		                    }
		                },
		                {
		                	className: 'col-xs-6',
		                    key: 'parentIdentif',
		                    type: 'typeahead',
		                    templateOptions: {
		                        label: $translate.instant('CatalProductFamily.parentIdentif'),
		                        disabled: disabled,
		                        options:function(viewValue){
		                            return getCatlProdFmlys(viewValue);
		                        }
		                    }
		                }
		            ]
                },
                {
                	className: 'row',
                    fieldGroup: [
                        {
                        	className: 'col-xs-6',
		                    key: 'famPath',
		                    type: 'input',
		                    modelOptions: {
		                        getterSetter: true
		                    },
		                    templateOptions: {
		                        label: $translate.instant('CatalProductFamily.famPath'),
		                        disabled: true
		                    }
                        }
                    ]
                }
            ];

            return fields;
        };

        var getFormFields = function(disabled) {

            var fields = [
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'identif',
                            templateOptions: {
		                        label: $translate.instant('cntnrIdentif'),
                                disabled:disabled,
                                required:true
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'artName',
                            templateOptions: {
                                label: $translate.instant('CatalArtFeatMapping.artName'),
                                //pattern: '\\d{5}',
                                disabled:disabled,
                                required:true
                            }
                        },
                        {
                            className: 'col-xs-4',
                            type: 'input',
                            key: 'shortName',
                            templateOptions: {
                                label: $translate.instant('Family ShortName'),
                                //pattern: '\\d{5}',
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
		                    key: 'parentIdentif',
		                    type: 'typeahead',
		                    templateOptions: {
		                        label: $translate.instant('CatalProductFamily.parentIdentif'),
		                        disabled: disabled,
		                        options:function(viewValue){
		                            return getCatlProdFmlys(viewValue);
		                        }
		                    }
                        },
                        {
                        	className: 'col-xs-6',
		                    key: 'famPath',
		                    type: 'input',
		                    modelOptions: {
		                        getterSetter: true
		                    },
		                    templateOptions: {
		                        label: $translate.instant('CatalProductFamily.famPath'),
		                        disabled: true
		                    }
                        }
                    ]
                },
                
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-6',
                            key: 'purpose',
                            type: 'textarea',
                            templateOptions: {
                                label: $translate.instant('CatalAbstractFeatMapping.purpose'),
                                disabled: disabled
                            }
                        },
                        {
                            className: 'col-xs-6',
                            key: 'usage',
                            type: 'textarea',
                            templateOptions: {
                                label: $translate.instant('CatalAbstractFeatMapping.usage'),
                                disabled: disabled
                            }
                        }
                    ]
                },
                
                {
                    className: 'row',
                    fieldGroup: [
                        {
                            className: 'col-xs-6',
                            key: 'warnings',
                            type: 'textarea',
                            templateOptions: {
                                label: $translate.instant('CatalAbstractFeatMapping.warnings'),
                                disabled: disabled
                            }
                        },
                        {
                            className: 'col-xs-6',
                            key: 'substances',
                            type: 'textarea',
                            templateOptions: {
                                label: $translate.instant('CatalAbstractFeatMapping.substances'),
                                disabled: disabled
                            }
                        }
                    ]
                }
                
            ];

            return fields;

        };
        


        var catalProdFmlyId = '';

        var service = {
            getFormFields: getFormFields,
            catalProdFmlyId: catalProdFmlyId
        };

        return service;

    }

})();
