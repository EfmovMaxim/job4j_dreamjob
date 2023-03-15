package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.City;

import java.util.Collection;
import java.util.Optional;

public interface CityService {
    Collection<City> findAll();
    Optional<City> findById(int id);
}
