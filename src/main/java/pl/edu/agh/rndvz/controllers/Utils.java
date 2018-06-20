package pl.edu.agh.rndvz.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
}
