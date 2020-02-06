package com.example.booksapi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchBooksResultController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/searchbooks")
    public SearchBooksResult searchBooks(@RequestParam(value = "search", defaultValue = "DEFAULT") String search) {
        List<Book> b = new ArrayList<>();
        return new SearchBooksResult(
                counter.incrementAndGet(),
                search);
    }
}