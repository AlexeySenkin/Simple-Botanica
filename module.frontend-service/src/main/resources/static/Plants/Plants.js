angular.module('Simple-Botanica-app')
    .controller('plants-controller', function ($http, $rootScope, $scope, $localStorage,
                                               settings, userFactory) {
        const plantsPath = settings.PLANTS_PATH;

        $scope.getPlants = function () {
            $localStorage.plantCardCallPlace = 1;
            delete $localStorage.plantInfo;
            $http.get(plantsPath + '/plants', {
                params: {
                    title: $scope.plantNameFilter,
                    page: $scope.currentPage
                }
            }).then(function successCallback(response) {
                    $scope.plantsPage = response.data.content;
                    $scope.imgPath = settings.IMG_DIRECTORY;
                    $scope.totalPages = response.data.totalPages;
                    $scope.currentPage = response.data.number + 1;
                    $scope.totalItems = response.data.totalElements + 1;
                    $scope.maxPages = 6;
                },
                function errorCallback(reason) {
                    console.log('Ошибка: '+ reason.data.status + ' с текстом: ' +reason.data.error);

                })
        }

        $scope.showPlantDetails = function (plantId) {
            $localStorage.plantId = plantId;
            location.assign("#!/plant-info");
        }

        $scope.isAdmin = function () {
            return userFactory.isAdmin();
        }

        $scope.addNewPlant = function(){
            console.log("добавление нового растения")
        }

        $scope.deletePlant = function(plantId) {
            console.log("Удалить растение " + plantId + " из БД");
        }

        $scope.getPlants();

    })