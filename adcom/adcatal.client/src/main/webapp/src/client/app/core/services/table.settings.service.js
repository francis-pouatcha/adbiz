/* jshint ignore:start */

(function() {
    'use strict';

    angular
        .module('app.core')
        .factory('TableSettings', factory);

    factory.$inject = ['ngTableParams'];
    /* @ngInject */
    function factory(ngTableParams) {

        var previousEntity;

        var getData = function(Entity) {
            return function($defer, params) {

                var requestParams = convertToServerRequestParams(params.$params);

                if (previousEntity !== Entity) {
                    previousEntity = Entity;
                    tableParams.$params.filter = {};
                }
                console.log(requestParams);
                Entity.get(requestParams, function(response) {
                    params.total(response.total);
                    $defer.resolve(response.resultList);
                });
            };
        };

        var getDataWithFind = function(Entity, coreSearchInput) {
            return function($defer, params) {
                Entity.findBy(coreSearchInput, function(response) {
                    params.total(response.total);
                    $defer.resolve(response.resultList);
                });
            };
        };

        var params = {
            page: 1,
            count: 10
        };

        var settings = {
            total: 0,
            counts: [5, 10, 15],
            filterDelay: 0
        };

        var tableParams = new ngTableParams(params, settings);

        var getParams = function(Entity) {
            tableParams.settings({getData: getData(Entity)});
            return tableParams;
        };
        var getParamsWithFind = function(Entity, coreSearchInput) {
            tableParams.settings({getData: getDataWithFind(Entity, coreSearchInput)});
            return tableParams;
        };

        var service = {
            getParams: getParams,
            getParamsWithFind: getParamsWithFind
        };

        return service;

    }

    function convertToServerRequestParams(tableParams) {
        var serverParams = {};
        var filterServerParams = getFilterServerParams(tableParams);
        var keySort = Object.keys(tableParams.sorting)[0];

        if (filterServerParams) {
            serverParams.where = filterServerParams;
        };

        if (keySort) {
            serverParams.sort = keySort + ' ' + tableParams.sorting[keySort];
        };

        serverParams.max = tableParams.count;
        serverParams.start = (tableParams.page - 1) * tableParams.count;

        return serverParams;
    }

    function getFilterServerParams(tableParams) {
        var tableFilters = tableParams.filter;
        var tableFiltersNumber = Object.keys(tableFilters).length;
        var filterServerParams;

        if (tableFiltersNumber === 1) {
            var key = Object.keys(tableFilters)[0];
            filterServerParams = getFilterServer(key, tableFilters);
        }
        else if (tableFiltersNumber > 1) {
            var arrayFilters = [];
            var keys = Object.keys(tableFilters);
            var filter;

            keys.forEach(function(key) {
                filter = getFilterServer(key, tableFilters);
                arrayFilters.push(filter);
            });

            filterServerParams = {
                'and': arrayFilters
            };
        }

        return filterServerParams;
    }

    function getFilterServer(key, tableFilters) {
        var filterServer = {};

        filterServer[key] = {
            'contains':tableFilters[key]
        };

        return filterServer;
    }

})();
