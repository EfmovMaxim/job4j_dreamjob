package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Vacancy implements Serializable {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "title", "title",
            "description", "description",
            "creation_date", "creationDate",
            "visible", "visible",
            "city_id", "cityId",
            "file_id", "fileId"
    );
    private int id;
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private boolean visible;
    private City city;
    private int cityId;
    private int fileId;


    public Vacancy(int id, String title, String description, City city, boolean visible, int fileId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.visible = visible;
        this.city = city;
        this.creationDate = LocalDateTime.now();
        this.fileId = fileId;
        this.cityId = city.getId();
    }

    public Vacancy(int id, String title, String description, int cityId, boolean visible, int fileId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.visible = visible;
        this.cityId = cityId;
        this.creationDate = LocalDateTime.now();
        this.fileId = fileId;
    }


    public Vacancy(String title, String description, City city, boolean visible) {
        this.title = title;
        this.description = description;
        this.visible = visible;
        this.city = city;
        this.creationDate = LocalDateTime.now();
    }

    public Vacancy(String title, String description, int cityId, boolean visible) {
        this.title = title;
        this.description = description;
        this.visible = visible;
        this.cityId = cityId;
        this.creationDate = LocalDateTime.now();
    }


    public Vacancy() {
         this.creationDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}