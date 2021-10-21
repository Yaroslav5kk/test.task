package com.test.task.service;

import com.test.task.service.i.PageLoader;
import org.javatuples.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class PageLoaderImpl implements PageLoader {

    private final ExecutorService executorService;

    public PageLoaderImpl(
            final ExecutorService executorService
    ) {
        this.executorService = executorService;
    }

    @Override
    public List<Pair<Document, Boolean>> loadMany(final Set<String> urls, final int maxNumberDocumentToLoad) {
        int notLoadedUrlNumber = maxNumberDocumentToLoad;
        final List<Pair<Document, Boolean>> documentAndLoadedResult = new ArrayList<>();
        final List<Future<Pair<Document, Boolean>>> documentAndLoadedResultFutures = new ArrayList<>();
        for (final String url : urls) {
            if (--notLoadedUrlNumber == 0) {
                break;
            }
            documentAndLoadedResultFutures.add(executorService.submit(() -> jsoupLoadPage(url)));
        }
        for (final Future<Pair<Document, Boolean>> itFuture : documentAndLoadedResultFutures) {
            try {
                final Pair<Document, Boolean> itDocumentLoadedResult = itFuture.get();
                documentAndLoadedResult.add(itDocumentLoadedResult);
                // remove when was success completed future.get()
                urls.remove(itDocumentLoadedResult.getValue0().baseUri());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        urls.forEach(url -> documentAndLoadedResult.add(Pair.with(new Document(url), false)));
        return documentAndLoadedResult;
    }

    @Override
    public Pair<Document, Boolean> load(final String url) {
        try {
            return executorService.submit(() -> jsoupLoadPage(url)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Pair.with(new Document(url), false);
        }
    }

    private Pair<Document, Boolean> jsoupLoadPage(final String url) {
        try {
            return Pair.with(Jsoup.connect(url).get(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return Pair.with(new Document(url), false);
        }
    }
}


























