'use strict';

angular.module('app.core')

.directive('stDateRange', [ function () {
    return {
        restrict: 'E',
        require: '^stTable',
        scope: {
            before: '=',
            after: '='
        },
        templateUrl: '/adres.client/views/stDateRange.html',
        link: function (scope, element, attr, table) {
            var inputs = element.find('input');
            var inputBefore = angular.element(inputs[0]);
            var inputAfter = angular.element(inputs[1]);
            var predicateName = attr.predicate;

            [inputBefore, inputAfter].forEach(function (input) {
                input.bind('change blur', function () {
                    var query = {};
                    if (!scope.isBeforeOpen && !scope.isAfterOpen) {
                        if (scope.before) {
                            query.before = scope.before;
                        }
                        if (scope.after) {
                            query.after = scope.after;
                        }
                        scope.$apply(function () {
                            table.search(query, predicateName);
                        })
                    }
                });
            });
            function open(before) {
                return function ($event) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    if (before) {
                        scope.isBeforeOpen = true;
                    } else {
                        scope.isAfterOpen = true;
                    }
                }
            }
            scope.openBefore = open(true);
            scope.openAfter = open();
        }
    }
}])
.directive('stDateSearch', [ function () {
    return {
        restrict: 'E',
        require: '^stTable',
        scope: {
        	selectedDate: '='
        },
        templateUrl: '/adres.client/views/stDateSearch.html',
        link: function (scope, element, attr, table) {
            var inputs = element.find('input');
            var inputDate = angular.element(inputs[0]);
            var predicateName = attr.predicate;

            [inputDate].forEach(function (input) {
                input.bind('change blur', function () {
                    var query = {}, isFieldNotEmpty = false;
                    if (!scope.isDatepickerOpen) {
                        if (scope.selectedDate) {
                            query = scope.selectedDate;
                            isFieldNotEmpty = true
                        }
                        scope.$apply(function () {
                        	if (isFieldNotEmpty) {
                        		table.search(query, predicateName);
							} else {
								delete table.tableState().search.predicateObject[predicateName];
								table.search('', '');
							}
                        })
                    }
                });
            });
            function open() {
                return function ($event) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    scope.isDatepickerOpen = true;
                }
            }
            scope.openDatepicker = open();
        }
    }
}])