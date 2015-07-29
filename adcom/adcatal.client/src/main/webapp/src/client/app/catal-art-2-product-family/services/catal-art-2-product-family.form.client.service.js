(function() {
    'use strict';

    angular
        .module('app.catalArt2ProductFamily')
        .factory('CatalArt2ProductFamilyForm', factory);
    factory.$inject = ['$translate'];

    function factory($translate) {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'artPic',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArt2ProductFamily.artPic'),
                        disabled: true
                    }
                },
                {
                    key: 'famCode',
                    type: 'input',
                    templateOptions: {
                        label: $translate.instant('CatalArt2ProductFamily.famCode'),
                        disabled: disabled
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
