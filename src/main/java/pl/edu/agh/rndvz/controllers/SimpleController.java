package pl.edu.agh.rndvz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.persistence.UserRepository;


@RestController
public class SimpleController {


    @Autowired
    public SimpleController(){ }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public @ResponseBody
    String accept() {
        return "{ \"a\": \"d\" }";
    }
}
