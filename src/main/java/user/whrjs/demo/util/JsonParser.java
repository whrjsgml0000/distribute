package user.whrjs.demo.util;

import java.io.IOException;
import java.nio.file.Files;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import user.whrjs.demo.config.Category;

public class JsonParser {
    private ClassPathResource resource = new ClassPathResource("url.json");

    public JSONArray getParsedData(Category requestCategory) throws IOException {
        String jsonString = new String(Files.readAllBytes(resource.getFile().toPath()));
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getJSONArray(requestCategory.getCategory());
    }
}
