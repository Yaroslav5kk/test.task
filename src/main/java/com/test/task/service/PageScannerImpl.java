package com.test.task.service;

import com.test.task.model.response.SearchResponse;
import com.test.task.service.i.PageScanner;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PageScannerImpl implements PageScanner {


    @Override
    public Set<String> extractAllLinks(final Document document) {
        return document.select("a[href]").stream()
                .map(element -> element.attributes().get("href"))
                .filter(s -> s.startsWith("https"))
                .collect(Collectors.toSet());
    }

}

























