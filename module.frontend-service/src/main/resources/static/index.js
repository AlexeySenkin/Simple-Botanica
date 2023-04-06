(function () {
    angular.module('Simple-Botanica-app', ['ngStorage', 'ngRoute', 'ui.bootstrap'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            // .when('/', {templateUrl: 'index.html', controller: 'SimpleBotanica-controller'})
            .when('/', {templateUrl: 'Plants/Plants.html', controller: 'plants-controller'})
            .when('/plant-info', {templateUrl: 'PlantCard/PlantCard.html', controller: 'plant-card-controller'})
            .when('/user-profile', {templateUrl: 'User/UserProfile.html', controller: 'plant-card-controller'})
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
    ENTRY_POINT: 'http://localhost:9890/botanica/',
    // пока нет шлюза для доступа через единую точку тут будут лежать адреса сервисов
    PLANTS_PATH: 'http://localhost:8189/botanica/api/',
    API_V: '',
    // директория хранения картинок растений
    IMG_DIRECTORY: 'img/db/',
    // кнопки ухода
    ACTION_BUTTONS: [{id: 1, img: 'img/watering_can.png', hint: 'Полить'},
        {id: 2, img: 'img/sprayer.png', hint: 'Опрыскать'},
        {id: 3, img: 'img/fertilizer.png', hint: 'Удобрить'},
        {id: 4, img: 'img/shears.png', hint: 'Обрезать'},
        {id: 5, img: 'img/re-potting.png', hint: 'Пересадить'}]
})

botanicaApp.factory('roleCheckFactory', function ($localStorage) {
    let roleCheckFactoryObj = {};
    roleCheckFactoryObj.isAdmin = function () {
        return !!(($localStorage.botanicaWebUser) && ($localStorage.botanicaWebUser.role === 'admin'));
    };
    roleCheckFactoryObj.isAuthorized = function () {
        return !!($localStorage.botanicaWebUser);
    };
    return roleCheckFactoryObj;
})

botanicaApp
    .controller('SimpleBotanica-controller', function ($http, $rootScope, $scope, $localStorage, $location,
                                                       $uibModal, roleCheckFactory) {
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
            return roleCheckFactory.isAuthorized();
        };

    })