package pl.edu.agh.rndvz.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.model.jsonMappings.JsonBool;
import pl.edu.agh.rndvz.model.jsonMappings.JsonID;
import pl.edu.agh.rndvz.model.jsonMappings.JsonLogin;

public class Utils {
    public static ResponseEntity noChatFound() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("No chat found for given users ids");
    }

    public static ResponseEntity noPathFound() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("No messages found");
    }

    public static ResponseEntity noUserFoundforGivenLogin() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("No user with that login");
    }

    public static ResponseEntity wrapWithResponseEntity(Object object) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(object);
    }

    public static JsonID toJson(Long id) {
        return new JsonID(id);
    }

    public static JsonLogin toJson(String login) {
        return new JsonLogin(login);
    }

    public static JsonBool toJson(boolean value) {
        return new JsonBool(value);
    }

}
