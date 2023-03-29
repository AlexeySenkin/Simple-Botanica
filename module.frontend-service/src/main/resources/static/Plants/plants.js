angular.module('Simple-Botanica-app')
.controller('plants-controller', function ($http, $rootScope, $scope, botanicaConfig, api_version){
    const plantsPath = 'localhost:3010/api/' + api_version.api_v + 'plants'

    $scope.plantListCallPlace = botanicaConfig.plantListCallPlace;

    $scope.fillPlantsList = function (){
        // $scope.plantsList = ['/img/db/plant1.jpg'
        //     , '/img/db/plant2.jpg'
        //     , '/img/db/plant3.jpg'
        //     , '/img/db/plant4.jpg']

        $scope.plantsList = [{photo:'img/db/Philodendron.jpg', name:'Филодендрон', id: 1}
            , {photo:'img/db/Scindapsus.jpg', name:'Сциндапсус', id: 2}
            , {photo:'img/db/Spatifilum.jpg', name:'Спатифилум', id: 3}
            , {photo:'img/db/Alocasia.jpg', name:'Алоказия', id: 4}
            , {photo:'img/db/Spatifilum.jpg', name:'Спатифилум', id: 5}
            , {photo:'img/db/Spatifilum.jpg', name:'Спатифилум', id: 6}
            , {photo:'img/db/Spatifilum.jpg', name:'Спатифилум', id: 7}
            , {photo:'img/db/Spatifilum.jpg', name:'Спатифилум', id: 8}]
    }

    $scope.getPlants = function(){
        // $http.get(plantsPath + '/'+ $scope.plantNameFilter).then(function (response){
        //     $scope.plantsPage = response.data();
        // })
        alert($scope.plantNameFilter)
    }

    $scope.showPlantDetails = function (plantId){
        alert(plantId);
    }

    $scope.isAdmin = function (){
        return true;
    }
    $scope.fillPlantsList();


})