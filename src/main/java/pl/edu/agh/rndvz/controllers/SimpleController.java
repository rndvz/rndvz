package pl.edu.agh.rndvz.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SimpleController {

    @GetMapping(value = "/hello")
    public String accept() {
        return "hello there";
    }
}
