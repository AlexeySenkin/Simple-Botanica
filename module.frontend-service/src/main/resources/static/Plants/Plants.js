angular.module('Simple-Botanica-app')
    .controller('plants-controller', function ($http, $rootScope, $scope, $localStorage,
                                               settings, userFactory, plantFactory) {

        let plantListObj = this;

        plantListObj.init = function () {
            plantListObj.plantsPage = {};
        }

        plantListObj.getPlants = function () {
            delete $localStorage.plantId;
            let userId = null;
            if ($localStorage.botanicaWebUser) {
                userId = $localStorage.botanicaWebUser.userId;
            }
            $scope.plantNameFilter = null;
            plantFactory.getAllPlants(userId, $scope.plantNameFilter, $scope.currentPage, 8, $localStorage.plantListCallPlace)
                .then(function successCallback(response) {
                        plantListObj.plantsPage = response.data.content;
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
            console.log("Добавление нового растения");
            delete $localStorage.plantId;
            location.assign("#!/plant-edit")
        }

        $scope.deletePlant = function (plantId) {
            console.log("Удалить растение " + plantId + " из БД");
            plantFactory.deletePlant(plantId).then(
                function successCallback(response) {
                    console.log("Растение с id=${} удалено успешно", plantId);
                    $scope.getPlants();
                }, function errorCallback(reason) {
                    console.log("Ошибка при удалении растения id=${}", plantId);

                }
            );
        }

        $scope.getPlantImagePath = function (filePath) {
            return plantFactory.getPlantPhoto(filePath);
        }

        plantListObj.init();
        plantListObj.getPlants();

    })