package user.whrjs.demo.controller;

import java.util.concurrent.ExecutionException;
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

    /**
     * 검색을 진행한 후 메인 페이지의 하단에 그 결과를 반환한다. 서비스 로직 안에 Future 를 이용한 비동기적인 실행 과정이 있으므로 그 결과를 받아오는데 걸리는 시간이 존재한다. 따라서 검색 버튼을
     * 누른 뒤 즉시 결과를 반환받지 못하고 5초 정도의 시간이 지난 뒤에 결과를 받을 수 있다.
     *
     * @param q      검색 키워드
     * @param option 검색 옵션 ex) blog, news, shopping... 추가를 하려면 resources/templates/home.html, resources/url.json 파일을
     *               수정하면 된다.
     * @param model  매개변수 아님
     * @return       home.html 에 검색 결과와 함께 반환
     */
    @GetMapping("/search")
    public String search(@RequestParam("q") String q, @RequestParam("option") String option, Model model)
            throws ExecutionException, InterruptedException {
        // 입력으로 들어온 값을 로그로 찍음.
        log.info("q = " + q + ", option = " + option);
        // 옵션으로 들어온 값을 열거형 자료로 변환함.
        Category category = Enum.valueOf(Category.class, option.toUpperCase());
        model.addAttribute("array", searchService.searchParallel(q, category));
        return "home";
    }
}
