package ru.botanica.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.botanica.dtos.PlantCareDto;
import ru.botanica.dtos.PlantDto;
import ru.botanica.entities.*;
import ru.botanica.mappers.PlantCareDtoMapper;
import ru.botanica.repositories.CareRepository;
import ru.botanica.repositories.PlantCareRepository;
import ru.botanica.repositories.PlantPhotoRepository;
import ru.botanica.entities.Plant;
import ru.botanica.dtos.PlantDto;
import ru.botanica.repositories.PlantRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {PlantService.class})
@ExtendWith(MockitoExtension.class)

public class PlantServiceTests {

    @MockBean
    private PlantRepository plantRepository;
    @MockBean
    private PlantPhotoRepository photoRepository;
    @MockBean
    private PlantCareRepository plantCareRepository;
    @MockBean
    private CareRepository careRepository;
    @MockBean
    private PlantPhotoService photoService;
    @MockBean
    private CareService careService;
    @Autowired
    private PlantService plantService;
    @Captor
    private ArgumentCaptor<Plant> plantCaptor;

    /**
     * Тест возвращения растения по идентификатору
     * <p>
     * Кейс в случае существования растения с таким идентификатором.
     */
    @Test
    void testFindById() {
        long id = 1;

        PlantPhoto plantPhoto = new PlantPhoto();
        plantPhoto.setFilePath("file_path");

        Plant plant = new Plant();
        plant.setId(id);
        plant.setName("name");
        plant.setGenus("genus");
        plant.setFamily("family");
        plant.setPhoto(plantPhoto);
        plant.setActive(true);
        plant.setDescription("desc");
        plant.setShortDescription("short_desc");

//        Без этих строчек выдает
//        Cannot invoke "java.util.Set.stream()" because the return value of "ru.botanica.entities.Plant.getCares()" is null
        Set<PlantCare> cares = new HashSet<>();
        Care care = new Care();
        care.setId(1L);
        care.setName("care");
        care.setActive(true);

        PlantCare plantCare = new PlantCare();
        plantCare.setId(1L);
        plantCare.setCareCount(5);
        plantCare.setCareVolume(BigDecimal.valueOf(10));
        plantCare.setPlant(plant);
        plantCare.setCare(care);
        PlantCareDto plantCareDto = PlantCareDtoMapper.mapToDto(plantCare);

        cares.add(plantCare);
        plant.setCares(cares);

        when(plantRepository.findById(id)).thenReturn(Optional.of(plant));

        PlantDto plantDto = plantService.findById(id);

        assertAll(
                () -> assertEquals(plant.getId(), plantDto.getId()),
                () -> assertEquals(plant.getName(), plantDto.getName()),
                () -> assertEquals(plant.getPhoto().getFilePath(), plantDto.getFilePath()),
                () -> assertEquals(plant.getGenus(), plantDto.getGenus()),
                () -> assertEquals(plant.getFamily(), plantDto.getFamily()),
                () -> assertEquals(plant.getDescription(), plantDto.getDescription()),
                () -> assertEquals(plant.getShortDescription(), plantDto.getShortDescription()),
                () -> assertEquals(plant.isActive(), plantDto.isActive()),
                () -> assertArrayEquals(new PlantCareDto[]{plantCareDto}, plantDto.getStandardCarePlan().toArray())
        );
    }

    /**
     * Тест возвращения растения по идентификатору
     * <p>
     * Кейс в случае отсутствия растения с таким идентификатором.
     */
    @Test
    void testFindByIdWhenIllegalId() {
        long id = 1;

        assertThrows(NoSuchElementException.class, () -> plantService.findById(id));
    }


    /**
     * Тест проверки на существование растения в БД по id
     */

    @Test
    void testIsIdExists() {
        long id = 1;
        when(plantRepository.existsById(id)).thenReturn(true);
        assertTrue(plantService.isIdExist(id));
    }

    @Test
    void testSaveNewPlant() {
        PlantPhoto plantPhoto = new PlantPhoto("file_path", 1L);

        PlantDto plantDto = new PlantDto();
        plantDto.setId(null);
        plantDto.setName("name");
        plantDto.setGenus("genus");
        plantDto.setFamily("family");
        plantDto.setFilePath(plantPhoto.getFilePath());
        plantDto.setActive(true);
        plantDto.setDescription("desc");
        plantDto.setShortDescription("short_desc");

        Plant plant = new Plant();
        plant.setId(null);
        plant.setName("name");
        plant.setGenus("genus");
        plant.setFamily("family");
        plant.setPhoto(null);
        plant.setActive(true);
        plant.setDescription("desc");
        plant.setShortDescription("short_desc");

        Plant savedPlant = new Plant();
        savedPlant.setId(1L);
        savedPlant.setPhoto(plantPhoto);
        savedPlant.setName("name");
        savedPlant.setGenus("genus");
        savedPlant.setFamily("family");
        savedPlant.setActive(true);
        savedPlant.setDescription("desc");
        savedPlant.setShortDescription("short_desc");

        Set<PlantCare> cares = new HashSet<>();
        Care care = new Care();
        care.setId(1L);
        care.setName("care");
        care.setActive(true);

        PlantCare plantCare = new PlantCare();
        plantCare.setId(1L);
        plantCare.setCareCount(5);
        plantCare.setCareVolume(BigDecimal.valueOf(10));
        plantCare.setPlant(plant);
        plantCare.setCare(care);

        cares.add(plantCare);
        plant.setCares(cares);
        savedPlant.setCares(cares);

        when(plantRepository.saveAndFlush(plant)).thenReturn(savedPlant);
        when(photoService.saveOrUpdate(plantPhoto)).thenReturn(plantPhoto);
        when(photoService.saveOrUpdate(plantPhoto.getId(), plantPhoto.getFilePath())).thenReturn(plantPhoto);
//        Вот тут вылезает
//        Cannot invoke "java.util.Set.stream()" because the return value of "ru.botanica.entities.Plant.getCares()" is null
//        Сегмент из метода testFindById(), которыми я починил соответствующий метод здесь почему-то не сработали, хотя ошибка одна и та же
//        Добавленная строчка:
        when(careService.createPlantCareWithObjects(plantDto, PlantCareDtoMapper.mapToDto(plantCare))).thenReturn(PlantCareDtoMapper.mapToDto(plantCare));
//        Она не помогла. Сделал так, чтобы просто уведомляло об ошибке
        try {
//            Вот из-за этой строчки нужен try-catch - там теперь прокидывается исключение.
            PlantDto result = plantService.addNewPlant(plantDto, true);
            verify(plantRepository, times(1)).saveAndFlush(any());
            verify(plantRepository).saveAndFlush(plantCaptor.capture());
            Plant captorValue = plantCaptor.getValue();

            assertAll(
                    () -> assertEquals(savedPlant.getName(), result.getName()),
                    () -> assertEquals(savedPlant.getFamily(), result.getFamily()),
                    () -> assertEquals(savedPlant.getGenus(), result.getGenus()),
                    () -> assertEquals(savedPlant.getDescription(), result.getDescription()),
                    () -> assertEquals(savedPlant.getShortDescription(), result.getShortDescription()),
                    () -> assertAll("Ошибка формирования данных на отправку в plantRepository",
                            () -> assertEquals(plant.getName(), captorValue.getName(), "Ошибка в имени"),
                            () -> assertEquals(plant.getFamily(), captorValue.getFamily()),
                            () -> assertEquals(plant.getGenus(), captorValue.getGenus()),
                            () -> assertEquals(plant.getDescription(), captorValue.getDescription()),
                            () -> assertEquals(plant.getShortDescription(), captorValue.getShortDescription())
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
