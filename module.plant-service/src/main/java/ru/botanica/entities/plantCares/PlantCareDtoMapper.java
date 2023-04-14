package ru.botanica.entities.plantCares;

import org.springframework.stereotype.Component;
import ru.botanica.entities.care.Care;
import ru.botanica.entities.plants.Plant;

@Component
public final class PlantCareDtoMapper {
    public static PlantCareDto mapToDto(PlantCare plantCare) {
        PlantCareDto plantCareDto = new PlantCareDto();
        plantCareDto.setId(plantCare.getCare().getId());
        plantCareDto.setName(plantCare.getCare().getName());
        plantCareDto.setCareVolume(plantCare.getCareVolume());
        plantCareDto.setCareCount(plantCare.getCareCount());
        return plantCareDto;
    }

//    Я очень сомневаюсь, что это хорошая практика, но на данный момент не могу придумать
//    решение получше. Мало того, что мы на фронт не передаем реальный id операции, так еще и Entity не может
//    существовать без указания привязки по id растения и процедуры в БД. Заранее говорю: сейчас будет доработка,
//    но есть большой шанс, что этот метод просто будет приводить к ошибкам. После теста на добавление в БД процедуры
//    для конкретного растения, все отработало, но уверенности это не прибавило
    public static PlantCare mapToEntity(PlantCareDto plantCareDto, Plant plant, Care care) {
        PlantCare plantCare = new PlantCare();
        plantCare.setCareVolume(plantCareDto.getCareVolume());
        plantCare.setCareCount(plantCareDto.getCareCount());
        plantCare.setPlant(plant);
        plantCare.setCare(care);
        return plantCare;
    }
}
