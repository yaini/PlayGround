package com.yaini.controller;

import com.yaini.model.Simple;
import com.yaini.service.SimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SimpleController {

    private final SimpleService service;

    @GetMapping("/simple")
    public Simple simple() {
        return service.simpleJpaTest();
    }
}
