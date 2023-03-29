(function () {
    angular.module('Simple-Botanica-app', ['ngStorage', 'ngRoute', 'ui.bootstrap'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            // .when('/', {templateUrl: 'index.html', controller: 'SimpleBotanica-controller'})
            .when('/', {templateUrl: 'Plants/plants.html', controller: 'plants-controller'})
            // .when()
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

botanicaApp.factory('plantListFactory', function () {

})
// признак открытия списка растений
botanicaApp.value('botanicaConfig', {
    plantListCallPlace: 1
});

botanicaApp.constant('api_version',{
    api_v: ''
});

botanicaApp
    .controller('SimpleBotanica-controller', function ($http, $rootScope, $scope, $localStorage, $location,
                                                       botanicaConfig, $uibModal, api_version ) {
        botanicaConfig.plantListCallPlace = 2;
        $scope.cp = botanicaConfig.plantListCallPlace;


        $scope.openAuthForm = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'Auth/AuthForm.html',
                controller: 'authFormController',
                size: 'sm',
                resolve: {
                    $test : function(){
                    return 'res';}
                }
            });
            modalInstance.result.then(function successCallback(response){
                    console.log('index.js response=' + response)
                },
                function errorCallback(){
                    console.log(new Date());
                })
        };

        $scope.isUserLoggedIn = function (){
            if ($localStorage.botanicaWebUser) {
            return true;
            } else {
                return false;
            }
        }

    })