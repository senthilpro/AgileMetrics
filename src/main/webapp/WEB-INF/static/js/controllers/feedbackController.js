angular.module('myApp').controller('FeedbackController',['UserService', '$scope', '$rootScope', '$location', function (UserService, $scope, $rootScope, $location) {

	$scope.saveFeedBack = function() {
		var dataObj = {
				userName : $scope.user.userName,
				userEmail : $scope.user.email,
				userContact: $scope.user.contact,
				serviceProvider: $scope.user.provider.providerName,
				feedback: $scope.user.feedback
		};
		var saveFeedback = UserService.saveFeedback(dataObj)
		.then(function(result) {
			$rootScope.message = 'Thanks for your valuable feedback';
			$location.path('/404');
		})
	}
}]);