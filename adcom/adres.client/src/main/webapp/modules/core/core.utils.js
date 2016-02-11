/**
 * @author guymoyo
 * @name utils
 */
(function() {
    'use strict';

    angular
        .module('app.core')
        .factory('utils', utils);

    utils.$inject = ['$modal'];
    /* @ngInject */
    function utils($modal) {

        var service = {};

        function ModalInstanceCtrl($modalInstance, model) {
            var vm = this;
            vm.model = model;
            // function assignment
            vm.ok = ok;
            vm.cancel = cancel;

            // function definition
            function ok() {
                $modalInstance.close(vm.model);
            }

            function cancel() {
                $modalInstance.dismiss('cancel');
            }

        }

        service.templateModal = function(model, typeForm, templateUrl, parentCtrl) {
            var result = $modal.open({
                templateUrl: templateUrl,
                controller: ModalInstanceCtrl,
                controllerAs: 'vmModal',
                resolve: {
                    model: function() {
                        return model;
                    }
                }
            }).result;

            if (typeForm === 'createForm') {
                result.then(function(model) {
                    parentCtrl.create(model);
                });
            }
            if (typeForm === 'editForm') {
                result.then(function(model) {
                    parentCtrl.update(model);
                });
            }
        };


        service.searchInputInit= function(){
            var itemsPerPageVar = 10;
            var currentVar = 1;
            var maxSizeVar = 10;
            var searchInputVar = {
                searchInput:{
                    entity: {},
                    start: 0,
                    max: itemsPerPageVar,
                    fieldNames: [],
                    sortFieldNames: [],
                    className: ''
                },
                pagination:{
                    current: currentVar,
                    itemsPerPage: itemsPerPageVar,
                    maxSize: maxSizeVar
                },
                stPagination: {
                	start: 0,
                	number: itemsPerPageVar // Number of entries showed per page.
                }

            };
            return searchInputVar;
        };

        service.resetPagination = function(pagination){
            var itemsPerPageVar = 10;
            var currentVar = 1;
            var maxSizeVar = 10;
            pagination.current = currentVar;
            pagination.itemsPerPage = itemsPerPageVar;
            pagination.maxSize = maxSizeVar;
            return pagination;
        }

        service.pagination = function(searchInput, pagination, currentIn){
            searchInput.start = ((pagination.current -1) * pagination.itemsPerPage);
            pagination.current = currentIn;
        };

        service.getFieldsFromObject = function(object){
            var props = Object.keys(object);
            return props;
        };



        service.processSearch = function(searchInput, object){
        	if (searchInput) {
        		searchInput.entity = {}
        		searchInput.fieldNames = [];
        	}
            for(var key in object){
                if(object.hasOwnProperty(key)){
                    var keyValue = Object.getOwnPropertyDescriptor(object, key).value;
                    if(keyValue){
                        Object.defineProperty(searchInput.entity, key,
                            {value: keyValue, writable: true, enumerable: true, configurable: true});
                        searchInput.fieldNames.push(key);
                    }
                }
            }
            return searchInput;
        };

        service.fixDateField = function(dateField){
            if(angular.isUndefined(dateField) || !dateField) return dateField;

            if(Object.prototype.toString.call(dateField) === '[object Date]')return dateField;

            var dtObject = new Date();
            if(service.isNumber(dateField)){
                dtObject.setTime(dateField);
            } else {
                var dateMilis =Date.parse(dateField);
                dtObject.setTime(dateMilis);
            }
            return dtObject;
        };

        service.greaterThan = function(a,b){
            if(typeof a === 'undefined') return false;
            if(typeof b === 'undefined') return a>-1;
            return a>b;
        };
        service.isNumber = function (n) {
            return !isNaN(parseFloat(n)) && isFinite(n);
        };
        service.formatDate= function(fieldName, inPattern){
            var pattern = '';
            if(!inPattern) pattern = defaultDatePattern;
            else pattern = inPattern;
            return dateFormat(fieldName, pattern, '');
        };

        return service;

    }

    angular
        .module('app.core')
        .factory('fileExtractor', ['$window', function($window) {

            var  service = {
                extractFile : extractFile,
                saveFile:saveFile

            };
            return service ;
            //////////////////////////////////////////////////////////////////////////////////////////////////
            function extractFile(data, fileType)
            {
                var file = new Blob([data], {type: fileType});
                var fileURL = URL.createObjectURL(file);
                $window.open(fileURL);
            }

            function saveFile(data, fileName, fileType) {
                var file = new Blob([data], {type: fileType});
                saveAs(file, fileName);//jshint ignore:line
            }

        }]);

    angular
        .module('app.core')
        .factory('genericResource',['$http','$q', function($http,$q){
            var service = {};

            service.create = function(urlBase, entity){
                return $http.post(urlBase,entity);
            };

            service.update = function(urlBase, entity){
                return $http.put(urlBase+'/'+entity.id,entity);
            };

            service.findBy = function(urlBase, entitySearchInput){
                return $http.post(urlBase+'/findBy',entitySearchInput);
            };

            service.findByLike = function(urlBase, entitySearchInput){
                return $http.post(urlBase+'/findByLike',entitySearchInput);
            };

            service.builfReport = function(urlBase, entitySearchInput){
                return $http.post(urlBase, entitySearchInput,{responseType: 'arraybuffer'});
            };

            service.builfReportGet = function(urlBase, id){
                return $http.get(urlBase+"/"+id,{responseType: 'arraybuffer'});
            };

            service.findByLikePromissed = function (urlBase, fieldName, fieldValue){
                if(angular.isUndefined(urlBase) || !urlBase ||
                    angular.isUndefined(fieldName) || !fieldName ||
                    angular.isUndefined(fieldValue) || !fieldValue) return;

                var searchInput = {entity:{},fieldNames:[],start:0,max:10};
                searchInput.entity[fieldName]= fieldValue;
                if(searchInput.fieldNames.indexOf(fieldName)==-1)
                    searchInput.fieldNames.push(fieldName);
                var deferred = $q.defer();
                service.findByLike(urlBase, searchInput)
                    .success(function(entitySearchResult) {
                        deferred.resolve(entitySearchResult);
                    })
                    .error(function(error){
                        deferred.reject('No entity found');
                    });
                return deferred.promise;
            }
            service.findByLikeWithSearchInputPromissed = function (urlBase, searchInput){
                if(angular.isUndefined(urlBase) || !urlBase ||
                    angular.isUndefined(searchInput) || !searchInput) return;
                var deferred = $q.defer();
                service.findByLike(urlBase, searchInput)
                    .success(function(entitySearchResult) {
                        deferred.resolve(entitySearchResult);
                    })
                    .error(function(error){
                        deferred.reject('No entity found');
                    });
                return deferred.promise;
            }

            service.findById = function(urlBase, entityId){
                return $http.get(urlBase+'/'+entityId);
            };

            service.findByIdentif = function(urlBase, identif){
                return $http.get(urlBase+'/findByIdentif/'+identif);
            };

            service.findCustom = function(urlBase, entitySearchInput){
                return $http.post(urlBase+'/findCustom',entitySearchInput);
            };
            service.findCustomPromissed = function (urlBase, fieldName, fieldValue){
                if(angular.isUndefined(urlBase) || !urlBase ||
                    angular.isUndefined(fieldName) || !fieldName ||
                    angular.isUndefined(fieldValue) || !fieldValue) return;

                var searchInput = {entity:{},fieldNames:[],start:0,max:10};
                searchInput.entity[fieldName]= fieldValue;
                if(searchInput.fieldNames.indexOf(fieldName)==-1)
                    searchInput.fieldNames.push(fieldName);
                var deferred = $q.defer();
                service.findCustom(urlBase, searchInput)
                    .success(function(entitySearchResult) {
                        deferred.resolve(entitySearchResult);
                    })
                    .error(function(error){
                        deferred.reject('No entity found');
                    });
                return deferred.promise;
            }
            service.findCustomWithSearchInputPromissed = function (urlBase, searchInput){
                if(angular.isUndefined(urlBase) || !urlBase ||
                    angular.isUndefined(searchInput) || !searchInput) return;
                var deferred = $q.defer();
                service.findCustom(urlBase, searchInput)
                    .success(function(entitySearchResult) {
                        deferred.resolve(entitySearchResult);
                    })
                    .error(function(error){
                        deferred.reject('No entity found');
                    });
                return deferred.promise;
            }

            service.find = function(urlBase, entitySearchInput){
                return $http.post(urlBase,entitySearchInput);
            };
            service.customMethod = function(urlBase, entitySearchInput){
                return $http.post(urlBase,entitySearchInput);
            };
            service.listAll = function(urlBase){
                return $http.get(urlBase);
            };
            service.deleteById = function(urlBase, entityId){
                return $http.delete(urlBase+'/'+entityId);
            };
            // execute a simple get
            service.get = function(urlBase) {
                return $http.get(urlBase);
            }
            return service;

        }]);

    angular.module('app.core')
        .directive('adEnter', function () {
            return function (scope, element, attrs) {
                element.bind("keydown keypress", function (event) {
                    if(event.which === 13) {
                        scope.$apply(function (){
                            scope.$eval(attrs.adEnter);
                        });
                        event.preventDefault();
                    }
                });
            };
        });
    angular.module('app.core')
        .directive('adFocus', function($timeout) {
            return {
                link: function ( scope, element, attrs ) {
                    scope.$watch( attrs.adFocus, function ( val ) {
                        if ( angular.isDefined( val ) && val ) {
                            $timeout( function () { element[0].focus(); } );
                        }
                    }, true);
                }
            };
        });

    angular
     .module('app.core')
    .factory('searchResultHandler',['utils',function(utils){
        var service = {};
        service.newResultHandler = function(keyField){
            return new ResultHandler(keyField);
        };

        var ResultHandler = function(keyFieldIn){
            var handler = this;
            var itemPerPageVar = 10;
            var currentPageVar = 1;
            var nakedSearchResult = {
                count:0,resultList:[],
                searchInput:{
                    entity:{},
                    start:0, max:itemPerPageVar,
                    fieldNames:[]
                },
                // not exposed to the server environment.
                currentPage:currentPageVar,itemPerPage:itemPerPageVar,selectedIndex:-1
            };
            var searchResultVar = angular.copy(nakedSearchResult);
            var keyField = keyFieldIn;
            var displayInfoVar = {};
            var equalsFnct = function(entityA, entityB){
                if(!entityA && !entityB) return true;
                if(!entityB) return false;
                return entityA[keyField]==entityB[keyField];
            };
            var dependents = {};
            function setSelectedIndex(selectedIndexIn){
                searchResultVar.selectedIndex=selectedIndexIn;
                dependents = {};
            }
            this.searchResult = function(searchResultIn){
                if(searchResultVar===searchResultIn) return;
                if(angular.isUndefined(searchResultIn)) return;

                searchResultVar.count = searchResultIn.count;

// displayInfoVar = {};

                angular.copy(searchResultIn.resultList, searchResultVar.resultList);

                angular.copy(searchResultIn.searchInput, searchResultVar.searchInput);

                if(angular.isDefined(searchResultIn.currentPage)){
                    searchResultVar.currentPage=searchResultIn.currentPage;
                } else {
                    searchResultVar.currentPage=currentPageVar;
                }

                if(angular.isDefined(searchResultIn.itemPerPage)){
                    searchResultVar.itemPerPage=searchResultIn.itemPerPage;
                } else {
                    searchResultVar.itemPerPage=itemPerPageVar;
                }

                if(angular.isDefined(searchResultIn.selectedIndex)){
                    setSelectedIndex(searchResultIn.selectedIndex);
                } else {
                    setSelectedIndex(-1);
                }
            };
            this.hasEntities = function(){
                return searchResultVar.resultList && searchResultVar.resultList.length>0;
            };
            this.entities = function(){
                return searchResultVar.resultList;
            };
            this.selectedIndex= function(selectedIndexIn){
                if(utils.greaterThan(selectedIndexIn) && angular.isDefined(searchResultVar.resultList[selectedIndexIn])){
                    setSelectedIndex(selectedIndexIn);
                }
                return searchResultVar.selectedIndex;
            };
            this.selectedObject= function(selectedIn){
                if(angular.isUndefined(selectedIn))return searchResultVar.selectedIndex;
                var length = searchResultVar.resultList.length;
                for	(var index = 0; index < length; index++) {
                    if(!equalsFnct(selectedIn, searchResultVar.resultList[index])) continue;
                    searchResultVar.selectedIndex = index;
                    return searchResultVar.selectedIndex;
                }
                length = searchResultVar.resultList.push(selectedIn);
                searchResultVar.count +=1;
                searchResultVar.selectedIndex = length -1;
                return searchResultVar.selectedIndex;
            };
            this.entity = function(){
                if(!utils.greaterThan(searchResultVar.selectedIndex)) return;
                if(angular.isUndefined(searchResultVar.resultList[searchResultVar.selectedIndex])) return;
                return searchResultVar.resultList[searchResultVar.selectedIndex];
            };
            this.totalItems = function(){
                return searchResultVar.count;
            };
            this.currentPage = function(currentPageIn){
                if(utils.greaterThan(currentPageIn,-1)) searchResultVar.currentPage=currentPageIn;
                return searchResultVar.currentPage;
            };
            this.maxResult = function(maxResultIn) {
                if(utils.greaterThan(maxResultIn,-1)) searchResultVar.searchInput.max=maxResultIn;
                return searchResultVar.searchInput.max;
            };
            this.itemPerPage = function(itemPerPageIn){
                if(utils.greaterThan(itemPerPageIn,-1))searchResultVar.itemPerPage=itemPerPageIn;
                return searchResultVar.itemPerPage;
            };
            this.searchInput = function(searchInputIn){
                if(angular.isUndefined(searchInputIn))
                    return angular.copy(searchResultVar.searchInput);

                angular.copy(searchInputIn, searchResultVar.searchInput);
                return searchInputIn;
            };
            this.searchInputChanged = function(searchInputIn){
                return angular.equals(searchResultVar.searchInput, searchInputIn);
            };
            this.newSearchInput = function(){
                return angular.copy(nakedSearchResult.searchInput);
            };
            this.paginate = function(){
                searchResultVar.searchInput.start = ((searchResultVar.currentPage - 1)  * searchResultVar.itemPerPage);
                searchResultVar.searchInput.max = searchResultVar.itemPerPage;
                return handler.searchInput();
            };
            this.indexOf = function(entity){
                if(angular.isUndefined(entity) || !entity) return -1;
                var length = searchResultVar.resultList.length;
                for	(var index = 0; index < length; index++) {
                    if(equalsFnct(entity, searchResultVar.resultList[index])) return index;
                }
                return -1;
            };
            // Replaces the object at the specified index.
            this.replace = function(entity){
                if(angular.isUndefined(entity) || !entity) return;
                var index = handler.indexOf(entity);
                if(angular.isUndefined(index) || index<0) return;
                searchResultVar.resultList[index]=entity;
                return index;
            };
            // Add the specified entity to the end of the list. Remove the copy
            // from the array if existent.
            this.push = function(entity){
                if(angular.isUndefined(entity) || !entity) return;
                var index = handler.indexOf(entity);
                if(angular.isDefined(index) && index>=0){ // slice
                    searchResultVar.resultList.splice(index, 1);
                    searchResultVar.count -=1;
                }
                var length = searchResultVar.resultList.push(entity);
                searchResultVar.count +=1;
                return length -1;
            };
            // Add the specified entity to the start of the list. Remove the copy
            // from the array if existent.
            this.unshift = function(entity){
                if(angular.isUndefined(entity) || !entity) return;
                var index = handler.indexOf(entity);
                if(utils.greaterThan(index) && index>=0){ // slice
                    searchResultVar.resultList.splice(index, 1);
                    searchResultVar.count -=1;
                }
                var length = searchResultVar.resultList.unshift(entity);
                searchResultVar.count +=1;
                return 0;
            }
            this.previous = function (){
                if(searchResultVar.resultList.length<=0) return;

                if(searchResultVar.selectedIndex<=0){
                    setSelectedIndex(searchResultVar.resultList.length-1);
                } else {
                    setSelectedIndex(searchResultVar.selectedIndex-=1);
                }
                return handler.entity();
            };
            this.next = function(){
                if(searchResultVar.resultList.length<=0) return;

                if(searchResultVar.selectedIndex>=searchResultVar.resultList.length-1 || searchResultVar.selectedIndex<0){
                    setSelectedIndex(0);
                } else {
                    setSelectedIndex(searchResultVar.selectedIndex+=1);
                }

                return handler.entity();
            };
            this.unsetDependent = function(fieldName){
                if(angular.isUndefined(fieldName)) return;

                if(angular.isDefined(dependents[fieldName]))
                    delete dependents[fieldName];
            };
            this.dependent = function(fieldName, dependentIn){
                if(angular.isUndefined(fieldName)) return;

                if(angular.isDefined(dependentIn) && dependentIn)
                    dependents[fieldName] = dependentIn;

                return dependents[fieldName];
            };
            this.displayInfo = function(displayInfoIn){
                if(angular.isDefined(displayInfoIn) && displayInfoIn)
                    displayInfoVar = displayInfoIn;
                return displayInfoVar;
            };
        };

        return service;

    }])

})();
