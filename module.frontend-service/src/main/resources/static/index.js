(function () {
    angular.module('Simple-Botanica-app', ['ngStorage', 'ngRoute', 'ui.bootstrap'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {templateUrl: 'Plants/Plants.html', controller: 'plants-controller'})
            .when('/plant-info', {templateUrl: 'PlantCard/PlantCard.html', controller: 'plant-card-controller'})
            .when('/user-profile', {templateUrl: 'User/UserProfile.html', controller: 'user-profile-controller'})
            .when('/plant-edit', {templateUrl: 'PlantCard/PlantCard.html', controller: 'plant-card-controller'})
            .otherwise({redirectTo: '/'})
    }

    function run($localStorage, $rootScope, $http) {
        if ($localStorage.botanicaWebUser) {
            let jwt = $localStorage.botanicaWebUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);

            if (currentTime > payload.exp) {
                console.log('the token has expired');
                delete $localStorage.botanicaWebUser;
                $http.defaults.headers.common.Authorization = '';
            }
        }
    }
})();
var botanicaApp = angular.module('Simple-Botanica-app');

botanicaApp.value('plantInfo', {
    plantObject: {}
})

botanicaApp.constant('settings', {
    ENTRY_POINT: 'http://localhost:9890/botanica',
    // пока нет шлюза для доступа через единую точку тут будут лежать адреса сервисов
    PLANTS_PATH: 'http://localhost:8189/botanica/api',
    AUTH_PATH: 'http://localhost:8188/botanica/api',
    USER_PATH: '',
    API_V: '',
    // директория хранения картинок растений
    IMG_DIRECTORY: 'img/db/',
    NO_PHOTO_PLACEHOLDER: "../No-Image-Placeholder.png",
    // кнопки ухода
    ACTION_BUTTONS: [{id: 1, img: 'img/watering_can.png', hint: 'Полить'},
        {id: 2, img: 'img/sprayer.png', hint: 'Опрыскать'},
        {id: 3, img: 'img/fertilizer.png', hint: 'Удобрить'},
        {id: 4, img: 'img/shears.png', hint: 'Обрезать'},
        {id: 5, img: 'img/re-potting.png', hint: 'Пересадить'}]
})

botanicaApp.factory('userFactory', function ($localStorage) {
    let userFactoryObj = {};
    userFactoryObj.isAdmin = function () {
        if ($localStorage.botanicaWebUser) {
            let user_roles = JSON.parse(atob($localStorage.botanicaWebUser.token.split('.')[1])).roles;
            if (user_roles.includes('ROLE_ADMIN')) {
                return true;
            }
        }
        return false;
    };
    userFactoryObj.isAuthorized = function () {
        return !!($localStorage.botanicaWebUser);
    };

    userFactoryObj.logOut = function () {
        if ($localStorage.botanicaWebUser && confirm('Выйти?')) {
            delete $localStorage.botanicaWebUser;
            location.assign('#!/')
        }
    }
    return userFactoryObj;
})

botanicaApp.factory('plantFactory', function ($http, settings, $q) {
    let plantFactoryObj = {};
    let plant = {
        id: null,
        name: "",
        family: "",
        genus: "",
        shortDescription: "",
        description: "",
        isActive: true,
        filePath: ""
    };
    const plantPath = settings.PLANTS_PATH;

    let getPlantPhoto = function (filePathString) {
        if (filePathString === null || filePathString.trim() === '' || typeof filePathString === undefined) {
            return settings.NO_PHOTO_PLACEHOLDER;
        }
        return filePathString;
    }

    plantFactoryObj.getPlant = function (plantId) {
        let defer = $q.defer();
        $http.get(plantPath + '/plant/' + plantId).then(function successCallback(response) {
            plant = response.data;
            plant.file_path = getPlantPhoto(plant.file_path);
            defer.resolve(plant);
        }, function errorCallback(reason) {
            defer.reject(reason);
        });
        return defer.promise;
    };

    plantFactoryObj.deletePlant = function (plantId) {
        let defer = $q.defer();
        $http.delete(plantPath + '/plant/' + plantId).then(function successCallback(response) {
            defer.resolve(response);
        }, function errorCallback(reason) {
            defer.reject(reason);
        });
        return defer.promise;
    };

    plantFactoryObj.saveOrUpdate = function (plantObject) {
        plant = plantObject;
        plant.isActive = true;
        console.log(plant);
        let defer = $q.defer();
        $http.post(plantPath + '/plant', JSON.stringify(plant)).then(function successCallback(response){
            defer.resolve(response);
            console.log('plant saved id=' + response.data);
        }, function errorCallback(reason){
            defer.reject(reason);
            console.log('error occurred while saving. error code:' + reason.data.status);
        });
        return defer.promise;
    };
    return plantFactoryObj;
});

botanicaApp
    .controller('SimpleBotanica-controller', function ($http, $rootScope, $scope, $localStorage, $location,
                                                       $uibModal, userFactory) {
        $scope.openAuthForm = function () {
            let modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'Auth/AuthForm.html',
                controller: 'authFormController',
                size: 'sm',
                resolve: {
                    $test: function () {
                        return 'res';
                    }
                }
            });
            modalInstance.result.then(function successCallback(response) {
                    console.log('index.js response=' + response)
                },
                function errorCallback() {
                    console.log(new Date());
                })
        };

        $scope.isUserLoggedIn = function () {
            return userFactory.isAuthorized();
        };

        $scope.logOut = function () {
            userFactory.logOut();
        }

    })