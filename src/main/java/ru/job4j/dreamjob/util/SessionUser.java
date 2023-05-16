package ru.job4j.dreamjob.util;

import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

public final class SessionUser {
    private SessionUser() {
    }

    public static void initUser(Model model, HttpSession session) {

        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}
