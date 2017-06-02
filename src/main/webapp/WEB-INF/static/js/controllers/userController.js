angular.module('myApp').controller('UserController',['LocationService', 'UserService', '$scope', '$location', function (LocationService, UserService, $scope, $location) {
	$scope.cities=LocationService.getCity();
	$scope.getLocations = function() {
		$scope.location = LocationService.getCityLocation($scope.user.selectedCity);
	}
	$scope.services=LocationService.getServices();

	$scope.registerUser = function() {
		var serviceSelected = '';
		angular.forEach($scope.user.selectedService, function(value, key) {
			serviceSelected = serviceSelected.concat(',').concat(value.service);
		});

		var dataObj = {
				providerName : $scope.user.providername,
				businessName : $scope.user.businessName,
				city : $scope.user.selectedCity.city,
				location : $scope.user.selectedLocation.location,
				serviceType : serviceSelected,
				mobileNumber : $scope.user.mobileNumber,
				alternateMobileNumber : $scope.user.alternateMobileNumber,
				emailId : $scope.user.emailId,
				password : $scope.user.password,
				businessAddress : $scope.user.businessAddress,
		};
		console.log($scope.user.selectedService);
		var registerMessage = UserService.registerUser(dataObj)
		.then(function(result) {
			if(result.response == 'Registration Succesful!') {
				$location.path('/login');
			} else {
				$location.path('/404');
			}
		})
	}
}]);