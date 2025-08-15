package com.cfn.main.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingControllers {

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello form our API");
    }


    @GetMapping("/good-bye")
    public ResponseEntity<String> sayGoodBye(){
        return ResponseEntity.ok("Good Bye from our API");
    }

}
