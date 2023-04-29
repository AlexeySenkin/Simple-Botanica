angular.module('Simple-Botanica-app')
    .controller('plants-controller', function ($http, $rootScope, $scope, $localStorage,
                                               settings, userFactory, plantFactory) {

        $scope.getPlants = function () {
            delete $localStorage.plantId;
            let plantListCallMode = $localStorage.plantListCallPlace;
            let userId = null;
            if ($localStorage.botanicaWebUser) {
                userId = $localStorage.botanicaWebUser.userId;
            }
            plantFactory.getAllPlants(userId, $scope.plantNameFilter, $scope.currentPage, 8, plantListCallMode)
                .then(function successCallback(response) {
                        $scope.plantsPage = response.data.content;
                        $scope.imgPath = settings.IMG_DIRECTORY;
                        $scope.totalPages = response.data.totalPages + 1;
                        $scope.currentPage = response.data.number + 1;
                        $scope.totalItems = response.data.totalElements + 1;
                        $scope.maxPages = 6;
                    },
                    function errorCallback(reason) {
                        console.log('Ошибка: ' + reason.data.status + ' с текстом: ' + reason.data.error);
                    })
        }

        $scope.showPlantDetails = function (plantId) {
            $localStorage.plantId = plantId;
            location.assign("#!/plant-info");
        }

        $scope.isAdmin = function () {
            return userFactory.isAdmin();
        }

        $scope.addNewPlant = function () {
            console.log("добавление нового растения");
            delete $localStorage.plantId;
            location.assign("#!/plant-edit")
        }

        $scope.deletePlant = function (plantId) {
            console.log("Удалить растение " + plantId + " из БД");
            plantFactory.deletePlant(plantId).then(
                function successCallback(response) {
                    console.log("Plant id={} was successfully deleted", plantId);
                    $scope.getPlants();
                }, function errorCallback(reason) {
                    console.log("Error occurred while deleting plant id={}", plantId);

                }
            );
        }

        $scope.getPlantImagePath = function (filePath) {
            return plantFactory.getPlantPhoto(filePath);
        }

        $scope.getPlants();

    })