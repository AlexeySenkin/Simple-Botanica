angular.module('Simple-Botanica-app')
.controller('plants-controller', function ($http, $rootScope, $scope, botanicaConfig){
    const plantsPath = 'localhost:3010/api/v1/plants'
    $scope.plantListCallPlace = botanicaConfig.plantListCallPlace;
    $scope.fillPlantsList = function (){
        // $scope.plantsList = ['/img/db/plant1.jpg'
        //     , '/img/db/plant2.jpg'
        //     , '/img/db/plant3.jpg'
        //     , '/img/db/plant4.jpg']

        $scope.plantsList = [{photo:'img/db/Philodendron.jpg', name:'Филодендрон'}
            , {photo:'img/db/Scindapsus.jpg', name:'Сциндапсус'}
            , {photo:'img/db/Spatifilum.jpg', name:'Спатифилум'}
            , {photo:'img/db/Alocasia.jpg', name:'Алоказия'}
            , {photo:'img/db/Spatifilum.jpg', name:'Спатифилум'}
            , {photo:'img/db/Spatifilum.jpg', name:'Спатифилум'}
            , {photo:'img/db/Spatifilum.jpg', name:'Спатифилум'}
            , {photo:'img/db/Spatifilum.jpg', name:'Спатифилум'}]
    }

    $scope.findAllPlantsByNameLike = function(plantName){
        $http.get(plantsPath + '/'+ plantName).then(function (response){
            $scope.plantsPage = response.data();
        })
    }
    $scope.fillPlantsList();


})