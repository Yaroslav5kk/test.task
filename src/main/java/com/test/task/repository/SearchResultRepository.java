package com.test.task.repository;

import com.test.task.model.response.SearchResponse;

import java.util.List;

public interface SearchResultRepository {
    void add(SearchResponse searchResponse);

    List<SearchResponse> getResult();
}
