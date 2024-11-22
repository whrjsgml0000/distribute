package user.whrjs.demo.controller;

import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import user.whrjs.demo.service.SearchService;

@Controller
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String q, Model model) throws URISyntaxException {
        String search = searchService.search(q);
        model.addAttribute("q", search);
        return "home";
    }
}
