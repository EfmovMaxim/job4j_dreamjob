package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

public class Candidate {
    private static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "description", "description",
            "creation_date", "creationDate",
            "file_id", "fileId");

    private int id;
    private String name;
    String description;
    LocalDateTime creationDate;
    int fileId;

    public Candidate(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public Candidate(int id, String name, String description, int fileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.fileId = fileId;
    }

    public Candidate(String name, String description) {
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public Candidate(String name, String description, int fileId) {
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.fileId = fileId;
    }

    public Candidate() {
        this.creationDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
