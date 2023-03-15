package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Optional;

public interface CityRepository {
    Collection<City> findAll();
    Optional<City> findById(int id);
}
