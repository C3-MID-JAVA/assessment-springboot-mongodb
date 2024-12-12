package com.kgalarza.bancointegrador.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/ping")
public class PingController {

    @GetMapping
    public ResponseEntity<?> ping(){
        return ResponseEntity.ok("On Line");
    }
}
