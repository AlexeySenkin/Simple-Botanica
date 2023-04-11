botanicaApp
    .controller('authFormController', function ($scope, $http, $localStorage, $uibModalInstance, settings) {
        const authPath = settings.AUTH_PATH;

        $scope.tryToAuth = function () {
            $http.post(authPath + '/auth/', $scope.user).then(function successCallback(response) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.botanicaWebUser = {
                        username: $scope.user.username,
                        token: response.data.token,
                    };
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $uibModalInstance.close('auth.ok')
                },
                function errorCallback(response) {
                console.log(response);
                });
        };
        $scope.cancel = function () {
            $uibModalInstance.close('auth.cancel');
        }
    })