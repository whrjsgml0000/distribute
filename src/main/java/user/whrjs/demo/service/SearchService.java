package user.whrjs.demo.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
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

    public List<ResultDTO> searchParallel(String q, Category category)
            throws ExecutionException, InterruptedException, java.util.concurrent.TimeoutException {
        List<DTO> parsedData = jsonParser.getParsedData(category);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        // USER-AGENT 값 수정.
        firefoxOptions.addPreference("USER_AGENT",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:132.0) Gecko/20100101 Firefox/132.0");
        firefoxOptions.addArguments("--headless");
        LinkedList<ResultDTO> resultDTOS = new LinkedList<>();
        LinkedList<Future<ResultDTO>> futures = new LinkedList<>();
        for (DTO d : parsedData) {
            String url = getUriWithQuery(q, d);
            log.info(url);
            futures.add(threadSearch(firefoxOptions, url, d));
        }

        for(Future<ResultDTO> future : futures){
            resultDTOS.add(future.get());
        }

        log.debug("searchParallel close");
        return resultDTOS;
    }

    private Future<ResultDTO> threadSearch(FirefoxOptions firefoxOptions, String url, DTO d) {
        CompletableFuture<ResultDTO> future = new CompletableFuture<>();

        // 비동기 작업 시작
        CompletableFuture.runAsync(() -> {
            ResultDTO resultDTO = new ResultDTO();
            RemoteWebDriver driver = null;

            try {
                log.info("Thread " + (++thread) + " is running now");

                // RemoteWebDriver 설정
                driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
                driver.get(url);

                // 페이지 로드 후 알림 닫기 처리
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
                try {
                    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                    alert.dismiss(); // 알림 닫기
                } catch (TimeoutException e) {
                    // 알림이 없으면 예외 처리
                    log.info("No alert present");
                }

                // JavaScript 실행 (페이지 스크롤)
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0, 1000)");

                // 링크와 제목 추출
                List<String> links = driver.findElements(By.cssSelector(d.getCssSelector())).stream()
                        .limit(5)
                        .map(it -> it.getAttribute("href"))
                        .toList();
                List<String> titles = driver.findElements(By.cssSelector(d.getTitle())).stream()
                        .limit(5)
                        .map(WebElement::getText)
                        .toList();

                // 결과 저장
                resultDTO.setName(d.getName());
                resultDTO.setLinks(links);
                resultDTO.setTitles(titles);

                log.info("Found " + links.size() + " links for URL: " + url);

                // CompletableFuture 완료 처리
                future.complete(resultDTO);

            } catch (Exception e) {
                log.error("Error while fetching data", e);
                future.completeExceptionally(e);
            } finally {
                // Driver 종료
                if (driver != null) {
                    driver.quit();
                }
                log.info("Thread " + (--thread) + " is finished");
            }
        });

        return future;
    }

    private String getUriWithQuery(String q, DTO d) {
        String url = d.getUrl() + "?" + d.getQuery() + "=" + q;
        log.info("URL = " + url);
        return url;
    }
}
