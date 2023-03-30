package ru.botanica.entities.plants;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// TODO: После привязки к БД, скрипт инициализации БД начал выдавать две ошибки о том, что не может изменить данную таблицу, а именно:
//  Error executing DDL "drop table if exists plants" via JDBC Statement и
//  Error executing DDL "create table plants (plant_id bigint not null auto_increment, description varchar(255) not null, family varchar(128) not null,
//  genus varchar(128) not null, is_active bit not null, name varchar(128) not null, short_description varchar(128) not null,
//  primary key (plant_id)) engine=InnoDB" via JDBC Statement . Нагуглить решение или исправить сам не смог.
//  Не смотря на ошибки, работоспособность сохраняется
@Entity
@Getter
@Setter
@Table(schema = "simple_botanica", name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;

    @Column(nullable = false, length = 128, name = "name")
    private String name;

    @Column(nullable = false, length = 128, name = "family")
    private String family;

    @Column(nullable = false, length = 128, name = "genus")
    private String genus;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, length = 128, name = "short_description")
    private String shortDescription;

    @Column(nullable = false, name = "is_active")
    private boolean isActive;

    private String filePath;

    public Plant() {
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", genus='" + genus + '\'' +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", isActive=" + isActive + '\'' +
                ", filePath=" + filePath +
                '}';
    }
}
