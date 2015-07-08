/**
 * @author Francis Pouatcha
 * @name navbarCtrl
 */

'use strict';
angular.module('SessionManager');
angular.module('AuthInterceptor');
angular.module('pascalprecht.translate');
angular.module('NavBar',[
     'SessionManager',
     'AuthInterceptor',
     'pascalprecht.translate',
     'ADUtils'
])
.run(['$translate','$translatePartialLoader',
      function($translate,$translatePartialLoader){
	$translatePartialLoader.addPart('/adres.client/i18n/shared');
	$translate.refresh();		
}])
.controller('navbarCtrl', 
	['$scope', '$translate','$translatePartialLoader', '$rootScope','sessionManager','$location', 
		 function ($scope, $translate, $translatePartialLoader, $rootScope,sessionManager,$location) {

			$rootScope.$on('$translateChangeSuccess', function () {
				$translate(['workspace_ouWsIdentif', 'workspace_user', 'workspace_appMenu','workspace_logOut',
				             'navbar_appMenu','navbar_loginName','navbar_ouWsIdentif','navbar_user','navbar_logOut'])
				 .then(function (translations) {
					 $scope.workspace_ouWsIdentif = translations.workspace_ouWsIdentif;
					 $scope.workspace_user = translations.workspace_user;
					 $scope.workspace_appMenu = translations.workspace_appMenu;
					 $scope.workspace_logOut = translations.workspace_logOut;
					 $scope.navbar_appMenu=translations.navbar_appMenu;
					 $scope.navbar_loginName=translations.navbar_loginName;
					 $scope.navbar_ouWsIdentif=translations.navbar_ouWsIdentif;
					 $scope.navbar_user=translations.navbar_user;
					 $scope.navbar_logOut=translations.navbar_logOut;
			 	 });
				 
			});
			$scope.userSession = sessionManager.userSession;
			$scope.loginName = sessionManager.loginName;
			$scope.workspaceLink =sessionManager.workspaceLink;
			$scope.workspaces = sessionManager.workspaces;
			$scope.logout=sessionManager.logout;
	        $scope.appMenuUrl = sessionManager.appMenuUrl;
	        $scope.currentWs = sessionManager.userWsData();
	        $scope.hasWorkspace = function(){
	        	return sessionManager.isSet($scope.currentWs.roleIdentif);
	        };
	        $scope.switchTo = function(langKey){
	        	return !$scope.hasWorkspace() && sessionManager.language()!=langKey;
	        };
			$scope.changeLanguage = function (langKey) {
				sessionManager.language(langKey,false);
	        };
	        $scope.matchesRoute = function(route) {
	        	var path = $location.path();
	        	return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
	        };
		}
	]
);
