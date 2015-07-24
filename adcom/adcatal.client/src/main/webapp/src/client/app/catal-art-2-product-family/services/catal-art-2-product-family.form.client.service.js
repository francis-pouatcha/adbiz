(function() {
    'use strict';

    angular
        .module('app.catalArt2ProductFamily')
        .factory('CatalArt2ProductFamilyForm', factory);

    function factory() {

        var getFormFields = function(disabled) {

            var fields = [
                {
                    key: 'artPic',
                    type: 'input',
                    templateOptions: {
                        label: 'artPic:',
                        disabled: true
                    }
                },
                {
                    key: 'famCode',
                    type: 'input',
                    templateOptions: {
                        label: 'famCode:',
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
