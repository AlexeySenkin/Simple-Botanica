angular.module('Simple-Botanica-app')
    .controller('plant-card-controller', function ($http, $rootScope, $scope, $localStorage,
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
            filePath: "No-Image-Placeholder.png"
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
                case 2: {
                    break;
                }
                case 3: {
                    break
                }
                case 4: {
                    break;
                }
                case 5: {
                    break;
                }
            }
        };

        $scope.addPlantToUserList = function (plantId) {
            console.log("Добавление растения " + plantId + "в список пользователя");
        };

        $scope.showPlantDetails = function () {
            let plantId = $localStorage.plantId;
            if (plantId) {
                $http.get(plantsPath + '/plant/' + plantId).then(function successCallback(response) {
                    $scope.plant = response.data;
                    if (!response.data.filePath) {
                        $scope.plant.filePath = "No-Image-Placeholder.png";
                    }
                }, function errorCallback(reason) {
                });
            } else {
                $scope.plant = {};
                $scope.plant.id = null;
                $scope.plant.filePath = "No-Image-Placeholder.png";
            }
        }

        $scope.savePlant = function () {
            plantFactory.saveOrUpdate($scope.plant).then(function (response){
                console.log(response);
                location.assign('#!/');
            }, function (reason){
                console.log(reason);
            })
        }

        $scope.home = function () {
            location.assign('#!/')
        }
        $scope.adm = userFactory.isAdmin();

        $scope.showPlantDetails();

    })