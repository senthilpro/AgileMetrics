angular.module('myApp').controller('LoginController',['UserService', '$scope', '$location', '$rootScope',  
	function (UserService, $scope, $location, $rootScope) {

	$scope.login = function() {
		var dataObj = {
				userType : $scope.user.userType,
				emailId : $scope.user.emailId,
				password : $scope.user.password
		};
		var loginMessage = UserService.login(dataObj)
		.then(function(result) {
			if(result.response == 'Login Succesful!') {
				if(dataObj.userType == 'admin') {
					$rootScope.emailId = dataObj.emailId;
					$location.path('/adminHome');
				} else if(dataObj.userType == 'serviceProvider') {
					$rootScope.emailId = dataObj.emailId;
					$location.path('/providerHome');
				} 
			} else {
				$scope.error = "Invalid user credentials.";
				$location.path('/login');
			}
		});

	}

}]);