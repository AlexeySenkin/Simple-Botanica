angular.module('Simple-Botanica-app')
    .controller('plants-controller', function ($http, $rootScope, $scope, $localStorage,
                                               settings, userFactory, plantFactory) {
        const plantsPath = settings.PLANTS_PATH;

        $scope.getPlants = function () {
            $localStorage.plantCardCallPlace = 1;
            $http.get(plantsPath + '/plants', {
                params: {
                    title: $scope.plantNameFilter,
                    page: $scope.currentPage
                }
            }).then(function successCallback(response) {
                    console.log("get all plants")
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
            // let res = plantFactory.deletePlant(plantId);
            $http.delete(plantsPath + '/plant/' + plantId)
                .then(function successCallback(response) {
                    if (response.status === 200) {
                        console.log("get all plants")
                        console.log(response.status)
                        $scope.getPlants();
                        // location.reload();
                    }
                    console.log(response);
                })
        }

        $scope.getPlantImagePath = function (filePath) {
            return plantFactory.getPlantPhoto(filePath);
        }

        $scope.getPlants();

    })