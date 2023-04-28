angular.module('Simple-Botanica-app')
    .controller('plant-card-controller', function ($rootScope, $scope, $localStorage,
                                                   settings, userFactory, plantFactory, plantInfo) {

        $scope.actionButtons = settings.ACTION_BUTTONS;
        $scope.imgPath = settings.IMG_DIRECTORY;

        $scope.plant = {
            id: null,
            name: "",
            family: "",
            genus: "",
            shortDescription: "",
            description: "",
            isActive: true,
            filePath: ""
        }

        $scope.isAdmin = function () {
            return userFactory.isAdmin();
        }

        $scope.isAuthorized = function () {
            return userFactory.isAuthorized();
        }

        $scope.careAction = function (careId) {
            switch (careId) {
                case 1: {
                    console.log(plantInfo.plantObject);
                    console.log("полить");
                    break;
                }
                case 3: {
                    console.log("опрыскать");
                    break;
                }
                case 2: {
                    console.log("удобрить");
                    break
                }
                case 4: {
                    console.log("обрезать");
                    break;
                }
                case 5: {
                    console.log("пересадить");
                    break;
                }
            }
        };

        $scope.addPlantToUserList = function (plantId) {
            console.log("Добавление растения " + plantId + "в список пользователя");
            plantFactory.addPlantToUsersList($localStorage.botanicaWebUser.userId, plantId).then(
                function successCallBack(response) {
                    console.log(response);
                    $localStorage.plantListCallPlace = 1;
                    location.assign("#!/user-plants");
                }, function errorCallback(reason) {
                    console.log(reason);
                }
            );
        };

        $scope.showPlantDetails = function () {
            let plantId = $localStorage.plantId;
            // признак откуда была открыта карточка растения
            $scope.callPlace = $localStorage.plantListCallPlace;

            plantFactory.getPlant(plantId).then(function successCallback(response) {
                $scope.plant = response.data;
                $scope.plantPhotoCurrent = response.photoPath;
                if ($localStorage.plantListCallPlace === 1) {
                    $scope.actionButtons = response.actualCareButtons;
                } else {
                    $scope.actualCare = response.actualCare;
                    $scope.careDictionary = updateCareWithActualPlan(plantInfo.actions, $scope.actualCare);
                }
            }, function errorCallback(reason) {
                console.log('error occurred while fetching a plant info:' + reason);
            });
        }

        let updateCareWithActualPlan = function (careDictionary, actualCarePlan) {
            let newCareDictionaryWithActualIntervals = [];
            for (let i = 0; i < careDictionary.length; i++) {
                let interval = null;
                let care = careDictionary[i];
                if (actualCarePlan) {
                    for (let j = 0; j < actualCarePlan.length; j++) {
                        if (careDictionary[i].id === actualCarePlan[j].careDto.id) {
                            interval = actualCarePlan[j].careCount;
                            break;
                        }
                    }
                }
                care.interval = interval;
                newCareDictionaryWithActualIntervals.push(care);
            }
            return newCareDictionaryWithActualIntervals;
        }

        let findCareInPlanById = function (carePlan, id) {
            for (let i = 0; i < carePlan.length; i++) {
                if (carePlan[i].careDto.id === id) {
                    return i;
                }
            }
            return -1;
        }

        let newCareFromPlan = function (carePlanItm) {
            return {
                id: null,
                careCount: carePlanItm.interval,
                careVolume: 300,
                careDto: {
                    id: carePlanItm.id,
                    name: carePlanItm.name,
                    active: true
                }
            };
        }

        let assemblePlantObject = function (plantObject, newCarePlan) {
            let newPlantObject = plantObject;
            let carePlan = [];
            let currentCarePlan = plantObject.standardCarePlan;
            for (let i = 0; i < newCarePlan.length; i++) {
                let j = findCareInPlanById(currentCarePlan, newCarePlan[i].id);
                if (newCarePlan[i].interval != null) {
                    if (j >= 0) {
                        let updCarePlan = currentCarePlan[j];
                        updCarePlan.careCount = newCarePlan[i].interval;
                        carePlan.push(updCarePlan);
                        // break;
                    } else {
                        let updCarePlan = newCareFromPlan(newCarePlan[i])
                        carePlan.push(updCarePlan);
                        // break;
                    }
                }
            }
            newPlantObject.standardCarePlan = carePlan;
            return newPlantObject;
        }


        $scope.savePlant = function () {
            let newPlantObject = assemblePlantObject($scope.plant, $scope.careDictionary);
            // console.log(newPlantObject);
            plantFactory.saveOrUpdate(newPlantObject).then(function (response) {
                // console.log(response);
                location.assign('#!/');
            }, function (reason) {
                console.log(reason);
            })
        }

        $scope.home = function () {
            $localStorage.plantListCallPlace = 0;
            location.assign('#!/')
        }

        $scope.addPlantPhoto = function () {
            alert("Извините, мы пока не умеем загружать фото!");
        }

        $scope.showPlantDetails();

        $scope.careSelect = function (careId) {
            let careAction = document.getElementById("careAction-" + careId.toString());
            let careInterval = document.getElementById("careInterval-" + careId.toString());
            if (careAction && careInterval) {
                careInterval.disabled = !careAction.checked;
                if (careInterval.disabled) {
                    careInterval.value = null;
                }
            }
        }

        $scope.toCheck = function (careId) {
            let actualCare = $scope.actualCare
            if (actualCare) {
                for (let i = 0; i < actualCare.length; i++) {
                    if (actualCare[i].careDto.id === careId) {
                        return true;
                    }
                }
            }
            return false;
        }

        $scope.enableCareIntervalInput = function (careId) {
            let carePlanArr = document.getElementsByName("carePlan");

            for (const carePlanArrElement of carePlanArr) {
                if (carePlanArrElement.value === careId.toString() && carePlanArrElement.checked) {
                    return true;
                }
            }
            return false;
        }

        $scope.fillInterval = function (careId) {
            let careInterval = document.getElementById("careInterval-" + careId.toString());
            if (careInterval) {
                let actualCare = $scope.actualCare;
                careInterval.value = null;
                for (let i = 0; i < actualCare.length; i++) {
                    if (actualCare[i].careDto.id === careId) {
                        careInterval.value = actualCare[i].careCount;
                        break;
                    }
                }
            }
            return true;
        }


        $scope.showInterval = function () {
            let intervals = document.getElementsByName("careInterval");
            for (const interval of intervals) {
                console.log(interval.value);
            }
        }
    })