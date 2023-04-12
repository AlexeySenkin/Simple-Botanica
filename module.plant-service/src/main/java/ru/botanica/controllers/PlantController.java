package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.botanica.entities.plants.PlantDto;
import ru.botanica.entities.plants.PlantDtoShort;
import ru.botanica.entities.responses.AppResponse;
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
    public Page<PlantDtoShort> findAllByFilters(@RequestParam(required = false, defaultValue = "1") int page,
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
     * @return responseEntity с кодом и сообщением
     */
    @PutMapping("/plant")
    public ResponseEntity<?> updateById(@RequestBody PlantDto plantDto) {
        Long id = plantDto.getId();
        if (!plantService.isIdExist(id)) {
            /**
             * Если растение не существует
             */
            return new ResponseEntity<>(new AppResponse(HttpStatus.BAD_REQUEST.value(),
                    "Растение не существует, id- " + id), HttpStatus.BAD_REQUEST);
        } else {
            try {
                plantService.updatePlant(plantDto);
            } catch (Exception e) {
                /**
                 * Неудачное обновление
                 */
                return new ResponseEntity<>(new AppResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        "Сервер не смог обновить растение с id " + id), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            /**
             * Удачное обновление
             */
            return new ResponseEntity<>(new AppResponse(HttpStatus.OK.value(),
                    "Растение обновлено, id: " + id), HttpStatus.OK);
        }
    }

    /**
     * Добавляет растения с данными значениями
     *
     * @param plantDto JSON с параметрами PlantDto.class
     * @return responseEntity с кодом и сообщением
     */
    @PostMapping("/plant")
    public ResponseEntity<?> addPlant(@RequestBody PlantDto plantDto, @RequestParam(name = "isOverwriting") boolean isOverwriting) {
        if (plantService.findByName(plantDto.getName()).isPresent() && !isOverwriting) {
            /**
             * Если растение существует и его нельзя перезаписывать
             */
            return new ResponseEntity<>(new AppResponse(HttpStatus.BAD_REQUEST.value(),
                    "Растение с таким именем существует"), HttpStatus.BAD_REQUEST);
        } else {
            try {
                plantService.addNewPlant(plantDto);
            } catch (Exception e) {
                /**
                 * Неудачное сохранение
                 */
                return new ResponseEntity<>(new AppResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        "Сервер не смог добавить растение"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            /**
             * Удачное сохранение
             */
            return new ResponseEntity<>(new AppResponse(HttpStatus.OK.value(),
                    "Растение создано, имя: " + plantDto.getName()), HttpStatus.OK);
        }
    }

    /**
     * Удаляет растение по идентификатору
     *
     * @param id Идентификатор
     * @return responseEntity с кодом и сообщением
     */
    @DeleteMapping("plant/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable long id) {
        if (!plantService.isIdExist(id)) {
            /**
             * Если растение не существует
             */
            return new ResponseEntity<>(new AppResponse(HttpStatus.BAD_REQUEST.value(),
                    "Растение не существует, id- " + id), HttpStatus.BAD_REQUEST);
        } else {
            try {
                plantService.deletePlantById(id);
            } catch (Exception e) {
                /**
                 * Неудачное удаление
                 */
                return new ResponseEntity<>(new AppResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        "Сервер не смог удалить растение с id " + id), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            /**
             * Удачное удаление
             */
            return new ResponseEntity<>(new AppResponse(HttpStatus.OK.value(),
                    "Растение удалено, id: " + id), HttpStatus.OK);
        }
    }
}
