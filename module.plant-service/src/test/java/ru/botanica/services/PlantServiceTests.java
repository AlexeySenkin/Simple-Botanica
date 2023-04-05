package ru.botanica.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.botanica.entities.photos.PlantPhoto;
import ru.botanica.entities.photos.PlantPhotoRepository;
import ru.botanica.entities.plants.Plant;
import ru.botanica.entities.plants.PlantDto;
import ru.botanica.entities.plants.PlantRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PlantService.class})
@ExtendWith(MockitoExtension.class)
public class PlantServiceTests {

    @MockBean
    private PlantRepository plantRepository;
    @MockBean
    private PlantPhotoRepository photoRepository;
    @Autowired
    private PlantService plantService;

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
                () -> assertEquals(plant.isActive(), plantDto.isActive())
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

}
