package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.botanica.entities.plants.PlantDto;
import ru.botanica.services.PlantService;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PlantController {
    private final PlantService plantService;

    /**
     * Возвращает список растений, учитывающий заданные для поиска параметры
     *
     * @param page  Номер страницы
     * @param size  Размер страницы
     * @param title Название
     * @return Список растений
     */
    @GetMapping("/plants")
    public Page<PlantDto> findAllByFilters(@RequestParam(required = false, defaultValue = "1") int page,
//                                           Изначально помещается 10, но, чтобы при текущих данных в БД было
//                                           две страницы, сместил значение по умолчанию до 8
                                           @RequestParam(required = false, defaultValue = "8") int size,
                                           @RequestParam(required = false) String title) {
        int currentPage = page - 1;
        return plantService.findAll(title, PageRequest.of(currentPage, size));
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
     * @param plantDto JSON с параметрами PlantDto.class
     * @return Идентификатор
     */
    @PutMapping("/plant")
    public Long updateById(@RequestBody PlantDto plantDto) {
        return plantService.updateByID(plantDto);
    }

    /**
     * Добавляет растения с данными значениями
     *
     * @param plantDto JSON с параметрами PlantDto.class
     * @return Идентификатор
     */
    @PostMapping("/plant")
    public Long addPlant(@RequestBody PlantDto plantDto) {
//        return plantService.addPlant(plantDto);
        return plantService.addNewPlant(plantDto).getId();
    }

    /**
     * Удаляет растение по идентификатору
     *
     * @param id Идентификатор
     */
    @DeleteMapping("plant/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable long id) {
        try {
            return ResponseEntity.ok(plantService.deletePlantById(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка удаления");
        }
    }
}
