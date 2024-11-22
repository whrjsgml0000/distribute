package user.whrjs.demo.util;

import java.io.IOException;
import java.nio.file.Files;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import user.whrjs.demo.config.Category;

public class JsonParser {
    private ClassPathResource resource = new ClassPathResource("url.json");

    public String getParsedData(Category requestCategory) throws IOException {
        String jsonString = new String(Files.readAllBytes(resource.getFile().toPath()));
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject category = jsonObject.getJSONObject(requestCategory.getCategory());
        String string = category.getJSONObject("tistory").getString("url");
        return string;
    }
}
