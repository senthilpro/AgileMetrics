angular.module('myApp')
.controller('ProviderController',['ProviderService','UserService', 'LocationService', '$scope', '$rootScope', '$location',
	function (ProviderService, UserService, LocationService, $scope, $rootScope, $location) {
	$rootScope.tabName= 'tab1';
	$scope.updateUser = function() {
		var dataObj = {
				providerName : $scope.user.providername,
				businessName : $scope.user.businessName,
				city : $scope.user.city.city,
				location : $scope.user.selectedLocation.location,
				serviceType : $scope.user.selectedService.service,
				mobileNumber : $scope.user.mobileNumber,
				alternateMobileNumber : $scope.user.alternateMobileNumber,
				emailId : $rootScope.emailId,
				password : $scope.user.passwordEdit,
				businessAddress : $rootScope.businessAddress
		};
		$scope.getUpdatedProvider = UserService.update(dataObj)
		.then(function(result) {
			if(result.provider.length === 0) {
				$scope.message='Error saving data.';
			}else {
				$rootScope.provider=result.provider;
				$rootScope.error = '';
				$rootScope.message= 'Details saved successfully.';
				$location.path('/404');
			}
		});
	}
}
]);
