package com.project.voting.web;

import com.project.voting.model.Role;
import com.project.voting.model.User;
import com.project.voting.repository.UserRepository;
import com.project.voting.util.validation.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Objects;

@Controller
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/users")
    public String getAll(Model model) {
        log.info("getAll");
        model.addAttribute("users", repository.findAll());
        return "users";
    }

    @GetMapping("/registration")
    public String registration() {
        log.info("registration get");
        return "registration";
    }

    @PostMapping("/registration")
    public String create(@RequestParam String username,
                         @RequestParam String password) {
//        User user = new User(null, username, bCryptPasswordEncoder.encode(password), true, Collections.singleton(Role.ADMIN));
//        log.info("create {}", user);
//        ValidationUtil.checkNew(user);
//        repository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        repository.deleteById(getId(request));
        log.info("delete User {}", getId(request));
        return "redirect:/users";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
