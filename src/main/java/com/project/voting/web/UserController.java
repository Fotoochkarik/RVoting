package com.project.voting.web;

import com.project.voting.model.User;
import com.project.voting.repository.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    CrudUserRepository repository;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", repository.findAll());
        return "users";
    }

    @GetMapping("/user")
    public String save() {
        return "user";
    }

    @PostMapping("/user")
    public String create(@RequestParam String username,
                         @RequestParam String password) {
        User user = new User(username, password);
        repository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        repository.deleteById(getId(request));
        return "redirect:/users";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
