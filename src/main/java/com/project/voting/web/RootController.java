package com.project.voting.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    Logger log = LoggerFactory.getLogger(RootController.class);

    @GetMapping({"/"})
    public String index() {
        log.info("index");
        return "index";
    }
}
