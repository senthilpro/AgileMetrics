angular.module('myApp')

.directive('providerView', function () {
   return {
     restrict: 'E',
     transclude: true,
     templateUrl: 'html/providerView.html',
   };
});