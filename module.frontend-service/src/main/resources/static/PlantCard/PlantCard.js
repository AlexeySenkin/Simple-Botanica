angular.module('Simple-Botanica-app')
    .controller('plant-card-controller', function ($http, $rootScope, $scope, $localStorage,
                                                   roleCheckFactory, settings) {

        // признак откуда была открыта карточка растения
        // $scope.callPlace = $localStorage.plantCardCallPlace;
        $scope.callPlace = 2;
        //растение
        $scope.plant = $localStorage.plantInfo;

        $scope.actionButtons = settings.actionButtons;
        $scope.imgPath = settings.img_directory;

        $scope.isAdmin = function () {
            return roleCheckFactory.isAdmin();
        }

        $scope.careAction = function (careId) {
            switch (careId) {
                case 1: {
                    break;
                }
                case 2: {
                    break;
                }
                case 3: {
                    break
                }
                case 4: {
                    break;
                }
            }
        }
    })