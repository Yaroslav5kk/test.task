package com.test.task.repository;

import com.test.task.model.response.SearchResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
public class SearchResultRepositoryImpl implements SearchResultRepository {

    private final List<SearchResponse> result = new ArrayList<>();

    @Override
    public void add(final SearchResponse searchResponse) {
        result.add(searchResponse);
    }

    @Override
    public List<SearchResponse> getResult() {
        return result;
    }
}
