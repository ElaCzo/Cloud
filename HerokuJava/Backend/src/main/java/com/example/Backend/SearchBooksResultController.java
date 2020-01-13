package com.example.Backend;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchBooksResultController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/searchbooks")
    public SearchBooksResult greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new SearchBooksResult(counter.incrementAndGet(), String.format(template, name));
    }
}