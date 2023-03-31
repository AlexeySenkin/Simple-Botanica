package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.botanica.entities.plants.PlantDto;
import ru.botanica.services.PlantService;


@RestController
@RequestMapping("/plants")
@RequiredArgsConstructor
@Slf4j
public class PlantController {
    private final PlantService plantService;
    private final int PAGE_SIZE = 10;

    @GetMapping()
    public Page<PlantDto> findAllByFilters(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false) String title) {
        int currentPage = page - 1;
        int sizeValue = PAGE_SIZE;
        return plantService.findAll(title, PageRequest.of(currentPage, sizeValue));
    }
}
