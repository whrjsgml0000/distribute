package user.whrjs.demo.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import user.whrjs.demo.config.Category;
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
    public String search(@RequestParam("q") String q, @RequestParam("option") String option, Model model)
            throws ExecutionException, InterruptedException, TimeoutException {
        Category category = Enum.valueOf(Category.class, option.toUpperCase());
        log.info("q = " + q + ", option = " + option);
        model.addAttribute("array", searchService.searchParallel(q, category));
        return "home";
    }
}
