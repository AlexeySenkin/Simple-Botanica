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
    private final int PAGE_SIZE = 7;

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

}
