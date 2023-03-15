package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private AtomicInteger nextId = new AtomicInteger();

    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();
    CityRepository cityRepository = new MemoryCityRepository();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "desc 1", cityRepository.findById(1).get(), true, 0));
        save(new Vacancy(0, "Junior Java Developer", "desc 2", cityRepository.findById(2).get(), true, 0 ));
        save(new Vacancy(0, "Junior+ Java Developer", "desc 3", cityRepository.findById(3).get(), true, 0));
        save(new Vacancy(0, "Middle Java Developer", "desc 4", cityRepository.findById(1).get(), true, 0));
        save(new Vacancy(0, "Middle+ Java Developer", "desc 5", cityRepository.findById(2).get(), true, 0));
        save(new Vacancy(0, "Senior Java Developer", "desc 6", cityRepository.findById(3).get(), true, 0));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.getAndIncrement());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) ->
                new Vacancy(oldVacancy.getId(), vacancy.getTitle(),
                        vacancy.getDescription(), vacancy.getCity(), vacancy.isVisible(), vacancy.getFileId())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}
