package user.whrjs.demo.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import user.whrjs.demo.config.Category;
import user.whrjs.demo.config.Parsing;

class JsonParserTest {

    @Test
    @DisplayName(".json 파일 파싱 테스트")
    void getParsedData() throws IOException {
        JsonParser jsonParser = new JsonParser();
        String parsedData = jsonParser.getParsedData(Category.BLOG);
        assertEquals("https://www.tistory.com/search", parsedData);
    }
}