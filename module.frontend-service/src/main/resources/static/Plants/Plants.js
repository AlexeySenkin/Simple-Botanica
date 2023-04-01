angular.module('Simple-Botanica-app')
    .controller('plants-controller', function ($http, $rootScope, $scope, roleCheckFactory,
                                               settings, plantInfo, calls) {
        const plantsPath = settings.plants_path;
        $scope.plantListCallPlace = calls.plantListCallPlace;

        let plantsArray = [{photo: 'img/db/Philodendron.jpg', name: 'Филодендрон', id: 1}
            , {photo: 'img/db/Scindapsus.jpg', name: 'Сциндапсус', id: 2}
            , {photo: 'img/db/Spatifilum.jpg', name: 'Спатифилум', id: 3}
            , {photo: 'img/db/Alocasia.jpg', name: 'Алоказия', id: 4}
            , {photo: 'img/db/Spatifilum.jpg', name: 'Спатифилум', id: 5}
            , {photo: 'img/db/Scindapsus.jpg', name: 'Сциндапсус', id: 6}
            , {photo: 'img/db/Spatifilum.jpg', name: 'Спатифилум', id: 7}
            , {photo: 'img/db/Spatifilum.jpg', name: 'Спатифилум', id: 8}]


        $scope.getPlants = function () {
            calls.plantCardCallPace = 1;
            $http.get(plantsPath, {
                params: {
                    name: $scope.plantNameFilter,
                    page: $scope.currentPage
                }
            }).then(function successCallback(response) {
                    $scope.plantsPage = response.data.content;
                    $scope.imgPath = settings.img_directory;
                    $scope.totalPages = response.data.totalPages;
                    $scope.currentPage = response.data.number + 1;
                    $scope.totalItems = response.data.size;
                },
                function errorCallback(reason) {
                    $scope.plantsPage = plantsArray;
                    $scope.totalPages = 4;
                    $scope.currentPage = 2;
                    $scope.totalItems = 30;
                })
        }

        $scope.showPlantDetails = function (plantId) {
            $http.get(plantsPath + '/' + plantId).then(function successCallback(response) {
                    plantInfo.plantObject = response.data;
                    location.assign('#!/plantinfo');
                },
                function errorCallback(reason) {
                });
            location.assign('#!/plantinfo');
        }

        $scope.isAdmin = function () {
            return true;
            // roleCheckFactory.isAdmin();
        }
        $scope.getPlants();

    })