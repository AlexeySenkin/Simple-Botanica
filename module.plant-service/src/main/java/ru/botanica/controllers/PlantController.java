package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.botanica.entities.plants.PlantDto;
import ru.botanica.services.PlantService;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PlantController {
    private final PlantService plantService;
    private final int PAGE_SIZE = 10;


    /**
     * Возвращает список растений, учитывающий заданные для поиска параметры
     *
     * @param page  Номер страницы
     * @param title Название
     * @return Список растений
     */
    @GetMapping("/plants")
    public Page<PlantDto> findAllByFilters(@RequestParam(required = false, defaultValue = "1") int page,
                                           @RequestParam(required = false) String title) {
        int currentPage = page - 1;
        int sizeValue = PAGE_SIZE;
        return plantService.findAll(title, PageRequest.of(currentPage, sizeValue));
    }

    /**
     * Возвращает растение по идентификатору
     *
     * @param id Идентификатор
     * @return Растение
     */
    @GetMapping("/plant/{id}")
    public PlantDto findById(@PathVariable long id) {
        return plantService.findById(id);
    }

    /**
     * Обновляет значения растения
     *
     * @param plantId          Идентификатор
     * @param name             Название
     * @param family           Семейство
     * @param genus            Род
     * @param shortDescription Краткое описание
     * @param description      Полное описание
     * @param filePath         Путь к фото
     * @param isActive         Флаг активного растения
     * @return Идентификатор
     */
    @PutMapping("/plant")
    public Long updateById(@RequestParam(name = "plant_id") long plantId, @RequestParam String name, @RequestParam String family,
                           @RequestParam String genus, @RequestParam(name = "short_description") String shortDescription,
                           @RequestParam String description, @RequestParam(required = false, name = "file_path") String filePath,
                           @RequestParam boolean isActive) {
        return plantService.updateByID(plantId, name, family, genus, shortDescription, description, filePath, isActive);
    }

    /**
     * Добавляет растения с данными значениями
     *
     * @param name             Название
     * @param family           Семейство
     * @param genus            Род
     * @param shortDescription Краткое описание
     * @param description      Полное описание
     * @param filePath         Путь к фото
     * @param isActive         Флаг активного растения
     * @return Идентификатор
     */
    @PostMapping("/plant")
    public Long addPlant(@RequestParam String name, @RequestParam String family,
                         @RequestParam String genus, @RequestParam(name = "short_description") String shortDescription,
                         @RequestParam String description, @RequestParam(required = false, name = "file_path") String filePath,
                         @RequestParam boolean isActive) {
        return plantService.addProduct(name, family, genus, shortDescription, description, filePath, isActive);
    }

    /**
     * Удаляет растение по идентификатору
     *
     * @param id Идентификатор
     */
    @DeleteMapping("plant/{id}")
    public void deletePlant(@PathVariable long id) {
        plantService.deletePlantById(id);
    }
}
