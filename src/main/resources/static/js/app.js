angular.module('FactoryMonitor', []).
controller('machineController', function($scope, $http) {
$scope.machine ="asdf";

$http({
  method: 'GET',
  url: '/alerts'
}).then(function successCallback(response) {
    $scope.alerts=response.data;
  }, function errorCallback(response) {
    $scope.machine=response;
  });

});