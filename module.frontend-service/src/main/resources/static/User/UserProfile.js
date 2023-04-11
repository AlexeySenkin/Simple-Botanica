angular.module('Simple-Botanica-app')
    .controller('user-profile-controller', function ($http, $rootScope, $scope, $localStorage,
                                                     userFactory, settings) {
        const userPath = settings.USER_PATH;

        $scope.getUserData = function () {
            $http.get(userPath + '/user' , $localStorage.botanicaWebUser.username).then(
                function successCallback(response) {
                    $scope.userData = response.data;
                }, function errorCallback(reason) {
                    console.log('Ошибка: '+ reason.data.status + ' с текстом: ' +reason.data.error)
                }
            )
        }

        $scope.getUserData();

    })