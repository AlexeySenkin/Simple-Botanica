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
    USER_SERVICE_PATH: '',
    API_V: '',
    // директория хранения картинок растений
    IMG_DIRECTORY: 'img/db/',
    NO_PHOTO_PLACEHOLDER: "img/No-Image-Placeholder.png",
    // кнопки ухода
    ACTION_BUTTONS: [{id: 1, img: 'img/watering_can.png', hint: 'Полить'},
        {id: 2, img: 'img/sprayer.png', hint: 'Опрыскать'},
        {id: 3, img: 'img/fertilizer.png', hint: 'Удобрить'},
        {id: 4, img: 'img/shears.png', hint: 'Обрезать'},
        {id: 5, img: 'img/re-potting.png', hint: 'Пересадить'}]
})

botanicaApp.factory('userFactory', function ($localStorage, $http, $q, settings) {
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

    userFactoryObj.updateWebUser = function (username, token) {
        $localStorage.botanicaWebUser = {
            username: username,
            token: token,
        };
    };

    userFactoryObj.tryToAuth = function (user) {
        let defer = $q.defer();
        $http.post(settings.AUTH_PATH + '/auth', user).then(function successCallback(response) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                userFactoryObj.updateWebUser(user.username, response.data.token)

                user.username = null;
                user.password = null;
                defer.resolve({user: user, status: 200});
            },
            function errorCallback(reason) {
                defer.reject(reason);
            });
        return defer.promise;
    }

    userFactoryObj.registerUser = function (user) {
        let defer = $q.defer();
        $http.post(settings.AUTH_PATH + '/register', user).then(
            function successCallback(response) {
                userFactoryObj.updateWebUser(user.username, response.data.token)

                user.username = null;
                user.password = null;
                user.email = null;

                defer.resolve({user: user, status: 200})
            }, function errorCallback(reason) {
                defer.reject(reason);
            });
        return defer.promise;
    }
    return userFactoryObj;
})

botanicaApp.factory('plantFactory', function ($http, settings, $q) {
    let plantFactoryObj = {};
    const plantPath = settings.PLANTS_PATH;

    plantFactoryObj.getPlantPhoto = function (filePathString) {
        if (typeof filePathString !== undefined && filePathString != null && filePathString.trim() !== '') {
            return settings.IMG_DIRECTORY + filePathString;
        }
        return settings.NO_PHOTO_PLACEHOLDER;
    };

    plantFactoryObj.getPlant = function (plantId) {
        let defer = $q.defer();
        let responseEntity = {data: {}, photoPath: ""};
        if (plantId) {
            $http.get(plantPath + '/plant/' + plantId).then(function successCallback(response) {
                responseEntity.data = response.data;
                responseEntity.photoPath = plantFactoryObj.getPlantPhoto(responseEntity.data.filePath);
                defer.resolve(responseEntity);
            }, function errorCallback(reason) {
                defer.reject(reason);
            });
            return defer.promise;
        } else {
            responseEntity.data = {
                id: null,
                name: "",
                family: "",
                genus: "",
                shortDescription: "",
                description: "",
                isActive: true,
                filePath: ""
            };
            responseEntity.photoPath = plantFactoryObj.getPlantPhoto("");
            defer.resolve(responseEntity);
            return defer.promise;
        }
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
        let plant = plantObject;
        if (!plant.isActive) {
            plant.isActive = true;
        }
        let defer = $q.defer();
        if (plant.id === null) {
            console.log('Saving a new plant: ' + plant);
            $http.post(plantPath + '/plant', JSON.stringify(plant)).then(function successCallback(response) {
                defer.resolve(response);
                console.log('New plant saved, id=' + response.data);
            }, function errorCallback(reason) {
                defer.reject(reason);
                console.log('Error occurred while saving a new plant. error code:' + reason.data.status);
            })
        } else {
            console.log('Saving changes into plant: ' + plant);
            $http.put(plantPath + '/plant', JSON.stringify(plant))
                .then(function successCallback(response) {
                    defer.resolve(response);
                    console.log('Changes into plant with id = ' + response.data + ' saved');
                }, function errorCallback(reason) {
                    defer.reject(reason);
                    console.log('Error occurred while saving changes into plant. error code:' + reason.data.status);
                })

        }
        return defer.promise;
    };

    plantFactoryObj.getAllPlants = function (userId) {
        let getPath = "";
        let defer = $q.defer;
        if (userId === null) {
            //    растения из базы знаний
            getPath = plantPath + '/plants';
        } else {
            //    растения из списка пользователя
            getPath = settings.USER_SERVICE_PATH + '/plants';
        }

        $http.get(getPath).then(

        )
    }
    return plantFactoryObj;
});

botanicaApp
    .controller('SimpleBotanica-controller', function ($http, $rootScope, $scope, $localStorage, $location,
                                                       $uibModal, userFactory) {
        $scope.openAuthRegisterForm = function (registerSign) {
            let modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'Auth/AuthAndRegisterForm.html',
                controller: 'authFormController',
                size: 'sm',
                resolve: {
                    test: function () {
                        return registerSign;
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