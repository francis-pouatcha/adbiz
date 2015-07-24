(function() {
    'use strict';

    angular
        .module('app.catalArtEquivalence')
        .factory('CatalArtEquivalenceForm', factory);

    function factory() {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'mainArtIdentif',
                    type: 'input',
                    templateOptions: {
                        label: 'mainArtIdentif:',
                        disabled: true,
                        required: true
                    }
                },
                {
                    key: 'equivArtIdentif',
                    type: 'input',
                    templateOptions: {
                        label: 'equivArtIdentif:',
                        disabled: disabled,
                        required: true
                    }
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
