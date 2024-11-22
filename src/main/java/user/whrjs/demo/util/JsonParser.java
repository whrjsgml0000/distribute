package user.whrjs.demo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import user.whrjs.demo.config.Category;
import user.whrjs.demo.config.Parsing;
import user.whrjs.demo.dto.DTO;

public class JsonParser {
    private ClassPathResource resource = new ClassPathResource("url.json");
    private JSONObject jsonObject;

    public JsonParser() {
        String jsonString = null;
        try {
            jsonString = new String(Files.readAllBytes(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jsonObject = new JSONObject(jsonString);
    }

    public List<DTO> getParsedData(Category requestCategory) {
        LinkedList<DTO> list = new LinkedList<>();
        JSONArray jsonArray = jsonObject.getJSONArray(requestCategory.getCategory());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            DTO dto = new DTO();
            dto.setName(json.getString(Parsing.NAME.getData()));
            dto.setUrl(json.getString(Parsing.URL.getData()));
            dto.setQuery(json.getString(Parsing.QUERY.getData()));
            dto.setCssSelector(Parsing.CSS_SELECTOR.getData());
            list.add(dto);
        }
        return list;
    }
}
