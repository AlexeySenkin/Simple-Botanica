angular.module('Simple-Botanica-app')
    .controller('plant-card-controller', function ($rootScope, $scope, $localStorage,
                                                   settings, userFactory, plantFactory) {
        const plantsPath = settings.PLANTS_PATH;
        // признак откуда была открыта карточка растения
        $scope.callPlace = $localStorage.plantCardCallPlace;

       $scope.actionButtons = settings.ACTION_BUTTONS;
        $scope.imgPath = settings.IMG_DIRECTORY;

        $scope.plant = {
            id: null,
            name: "",
            family: "",
            genus: "",
            shortDescription: "",
            description: "",
            isActive: true,
            filePath: ""
        }

        $scope.isAdmin = function () {
            return userFactory.isAdmin();
        }

        $scope.careAction = function (careId) {
            switch (careId) {
                case 1: {
                    console.log("полить");
                    break;
                }
                case 3: {
                    console.log("опрыскать");
                    break;
                }
                case 2: {
                    console.log("удобрить");
                    break
                }
                case 4: {
                    console.log("обрезать");
                    break;
                }
                case 5: {
                    console.log("пересадить");
                    break;
                }
            }
        };

        $scope.addPlantToUserList = function (plantId) {

            console.log("Добавление растения " + plantId + "в список пользователя");
        };

        $scope.showPlantDetails = function () {
            let plantId = $localStorage.plantId;
            plantFactory.getPlant(plantId).then(function successCallback(response) {
                $scope.plant = response.data;
                $scope.plantPhotoCurrent = response.photoPath;
                $scope.actionButtons = response.actualCareButtons;
            }, function errorCallback(reason) {
                console.log('error occurred while fetching a plant info:' + reason);
            });
        }

        $scope.savePlant = function () {
            plantFactory.saveOrUpdate($scope.plant).then(function (response) {
                console.log(response);
                location.assign('#!/');
            }, function (reason) {
                console.log(reason);
            })
        }

        $scope.home = function () {
            location.assign('#!/')
        }

        $scope.addPlantPhoto = function () {
            alert("Извините, мы пока не умеем загружать фото!");
        }

        $scope.showPlantDetails();

    })