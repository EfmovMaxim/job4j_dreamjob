package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

import static ru.job4j.dreamjob.util.SessionUser.initUser;

@Controller
@ThreadSafe
public class IndexController {

    @GetMapping({"/index", "/"})
    public String getIndex(Model model, HttpSession session) {
        return "index";
    }
}