angular.module('myApp').controller('SearchController',['LocationService','SearchService', '$scope', function (LocationService, SearchService, $scope) {

	$scope.cities=LocationService.getCity();
	$scope.getLocations = function() {
		$scope.location = LocationService.getCityLocation($scope.user.selectedCity);
	}
	$scope.services=LocationService.getServices();

	$scope.getProviderList = function() {
		var providers = SearchService.fetchServices($scope.user.selectedCity,
				$scope.user.selectedLocation, $scope.user.selectedService)
				.then(function(result) {
					if(result.providers.length === 0) {
						$scope.provider='';
						$scope.error='No results found.';
					}else {
						$scope.provider=result.providers;
						$scope.error = "";
					}
				});

	}
}]);