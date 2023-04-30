package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.botanica.dtos.CareDtoShort;
import ru.botanica.dtos.PlantDto;
import ru.botanica.dtos.PlantDtoShort;
import ru.botanica.responses.AppResponse;
import ru.botanica.services.CareService;
import ru.botanica.services.PlantService;

import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PlantController {
    private final PlantService plantService;
    private final CareService careService;

    /**
     * Возвращает список растений, учитывающий заданные для поиска параметры
     *
     * @param page  Номер страницы
     * @param size  Размер страницы
     * @param title Название
     * @return Список растений
     */
    @GetMapping("/plants")
    public Page<PlantDtoShort> findAllByFilters(@RequestParam(required = false, defaultValue = "1") int page,
//                                           Изначально помещается 10, но, чтобы при текущих данных в БД было
//                                           две страницы, сместил значение по умолчанию до 8
                                                @RequestParam(required = false, defaultValue = "8") int size,
                                                @RequestParam(required = false) String title) throws Exception {
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
    public PlantDto findById(@PathVariable long id) throws Exception {
        return plantService.findById(id);
    }

    /**
     * Обновляет значения растения
     *
     * @param plantDto JSON с параметрами PlantDto.class
     * @return responseEntity с кодом и сообщением
     */
    @PutMapping("/plant")
    public ResponseEntity<?> updateById(@RequestBody PlantDto plantDto) throws Exception {
        log.debug("Попытка обновления, входящие данные: {}", plantDto.toString());
        plantService.updatePlant(plantDto);
        log.debug("Растение обновлено, id {}", plantDto.getId());
        return new ResponseEntity<>(new AppResponse(HttpStatus.OK.value(),
                "Растение обновлено, id: " + plantDto.getId()), HttpStatus.OK);
    }

    /**
     * Добавляет растения с данными значениями
     *
     * @param plantDto      JSON с параметрами PlantDto.class
     * @param isOverwriting флаг, нужно ли перезаписать существующие растение
     * @return responseEntity с кодом и сообщением
     */
    @PostMapping("/plant")
    public ResponseEntity<?> addPlant(@RequestBody PlantDto plantDto,
                                      @RequestParam(name = "isOverwriting", defaultValue = "false") boolean isOverwriting) throws Exception {
        log.debug("Попытка сохранения: {}", plantDto.toString());
        plantService.addNewPlant(plantDto, isOverwriting);
        log.debug("Растение создано, имя: {}", plantDto.getName());
        return new ResponseEntity<>(new AppResponse(HttpStatus.OK.value(),
                "Растение создано, имя: " + plantDto.getName()), HttpStatus.OK);

    }

    /**
     * Удаляет растение по идентификатору
     *
     * @param id Идентификатор
     * @return responseEntity с кодом и сообщением
     */
    @DeleteMapping("plant/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable long id) throws Exception {
        log.debug("Попытка удаления по id: {}", id);
        plantService.deletePlantById(id);
        log.debug("Удаление успешно, id: {}", id);
        return new ResponseEntity<>(new AppResponse(HttpStatus.OK.value(),
                "Растение удалено, id: " + id), HttpStatus.OK);
    }

    /**
     * Возвращает список процедур, если они активны
     *
     * @return Список процедур
     */
    @GetMapping("/care_types")
    public List<CareDtoShort> findAllActiveCares() throws Exception {
        return careService.findAllActive();
    }
}
