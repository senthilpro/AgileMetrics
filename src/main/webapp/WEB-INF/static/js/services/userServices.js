angular.module('myApp')
.factory('UserService', ['$http', '$q', function($http, $q){
	var REST_SERVICE_URI = 'http://localhost:8080/HappyServices/';
	return {
		registerUser: registerUser,
		login: login,
		activateOrDeactivate: activateOrDeactivate,
		update: update,
		saveFeedback: saveFeedback
	};

	function registerUser(dataObj) {
		var deferred = $q.defer();
		$http.post(REST_SERVICE_URI.concat('register'), JSON.stringify(dataObj), {
            transformRequest: angular.identity,
            headers: {
              'Content-Type': 'application/json; charset=utf-8'
            }
          })
		.then(
				function (response) {
					deferred.resolve(response.data);
				},
				function(errResponse){
					console.error('Error while registering user');
					deferred.reject(errResponse);
				}
		);
		return deferred.promise;  
	}
	
	function login(dataObj) {
		var deferred = $q.defer();
		$http.post(REST_SERVICE_URI.concat('login'), JSON.stringify(dataObj), {
            transformRequest: angular.identity,
            headers: {
              'Content-Type': 'application/json; charset=utf-8'
            }
          })
		.then(
				function (response) {
					deferred.resolve(response.data);
				},
				function(errResponse){
					console.error('Error while logging in user');
					deferred.reject(errResponse);
				}
		);
		return deferred.promise;  
	}
	
	function activateOrDeactivate(dataObj){
		var deferred = $q.defer();
		$http.put(REST_SERVICE_URI.concat('activateOrDeactivate'), dataObj)
		.then(
				function (response) {
					deferred.resolve(response.data);
				},
				function(errResponse){
					console.error('Error while activating/deactivating user');
					deferred.reject(errResponse);
				}
		);
		return deferred.promise;  
	}
	
	function update(dataObj){
		var deferred = $q.defer();
		$http.put(REST_SERVICE_URI.concat('update'), dataObj)
		.then(
				function (response) {
					deferred.resolve(response.data);
				},
				function(errResponse){
					console.error('Error while updating user');
					deferred.reject(errResponse);
				}
		);
		return deferred.promise;  
	}
	
	function saveFeedback(dataObj){
		var deferred = $q.defer();
		$http.post(REST_SERVICE_URI.concat('feedback'), dataObj, {
            headers: {
              'Content-Type': 'application/json; charset=utf-8'
            }
          })
		.then(
				function (response) {
					deferred.resolve(response.data);
				},
				function(errResponse){
					console.error('Error while inserting user feedback');
					deferred.reject(errResponse);
				}
		);
		return deferred.promise;  
	}
}]);