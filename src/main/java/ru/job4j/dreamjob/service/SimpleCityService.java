package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.repository.MemoryCityRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleCityService implements CityService{
    private final MemoryCityRepository memoryCityRepository;

    public SimpleCityService() {
        this.memoryCityRepository = new MemoryCityRepository();
    }

    @Override
    public Collection<City> findAll() {
        return memoryCityRepository.findAll();
    }

    public Optional<City> findById(int id) {
        return memoryCityRepository.findById(id);
    }
}
