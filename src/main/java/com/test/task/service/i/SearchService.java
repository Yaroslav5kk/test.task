package com.test.task.service.i;

import com.test.task.model.response.SearchResponse;

import java.util.List;

public interface SearchService {

    List<SearchResponse> search(
            String url,
            String textToSearch,
            int maxNumberUrlToSearch
    );
}
