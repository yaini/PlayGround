package com.yaini.controller;

import com.yaini.model.Simple;
import com.yaini.service.SimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/simple")
@RequiredArgsConstructor
public class SimpleController {

    private final SimpleService service;

    @GetMapping
    public String hello() {
        return "hello";
    }

    @GetMapping("{id}")
    public Simple read(@PathVariable final Long id) {
        return service.findOne(id);
    }

    @PostMapping
    public void add() {
        service.create();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable final Long id) {
        service.delete(id);
    }
}
