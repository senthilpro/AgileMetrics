angular.module('myApp').controller('AdminController',['UserService', '$scope', '$rootScope', '$location', function (UserService, $scope, $rootScope, $location) {

	$scope.tabName='tab1';
	$scope.activateOrDeactivate = function(isActive, emailId) {
		var dataObj = {
				isActive : isActive,
				emailId : emailId
		};
		var loginMessage = UserService.activateOrDeactivate(dataObj)
		.then(function(result) {
			$rootScope.providers = result.providers;
			$location.path("/adminHome");
		});
	}
}]);