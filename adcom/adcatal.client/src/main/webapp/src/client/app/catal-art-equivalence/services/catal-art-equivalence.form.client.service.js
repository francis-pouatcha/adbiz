(function() {
    'use strict';

    angular
        .module('app.catalArtEquivalence')
        .factory('CatalArtEquivalenceForm', factory);

    factory.$inject = ['$translate'];
    /* @ngInject */
    function factory($translate) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'mainArtIdentif',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtEquivalence.mainArtIdentif'),
                        disabled: true,
                        required: true
                    }
                },
                {
                    key: 'equivArtIdentif',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArtEquivalence.equivArtIdentif'),
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
