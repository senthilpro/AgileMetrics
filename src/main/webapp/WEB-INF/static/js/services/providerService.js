angular.module('myApp').factory('ProviderService', ['$http', '$q', function($http, $q){
	var REST_SERVICE_URI = 'http://localhost:8080/HappyServices/provider';

	return {
		fetchProviderDetails: fetchProviderDetails
	};

	function fetchProviderDetails(emailId) {
		var deferred = $q.defer();
		var dataObj = {
				emailId : emailId
		};
		$http.post(REST_SERVICE_URI, JSON.stringify(dataObj))
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