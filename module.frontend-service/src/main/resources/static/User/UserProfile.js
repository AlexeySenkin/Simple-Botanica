angular.module('Simple-Botanica-app')
    .controller('user-profile-controller', function ($rootScope, $scope, $localStorage, userFactory) {
        $scope.toDisable = true;
        $scope.getUserData = function () {
            if (userFactory.isAuthorized()) {
                userFactory.getUserProfileData().then(function successCallback(response) {
                    $scope.userData = response.data;
                    if (userFactory.isAdmin()){
                        $scope.toDisable = false;
                    }
                        }, function errorCallback(reason) {
                    console.log(reason)
                    // TODO: сделать единую страницу для отображения ошибок пользователю
                });
            }
        }


        $scope.getUserData();

    })