package user.whrjs.demo.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import user.whrjs.demo.config.Category;
import user.whrjs.demo.dto.DTO;
import user.whrjs.demo.dto.ResultDTO;
import user.whrjs.demo.util.JsonParser;

@Service
public class SearchService {
    private static final Logger log = LogManager.getLogger(SearchService.class);
    private static int thread = 0;
    private final JsonParser jsonParser = new JsonParser();

    public List<ResultDTO> searchParallel(String q, Category category) {
        List<DTO> parsedData = jsonParser.getParsedData(category);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        // USER-AGENT 값 수정.
        firefoxOptions.addPreference("USER_AGENT",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:132.0) Gecko/20100101 Firefox/132.0");
        firefoxOptions.addArguments("--headless");
        LinkedList<ResultDTO> resultDTOS = new LinkedList<>();

        for (DTO d : parsedData) {
            String url = getUriWithQuery(q, d);
            log.info(url);
            ResultDTO resultDTO = threadSearch(firefoxOptions, url, d);
            resultDTOS.add(resultDTO);
        }
        log.debug("searchParallel close");
        return resultDTOS;
    }

    private ResultDTO threadSearch(FirefoxOptions firefoxOptions, String url, DTO d) {
        ResultDTO resultDTO = new ResultDTO();
        new Thread(() -> {
            log.info("Thread " + (++thread) + "are(is) Running Now");
            RemoteWebDriver driver = null;
            try {
                driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
                driver.get(url);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
                try {
                    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                    alert.dismiss(); // 알림 닫기
                } catch (TimeoutException e) {
                    // 알림이 나타나지 않으면 예외 처리
                }
                JavascriptExecutor js = (JavascriptExecutor) driver;
                // 윈도우 창을 아래로 1000만큼 내림. 내려야지 게시글이 더 많이 나오도록 동적으로 만들어진 웹에 대비.
                js.executeScript("window.scrollBy(0, 1000)");

                List<String> links = driver.findElements(By.cssSelector(d.getCssSelector())).stream()
                        .map(it -> it.getAttribute("href")).toList();
                List<String> titles = driver.findElements(By.cssSelector(d.getTitle())).stream()
                        .map(WebElement::getText)
                        .toList();

                resultDTO.setName(d.getName());
                resultDTO.setLinks(links);
                resultDTO.setTitles(titles);

                log.info("TITLE search result : " + links.size());
                log.info("URL = " + url + " search result : " + links.size());

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } finally {
                if (driver != null) {
                    driver.quit();
                }
                log.info("Thread " + --thread + " are(is) Running Now");
            }
        }).start();
        return resultDTO;
    }

    private String getUriWithQuery(String q, DTO d) {
        String url = d.getUrl() + "?" + d.getQuery() + "=" + q;
        log.info("URL = " + url);
        return url;
    }
}
