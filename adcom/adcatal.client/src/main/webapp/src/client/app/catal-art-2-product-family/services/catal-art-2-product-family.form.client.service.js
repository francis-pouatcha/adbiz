(function() {
    'use strict';

    angular
        .module('app.catalArt2ProductFamily')
        .factory('CatalArt2ProductFamilyForm', factory);
    factory.$inject = ['$translate','CatalProdFmly'];

    function factory($translate, CatalProdFmly) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'cntnrIdentif',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArt2ProductFamily.artPic'),
                        disabled: true
                    }
                },
                {
	                  key: 'famCode',
	                  type: 'typeahead',
	                  templateOptions: {
	                      label: $translate.instant('CatalArt2ProductFamily.famCode'),
	                      disabled: disabled,
	                      required: true
	                  },
	                  controller: /* @ngInject */
	                    function($scope, CatalProdFmly) {
	                        var coreSearchInput = {};
	                        coreSearchInput.start = 0;
	                        coreSearchInput.max = 10;
	                        coreSearchInput.entity = {};
	                        coreSearchInput.fieldNames = [];
	                        coreSearchInput.className = 'org.adorsys.adcatal.jpa.CatalProdFmlyLangMapSearchInput';
	                        coreSearchInput.fieldNames.push('identif');
	
	                        $scope.to.options = function(value) {
	                            coreSearchInput.entity.identif = value;
	                            return CatalProdFmly.findByLike(coreSearchInput)
	                                .$promise.then(function(response) {
	                                    return response.resultList;
	
	                                });
	                        	};
                  		}
                }
                /*,
                {
                    key: 'artName',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtFeatMapping.artName'),
                        disabled: true
                    }
                },
                {
                    key: 'shortName',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtFeatMapping.shortName'),
                        disabled: true
                    }
                },
                {
                    key: 'langIso2',
                    type: 'select',
                    templateOptions: {
                        label: $translate.instant('langIso2'),
                        disabled: true,
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
                        disabled: true
                    }
                },
                {
                    key: 'usage',
                    type: 'textarea',
                    templateOptions: {
                        label: $translate.instant('CatalAbstractFeatMapping.usage'),
                        disabled: true
                    }
                },
                {
                    key: 'warnings',
                    type: 'textarea',
                    templateOptions: {
                        label: $translate.instant('CatalAbstractFeatMapping.warnings'),
                        disabled: true
                    }
                },
                {
                    key: 'substances',
                    type: 'textarea',
                    templateOptions: {
                        label: $translate.instant('CatalAbstractFeatMapping.substances'),
                        disabled: true
                    }
                }*/
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
