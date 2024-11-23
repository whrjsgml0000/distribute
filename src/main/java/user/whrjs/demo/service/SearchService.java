package user.whrjs.demo.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import user.whrjs.demo.config.Category;
import user.whrjs.demo.dto.DTO;
import user.whrjs.demo.util.JsonParser;

@Service
public class SearchService {
    private final JsonParser jsonParser = new JsonParser();

    public String searchParallel(String q, Category category) {
        List<DTO> parsedData = jsonParser.getParsedData(category);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("USER_AGENT",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:132.0) Gecko/20100101 Firefox/132.0");
        firefoxOptions.addArguments("--headless");

        for (DTO d : parsedData) {
            String url = getUriWithQuery(q, d);
            searchParallel(firefoxOptions, url, d);
        }

        return null;
    }

    private void searchParallel(FirefoxOptions firefoxOptions, String url, DTO d) {
        new Thread(() -> {
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

                List<WebElement> elements = driver.findElements(By.cssSelector(d.getCssSelector()));
                System.out.println(elements.size());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } finally {
                if (driver != null) {
                    driver.quit();
                }
            }
        }).start();
    }

    private String getUriWithQuery(String q, DTO d) {
        return d.getUrl() + "?" + d.getQuery() + q;
    }
}
