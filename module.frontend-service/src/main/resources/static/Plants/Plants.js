angular.module('Simple-Botanica-app')
    .controller('plants-controller', function ($http, $rootScope, $scope, $localStorage,
                                               roleCheckFactory, settings, plantInfo) {
        const plantsPath = settings.plants_path;

        $scope.getPlants = function () {
            $localStorage.plantCardCallPlace = 1;
            $http.get(plantsPath + 'plants', {
                params: {
                    title: $scope.plantNameFilter,
                    page: $scope.currentPage
                }
            }).then(function successCallback(response) {
                    $scope.plantsPage = response.data.content;
                    $scope.imgPath = settings.img_directory;
                    $scope.totalPages = response.data.totalPages;
                    $scope.currentPage = response.data.number + 1;
                    $scope.totalItems = response.data.totalElements + 1;
                    $scope.maxPages = 6;
                    // $scope.totalItems = 11;
                },
                function errorCallback(reason) {
                    console.log('Ошибка: '+ reason.data.status + ' с текстом: ' +reason.data.error);

                })
        }

        $scope.showPlantDetails = function (plantId) {
            $http.get(plantsPath + 'plant/' + plantId).then(function successCallback(response) {
                    $localStorage.plantInfo = response.data;
                    location.assign('#!/plantinfo');
                },
                function errorCallback(reason) {
                });
        }

        $scope.isAdmin = function () {
            return true;
            // roleCheckFactory.isAdmin();
        }


        $scope.testButton = function(){
            alert($scope.plantNameFilter);
        }

        $scope.getPlants();

    })