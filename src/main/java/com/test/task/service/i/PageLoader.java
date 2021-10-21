package com.test.task.service.i;

import org.javatuples.Pair;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Set;

public interface PageLoader {
    List<Pair<Document, Boolean>> loadMany(Set<String> urls, int maxNumberDocumentToLoad);

    Pair<Document, Boolean> load(String url);
}
