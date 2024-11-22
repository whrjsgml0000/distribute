package user.whrjs.demo.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import user.whrjs.demo.config.Category;
import user.whrjs.demo.config.Parsing;
import user.whrjs.demo.dto.DTO;

class JsonParserTest {

    @Test
    @DisplayName(".json 파일 파싱 테스트")
    void getParsedData() throws IOException {
        JsonParser jsonParser = new JsonParser();
        List<DTO> parsedData = jsonParser.getParsedData(Category.BLOG);
        DTO dto = parsedData.get(0);
        assertEquals("tistory", dto.getName());
    }

    @Test
    @DisplayName("json의 값을 enum으로 교체")
    void transform(){
        Category category = Enum.valueOf(Category.class, "blog".toUpperCase());
        assertEquals(Category.BLOG, category);
    }
}