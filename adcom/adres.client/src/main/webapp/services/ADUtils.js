/**
 * @author Francis Pouatcha
 * @name ADUtils
 */

'use strict';

angular.module('ADUtils',[])
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
})
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
})
.directive('roundConverter', function () {
	
	function isInvalid(number) {
		return (/[^\d^,^.]/).test(number);
	}
	
	function unformat(number, O) {
		return accounting.unformat(accounting.toFixed(number, O));
	}
	
	function makeParserOrFormatter(ngModel, f, decimals) {
		return function (value) {
			if (ngModel.$isEmpty(value)) {
				return value;
		}
		
		if (isInvalid(value)) {
			ngModel.$setValidity('numberFormat', false);
			return undefined;
		}
		
		ngModel.$setValidity('numberFormat', true);
			return f(value, decimals);
		};
	}
	
	function makeEventHandler(element, ngModel, f, decimals) {
		return function () {
			var value = element.val();
			
			if (ngModel.$invalid || ngModel.$isEmpty(value)) {
				return;
			}
			element.val(f(value, decimals));
		};
	}
	
	return {
		require: 'ngModel',
		restrict: 'A',
		link: function (scope, element, attrs, ngModel) {
			var decimals = 0;
			ngModel.$render = function () {
				if (ngModel.$isEmpty(ngModel.$viewValue)) {
					return;
				}
				
				element.val(accounting.formatNumber(ngModel.$viewValue, decimals));
			};
			
			ngModel.$formatters.unshift(makeParserOrFormatter(ngModel, accounting.formatNumber, decimals));
			ngModel.$parsers.unshift(makeParserOrFormatter(ngModel, unformat, decimals));
			element.on('change blur', makeEventHandler(element, ngModel, accounting.formatNumber, decimals));
			element.on('focus', makeEventHandler(element, ngModel, unformat, decimals));
			
			element.on('$destroy', function () {
				 element.off('change blur focus');
			});
		}
	};
})
.directive('numeric', ['$filter', function ($filter) {
    return {
        require: 'ngModel',
        link: function(scope, element, attrs,modelCtrl) {

                $(element[0]).numericInput({ allowFloat: true });

                var format = function (inputValue) {
                    if (angular.isUndefined(inputValue))
                        return;

                    function addCommas(number, decimals, dec_point, thousands_sep)
                    {
                        number = (number + '')
                            .replace(/[^0-9+\-Ee.]/g, '');
                        var n = !isFinite(+number) ? 0 : +number,
                            prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
                            sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
                            dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
                            s = '',
                            toFixedFix = function(n, prec) {
                                var k = Math.pow(10, prec);
                                return '' + (Math.round(n * k) / k)
                                    .toFixed(prec);
                            };
                        // Fix for IE parseFloat(0.55).toFixed(0) = 0;
                        s = (prec ? toFixedFix(n, prec) : '' + Math.round(n))
                            .split('.');
                        if (s[0].length > 3) {
                            s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
                        }
                        if ((s[1] || '')
                            .length < prec) {
                            s[1] = s[1] || '';
                            s[1] += new Array(prec - s[1].length + 1)
                                .join('0');
                        }
                        return s.join(dec);

                    }


                    var formatNb = addCommas(inputValue,0,'.',' ')
                    if (formatNb !== inputValue) {

                        modelCtrl.$setViewValue(formatNb);

                        modelCtrl.$render();
                    }
                    var str = inputValue.replace(/\s/g, '');
                    return str;
                }
                modelCtrl.$parsers.push(format);

        }
    };
}])
.filter('yesNo', function() {
    return function(input) {
        return input ? 'true' : 'false';
    };
})
.filter('simplePrice', function() {
    return function(number) {
   	  number = accounting.toFixed(number, 0);
      return accounting.formatNumber(number, { precision : 0, thousand: " ", decimal : "."});
    };
  })
.filter('currencyAccounting', function() {
    return function(number, currencyCode) {
    	
    	accounting.settings = {
    			currency: {
    				symbol : "XAF",   // default currency symbol is '$'
    				format: "%v %s", // controls output: %s = symbol, %v =
										// value/number (can be object: see
										// below)
    				decimal : ".",  // decimal point separator
    				thousand: " ",  // thousands separator
    				precision : 0   // decimal places
    			},
    			number: {
    				precision : 0,  // default precision on numbers is 0
    				thousand: " ",
    				decimal : "."
    			}
    		};
    	
   	  number = accounting.toFixed(number, 0);
      var currency = {
    	XAF: "XAF",
    	EUR: "€",
    	NGN: "₦",
        USD: "$"
      },
      thousand, decimal, format, precision;
      
      if(currency[currencyCode] === "undefined"){
    	  return accounting.formatMoney(number, { symbol: "XAF",  format: "%v %s", thousand: " ", precision : 0 });
      }
      
      return accounting.formatMoney(number, { symbol: currency[currencyCode],  format: "%v %s", thousand: " ", precision : 0 });
    };
  })
.directive('priceStyle', function () {
    return function (scope, element, attrs) {
    	element.addClass('default-style');
    };
})
.directive('priceRed', function () {
    return function (scope, element, attrs) {
        element.addClass('red-style');
    };
})
.directive('priceBlack', function () {
    return function (scope, element, attrs) {
        element.addClass('black-style');
    };
})
.directive('priceGreen', function () {
    return function (scope, element, attrs) {
        element.addClass('green-style');
    };
})
.factory('adUtils',['$location','$filter',function($location,$filter){
    var service = {};
    var defaultDatePattern = 'dd-MM-yyyy HH:mm';
    var dateFormat = $filter('date');
    service.loadApp = function(contextRoot){
		var absUrl = $location.protocol() + '://' + $location.host();
		var port = $location.port();
		if(port && port!=80 && port!=443){
			absUrl += ':'+port;
		}
		absUrl +=contextRoot;
		window.location.href=absUrl;
    };
    service.removeSearchOnUrl = function(){
    	$location.url('trm',null);
    	$location.url('usr',null);
    };
    service.greaterThan = function(a,b){
    	if(typeof a === 'undefined') return false;
    	if(typeof b === 'undefined') return a>-1;
    	return a>b;
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
    service.isNumber = function (n) {
    	return !isNaN(parseFloat(n)) && isFinite(n);
    };
    service.formatDate= function(fieldName, inPattern){
        var pattern = '';
        if(!inPattern) pattern = defaultDatePattern;
        else pattern = inPattern;
        return dateFormat(fieldName, pattern, '');
    };
    service.printPdfs = function(el){               
            var DocumentContainer = document.getElementById(el);
             var html = '<html><head>'+
                        '<link rel="stylesheet" type="text/css" href="styles/print/custom.css">'+
                        '<link rel="stylesheet" type="text/css" href="styles/print/bootstrap.min.css">'+
                        '</head><body style="background:#ffffff; font-size: 10px;">'+
                         DocumentContainer.innerHTML+
                        '<iframe name="print_frame" width="0" height="0" frameborder="0" title="Adcom" src="Adcom"> </iframe>'+
                        '</body></html>';
             var WindowObject = window.open("", "PrintWindow",
                     "width=750,height=650,top=200,left=10,toolbars=no,scrollbars=yes,status=no,resizable=yes");
             WindowObject.document.writeln(html);
             WindowObject.document.close();
             WindowObject.focus();
             WindowObject.print();
             WindowObject.close();
    };
    return service;
}])
.factory('commonTranslations',['$translate',function($translate){
	var service={};
	service.translations=[];
    service.translate = function(){
    	$translate([
            'current_language',
            'Entity_leave.title',
            'Entity_change.title',
            'Entity_activate.title',
            'Entity_desactivate.title',
            'Entity_block.title',
            'Entity_unblock.title',
            'Entity_create.title',
            'Entity_delete.title',
            'Entity_save.title',
            'Entity_saveleave.title',
            'Entity_search.title',
            'Entity_update.title',
            'Entity_edit.title',
            'Entity_cancel.title',
            'Entity_Finish.title',
            'Entity_reset.title',
            'Entity_ok.title',
            'Entity_yes.title',
            'Entity_no.title',
            'Entity_select.title',
            'Entity_add.title',
            'Entity_show.title',
            'Entity_export.title',
            'Entity_confirm.title',
            'Window_close.title',
            'Entity_login.title',
            'Entity_userName.title',
            'Entity_password.title',
            'Entity_print.title',
            'Entity_next.title',
            'Entity_reprint.title',
            'Entity_previous.title',
            'Entity_first.title',
            'Entity_last.title',
            'Entity_back.title',
            'Entity_validFrom.title',
            'Entity_validTo.title',
            'Entity_Result.title',
            'Entity_By.title',
            'Entity_Of.title',
            'Entity_required.title',
            'BaseGender_FEMALE_description.title',
        	'BaseGender_MALE_description.title',
            'BaseLanguage_fr_description.title',
            'BaseLanguage_en_description.title',
            'Entity_recompute.title',
            'Entity_From.title',
            'Entity_To.title'
            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.translate();
    return service;
	
}])
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
    
}])
.factory('searchResultHandler',['adUtils',function(adUtils){
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
            if(adUtils.greaterThan(selectedIndexIn) && angular.isDefined(searchResultVar.resultList[selectedIndexIn])){
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
        	if(!adUtils.greaterThan(searchResultVar.selectedIndex)) return;
        	if(angular.isUndefined(searchResultVar.resultList[searchResultVar.selectedIndex])) return;
        	return searchResultVar.resultList[searchResultVar.selectedIndex];
        };
        this.totalItems = function(){
            return searchResultVar.count;
        };
        this.currentPage = function(currentPageIn){
            if(adUtils.greaterThan(currentPageIn,-1)) searchResultVar.currentPage=currentPageIn;
            return searchResultVar.currentPage;
        };
        this.maxResult = function(maxResultIn) {
            if(adUtils.greaterThan(maxResultIn,-1)) searchResultVar.searchInput.max=maxResultIn;
            return searchResultVar.searchInput.max;
        };
        this.itemPerPage = function(itemPerPageIn){
            if(adUtils.greaterThan(itemPerPageIn,-1))searchResultVar.itemPerPage=itemPerPageIn;
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
        	if(adUtils.greaterThan(index) && index>=0){ // slice
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
.factory('dependentTabManager',[function(){
    var service = {};
    
    // Instantiates a new TabManager
    service.newTabManager = function(tabNameList){
    	return new TabManager(tabNameList); 
    };

    // Create a new tab manager with a tab name list.
    var TabManager = function(tabNameListIn){
    	if(!angular.isArray(tabNameListIn) || tabNameListIn.length<1)
    		throw "Tab manager expecting an array of string, with tab name.";
    	var tabNameList = tabNameListIn;
        var activeTabNameVar= tabNameList[0];
        
        this.activeTab=function(activeTabNameIn){
        	if(angular.isDefined(activeTabNameIn) && tabNameList.indexOf(activeTabNameIn)>-1)
        		activeTabNameVar=activeTabNameIn;
        	return activeTabNameVar;
        };
        
        this.isActive = function(tabName){
        	if(angular.isDefined(tabName) && tabNameList.indexOf(tabName)>-1)
        		return angular.equals(activeTabNameVar,tabName);

        	return false;
        };
    };
    
    return service;
}])
.factory('conversionPrice',['$translate',function($translate){
	var service={};
	
	service.currency=[
	    	"XAF",
	    	"EUR",
	    	"NGN",
	        "USD"
	      ];
    
    return service;
	
}])
.factory('fileExtractor',['$window',function($window){
        var  service = {
            extractFile : extractFile,
            saveFile:saveFile

        };
        return service ;
        //////////////////////////////////////////////////////////////////////////////////////////////////
        function extractFile(data,fileType)
        {
            var file = new Blob([data], {type: fileType});
            var fileURL = URL.createObjectURL(file);
            $window.open(fileURL);
        }

        function saveFile(data,fileType,fileName)
        {
            var file = new Blob([data], {type: fileType});
            saveAs(file, fileName);
        }

}]);


