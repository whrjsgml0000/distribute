package user.whrjs.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import user.whrjs.demo.dto.ResultDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SearchControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void search() {
        List<ResultDTO> forObject = restTemplate.getForObject("/search?q=김치&option=blog", List.class);
        assertEquals(3, forObject.size());
    }
}