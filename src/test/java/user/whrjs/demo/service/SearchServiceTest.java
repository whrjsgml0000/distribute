package user.whrjs.demo.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import user.whrjs.demo.config.Category;
import user.whrjs.demo.util.JsonParser;

class SearchServiceTest {
    static SearchService searchService;
    static JsonParser jsonParser;

    @BeforeAll
    static void beforeAll() {
        jsonParser = new JsonParser();
        searchService = new SearchService();
    }

    @Test
    @DisplayName("검색 기능 확인")
    void search() throws ExecutionException, InterruptedException, TimeoutException {
        searchService.searchParallel("김치", Category.BLOG);
    }
}