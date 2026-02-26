package com.tongbora.ebanking.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class PipeController {

    @GetMapping("/pipeline")
    public ResponseEntity<Map<String, Object>> fallback() {
        Map<String, Object> body = Map.of(
                "service", "pipeline",
                "status", "DOWN",
                "message", "Hello, the Pipeline Service is currently unavailable. Please try again later."
        );

        return ResponseEntity.status(503).body(body);
    }
}

