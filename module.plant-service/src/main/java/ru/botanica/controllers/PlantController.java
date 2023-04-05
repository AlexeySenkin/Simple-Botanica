package ru.botanica.controllers;

import jakarta.annotation.PostConstruct;
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

//    Временный метод для тестов на месте
    @PostConstruct
    public void init() {
//        Отработал верно: по этому адресу у меня растение с уже новыми параметрами. Поле с file_path null в БД появилось
//        Фронт также показал изменения
//        updateById(6L, "Тестовый цветок", "Тестовые", "Новый", "Цветок для теста",
//                "Цветок стал невероятнее!", null, true);

//        Отработал верно: тут чисто после первых изменений отработало по фото. Взял фото с алоказии. Фронт его отрисовал
//        updateById(6L, "Тестовый цветок", "Тестовые", "Новый", "Цветок для теста",
//                "Цветок стал невероятнее!", "Alocasia.jpg", true);

//        Отработал верно: По существующему полю вместо пути к фото вставило null, т.к. путь не было прислан
//        updateById(6L, "Тестовый цветок", "Тестовые", "Новый", "Цветок для теста",
//                "Цветок стал невероятнее!", null, true);

//        Отработал верно: выдало ошибку. Точнее т.к. это PostConstruct, уронило сервис. Здесь null имя
//        updateById(6L, null, "Тестовые", "Новый", "Цветок для теста",
//                "Цветок стал невероятнее!", null, true);

//        Отработал верно: выдало ошибку. Здесь null семейство
//        updateById(6L, "Тестовый цветок", null, "Новый", "Цветок для теста",
//                "Цветок стал невероятнее!", null, true);

//        Отработал верно: выдало ошибку. Здесь null род
//        updateById(6L, "Тестовый цветок", "Тестовые", null, "Цветок для теста",
//                "Цветок стал невероятнее!", null, true);

//        Отработал верно: выдало ошибку. Здесь null краткое описание
//        updateById(6L, "Тестовый цветок", "Тестовые", "Новый", null,
//                "Цветок стал невероятнее!", null, true);

//        Отработал верно: выдало ошибку. Здесь null описание
//        updateById(6L, "Тестовый цветок", "Тестовые", "Новый", "Цветок для теста",
//                null, null, true);


    }

    /**
     * Возвращает список растений, учитывающий заданные для поиска параметры
     *
     * @param page Номер страницы
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
     * Обновляет значения растения {plant_id, name, short_description, description, file_path}
     *
     * @param plantId          Идентификатор
     * @param name              Название
     * @param family            Семейство
     * @param genus             Род
     * @param shortDescription Краткое описание
     * @param description       Полное описание
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
}
