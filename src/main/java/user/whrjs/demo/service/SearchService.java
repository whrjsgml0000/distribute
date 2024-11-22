package user.whrjs.demo.service;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SearchService {
    public String search(String q) throws URISyntaxException {
        URI uri = new URI("https://makeaplayground.tistory.com/187");
        RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.GET, uri);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);

        return exchange.getBody();
    }
}
