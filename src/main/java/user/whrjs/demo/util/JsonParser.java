package user.whrjs.demo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import user.whrjs.demo.config.Category;
import user.whrjs.demo.config.Parsing;
import user.whrjs.demo.dto.DTO;

public class JsonParser {
    public static final Logger log = LogManager.getLogger(JsonParser.class);
    private final ClassPathResource resource = new ClassPathResource("url.json");
    private final JSONObject jsonObject;

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
            DTO dto = getDto(json);
            printLogDTO(dto);
            list.add(dto);
        }
        return list;
    }

    private void printLogDTO(DTO dto) {
        log.info("name : " + dto.getName());
        log.info("url : " + dto.getUrl());
        log.info("query : " + dto.getQuery());
        log.info("cssSelector : " + dto.getCssSelector());
        log.info("title : " + dto.getTitle());
        log.info("absolute : " + dto.getAbsolute());
        log.info("prefix : " + dto.getPrefix());
    }

    private DTO getDto(JSONObject json) {
        DTO dto = new DTO();
        dto.setName(json.getString(Parsing.NAME.getData()));
        dto.setUrl(json.getString(Parsing.URL.getData()));
        dto.setQuery(json.getString(Parsing.QUERY.getData()));
        dto.setCssSelector(json.getString(Parsing.CSS_SELECTOR.getData()));
        dto.setTitle(json.getString(Parsing.TITLE.getData()));
        dto.setAbsolute(json.getBoolean(Parsing.ABSOLUTE.getData()));
        dto.setPrefix(json.optString(Parsing.PREFIX.getData()));
        return dto;
    }
}
