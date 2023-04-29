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

        let card = this;

        $scope.isAdmin = function () {
            return userFactory.isAdmin();
        }

        $scope.isAuthorized = function () {
            return userFactory.isAuthorized();
        }

        card.careLogEntries = {};

        $scope.careAction = function (careId) {
            plantFactory.careAction($localStorage.plantId, careId, 1, 20).then(
                function successCallback(response) {
                    $scope.careLog = response;
                    card.careLogEntries = response.content;
                    $scope.showDescription = false;
                    console.log('выполнен уход: ' + careId);
                }, function errorCallback(reason) {
                    console.log(reason.data)
                }
            );
        };

        $scope.addPlantToUserList = function (plantId) {
            console.log("Добавление растения " + plantId + "в список пользователя");
            plantFactory.addPlantToUsersList($localStorage.botanicaWebUser.userId, plantId).then(
                function successCallBack(response) {
                    console.log(response);
                    $localStorage.plantListCallPlace = 1;
                    location.replace("#!/user-plants");
                }, function errorCallback(reason) {
                    console.log(reason);
                }
            );
        };

        card.deletePlantFromUserList = function (userPlantId) {
            console.log("Удаление растения: " + userPlantId);
            plantFactory.deletePlantFromUserList(userPlantId);
            location.replace("#!/user-plants");
        }

        $scope.showPlantDetails = function () {
            let plantId = $localStorage.plantId;
            // признак откуда была открыта карточка растения
            $scope.callPlace = $localStorage.plantListCallPlace;

            plantFactory.getPlant(plantId).then(function successCallback(response) {
                    $scope.plant = response.data;
                    $scope.plantPhotoCurrent = response.photoPath;
                    if ($localStorage.plantListCallPlace === 1) {
                        $scope.actionButtons = response.actualCareButtons;
                        //журнал ухода
                        plantFactory.getPlantCareLog(plantId, 1, 20).then(
                            function successCallback(response) {
                                console.log('получен журнал ухода');
                                card.careLogEntries = response.data.content;
                                $scope.careLog = response.data;
                                $scope.showDescription = false;
                            }, function errorCallback(reason) {
                                console.log('ошибка при получении журнала ухода: ' + reason.data.status);
                            });
                    } else {
                        $scope.actualCare = response.actualCare;
                        $scope.careDictionary = updateCareWithActualPlan(plantInfo.actions, $scope.actualCare);
                        $scope.showDescription = true;
                    }
                }, function errorCallback(reason) {
                    console.log('ошибка при получении информации о растении:' + reason.data.status);
                }
            )
            ;
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
            if (!carePlan) {
                return -1;
            }
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
                    } else {
                        let updCarePlan = newCareFromPlan(newCarePlan[i])
                        carePlan.push(updCarePlan);
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

        $scope.changeOnDescription = function (toShowDescription) {
            $scope.showDescription = toShowDescription;
            if (!toShowDescription) {
                plantFactory.getPlantCareLog($localStorage.plantId, 1, 20).then(
                    function successCallback(response) {
                        // $scope.careLogEntries = response.data.content;
                        $scope.careLog = response.data;
                        card.careLogEntries = response.data.content;
                    }, function errorCallback(reason) {
                        console.log('error ocurred while fetching plant care log data:' + reason.data.status);
                    }
                )
            }
        }

        $scope.showPlantDetails();

    })