angular.module('Simple-Botanica-app')
    .controller('plants-controller', function ($http, $rootScope, $scope, botanicaConfig, api_version, roleCheckFactory) {
        const plantsPath = 'localhost:3010/api/' + api_version.api_v + 'plants'
        $scope.plantListCallPlace = botanicaConfig.plantListCallPlace;

        let plantsArray = [{photo: 'img/db/Philodendron.jpg', name: 'Филодендрон', id: 1}
            , {photo: 'img/db/Scindapsus.jpg', name: 'Сциндапсус', id: 2}
            , {photo: 'img/db/Spatifilum.jpg', name: 'Спатифилум', id: 3}
            , {photo: 'img/db/Alocasia.jpg', name: 'Алоказия', id: 4}
            , {photo: 'img/db/Spatifilum.jpg', name: 'Спатифилум', id: 5}
            , {photo: 'img/db/Scindapsus.jpg', name: 'Сциндапсус', id: 6}
            , {photo: 'img/db/Spatifilum.jpg', name: 'Спатифилум', id: 7}
            , {photo: 'img/db/Spatifilum.jpg', name: 'Спатифилум', id: 8}]


        $scope.getPlants = function () {
            $http.get(plantsPath, {
                params: {
                    name: $scope.plantNameFilter,
                    page: $scope.page
                }
            }).then(function successCallback(response) {
                    $scope.plantsPage = response.data.content;
                    $scope.pageNumbersArray = paginatonFactory.generateRangeForPagination(response.data.number,
                        8, response.data.totalPages - 1);
                    $scope.totalPages = response.data.totalPages;
                    $scope.currentPage = response.data.number;
                    $scope.totalItems = response.data.totalElements;
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
                    $scope.plant = response.data;
                    location.assign('#!/plantinfo');
                },
                function errorCallback(reason) {

                });
            location.assign('#!/plantinfo');
            alert(location.toString());
        }

        $scope.isAdmin = function() {
            return true;
            // roleCheckFactory.isAdmin();
        }
        $scope.getPlants();

    })