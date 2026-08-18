package com.example.backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.models.Message;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:4200/")
public class statusController {
    @GetMapping("")
    public Message test() {
        return new Message("OK");
    }
    
}
