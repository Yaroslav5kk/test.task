package com.test.task.model.response;

import com.test.task.model.type.SearchStatus;

public class SearchResponse {

    private String url;
    private SearchStatus status;
    private String message;

    public SearchResponse() {
    }

    public SearchResponse(String url, SearchStatus status, String message) {
        this.url = url;
        this.status = status;
        this.message = message;
    }

    public static SearchResponse found(final String url) {
        return new SearchResponse(url, SearchStatus.FOUND, "OK");
    }

    public static SearchResponse notFound(final String url) {
        return new SearchResponse(url, SearchStatus.NOT_FOUND, "Not found");
    }

    public static SearchResponse notLoad(final String url) {
        return new SearchResponse(url, SearchStatus.ERROR, "could not load web page");
    }

    public static SearchResponse error(final String url, final String errorMessage) {
        return new SearchResponse(url, SearchStatus.ERROR, errorMessage);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SearchStatus getStatus() {
        return status;
    }

    public void setStatus(SearchStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}





















