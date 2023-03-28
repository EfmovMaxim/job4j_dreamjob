package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.repository.MemoryVacancyRepository;
import ru.job4j.dreamjob.repository.VacancyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleVacancyService implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final FileService fileService;
    private final CityService cityService;

    public SimpleVacancyService(VacancyRepository vacancyRepository, FileService fileService, CityService cityService) {
        this.vacancyRepository = vacancyRepository;
        this.fileService = fileService;
        this.cityService = cityService;
    }

    @Override
    public Vacancy save(Vacancy vacancy, FileDto image) {
        saveNewFile(vacancy, image);
        return vacancyRepository.save(vacancy);
    }

    private void saveNewFile(Vacancy vacancy, FileDto image) {
        var file = fileService.save(image);
        vacancy.setFileId(file.getId());
    }

    @Override
    public boolean deleteById(int id) {
        var vacancyOptional = findById(id);

        if (vacancyOptional.isEmpty()) {
            return false;
        }
        var isDeleted = vacancyRepository.deleteById(id);

        if (isDeleted) {
            fileService.deleteById(vacancyOptional.get().getFileId());
        }

        return isDeleted;
    }

    @Override
    public boolean update(Vacancy vacancy, FileDto image) {
        var isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return vacancyRepository.update(vacancy);
        }
        /* если передан новый не пустой файл, то старый удаляем, а новый сохраняем */
        var oldFileId = vacancy.getFileId();
        saveNewFile(vacancy, image);
        var isUpdated = vacancyRepository.update(vacancy);
        fileService.deleteById(oldFileId);
        return isUpdated;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        var vacancyOptional = vacancyRepository.findById(id);

        if (!vacancyOptional.isEmpty()) {
            var vacancy = vacancyOptional.get();
            vacancy.setCity(cityService.findById(vacancy.getCityId()).get());
        }

        return vacancyOptional;
    }

    @Override
    public Collection<Vacancy> findAll() {
        var vacancies = vacancyRepository.findAll();
        vacancies.forEach(v -> v.setCity(cityService.findById(v.getCityId()).get()));
        return vacancies;
    }

}