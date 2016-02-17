'use strict';

angular.module('app.core')

.directive('stSelectOne', [ function () {
    return {
        restrict: 'E',
        require: '^stTable',
        scope: {
            collection: '=',
            predicate: '@',
            predicateExpression: '='
        },
        templateUrl: '/adres.client/views/stSelectOne.html',
        link: function(scope, element, attr, table) {
            var getPredicate = function() {
                var predicate = scope.predicate;
                if (!predicate && scope.predicateExpression) {
                    predicate = scope.predicateExpression;
                }
                return predicate;
            }
            scope.$watch('collection', function(newValue) {
                var predicate = getPredicate();
                if (newValue) {
                    var temp = [];
                    scope.distinctItems = [];
                    angular.forEach(scope.collection, function(value) {
                        if (value && angular.isObject(value) ) {
                            temp.push(value);
                        }
                    });
                    temp.sort();
                    scope.distinctItems = temp; //scope.distinctItems.concat(temp);
                    if (angular.isUndefined(scope.selectedOption) || scope.selectedOption === null) {
                        scope.selectedOption = scope.distinctItems[0].enumKey;
                    }
                    scope.optionChanged(scope.selectedOption);
                }
            }, true);

          scope.optionChanged = function(selectedOption) {
              var predicate = getPredicate();
              var query = selectedOption;
              if (query === 'All') {
                  query = '';
              }
              table.search(query, predicate);
          };
        }
    }
}])