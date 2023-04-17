package ru.botanica.builders;

import ru.botanica.entities.PlantPhoto;
import ru.botanica.entities.Plant;

/**
 * Билдер для объектов типа Plant
 */

public final class PlantBuilder {
    private Long id;
    private String name;
    private String family;
    private String genus;
    private String description;
    private String shortDescription;
    private boolean isActive;
    private PlantPhoto photo;


    public PlantBuilder() {
    }

    public PlantBuilder withId(Long value) {
        this.id = value;
        return this;
    }

    public PlantBuilder withName(String value) {
        this.name = value;
        return this;
    }

    public PlantBuilder withFamily(String value) {
        this.family = value;
        return this;
    }

    public PlantBuilder withGenus(String value) {
        this.genus = value;
        return this;
    }
    public PlantBuilder withShortDescription(String value) {
        this.shortDescription = value;
        return this;
    }
    public PlantBuilder withDescription(String value) {
        this.description = value;
        return this;
    }
    public PlantBuilder withIsActive(boolean value) {
        this.isActive = value;
        return this;
    }

    public PlantBuilder withPhoto(PlantPhoto object) {
        this.photo = object;
        return this;
    }

    public Plant build() {
        Plant plant = new Plant();
        plant.setId(id);
        plant.setName(name);
        plant.setGenus(genus);
        plant.setFamily(family);
        plant.setShortDescription(shortDescription);
        plant.setDescription(description);
        plant.setActive(isActive);
        plant.setPhoto(photo);
        return plant;
    }
}
