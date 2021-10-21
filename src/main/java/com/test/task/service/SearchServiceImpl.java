package com.test.task.service;

import com.test.task.model.response.SearchResponse;
import com.test.task.repository.SearchResultRepository;
import com.test.task.service.i.PageLoader;
import com.test.task.service.i.PageScanner;
import com.test.task.service.i.SearchService;
import org.javatuples.Pair;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class SearchServiceImpl implements SearchService {

    private final PageLoader pageLoader;
    private final PageScanner pageScanner;
    private final SearchResultRepository searchResultRepository;

    public SearchServiceImpl(
            final PageLoader pageLoader,
            final PageScanner pageScanner,
            final SearchResultRepository searchResultRepository
    ) {
        this.pageLoader = pageLoader;
        this.pageScanner = pageScanner;
        this.searchResultRepository = searchResultRepository;
    }

    @Override
    public List<SearchResponse> search(
            final String url,
            final String textToSearch,
            final int maxNumberUrlToSearch
    ) {
        int notSearchedUrlNumber = maxNumberUrlToSearch;
        final Pair<Document, Boolean> rootDocumentAndLoadedResult = pageLoader.load(url);
        if (!rootDocumentAndLoadedResult.getValue1()) {
            return Arrays.asList(SearchResponse.notLoad(url));
        }
        final Queue<Document> queue = new LinkedList<>();
        queue.add(rootDocumentAndLoadedResult.getValue0());
        Document currentDocument = queue.poll();
        while (currentDocument != null && notSearchedUrlNumber > 0) {
            final List<Pair<Document, Boolean>> childrenDocumentsAndLoadedResult = pageLoader.
                    loadMany(pageScanner.extractAllLinks(rootDocumentAndLoadedResult.getValue0()), notSearchedUrlNumber);
            for (int i = 0; i < childrenDocumentsAndLoadedResult.size() && notSearchedUrlNumber > 0; i++, notSearchedUrlNumber--) {
                final Document itDocument = childrenDocumentsAndLoadedResult.get(i).getValue0();
                if (!childrenDocumentsAndLoadedResult.get(i).getValue1()) {
                    searchResultRepository.add(SearchResponse.notLoad(itDocument.baseUri()));
                    continue;
                }
                searchResultRepository.add(searchInOneDocument(itDocument, textToSearch));
                queue.add(itDocument);
            }
            currentDocument = queue.poll();
        }

        return searchResultRepository.getResult();
    }

    private SearchResponse searchInOneDocument(
            final Document document,
            final String textToSearch
    ) {
        final String uri = document.baseUri();
        if (document.text().contains(textToSearch)) {
            return SearchResponse.found(uri);
        }
        return SearchResponse.notFound(uri);
    }
}



























