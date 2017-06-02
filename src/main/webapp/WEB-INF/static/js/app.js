'use strict';

angular.module('myApp', [ 'ngRoute']).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/', {
				templateUrl : 'html/search.html',
				controller : 'SearchController'
			}).when('/404', {
				templateUrl : 'html/404.html'
			}).when('/register', {
				templateUrl : 'html/register.html',
				controller : 'UserController'
			}).when('/login', {
				templateUrl : 'html/login.html',
				controller : 'LoginController'
			}).when('/adminHome', {
				templateUrl : 'html/adminHome.html',
				controller : 'AdminController',
				resolve: {	
					searchResults: ['$rootScope', 'SearchService', function ($rootScope, SearchService) {
						var providers = SearchService.fetchAllServices()
						.then(function(result) {
							if(result.providers === 'undefined') {
								$rootScope.error='No results found.';
							}else {
								$rootScope.providers=result.providers;
								$rootScope.error = "";
							}
						});
					}]
				}
			}).when('/providerHome', {
				templateUrl : 'html/provider.html',
				controller : 'ProviderController',
				resolve: {
					searchResults: ['$rootScope', 'ProviderService', 'LocationService', function ($rootScope, ProviderService, LocationService) {
						$rootScope.view=true;
						var providers = ProviderService.fetchProviderDetails($rootScope.emailId)
						.then(function(result) {
							if(result.providers.length === 0) {
								$rootScope.error='No results found.';
							}else {
								$rootScope.providers=result.providers;
								$rootScope.alternateMobileNumber = result.providers.alternateMobileNumber;
								$rootScope.businessAddress=result.providers.businessAddress;
								$rootScope.isActive=result.providers.isActive;
								
								$rootScope.indexOfCity=LocationService.getCityIndex($rootScope.providers.city);
								$rootScope.indexOfLocation=LocationService.getLocationIndex($rootScope.providers.location, $rootScope.providers.city);
								var serviceTypes = $rootScope.providers.serviceType;
								$rootScope.intiServices = serviceTypes.split(',');
								$rootScope.error = "";
							}
						});
						$rootScope.cities=LocationService.getCity();
						$rootScope.getLocations = function() {
							$rootScope.location = LocationService.getCityLocation($rootScope.user.city);
						}
						$rootScope.services=LocationService.getServices();

					}]
				}
			}).when('/feedback', {
				templateUrl : 'html/userfeedback.html',
				controller : 'FeedbackController',
				resolve: {	
					searchResults: ['$rootScope', 'SearchService', function ($rootScope, SearchService) {
						var providers = SearchService.fetchAllServices()
						.then(function(result) {
							if(result.providers === 'undefined') {
								$rootScope.message='No results found.';
							}else {
								$rootScope.providers = [];
								angular.forEach(result.providers, function(value, key) {
									if(value.isActive === 'Y') {
										$rootScope.providers.push(value)
									}
								})
								$rootScope.message = "";
							}
						});
					}]
				}
			}).otherwise('/');
		} ]);