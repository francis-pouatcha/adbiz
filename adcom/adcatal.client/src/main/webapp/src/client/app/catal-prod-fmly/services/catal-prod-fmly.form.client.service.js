(function() {
    'use strict';

    angular
        .module('app.catalProdFmly')
        .factory('CatalProdFmlyForm', factory);

    factory.$inject = ['$translate'];
    /* @ngInject */
    function factory($translate) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'parentIdentif',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalProductFamily.parentIdentif'),
                        disabled: disabled,
                        required: true
                    }
                },
                {
                    key: 'famPath',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalProductFamily.famPath'),
                        disabled: disabled
                    }
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
