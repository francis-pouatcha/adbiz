(function() {
    'use strict';

    angular
        .module('app.catalProdFmly')
        .factory('CatalProdFmlyForm', factory);

    function factory() {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'parentIdentif',
                    type: 'input',
                    templateOptions: {
                        label: 'parentIdentif:',
                        disabled: disabled,
                        required: true
                    }
                },
                {
                    key: 'famPath',
                    type: 'input',
                    templateOptions: {
                        label: 'famPath:',
                        disabled: disabled
                    }
                }
            ];

            return fields;

        };

        var catalProdFmlyId = "";

        var service = {
            getFormFields: getFormFields,
            catalProdFmlyId: catalProdFmlyId
        };

        return service;

    }

})();
