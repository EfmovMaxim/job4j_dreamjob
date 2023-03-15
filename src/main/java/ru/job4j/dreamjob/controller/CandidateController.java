package ru.job4j.dreamjob.controller;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.CandidateRepository;
import ru.job4j.dreamjob.repository.MemoryCandidateRepository;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.SimpleCandidateService;

@Controller
@RequestMapping("/candidates")
@ThreadSafe
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("candidate", new Candidate(0, "", ""));
        return "candidates/create";
    }

    @PostMapping("/create")
    public String addCandidate(@ModelAttribute Candidate candidate, Model model) {
        candidateService.save(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        var candidateOptional = candidateService.findById(id);
        if (candidateOptional.isEmpty()) {
            model.addAttribute("message", "Не удалось найти кандидата по id");
            return "errors/404";
        }
        model.addAttribute("candidate", candidateOptional.get());
        return "candidates/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Candidate candidate, Model model) {
        var isUpdated = candidateService.update(candidate);
        if (!isUpdated) {
            model.addAttribute("message", "Не удалось обновить резюме");
            return "errors/404";
        }
        return "redirect:/candidates";
    };

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var isDeleted = candidateService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Не удалось удалить кандидата");
            return "errors/404";
        }
        return "redirect:/candidates";
    }
}