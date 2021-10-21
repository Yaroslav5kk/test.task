package com.test.task.controller;

import com.test.task.model.response.SearchResponse;
import com.test.task.service.i.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(
            final SearchService searchService
    ) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<List<SearchResponse>> search(
            @RequestParam final String startUrl,
            @RequestParam final String textToSearch,
            @RequestParam final int maxNumberUrlToSearch
    ) {
        return ResponseEntity.ok(searchService.search(
                startUrl,
                textToSearch,
                maxNumberUrlToSearch
        ));
    }
}

























