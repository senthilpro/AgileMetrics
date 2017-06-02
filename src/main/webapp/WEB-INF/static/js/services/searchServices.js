angular.module('myApp')
.factory('SearchService', ['$http', '$q', function($http, $q){
	var REST_SERVICE_URI = 'http://localhost:8080/HappyServices/';

	return {
		fetchServices: fetchServices,
		fetchAllServices: fetchAllServices
	};

	function fetchServices(city, location, service) {
		var deferred = $q.defer();
		$http.get('http://localhost:8080/HappyServices/search', {params:{"city": city.city, "location": location.location, "service": service.service}})
		.then(
				function (response) {
					deferred.resolve(response.data);
				},
				function(errResponse){
					console.error('Error while fetching Services');
					deferred.reject(errResponse);
				}
		);
		return deferred.promise;  
	}
	
	function fetchAllServices() {
		var deferred = $q.defer();
		$http.get('http://localhost:8080/HappyServices/searchAll')
		.then(
				function (response) {
					deferred.resolve(response.data);
				},
				function(errResponse){
					console.error('Error while fetching Services');
					deferred.reject(errResponse);
				}
		);
		return deferred.promise;  
	}

}]);