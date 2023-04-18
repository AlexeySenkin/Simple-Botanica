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
    USER_SERVICE_PATH: 'http://localhost:8187/botanica/api',
    API_V: '',
    // директория хранения картинок растений
    IMG_DIRECTORY: 'img/db/',
    NO_PHOTO_PLACEHOLDER: "img/No-Image-Placeholder.png",
    // кнопки ухода
    ACTION_BUTTONS: [{id: 1, img: 'img/watering_can.png', hint: 'Полить'},
        {id: 3, img: 'img/sprayer.png', hint: 'Опрыскать'},
        {id: 2, img: 'img/fertilizer.png', hint: 'Удобрить'},
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
            userId: 0
        };
        userFactoryObj.getUserId(username).then(function successCallback(response) {
            $localStorage.botanicaWebUser.userId = response.userId;
        });
    };

    userFactoryObj.getUserId = function (username) {
        let defer = $q.defer();
        $http.get(settings.USER_SERVICE_PATH + '/user', {params: {username: username}}).then(
            function successCallback(response) {
                defer.resolve({userId: response.data});
            }, function errorCallback(reason) {
                defer.reject(reason);
            }
        );
        return defer.promise;
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

    userFactoryObj.getUserProfileData = function () {
        let defer = $q.defer();
        if ($localStorage.botanicaWebUser && $localStorage.botanicaWebUser.userId === 0) {
            console.log('user id not found. contact administrator');
            defer.reject({code: 404, message: "user id not found"})
            return defer.promise;
        }
        $http.get(settings.USER_SERVICE_PATH + '/user/' + $localStorage.botanicaWebUser.userId)
            .then(function successCallback(response) {
                defer.resolve(response);
            }, function errorCallback(reason) {
                defer.reject({code: reason.data.status, message: reason.data.message});
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

        let formCareButtonsObj = function (actualCareList) {
            let actualCareButtons = [];
            for (let i = 0; i < actualCareList.length; i++) {
                for (let j = 0; j < settings.ACTION_BUTTONS.length; j++) {
                    if (actualCareList[i].careDto.id === settings.ACTION_BUTTONS[j].id && actualCareList[i].careDto.active) {
                        actualCareButtons.push(settings.ACTION_BUTTONS[j]);
                        break;
                    }
                }
            }
            actualCareButtons.sort((a, b) => {
                if (a.id < b.id) {
                    return -1;
                }
                if (a.id > b.id) {
                    return 1
                }
                return 0
            });
            return actualCareButtons;
        }

        plantFactoryObj.getPlant = function (plantId) {
            let defer = $q.defer();
            let responseEntity = {data: {}, photoPath: ""};
            if (plantId) {
                $http.get(plantPath + '/plant/' + plantId).then(function successCallback(response) {
                    responseEntity.data = response.data;
                    responseEntity.actualCareButtons = formCareButtonsObj(response.data.cares);
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

        plantFactoryObj.getAllPlants = function (userId, nameFilter, page, size) {
            let getPath = "";
            let defer = $q.defer;
            if (userId === null) {
                //    растения из базы знаний
                getPath = plantPath + '/plants';
            } else {
                //    растения из списка пользователя
                getPath = settings.USER_SERVICE_PATH + '/user_plants';
                $http.get(getPath, {parameters: {userId: userId, page: page, size: size, filter: nameFilter}}).then(
                    function successCallback(response) {
                        defer.resolve();
                    },
                    function errorCallback(reason) {
                        defer.reject();
                    }
                )
            }
            return defer.promise;
        }

        plantFactoryObj.addPlantToUsersList = function () {

        }

        return plantFactoryObj;
    }
)
;

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
                    console.log('Auth response=' + response)
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