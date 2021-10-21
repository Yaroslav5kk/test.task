package com.test.task.service.i;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Set;

public interface PageScanner {
    Set<String> extractAllLinks(Document document);
}
