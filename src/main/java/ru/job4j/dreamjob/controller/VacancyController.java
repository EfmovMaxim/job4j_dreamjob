package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.repository.MemoryVacancyRepository;
import ru.job4j.dreamjob.repository.VacancyRepository;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.SimpleVacancyService;
import ru.job4j.dreamjob.service.VacancyService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/vacancies") /* Работать с кандидатами будем по URI /vacancies/** */
@ThreadSafe
public class VacancyController {

    private final VacancyService vacancyService;
    private final CityService cityService;

    public VacancyController(VacancyService vacancyService, CityService cityService) {
        this.vacancyService = vacancyService;
        this.cityService = cityService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("vacancies", vacancyService.findAll());
        return "vacancies/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("vacancy", new Vacancy("", "", null, false));
        model.addAttribute("cities", cityService.findAll());
        return "vacancies/create";
    }
    /*
    @PostMapping("/create")
    public String create(HttpServletRequest request) {
        var title = request.getParameter("title");
        var description = request.getParameter("description");
        var visible = Boolean.getBoolean(request.getParameter("visible"));
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        vacancyService.save(new Vacancy(0, title, description, cityService.findById(cityId).get(), visible));
        return "redirect:/vacancies";
    }
    */

    @PostMapping("/create")
    public String create(@ModelAttribute Vacancy vacancy, @RequestParam MultipartFile file, Model model) {
        try {
            vacancy.setCity(cityService.findById(vacancy.getCity().getId()).get());
            vacancyService.save(vacancy, new FileDto(file.getOriginalFilename(), file.getBytes()));
            return "redirect:/vacancies";
        } catch (IOException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }


    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var vacancyOptional = vacancyService.findById(id);
        if (vacancyOptional.isEmpty()) {
            model.addAttribute("message", "Не найдена вакансия с указанным id");
            return "errors/404";
        }
        model.addAttribute("vacancy", vacancyOptional.get());
        model.addAttribute("cities", cityService.findAll());
        return "vacancies/one";

    }

    @PostMapping("/update")
    public String update(@ModelAttribute Vacancy vacancy, @RequestParam MultipartFile file, Model model)   {
        try {
            vacancy.setCity(cityService.findById(vacancy.getCity().getId()).get());
            var isUpdated = vacancyService.update(vacancy, new FileDto(file.getOriginalFilename(), file.getBytes()));
            if (!isUpdated) {
                model.addAttribute("message", "Вакансия с указанным id не найдена");
                return "errors/404";
            }
            return "redirect:/vacancies";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model)   {
        var isDeleted = vacancyService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Вакансия с указанным id не найдена");
            return "errors/404";
        }
        return "redirect:/vacancies";
    }

}