angular.module('myApp')

.directive('providerEdit', function () {
   return {
     restrict: 'E',
     transclude: true,
     templateUrl: 'html/providerEdit.html',
   };
});