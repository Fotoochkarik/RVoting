package com.project.voting.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    private final Logger log = LoggerFactory.getLogger(RootController.class);

    @GetMapping({"/"})
    public String index() {
        log.info("index");
        return "index";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @GetMapping("/news")
    public String news() {
        return "news";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
