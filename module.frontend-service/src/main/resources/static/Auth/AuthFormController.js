botanicaApp
    .controller('authFormController', function ($scope, $localStorage, $uibModalInstance,
                                                userFactory, test) {

        $scope.authOrRegister = function () {
            console.log(test);
            if ($scope.isAuth()) {
                userFactory.tryToAuth($scope.user).then(
                    function successCallback(result) {
                        $scope.user = result.user;
                        $uibModalInstance.close("auth.ok");
                    }, function errorCallback(response) {
                        console.log("Authorization error. Code: " + response.data.statusCode
                            + "With message: " + response.data.message);
                    }
                )
            } else {
                userFactory.registerUser($scope.user).then(
                    function successCallback(response) {
                        $scope.user = response.user;
                        $uibModalInstance.close("register.ok")
                    }, function errorCallback(reason) {
                        console.log("Registration error. Code: " + reason.data.statusCode
                            + "With message: " + reason.data.message);
                    }
                )
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.close('auth.cancel');
        };

        $scope.isAuth = function (){
            return test === 2;
        };

    })