package user.whrjs.demo.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import user.whrjs.demo.config.Category;
import user.whrjs.demo.dto.ResultDTO;
import user.whrjs.demo.service.SearchService;

@Controller
public class SearchController {
    public static final Logger log = LogManager.getLogger(SearchController.class);
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<ResultDTO> search(@RequestParam("q") String q, @RequestParam("option") String option)
            throws ExecutionException, InterruptedException, TimeoutException {
        Category category = Enum.valueOf(Category.class, option.toUpperCase());
        log.info("q = " + q + ", option = " + option);
        return searchService.searchParallel(q, category);
    }
}
