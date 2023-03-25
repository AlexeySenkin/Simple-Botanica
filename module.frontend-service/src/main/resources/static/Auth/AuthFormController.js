botanicaApp
    .controller('authFormController', function ($scope, $http, $localStorage, $uibModalInstance, $test) {
        $scope.tryToAuth = function () {
            $http.post('/auth/', $scope.user).then(function successCallback(response) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.botanicaWebUser = {
                        username: $scope.user.username, token: response.data.token,
                        role: response.data.role, userId: response.data.userId
                    };
            // $localStorage.botanicaWebUser = {
            //     username: $scope.user.username,
            //     token: 'wqwldk[k090qewmlk3mjqewd98eqdmw;dlwpodiew',
            //     role: 'admin', userId: 11
            // };
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $uibModalInstance.close('auth.ok')
                },
                function errorCallback(response) {
                    // console.log(response.data.errorCode);
                    console.log('err');
                });
        };
        $scope.cancel = function (){
            $uibModalInstance.close('auth.cancel');
        }
    })