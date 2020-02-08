package com.example.booksapi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchBooksResultController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/searchbooks")
    public SearchBooksResult searchBooks(@RequestParam(value = "search", defaultValue = "DEFAULT") String search) {
        return new SearchBooksResult(
                counter.incrementAndGet(),
                search);
    }

    @GetMapping("/searchbooksregex")
    public SearchBooksResultRegex searchBooksRegex(@RequestParam(value = "search", defaultValue = "DEFAULT") String search) {
        return new SearchBooksResultRegex(
                counter.incrementAndGet(),
                search);
    }
}